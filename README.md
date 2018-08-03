
[![Build Status](https://travis-ci.org/sunday-take-away/employee-service.svg?branch=master)](https://travis-ci.org/sunday-take-away/employee-service)

# employee-service

A rest service for employees. 

This is project is intended to be run in conjunction with other services.
For full installation of all relevant software, how to run and install, see:

https://github.com/sunday-take-away/employee-containers

# Continuous Integration
For historical build status, see:

https://travis-ci.org/sunday-take-away/employee-service

# Rest Service Operations 

## Echo
See the service echo status, which is a simple echo with time.
```
curl -X GET "http://localhost:8001/echo" -H  "accept: application/json" -H  "content-type: application/json"
```

## Create Employee
Creates a new employee (need to be authenticated). Location response header header provides URI for getting employee.

Note: ID and CREATED do NOT need to be provided.

Example
* BASIC-AUTH = Basic YWRtaW46SGFja0FTbmFjaw==
* JSON-DATA = {  \"email\": \"rowan.bean@icloud.com\",  \"firstName\": \"Rowan\",  \"lastName\": \"Bean\",  \"birthDay\": \"1966-04-01\",  \"hobbies\": [    \"laughing\"  ]}
```
curl -i -X POST "http://localhost:8001/employee" -H  "accept: application/json" -H  "content-type: application/json" -H  "authorization: {BASIC-AUTH}" -d "{JSON-DATA}"
```

## Get Employee
Get an employee for specified ID (Do not need to be authenticated)

Example
* ID = 5b62f2b86f5e5d00010c355f
```
curl "http://localhost:8001/employee/{ID}" -H  "content-type: application/json"
```
curl "http://localhost:8001/employee/5b63d0262e40b456aa4dbeb6" -H  "content-type: application/json"


## Update Employee
Update existing employee (need to be authenticated).

Example
* ID = 5b62f2b86f5e5d00010c355f
* BASIC-AUTH = Basic YWRtaW46SGFja0FTbmFjaw==
* JSON-DATA = {  \"id\": \"5b62f2b86f5e5d00010c355f\",  \"email\": \"rowan.bean@bbc.com\",  \"firstName\": \"Rowan\",  \"lastName\": \"Bean\",  \"birthDay\": \"1966-04-01\",  \"hobbies\": [ \"Joking\"  ], \"created\":\"02.08.2018 12:02:00\"}
```
curl -X PUT "http://localhost:8001/employee/{ID}" -H  "accept: application/json" -H  "content-type: application/json" -H  "authorization: {BASIC-AUTH}" -d "{JSON-DATA}"
```

## Delete Employee
Delete existing employee (need to be authenticated).

Example
* ID = 5b62f2b86f5e5d00010c355f
* BASIC-AUTH = Basic YWRtaW46SGFja0FTbmFjaw==
```
curl -X DELETE "http://localhost:8001/employee/{ID}" -H  "accept: application/json" -H  "content-type: application/json" -H  "authorization: {BASIC-AUTH}" 
```