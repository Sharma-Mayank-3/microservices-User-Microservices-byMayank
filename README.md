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

