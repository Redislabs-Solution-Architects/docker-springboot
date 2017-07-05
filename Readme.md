

#Spring Boot Sample Application

A sample Spring Boot application integration with spring boot

The example covers the following points

* Connecting to Redis Pack using [Discovery Service aka Sentinel](https://redislabs.com/redis-enterprise-documentation/concepts-architecture/concepts/discovery-service/)
* A template example to read and write
* A Redis Repository for a Java Object
    * Exposed as a REST service
    * Object attribute index
    * Object TTL example

## How to Run
### Stand Alone

*edit* application.yml file and change the nodes and change the *nodes* and *master* property to RP nodes IP and db name respectively.

build the project using maven
```
./mvnw package -DskipTests=true
```
Run the application
```
./mvnw spring-boot:run
```

### As a Docker image

Build the docker image
```
./mvnw package docker:build -DskipTests=true
```
Run the image in Docker change the ip address for nodes master DB name accordingly
```
docker run -it --rm -e redis_sentinel_nodes=RP_NODE1_IP:8001,RP_NODE2_IP:8001,RP_NODE3_IP:8001 -e redis_sentinel_master=DB_NAME  --name=rp-spring -p 8080:8080  redislabs/docker-rp-spring
```
e.g 
```
docker run -it --rm -e redis_sentinel_nodes=172.26.0.11:8001,172.26.0.12:8001,172.26.0.13:8001 -e redis_sentinel_master=test --network=d1_rp  --name=rp-spring -p 8080:8080  redislabs/docker-rp-spring
```

### Some commands to Run

```
curl -i -X POST -H "Content-Type:application/json" -d "{  \"id\" : \"1\",  \"firstName\" : \"Mathew\" , \"lastName\" : \"Spencer\" ,\"age\" : \"42\" }" http://localhost:8080/customers
```
get  customer with ID 1
```
curl  http://localhost:8080/customers/1
```
Run a service that checks Redis if object not found gets object from backing service and save to Redis with TTL
```
curl http://localhost:8080/getcustomer?id=2
```

##Spring Redis Features

### Redis Pack Connection
* Popular libraries supported (Jedis, Lettuce)
* Connection Support for RP using endpoint(DNS) and Sentinel details [here](http://docs.spring.io/spring-data/redis/docs/current/reference/html/#redis:connectors)
* Use Single endpoint if DNS is setup otherwise use Sentinel with RP for HA and automatic failover
* Both connection approaches will work with containers
* High level abstraction Repositories and standard Redis commands available

### RP/Spring Support
* Redis API operations fully supported
* Spring Data Redis also supports Templates
* MultipLe out of box serializers [here](http://docs.spring.io/spring-data/redis/docs/current/reference/html/#redis:serializer)
    * XML
    * JSON
    * Java
    * Custom
* Multiple Hash Mappers also supported details [here](http://docs.spring.io/spring-data/redis/docs/current/reference/html/#redis.hashmappers.root)


### Messaging/Transactions Support
* Messaging
    * Pub/Sub
    * Message Listeners
* Transactions using Annotations

### RP/Spring Redis Repositories
* Stores domain Objects in Redis Hashes
* Custom mapping strategies available
* Search by ID
* Secondary index support using Redis sets out of box
* Query Methods
    * findByLastnameAndFirstname
    * findByLastnameOrFirstname
    * findByFirstname
    * findFirst10ByFirstname




