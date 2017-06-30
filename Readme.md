







./mvnw package docker:build -DskipTests=true
docker run -it --rm --network=d1_rp  --name=rp-spring -p 8080:8080  redislabs/docker-rp-spring

curl -i -X POST -H "Content-Type:application/json" -d "{  \"id\" : \"1\",  \"firstName\" : \"kamran\" , \"lastName\" : \"yousaf\" ,\"age\" : \"42\" }" http://localhost:8080/customers
curl  http://localhost:8080/customers
curl  http://localhost:8080/customers/1
./mvnw package -DskipTests=true
./mvnw spring-boot:run
