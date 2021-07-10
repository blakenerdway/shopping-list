docker ps --filter name=jobmanager --format="{{.ID}}" > tmpFile
set /p JM_CONTAINER=< tmpFile
del tmpFile
set JOB_CLASS_NAME="shoppinglist.beam.products.pipelines.TargetParse"
docker cp beam-pipelines.jar %JM_CONTAINER%:/beam-pipelines.jar
docker exec -t -i %JM_CONTAINER% flink run -d -c %JOB_CLASS_NAME% /beam-pipelines.jar \
–-runner=FlinkRunner --bootstrapServers=localhost:9092 --inputTopics=target.products