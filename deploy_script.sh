#!/bin/bash

# Pull the latest Docker image
docker pull $DOCKER_IMAGE

# Stop and remove the existing container
docker stop pocketurl || true
docker rm pocketurl || true

# Run the Docker container
docker run -d -p 80:8080 --name pocketurl $DOCKER_IMAGE
