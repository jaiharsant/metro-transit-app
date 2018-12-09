Spring Boot MetroTransit Consumer App
-------------------------------------
MetroTransitService provides an api which will tell you how long it is until the next bus on “BUS ROUTE” leaving from 
“BUS STOP NAME” going “DIRECTION” using the api defined at http://svc.metrotransit.org/ 

E.g
Service URL:

   http://localhost:8080/api/v1/trips/nextbus

POST:

	"{
		"route": "METRO Blue Line",
		"stop": "Mall of America Station",
		"direction": "NORTH"
	}"

Requirements
------------
   Open JDK 11
   Maven 3
   Spring Boot Release 2.1.1

Running the application locally
-------------------------------

There are several ways to run a Spring Boot application on your local machine. 

   1. One way is to execute the main method in the com.mts.spring.MetroTransitServiceApp class from your IDE.

   2. Alternatively you can use the Spring Boot Maven plugin like so
        
     'mvn spring-boot:run'
    
   3. Alternatively you can pull Docker image "saravkandasa/metro-transit-service" from Docker registry and 
   run the following Docker run command
   
     'docker run -p 8080:8080 saravkandasa/metro-transit-service'
   

