#!/bin/bash

CALLER_DIRECTORY=$(pwd)
SCRIPT_DIRECTORY=$(dirname $(realpath ${BASH_SOURCE[0]}))

# Go back to the caller directory
# then exit with the given code
# or 0 if no code is provided
function exit_script {
       
    local EXIT_CODE=0
       
    if [ ! -z $1];then
    	EXIT_CODE=$1
    fi
       
    cd $CALLER_DIRECTORY
	exit $EXIT_CODE
}

# Build the maven project with docker profile
cd $SCRIPT_DIRECTORY/..
if ! (mvn -Pdocker clean package)
then
    # Exit with error code from previous command
    exit_script $?
fi

# Build the docker image and start containers
cd $SCRIPT_DIRECTORY
if ! (docker-compose up --build -d)
then
    # Exit with error code from previous command
    exit_script $?
fi

# Exit with code 0 (no error)
exit_script
