# hateoas-with-pagination
The objective of the application is to have a progressive approach of understanding how one can implement pagination with hateoas in a spring boot application.

# Application Description
## Summary 
This is a spring boot application showcasing different ways of fetching data from the database.
This application exposes endpoints which returns the same data in the multiple forms below as we start from a basic List
and move towards pagination with hateoas.
Below are the step by step end-points at a high level
1. As a List
2. As a filtered List
3. As a filtered and sorted List
4. Filtered and sorted but in page format(without hateoas and links to other pages)
5. Filtered and sorted, in page format with links to other pages as well
## Use Case 
If you have worked on smaller applications, you have probably only returned a list of objects in your Apis.
This is a perfectly fine approach for much smaller applications with less amount of data and/or requests. 
Either as the number of requests grow or as data grows, we need to provide better ways of sending data to our clients.
This keeps data transmission to a minimum of only what the client needs. 
For example: If there is a table with 1 million rows in our Database, and our client is a website, 
it is probably not a good solution to have the http response to our client to contain 1 million rows. 
Instead, our goal in this app is to give the client parts of the data and provide links to access the other data if required.
This is where hateoas comes into picture. In the HTTP Response, it provides links to the first, current, next and last pages of data.

# Application Setup
## Java Files 
### Customer 
This is the Customer Entity class. Each Customer has an id(sequence generated), customer Id(uuid), first name and last name. 
### Customer Controller
The Controller holds the end points defined. There are 5 endpoints with different functionalities defined later in the document
### Customer Service
Service holds the business logic and calls the required repository methods to fetch data from the DB
### CustomerRepository
Repository class for the customer entity
### CustomerModel and CustomerModelAssembler
2 Classes required for implementing pagination with hateoas 

## Database Setup
### Database and Connectivity
For this application we are using an in memory H2 Database.
As this is a H2 DB no external installation needs to be done on the system running the application.
Once the application is running, it can be accessed using the web browser: http://localhost:8080/h2-console
Username: sa
Enter the above username, without any password and click connect.
### Raw Data Setup 
Using spring boot behaviour, there are 2 files in the resources folder
These 2 files will get auto executed each time the application starts up and needs no modification
1. schema.sql - this creates the required DB in our in memory H2 DB each time we launch our application.
   For this application, we are creating one table customer with 4 columns. 
2. data.sql - this contains the script to be run on our DB
   For this application, we are inserting  1000 rows of data into the customers table created in step 1. 

# Application Execution
## Running the application
To run the application you need java 11 and maven installed.
Navigate to the root folder of the application and run
```
mvn spring-boot:run
```
Or you can use an IDE that support java applications such as IntelliJ and run the application on the IDE 
## Testing the application
To test the application, below are the endpoints that can be executed once the application is running.
You can directly try the Url on your web browser or use tools such as postman.

### Endpoint 1 : As a List
Request Type: GET 
Request Url: http://localhost:8080/api/v0/customers
Description: This url returns ALL the customers as a List. 
Response Format: 
```
[{
   "id": 1,
   "customerId": "6252fcab-a17e-4af4-aa70-0fda826e67cf",
   "firstName": "Dorine",
   "lastName": "McGrouther"
}]
```

### Endpoint 2: As a filtered List
Request Type: GET
Request Url: http://localhost:8080/api/v1/customers?firstNameFilter=R&lastNameFilter=A
Description:  This url returns ALL the customers as a List after applying filter conditions to first name and last name.
Response Format:
```
[{
   "id": 161,
   "customerId": "b1c494ce-775c-4e4f-a8eb-fa85a912ea8e",
   "firstName": "Ripley",
   "lastName": "Anten"
}]
```
Alternative Test Urls:
http://localhost:8080/api/v1/customers?firstNameFilter=Roscoe
http://localhost:8080/api/v1/customers?lastNameFilter=Ab

### Endpoint 3: As a filtered and sorted List
Request Type: GET
Request Url: http://localhost:8080/api/v2/customers?firstNameFilter=A&lastNameFilter=S&page=0&size=30
Description:  This url returns the customers of the specified page with the specified size, after applying filter conditions to first name and last name.
Response Format:
```
   {"content": 
      [{
      "id": 65,
      "customerId": "6021c230-9072-4e26-865f-9ff2372be87b",
      "firstName": "Arni",
      "lastName": "Van Der Straaten"},
      {
      "id": 885,
      "customerId": "0806ad06-3849-4615-8904-f2c5c4174d50",
      "firstName": "Arlena",
      "lastName": "Sherbrook"},
      {
      "id": 976,
      "customerId": "5fbe2019-9d53-4578-9b41-ef0bca6125ca",
      "firstName": "Archibold",
      "lastName": "Smeeton"}
      ],
   "pageable": {
      "sort": {
      "empty": true,
      "unsorted": true,
      "sorted": false
      },
      "offset": 0,
      "pageNumber": 0,
      "pageSize": 30,
      "paged": true,
      "unpaged": false
      },
   "last": true,
   "totalPages": 1,
   "totalElements": 3,
   "size": 30,
   "number": 0,
   "sort": {
      "empty": true,
      "unsorted": true,
      "sorted": false
   },
   "first": true,
   "numberOfElements": 3,
   "empty": false
   }
```
Alternative Test Urls:
http://localhost:8080/api/v2/customers
http://localhost:8080/api/v2/customers?page=4&size=60
http://localhost:8080/api/v2/customers?firstNameFilter=A&lastNameFilter=Sa

