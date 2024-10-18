#!/bin/bash

set -e

echo "Building the project..."
./gradlew clean build

JAR_FILE="build/libs/InfoHub-0.0.1-SNAPSHOT.jar"
if [ ! -f "$JAR_FILE" ]; then
    echo "Error: JAR file not found!"
    exit 1
fi

echo "Copying JAR file..."
cp $JAR_FILE ./application.jar

echo "Building Docker image..."
docker build -t infohub-app .

echo "Running Docker container..."
docker run -d -p 8080:8080 infohub-app

echo "Application is running at http://localhost:8080"
