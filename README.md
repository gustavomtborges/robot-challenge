# Robot-challenge

REST API to send commands and control robot cameras at Mars.

### Prerequisites

- Java 8
- Maven

## Test

```
$ mvn test
```

### Install and Run

```
$ mvn spring-boot:run
```

Application published on localhost:8080

### API Requests

Endpoint | Code | Response
--- | --- | ---
**/rest/mars/MML** | 200 | { "position_x": 0, "position_y": 2, "orientation": "WEST" }
**/rest/mars/AAA** | 400 | InvalidInstructionException
**/rest/mars/MMMMMMM** | 400 | InvalidMovementException
 
### Build with

- Spring Boot