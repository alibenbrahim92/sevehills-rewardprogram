# sevehills-rewardprogram

1- To run the application, double click the run file, default host is localhost:8080, you can change port by modifying the run file param --server.port = yourPort 
2- If you want to re-compile the application, you should have maven installed and globally configured, in this case double click the compile file, mvn will run tests and the jar file will be generated in target folder. 
3- Once the application started, you can access the H2 databse console in : http://localhost:8080/h2-console Put jdbc:h2:mem:reward-program in JDBC Url in the H2-console page User name sa, no password required 
4- To access the Swagger documentation of the Restful API, Navigate to http://localhost:8080/swagger-ui/index.html 
5- You can use Swagger to call the Rest API Start by adding a customer (id must be null, it is auto-generated), then add transactions related to the customer (transaction id must be null, it is auto-generated) then you would be able to calculate the customer rewards points by calling the endpoint : http://localhost:8080/customer/1/rewardPoints 
6- logs are stored in log/rewardprogram.log
