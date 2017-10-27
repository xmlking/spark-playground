Apache Spark Machine Learning
=============================
Spark Machine Learning examples.

* TwitterLanguageAnalyzer
* TwitterSentimentAnalyzer

### Build

```bash
gradle spark-ml:shadowJar
# skip tests
gradle spark-ml:shadowJar -x test
```

### Run

#### Running Locally
    
```bash
# Submit Local
spark-submit \
    --class com.sumo.experiments.MLJobKt \
    --master local[2] \
    spark-ml/build/libs/spark-ml-0.1.0-SNAPSHOT-all.jar
```

In IDEs like IntelliJ, you can right-click the file and run directly.

#### Launching on a Cluster

```bash
# Submit to Cluster
spark-submit \
    --class com.sumo.experiments.MLJobKt \
    --master spark://localhost:7077 \
    spark-ml/build/libs/spark-ml-0.1.0-SNAPSHOT-all.jar
```
