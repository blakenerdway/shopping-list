docker ps -a -q | % { docker rm $_ -f}
docker images -a -q | % { docker image rm $_ -f}
