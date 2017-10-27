Hadoop on Docker
================

### Run
```bash
# To start spark containers
docker-compose up -d spark-master
docker-compose up -d spark-worker
docker-compose up -d zeppelin
# To start nifi containers
docker-compose up -d kafka nifi
    # scaling nifi containers
    docker-compose up -d --scale  nifi=2
    # see running containers & find the exposed ports
    docker-compose ps
    # connect(ssh) to a service and run a command
    docker-compose  exec  nifi bin/nifi.sh status
    docker-compose  exec  nifi bash
# To start Hive containers 
docker-compose up -d namenode hive-metastore-postgresql
docker-compose up -d datanode hive-metastore
docker-compose up -d hive-server
```

### Test

#### Spark and zeppelin
* http://localhost:8080/ (Spark Master)
* http://localhost:8088/ (Hue HDFS Filebrowser)
* http://localhost/ (Zeppelin) 

> To load the data to your cluster simply do:

```bash
docker-compose exec namenode bash
cd /opt/sansa-examples
hdfs dfs -copyFromLocal data /data
hdfs dfs -ls /data
```

#### NiFi and Kafka

* http://localhost:5080/ (NiFi)
```bash
# open shell to start producer 
docker-compose  exec kafka bash
# list topics
kafka-topics --list --zookeeper zookeeper:2181
# send messages
kafka-console-producer --broker-list kafka:9092 --topic applogs
# open other shell to start consumer 
docker-compose exec kafka bash
# receive messages
kafka-console-consumer --bootstrap-server kafka:9092 --topic applogs --from-beginning --property print.key=true
```

#### Hive 
```bash
# open shell to connect to hive-server
docker-compose exec hive-server bash
hadoop fs -mkdir /user/hive/olympics
# start hive cli
/opt/hive/bin/beeline -u jdbc:hive2://localhost:10000
```

```sql
set hive.support.concurrency=true;
set hive.enforce.bucketing=true;
set hive.exec.dynamic.partition.mode=nonstrict;
set hive.txn.manager=org.apache.hadoop.hive.ql.lockmgr.DbTxnManager;
set hive.compactor.initiator.on = true;
set hive.compactor.worker.threads = 1;

 
DROP TABLE IF EXISTS mydim;

CREATE TABLE mydim (key int, name string, zip string, is_current boolean)
CLUSTERED BY (key) INTO 3 BUCKETS
STORED AS ORC TBLPROPERTIES ('transactional'='true');s

INSERT INTO mydim VALUES
  (1, 'bob',   '95136', true),
  (2, 'joe',   '70068', true),
  (3, 'steve', '22150', true);

show tables;
select * from mydim;
show transactions;
show locks;
```

```sql
DROP TABLE IF EXISTS OLYMPICS;

CREATE TABLE
OLYMPICS(CITY STRING,EDITION INT,SPORT STRING,SUB_SPORT STRING,ATHLETE STRING,COUNTRY STRING,GENDER STRING,EVENT STRING,EVENT_GENDER STRING,MEDAL STRING)
CLUSTERED BY (EDITION)INTO 3 BUCKETS
ROW FORMAT DELIMITED
STORED AS ORC 
TBLPROPERTIES('transactional'='true');

show tables;
SELECT COUNT(*) FROM OLYMPICS;
select * from OLYMPICS;
```
 

### Cleanup
```bash
docker system prune
```

### Reference 
* Stream data into HIVE like a Boss using NiFi HiveStreaming - Olympics 1896-2008
    * https://community.hortonworks.com/articles/52856/stream-data-into-hive-like-a-king-using-nifi.html

