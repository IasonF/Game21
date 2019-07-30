# Game21

A small card game where you try to get close to 21 without getting busted. 

## Getting Started

Build with Maven:
```
mvn clean install
```

The game is a springboot application. Run it through maven:
```
mvn spring-boot:run
```

or simply run the Application.java on your IDE.

### Prerequisites

* JDK 8 
* Maven

### Play

Access the game at 
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html))
Use the API to start a new game, make one of the 3 moves (hit, stand, split) and check the status of the game at any time.

![Sample](https://github.com/IasonF/DeveloperCase/blob/master/src/main/resources/Frontend.png)

### Components 

* Game follows Spring MVC design.
* The model contains the components (Deck, Hand) and the logic of the game.
* API is made with Swagger.
* Swagger-UI is used as a basic frontend UI.


