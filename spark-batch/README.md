Apache Spark Batch
==================
Spark Batch examples.

* BatchJob - count total
* LoadJob - load two cvs tables, left join and output nested json. 

### Build

```bash
gradle spark-batch:shadowJar
# skip tests
gradle spark-batch:shadowJar -x test
```

### Run

#### Running Locally
    
```bash
# Submit Local
spark-submit \
    --class com.sumo.experiments.BatchJobKt \
    --master local[2] \
    --properties-file application.properties \
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
