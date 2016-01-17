**AngularMeetsVertx** is a simple project to test a simple Vertx-based HTTP server
 that hosts an AngularJS web application.

## Project Structure
This is a java project that uses gradle as a build tool.

The java source files are located in the `src/main/java` folder. The files that are relevant to the web application
can be found in the `src/main/resources/webroot` folder.

## Requirements

* Java 8
* Gradle

## Installation

### From IntelliJ
To install the project:

* Import the project from GitHub as a Gradle project
* Build the project with build.gradle
* Run the main method of the class com.eu.dobrev.VertxMain
* You can then access the web application from http://localhost:9999/ 
 
 
## Project Description

The web application is a very basic call log viewer. When you start the application you have to enter a phone number
(acts as a username). For now, no validation is integrated, so any values are allowed. After you enter the phone number,
 you will see the calls that are somehow connected to this number (caller or callee) and some additional details.
  
There is also a simple call simulator that can be used to simulate a call to a desired number. After you ended your simulation, 
the call entry will be saved to the server. If the save was successful you should see the new entry in the call log table.

With the help of the *change number* button in the upper right corner you can reset your number (logout) and then enter
 a new desired number (login)
 
The HTTP server is currently using only in-memory storage, so after each restart all call entries will be lost!
  
## Notes

THE PROJECT'S SOURCE CODE IS CURRENTLY NOT WELL DOCUMENTED - THIS WILL PROBABLY BE CHANGED IN THE FUTURE





