# Fightway Backend

Fightway Backend is a Spring Boot application that serves as the backend logic for the Fightway project. It includes features such as database handling, API management, and security functionalities. This README provides essential information for developers and contributors to understand and work with the project.

## Table of Contents

- [Design Patterns](#design-patterns)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Dependencies](#dependencies)
- [Build and Run](#build-and-run)
- [Api Information](#api-information)
- [License](#license)

## Design Patterns:

In the Fightway Backend project, various design patterns have been employed to enhance modularity, Here are some of the key design patterns utilized:

### 3 Layered Architecture:

The project follows a 3-layered architecture, separating concerns into Repository, Service, and Controller layers. This promotes a clear division of responsibilities, making the codebase more modular and maintainable.

### Singleton:

Singleton pattern ensures that a class has only one instance and provides a global point of access to it. It is used in scenarios where exactly one object is needed to coordinate actions across the system.

### Dependency Injection (DI):

Dependency Injection is employed to promote loose coupling between classes by injecting dependencies rather than having the class create its dependencies. Spring, being a dependency injection framework, facilitates the implementation of this pattern.

### DAO - Data Access Objects:

The Data Access Object pattern is utilized for abstracting and encapsulating all access to the data source. It provides a simple and uniform interface to interact with the database, promoting separation of concerns.

### Builder Pattern:

The Builder pattern is used to construct complex objects step by step. It is particularly useful when an object needs various configurations or when an object has a large number of parameters.

### Other Design Patterns:

In addition to the mentioned patterns, other design patterns may be employed as needed to address specific design challenges and enhance the overall structure of the codebase.


## Technologies Used

- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- Spring Web
- Validation
- Lombok 1.18.22
- ModelMapper 3.2.0
- Spring Security
- JSON Web Token (JWT)
- Springdoc OpenAPI 2.2.0

## Project Structure

The project follows the standard Spring Boot project structure. The main components include:

- `src/main/java/..`: Java source files.
- `src/main/resources`: Application properties and configuration files (for ex: openAPI)
- `pom.xml`: Maven Project Object Model file.

## Dependencies

- **Spring Boot Dependencies:**
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-validation`
  - `spring-boot-starter-web`
  - `spring-boot-devtools`
  - `spring-boot-configuration-processor`
  - `spring-boot-starter-test`

- **Database:**
  - `mysql-connector-j`
  - `spring-boot-starter-data-jpa` 


- **Security:**
  - `spring-boot-starter-security`
  - `jjwt-api`
  - `jjwt-impl`
  - `jjwt-jackson`

- **Documentation:**
  - `springdoc-openapi-starter-webmvc-ui`
  - `springdoc-openapi-starter-common`

- **Other:**
  - `annotations` (for Lombok)
  - `modelmapper`(for Mapping)

## Build and Run

To build and run the project locally, follow these steps:

1. Ensure you have Java 17 and Maven installed.
2. Clone the repository: [https://github.com/Natanel777/fightway-backend.git](https://github.com/Natanel777/fightway-backend.git)
3. Navigate to the project directory: `cd fightway-backend`
4. Build the project: `mvn clean install`
5. Run the application: `run`

The application will be accessible at `http://localhost:8080`.

## Api Information

Further instruction regarding the endpoints can be found on http://localhost:8080/swagger

## License

This project is licensed under the [Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0). Feel free to look up in the information.

For more details, see the [Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0) file.

