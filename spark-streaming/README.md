Apache Spark Streaming
======================
Spark Streaming examples.

* TwitterLanguageAnalyzer
* TwitterSentimentAnalyzer

### Build

```bash
gradle spark-streaming:shadowJar
# skip tests
gradle spark-streaming:shadowJar -x test
```

### Run

#### Running Locally
    
```bash
# Submit Local
spark-submit \
    --class com.sumo.experiments.StreamingJobKt \
    --master local[2] \
    spark-streaming/build/libs/spark-streaming-0.1.0-SNAPSHOT-all.jar
```

In IDEs like IntelliJ, you can right-click the file and run directly.

#### Launching on a Cluster

```bash
# Submit to Cluster
spark-submit \
    --class com.sumo.experiments.StreamingJobKt \
    --master spark://localhost:7077 \
    spark-streaming/build/libs/spark-streaming-0.1.0-SNAPSHOT-all.jar
```
