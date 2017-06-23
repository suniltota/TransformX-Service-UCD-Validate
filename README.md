# TransformX-Service-UCD-Validate
This project defines the code for validating UCD XML

This service runs on port :9019

To run the server, enter into project folder and run

mvn spring-boot:run (or) java -jar <location of the jar file>

The above line will start the server at port 9019

If you want to change the port. Please start the server as mentioned below 

syntax : java -jar <location of the jar file> --server.port= <server port number>
 
example: java -jar target/UCDValidator.jar --server.port=9055

API to validate UCD XML(/actualize/transformx/services/ucd/cd/validate) with input UCD XML

API to check the status of the service(actualize/transformx/services/ucd/ping); method :GET; 

syntax : <server address with port>/actualize/transformx/services/ucd/cd/validate; method :POST; Header: Content-Type:application/xml

example: http://localhost:9019/actualize/transformx/services/ucd/cd/validate ; method: POST; Header: Content-Type:application/xml
