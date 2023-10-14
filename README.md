# microservices-User-Microservices-byMayank

This is a part of a microservice project, where out aim is to create a different microservices, like user, hotel 
and rating microservice, also establish a synchronous communication between these respective services.

# User-Microservice
    ---------------------------------------------------------------

# User Entity 
1. Create, getById, getAll Users operation on user entity.
2. One User can give a lot of rating but rating will be a different microservice.

       List<Rating> ratings = new ArrayList<Rating> 
4. But here there is no mapping since Rating will be a different microservice.
5. use @Transient annotation to avoid putting entry of rating column in the User DB.

       @Transient
       List<Rating> ratings = new ArrayList<Rating> 


# Rest Template Call.....
  
      ---------------------------------------------------------------------
# Synchronous Microservice Communication using RestTemplate.
1. Call User Service using userId, Get all user details, from there call the 
   Rating Service to get the ratings, from List of Ratings get the Single Rating and 
   Call the Hotel Service to get the Hotel Info from rating.getHotelId();

   
2. Create a HotelController and HotelService to create, getById, getAll, 
   update and delete Hotel, by using restTemplate.


# Remove Port and host and use loadbalancer.
1. remove host and port and add service name instead.
2. add @loadbalancer annotation in the restTemplate bean creation.



# Feign Client Call.....

      ---------------------------------------------------------------------
# Synchronous Microservice Communication using Feign Client.
1. Create a Rating Controller and external.service package and create RatingFeignClient interface.
2. Add @EnableFeignClient annotation in main class.
3. Add dependencies openfeign

        <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

        <dependencyManagement>
          <dependencies>
            <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-dependencies</artifactId>
              <version>${spring-cloud.version}</version>
              <type>pom</type>
              <scope>import</scope>
            </dependency>
          </dependencies>
        </dependencyManagement>



# Add Eureka Server Client User-Service to eureka server.
# Add 2 dependencies in user-service.
# Add Eureka Server Client like add User service to eureka server.
1. cloud Bootstrap
2. eureka discovery client.

       <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter</artifactId>
       </dependency>

       <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
       </dependency>

# After Dependencies tag over
Add

    <dependencyManagement>
     <dependencies>
     <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-dependencies</artifactId>
      <version>${spring-cloud.version}</version>
      <type>pom</type>
      <scope>import</scope>
     </dependency>
     </dependencies>
    </dependencyManagement>


# Add spring cloud version to the above properties, what ever is mentioned above.

    <properties>
     <java.version>17</java.version>
     <spring-cloud.version>2022.0.4</spring-cloud.version>
    </properties>


# Now Add configuration in the application.properties file

      eureka.instance.preferIpAddress=true
      eureka.client.register-with-eureka=true
      eureka.client.fetch-registry=true
      eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
      spring.application.name=USER-SERVICE

# Add annotation in main class of user

    @EnableDiscoveryClient


# Fault Tolerance.

# Using Resilience 4j we can also do it using hystrix circuit breaker 
# Using Resilience 4J demo

Note 3 dependencies 
1. Actuator dependency
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
    <version>3.1.4</version>
</dependency>

```

2. AOP dependency
```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-aop</artifactId>
   <version>3.1.4</version>
</dependency>

```

3. Resiliance spring boot dependency
```xml
<dependency>
   <groupId>io.github.resilience4j</groupId>
   <artifactId>resilience4j-spring-boot3</artifactId>
   <version>2.1.0</version>
</dependency>

```

Note : Add Configuration in application.properties file 
1. Actuator Configuration
```properties
management.health.circuitbreakers.enabled=true
management.endpoints.web.exposure.include=health
management.endpoints.health.show-details=always
```

2. Resilience 4J Configuration
```properties
resilience4j.circuitbreaker.instances.hotelServiceBreaker.registerHealthIndicator=true
resilience4j.circuitbreaker.instances.hotelServiceBreaker.eventConsumerBufferSize=10
resilience4j.circuitbreaker.instances.hotelServiceBreaker.failureRateThreshold=50
resilience4j.circuitbreaker.instances.hotelServiceBreaker.minimumNumberOfCalls=5
resilience4j.circuitbreaker.instances.hotelServiceBreaker.automaticTransitionFromOpenToHalfOpenEnabled=true
resilience4j.circuitbreaker.instances.hotelServiceBreaker.waitDurationInOpenState=5000
resilience4j.circuitbreaker.instances.hotelServiceBreaker.permittedNumberOfCallsInHalfOpenState=3
resilience4j.circuitbreaker.instances.hotelServiceBreaker.slidingWindowSize=10
resilience4j.circuitbreaker.instances.hotelServiceBreaker.slidingWindowType=COUNT_BASED
```

# Actuator 
```
http://localhost:8080/actuator/health
```

# Retry using Resilience 4J.
1. Application.properties configuration for retry
```properties
resilience4j.retry.instances.userRatingRetry.maxRetryAttempts=3
resilience4j.retry.instances.myRetry.waitDuration=1000
```

2. RatingUserController
```
@Retry(name = "userRatingRetry", fallbackMethod = "getAllRatingbyHotelFail")
Actual method where rating service has been called.

private ResponseEntity<ApiResponse> getRatingByUserFail(
        int userId, Exception ex
        ){
        log.info("getRatingByUserFail breaker called.....");
        ApiResponse getRatingByUserFail = ApiResponse.builder().status(true).serviceName("user-service").message("agetRatingByUserFail").data(null).build();
        return new ResponseEntity<>(getRatingByUserFail, HttpStatus.OK);
        }

```

# RateLimiter, means in some time frame how many request we want to process to our server.
1. application.properties configuration
```properties
resilience4j.ratelimiter.instances.myRateLimiter.limitForPeriod=50
resilience4j.ratelimiter.instances.myRateLimiter.limitRefreshPeriod=10
resilience4j.ratelimiter.instances.myRateLimiter.timeoutDuration=1000

```


