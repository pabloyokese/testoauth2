# Reactive Restful service with Spring 5 and Spring Boot 2
[![Build Status](https://travis-ci.org/dserradji/reactive-customer-service.svg?branch=master)](https://travis-ci.org/dserradji/reactive-customer-service)

This project is about building a small Reactive RESTful service with Spring 5 And Spring Boot 2, the DB used is MongoDB and security is managed with Spring Security OAuth2 and SSL.

More details can be found here: https://dserradji.wordpress.com/

The "non-reactive" version is also available: [customer-service](https://github.com/dserradji/customer-service)

## Docker

All docker support files are located in the *docker* directory

- *Dockerfile*: this file describes the docker image of the service
- *docker-compose.yml*: this file manages 2 containers, a container based on the image created with *Dockerfile* file and a container based on the official MongoDB image.
- *docker-compose up --build -d*: build, start and link the 2 containers, this command is called from *start.sh*
- *./start.sh*: builds a jar file without the Embedded MondoDB dependency and calls *docker-compose up --build -d*
- *curl -f -s http://localhost:8081/application/health | jq '.status'*: can be used to check if the service is up or down
- *docker-compose down*: stop the containers and remove them
- *./stop.sh*: is identical to *docker-compose down*

**_Important_**
- This configuration has been tested with: Ubuntu Linux *16.04*, Docker CE *17.06.1-ce* and docker-compose *1.15.0*
- If *jq* is not installed on your system you can simply remove *| jq '.status'* from the *curl* command
- If you receive an error message from *docker-compose* about an unsupported version of *Dockerfile* please update your *docker-compose* as described [here](https://github.com/docker/compose/releases)
# testoauth2
echo "# testoauth2" >> README.md
git init
git add README.md
git commit -m "first commit"
git remote add origin https://github.com/pabloyokese/testoauth2.git
git push -u origin master
