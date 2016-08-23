SDK (Client + Domain Objects) for access and query to the SII TransitServer, which provides time-tables and real-time delay data for buses running in the South Tyrol region.

This SDK is for the version of TransitService currently in production; a new API is actively developed to make it mode general and useful, and to include more information. 

Documentation [can be found here](https://docs.google.com/a/servizist.it/document/d/1jJmH9hMi1gZDSZFmXRotRjdUPxXhpjdPRV54luUQxPI/edit?usp=sharing): it is comment-enabled, any feedback is welcome.

#Getting started

##Obtrain access credentials

Please contact `developer-support` at `servizist.it` to request your access credentials.

Once you have them, copy `application.properties.example` to `application.properties`:

    ldematte:/projects/TransitServerSDK$ cp src/main/resources/application.properties.example src/main/resources/application.properties
    
Edit the newly created `application.properties`. At the bottom, you will find these lines:

    baseUri=baseUri
    username=username
    password=password

Substitute `username` and `password` with your new credentials.
Also, set `baseUri` to the URL we will provide you.

##Build

This project is built with Maven. You just need to follow the usual steps:

    ldematte@client13-207:/projects/TransitServerSDK$ mvn install
