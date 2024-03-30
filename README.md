# README.md

## Bookstore-Inventory-Management-System

### Description

This project is built using a hexagonal architecture. The entire application is developed with Spring WebFlux, leveraging reactive programming from database (R2DBC) interactions to testing (Reactor Tests). gRPC serves as the endpoint for communication. 
ReactorTests, Junit, and Mockito were used for tests, and Testcontainers for integration tests.


### How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/MaxPopaylo/Bookstore-Inventory-Management-System-Test_Task-.git

2. Start docker
   
   ```bash
   sudo service docker start

3. Start database in docker-compose.file

   ```bash
   docker-compose -f docker-compose.yaml up -d 

And then you can run the project in any IDE by running the BookstoreInventoryManagementSystemApplication.java file.

### How to Test

You can test the project with unit or intertion tests in the project itself or using postman.

[List of Postman tests](https://www.postman.com/grey-star-845470/workspace/bookstoreinventorysystem/collection/660155ceca10cf65eb10aef0?action=share&creator=28421538)
