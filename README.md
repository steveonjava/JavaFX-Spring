JavaFX-Spring
=============

Example application demonstrating integration of JavaFX and Spring technologies on the client and server.  For more details about the technologies used, and a details of the code, please refer to the following 3 part blog series:

* [JavaFX in Spring Day 1 - Application Initialization](http://steveonjava.com/javafx-and-spring-day-1)
* [JavaFX in Spring Day 2 - Configuration and FXML](http://steveonjava.com/javafx-in-spring-day-2)
* [JavaFX in Spring Day 3 - Authentication and Authorization](http://steveonjava.com/javafx-in-spring-day-3)

To run this example, you will need to build and run the server and client projects individually using maven.  You can either do this via an IDE or from the command line.

To start with, please make sure you have the following prerequisites:

* Maven (3.x or higher)
* JDK 7 (update 4 or higher)

The command line steps to get this up and running are:

    cd server
    mvn jetty:run
    cd ..
    cd client
    mvn compile exec:java

If it doesn't work, make sure that maven is running the right version of java by calling "mvn -version".  If you are still having trouble, check out the blogs mentioned above, and post if your issue is not resolved.