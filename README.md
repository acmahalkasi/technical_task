***for windows***

**how to compile**\
```.\mvnw.cmd clean install```\
**how to run**\
```.\mvnw.cmd spring-boot:run```

***for linux***

**how to compile**\
```./mvnw clean install```\
**how to run**\
```./mvnw spring-boot:run```

**list of paths**\
@GetMapping(value = "/books")\
@GetMapping(value = "/users")\
@GetMapping(value = "/borrowed")\
@GetMapping(value = "/available-books")\
@GetMapping(value = "/users-borrowed-books-all-time")\
@GetMapping(value = "/users-borrowed-books")

**how to test**\
``curl http://localhost:8080/books`` \
``curl http://localhost:8080/users`` \
``curl http://localhost:8080/borrowed`` \
``curl http://localhost:8080/users-borrowed-books?date=07/15/2007`` \
``curl http://localhost:8080/available-books?todayDate=05/15/2008`` \
``curl http://localhost:8080/users-borrowed-books-all-time`` 


**NOTES: data has been provided as CSV. if it had been provided as sql data
then implementation would have been different than this. in that case
Book, User and Borrowed object must have been entities and there 
must have JpaRepositories to query the data** 