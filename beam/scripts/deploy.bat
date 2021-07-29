for %%I in ("%~dp0.") do for %%J in ("%%~dpI.") do set HOME_DIR=%%~dpnxJ
echo %HOME_DIR%
set BUILD_DIR=%HOME_DIR%\build\libs

docker ps --filter name=jobmanager --format="{{.ID}}" > tmpFile
set /p JM_CONTAINER=< tmpFile
del tmpFile
set TARGET_CLASS_NAME="shoppinglist.beam.products.pipelines.TargetParse"
set WALMART_CLASS_NAME="shoppinglist.beam.products.pipelines.WalmartParse"
docker cp "%BUILD_DIR%\beam-pipelines.jar" %JM_CONTAINER%:/beam-pipelines.jar

docker exec -t -i "%JM_CONTAINER%" flink run -d -c %TARGET_CLASS_NAME% /beam-pipelines.jar --runner=FlinkRunner --bootstrapServers=localhost:9092 --inputTopics=target.products
docker exec -t -i "%JM_CONTAINER%" flink run -d -c %WALMART_CLASS_NAME% /beam-pipelines.jar --runner=FlinkRunner --bootstrapServers=localhost:9092 --inputTopics=target.products