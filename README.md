# kick-start-camel
Part one of the camel development series.
### JDK version
You need to be on Java 21 or newer.

### Start Kafka
Using the docker compose yml file you can run kafka with the following command. 
```shell
docker compose up
```

### Run application
First build the application
```shell
mvn clean install
```
Then you can run the application from the command line at the root level of the project
```shell
mvn spring-boot:run -Dapp.profiles=default
```
There is already a file in the orders folder that will get processed.