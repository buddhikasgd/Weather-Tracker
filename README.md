# Weather-Tracker
Tracking weather by country and city

### Pre requirements
Maven, Java 8

### Commands
Build & Test: <br/>
mvn clean install

Run the app locally: <br/>
mvn spring-boot:run

### Testing app with data

Open up this Swagger url in your browser once the app is up and running: <br/>
http://localhost:8081/swagger-ui/

Endpoint: <br/>
/api/v1/current-weather/countries/{country}/cities/{city}

###Note:

1. In order to add cities and countries, update resources/static/city.list.json file prior to running the app.
   This is for validation purpose to reduce number of calls to open weather endpoint for invalid country and city combinations.
   <br/> Currently these locations are permitted. <br/>
   GB - London <br/> AU - Sydney <br/> AU - Melbourne <br/> AU - Perth <br/> AU - Brisbane <br/>
   <br/>
   You can find the latest city list data here http://bulk.openweathermap.org/sample/city.list.json.gz
   
   

2. Rate limit is 5 api calls per hour per api key. <br/>
   Currently these api keys are permitted.<br/>
   WTR-KEY-1 <br/>
   WTR-KEY-2 <br/>
   WTR-KEY-3 <br/>
   WTR-KEY-4 <br/>
   WTR-KEY-5 <br/>
   

3. if open weather api takes more than 2 seconds to response, it will use last updated data for the given location. If it is not in database, it will return service unavailable message till it gets back online.


4. You can modify location and weather data in H2 db using below url. <br/>
   http://localhost:8081/h2-console/login.do <br/>
   jdbc:h2:mem:weatherdb
   
   
   
