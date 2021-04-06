# springbootjpa
Spring boot API for CRUD services for product

<a href="http://localhost:9088/springboot/swagger-ui/">Swagger</a>

#Docker Commands:

docker network create products-mysql

docker network ls

docker container run --name mysqldb --network products-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=bootdb -d mysql:8


docker build -f Dockerfile -t docker-spring-boot .

docker container run --network products-mysql --name products-jdbc-container -p 9088:9088 docker-spring-boot