### Endpoint 4: Filtered and sorted but in page format(without hateoas and links to other pages)
Request Type: GET
Request Url: http://localhost:8080/api/v3/customers?firstNameFilter=R&lastNameFilter=S&page=0&size=10&sortList=firstName&sortOrder=ASC
Description: This url returns the customers of the specified page with the specified size, after applying filter conditions to first name and last name and sorting the data.
Response Format:
```
   {
   "content": [
      {"id": 342,
      "customerId": "db40ac17-ec53-4c7e-9af3-a227d8b4f5bb",
      "firstName": "Raimondo",
      "lastName": "Safont"},
      {"id": 794,
      "customerId": "f3e9c6b1-7619-4781-afad-2a1e02690e1d",
      "firstName": "Ricky",
      "lastName": "Stockwell"},
      {"id": 966,
      "customerId": "9e4ade7d-3a2b-4ba0-be20-e5a856a0af3b",
      "firstName": "Rosabel",
      "lastName": "Scholefield"}
   ],
   "pageable": {
      "sort": {
      "empty": false,
      "unsorted": false,
      "sorted": true
      },
      "offset": 0,
      "pageNumber": 0,
      "pageSize": 10,
      "paged": true,
      "unpaged": false
   },
   "last": true,
   "totalPages": 1,
   "totalElements": 4,
   "size": 10,
   "number": 0,
   "sort": {
      "empty": false,
      "unsorted": false,
      "sorted": true
   },
   "first": true,
   "numberOfElements": 4,
   "empty": false
   }
```
Alternative Test Urls:
http://localhost:8080/api/v3/customers?sortList=firstName&sortOrder=ASC
http://localhost:8080/api/v3/customers?sortList=firstName&sortOrder=DESC
http://localhost:8080/api/v3/customers?sortList=firstName,lastName&sortOrder=DESC
http://localhost:8080/api/v3/customers?firstNameFilter=R&page=0&size=10&sortList=firstName

### Endpoint 5: Filtered and sorted, in page format with links to other pages as well
Request Type: GET
Request Url: http://localhost:8080/api/v4/customers?firstNameFilter=R&lastNameFilter=S&page=0&size=10&sortList=firstName&sortOrder=ASC
Description: This url returns the customers of the specified page with the specified size, after applying filter conditions to first name and last name and sorting the data.
The response is of the pagination format supported by hateoas and also contains links to other pages such as next page and last page
Response Format:
```
{
   "_embedded": {
      "customerModelList": [
         {
         "id": 971,
         "customerId": "de6b8664-ba90-41fc-a9f4-da7d0b89c106",
         "firstName": "Rabi",
         "lastName": "Dufour"
         },
         {
         "id": 339,
         "customerId": "44b5c01d-c379-4f66-b8ed-0fda4837db4e",
         "firstName": "Rachelle",
         "lastName": "Fleischer"
         },
         {
         "id": 838,
         "customerId": "443b06fd-7160-4234-9102-93afb0f6d9ad",
         "firstName": "Rafaelia",
         "lastName": "Bladen"
         }
      ]
   },
   "_links": {
      "first": {
         "href": "http://localhost:8080/api/v4/customers?firstNameFilter=R&sortList=firstName&sortOrder=ASC&page=0&size=3&sort=firstName,asc"
      },
      "self": {
         "href": "http://localhost:8080/api/v4/customers?firstNameFilter=R&sortList=firstName&sortOrder=ASC&page=0&size=3&sort=firstName,asc"
      },
      "next": {
        "href": "http://localhost:8080/api/v4/customers?firstNameFilter=R&sortList=firstName&sortOrder=ASC&page=1&size=3&sort=firstName,asc"
      },
      "last": {
        "href": "http://localhost:8080/api/v4/customers?firstNameFilter=R&sortList=firstName&sortOrder=ASC&page=19&size=3&sort=firstName,asc"
      }
   },
   "page": {
      "size": 3,
      "totalElements": 60,
      "totalPages": 20,
      "number": 0
   }
}
```
Alternative Test Urls:
http://localhost:8080/api/v4/customers?firstNameFilter=R&page=0&size=3&sortList=firstName&sortOrder=ASC
http://localhost:8080/api/v4/customers?firstNameFilter=R&sortList=firstName&sortOrder=DESC