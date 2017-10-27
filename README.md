Spark Playground
================
Apache Spark playground

###  Examples
1. [Spark Batch](./spark-batch)
2. [Spark Streaming](./spark-streaming)
3. [Spark Machine Learning](./spark-ml)


### Prerequisites
1. Gradle > 4.2.1 [ Install via [sdkman](http://sdkman.io/)]
2. Docker for Mac [[Setup Instructions](./docs/Docker.md)]
3. Apache Spark [[Download Link](https://spark.apache.org/downloads.html)]

#### Install spark via downloading 
    
    Download `spark-x.x.x-bin-hadoop2.7.tgz` from  https://spark.apache.org/downloads.html
    Install Spark by unpacking i.e., /Developer/Applications/spark-2.2.0-bin-hadoop2.7

#### Install spark via brew (Mac)
```bash
# As an alternative, you can install spark via brew on Mac
brew update
brew install apache-spark
# verifying installation
spark-shell
```

### Build

```bash
gradle shadowJar
# skip tests
gradle shadowJar -x test
```

### Start Standalone Spark Cluster
```bash
# run foreground
docker-compose up spark-master
docker-compose up spark-worker
docker-compose up zeppelin
# scall up workers if needed
docker-compose scale spark-worker=2
# to restart any container 
docker-compose restart spark-master
# to shutdown
docker-compose down
# to see ports of workers bind to host
docker-compose ps
# to see logs
docker-compose logs -f zeppelin
# ssh to a service(master)
docker-compose exec spark-master bash
hadoop fs -ls /data/in
```

The SparkUI will be running at `http://${YOUR_DOCKER_HOST or localhost}:8080` with one worker listed.

### Run

> Set your environment path to spark commands, if you installed spark via downloading
```bash
SPARK_HOME=/Developer/Applications/spark-2.2.0-bin-hadoop2.7
PATH=$PATH:$SPARK_HOME/bin
# set this, if you get Error: WARN Utils: Service 'sparkDriver' could not bind on port 0. Attempting port 1.
export SPARK_LOCAL_IP="127.0.0.1" 
```

#### Spark Shell

> To open Spark Shell
```bash
spark-shell --master spark://localhost:7077
# or with docker-compose
    docker-compose exec master bash
    # start spark shell with in this bash
    spark-shell --master spark://master:7077
    # or run example `SparkPi` job
    run-example SparkPi 10
```

#### Running Locally
    
```bash
# Submit Local
spark-submit \
    --class com.sumo.experiments.BatchJobKt \
    --master local[2] \
    spark-batch/build/libs/spark-batch-0.1.0-SNAPSHOT-all.jar
    
spark-submit \
    --class com.sumo.experiments.LoadJobKt \
    --master local[2] \
    --properties-file application.properties \
    spark-batch/build/libs/spark-batch-0.1.0-SNAPSHOT-all.jar
```

In IDEs like IntelliJ, you can right-click the file and run directly.

#### Launching on a Cluster

```bash
# Submit to Cluster
spark-submit \
    --class com.sumo.experiments.BatchJobKt \
    --master spark://localhost:7077 \
    spark-batch/build/libs/spark-batch-0.1.0-SNAPSHOT-all.jar

spark-submit \
    --class com.sumo.experiments.LoadJobKt \
    --master spark://localhost:7077 \
    --properties-file application-prod.properties \
    spark-batch/build/libs/spark-batch-0.1.0-SNAPSHOT-all.jar

nohup spark-submit \
    --class com.sumo.experiments.LoadJobKt \
    --master yarn \
    --queue abcd \
    --num-executors 2 \
    --executor-memory 2G \
    --properties-file application-prod.properties \
    spark-batch/build/libs/spark-batch-0.1.0-SNAPSHOT-all.jar arg1 arg2 > app.log 2>&1 &
```

### Gradle Commands
```bash
# upgrade project gradle version
gradle wrapper --gradle-version 4.2.1 --distribution-type all
# gradle daemon status 
gradle --status
gradle --stop
# refresh dependencies
gradle build --refresh-dependencies
```

### Reference 
* https://bigdatagurus.wordpress.com/2017/03/01/how-to-start-spark-cluster-in-minutes/
* https://zeppelin.apache.org/docs/0.7.2/install/cdh.html
* https://spark.apache.org/examples.html
* https://github.com/cliftbar/etl-stack/blob/master/docker-compose.yml
* https://github.com/big-data-europe/docker-hive
* https://github.com/SANSA-Stack/SANSA-Notebooks/tree/develop
 

