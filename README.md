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

