
# URL Shortener



This simple application demonstrates the usage of several concepts related to Java, Spring Boot, and Docker.



The purpose is to illustrate how the following concepts can be integrated into a single application:



1. Spring Boot application

2. Usage of Maven as a dependency and build tool

3. Exposing a simple REST API with parameters

4. Using JPA to persist data in a simple table

5. Creating a Docker image as part of the build process

6. Using JUnit in TDD (Test Driven Development)

7. Implementing caching



## Requirements



To compile and run the application, you need to have Java 21, Maven 3.6.2, and Docker installed.



The application was tested under Linux Ubuntu 22 and WSL (Ubuntu 21) on Windows 11.



A writable folder `/data/` is required for the application to function properly.



## How to Build the Application



To build the application, simply run the command:



```

mvn clean package

```



## How to Run the Application



The application can be executed in two ways:



### Using Java Command



By executing the command:



```

java -jar target/urlshortener-0.0.1-SNAPSHOT.jar

```



### Using Docker Command



If Docker is installed, the following command will start a new Docker container:



```

docker run --rm --net=host -v /data:/data lumeche/notarius:0.0.1-SNAPSHOT

```



**NOTE**: The parameter `-v /data:/data` mounts the folder '/data' inside an existing folder '/data' on the running machine. This ensures that any data created by the Docker image is persisted for subsequent Docker images.



## How to Use the Application



The application starts on port 8080. In addition to the `/actuator` endpoint, there are two endpoints to be used:



### /encodeUrl?url=URL



This endpoint receives a single parameter, which is a string that will be encoded using a hashing algorithm. The result of the hashing operation is stored in a database so it can be retrieved in the future.



Example:



```

http://localhost:8080/encodeUrl?url=someurl/to/be/tested

```



The result of this command will be `dd5e3455`.



### /decodeUrl?url=URL



This endpoint receives the result from the previous operation and returns the original URL sent. For example, if the following request is sent:



```

http://localhost:8080/decodeUrl?url=dd5e3455

```



The result will be `someurl/to/be/tested`.



### Possible Exceptions



1. If the user sends a `/decodeUrl` request with a parameter that doesn't match a previously encoded URL, the server will return a 422 Error.

2. If there is an internal error, the server will return a 503 Error as expected.



## Future Enhancements



With more time allocated to this project, the following improvements could be made:



1. Add automatic end-to-end tests by loading the entire context, sending some requests, and validating that the responses are correct.

2. Enhance testing on the Persistence layer by using a real H2 file with some preloaded data to ensure it's being loaded correctly.

3. Conduct performance testing to highlight the benefits of using caching.

4. Add parameter validations, for instance, returning an error if the user sends an incomplete URL.
