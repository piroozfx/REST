#!/bin/bash
echo 'building spring boot docker image ...'
mvn clean install
mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
docker build -t course/back-end .
echo 'Image (course/back-end:latest) has been created successfully.'

docker-compose up
