# Muslasoft Drone Fleet

### build 
* To Build the Spring boot app and run the tests
```
gradle build
```
## Dependencies 
* sqlLite3
* spring boot 2.x

### API 

* See the **test.postman_collection.json** for example 
## example
To Load the drone of serial **{ANY_SERIAL_NUMBER}** with medications of id **[50, 60]** 
```
POST http://localhost:8080/drone/load/{ANY_SERIAL_NUMBER} # the drone serial number
BODY JSON 
{
[50, 60] # this contains the medication ID list
}

```
To Run health Check we use spring Actuator
```
GET http://localhost:8080/actuator/health
RESPONSE {
{"status":"UP"}
}
```
