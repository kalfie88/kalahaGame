# Katherine Alfaro Ramirez

##  Kalaha Game

This project is part of the Bol coding challenge, the idea is to implement the game called 
Kalaha/Mancala. The application is build in Java 8 with Spring Boot, Spring MVC for the backend and Thymeleaf
as UI.

## BackEnd API

### Project Structure

#### src/main/java

Under this path we have several packages mentioned below:

1. com.bol.kalaha -> We have the Spring Boot Application, and the Swagger configuration files.

2. com.bol.kalaha.controller -> Application controllers (Thymeleaf)

3. com.bol.kalaha.entities -> Objects to map the entities needed in the application

4. com.bol.kalaha.service -> Application services 


#### src/main/resources/templates

1. html files for the UI -> index.html 


#### src/test/java

1. com.bol.kalaha-> Integration test

2. com.bol.kalaha.controller  -> Unit test to check the controller's functionality

3. com.bol.kalaha.service -> Unit test to check the service's functionality



## Getting Started

This is an IntelliJ based, Gradle project written in Java 8, with Spring Boot, Lombok, Actuator and Swagger.
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.



### Prerequisites

So please follow these instructions, so you can set up the environment properly.

1. Get Git in your local -> In case you don’t have it already, go ahead and follow this guide: [Install-Git](https://www.atlassian.com/git/tutorials/install-git)

2. Get your IDE -> Since this is a Gradle project you can use the IDE of your preference, (I used IntelliJ) just make sure to import the project as an “Existing Gradle Project”

3. Point your IDE to the right JRE version -> This project is build over Java 8 so please make sure your Java Build Path is pointing to the JRE 1.8 version. You’ll find this option under: Preferences-> Build, Execution, Deplyoment -> Compiler -> Java Compiler.

4. Get Lombok -> Lombok is an external jar that needs to be added into your IDE. For more information on how to add Lombok to your IDE (Eclipse or IntelliJ) follow this guide: [Lombok-IDE](https://www.baeldung.com/lombok-ide)


#### Testing the backend

You can test the endpoints via Postman.

```
http://localhost:8080/
```
And since there's an actuator you can also hit that to test connectivity.

```
http://localhost:8080/actuator/health 
```

##### Installing the APP

Once your development environment is setup, we can go ahead and get the project into your IDE and run it. So please follow the next steps to do so:

1. Clone the repo into your local: https://gitlab.com/bolcom/katherine-alfaro-ramirez.git or download the zip folder.

```
git clone https://gitlab.com/bolcom/katherine-alfaro-ramirez.git
```

2. Import the project into your IDE: File -> New -> Project from existing sources... and follow the wizard.

3. Try Clean and Build first to make sure everything is compiled and ready. 

4. Run the project: This is a Spring Boot project, so just go to src/main/java/com/bol/kalaha/KalahaApplication.java and right click on it -> Run.

5. The project is running under: http://localhost:8080/


### Other endpoints available 

To see the documentation of the endpoints available you can refer to Swagger.

1. Run the app following the step #4 of the Installing section

2. Open a browser and type:

```
http://localhost:8080/swagger-ui.html#/

```

You’ll find the description for the endpoints here

```
http://localhost:8080/swagger-ui.html#/kalaha-controller

```


### Running the tests

For this project, we have one integration test, and several unit test, for the service and controller layers, under the src/main/test path.


## Web User Interface

The GUI was done using Thymeleaf, so it's a really simple interface. It's controlled by KalahaController.java and it uses the index.html under /resources/templates folder

To access the GUI you can hit this endpoint:

```
http://localhost:8080/

```

#Improvements
A functional UI that allows to follow the logic of the game.

## Author

* **Katherine Alfaro Ramirez** - *Personal GitHub ->* [kalfie88](https://github.com/kalfie88)
