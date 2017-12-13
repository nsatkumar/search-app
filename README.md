# search-app
Search using Apache Lucene

Section A: Steps to Index Text Files using Apache Lucene
---------------------------------------------------------

1. Checkout the project searchutil and import the same in to Eclipse or any other Java IDE
2. Change the Index directory (directory where apache lucene needs to index the given files and store) to an appropriate location of your choice for the variable indexDir in the method createWriter() of SimpleFileIndexer.java and similarly change the Data directory (where you have the text files to be indexed) for the variable dataDir in the main() method of SimpleFileIndexer.java
3. Once you have the text files to be indexed in the respective locations, execute the file SimpleFileIndexer.java to index the files using Apache Lucene

Section B: Steps to setup deploy the Search Application
--------------------------------------------------------

1. The search application has the following modules,

a. A web module searchrest-app which holds the restful service implementation using the Spring Rest Controller
b. A Facade java module which acts as a bridge between the searchrest-app and the searchservice java module. This follows the facade design pattern to decouple the web module from the service module and takes care of the orchestration
c. A searchservice java module which is the core module which performs the search using Apache Lucene

2. checkout all the 3 projects searchrest-app, searchfacade and searchservice

3. These modules use the logback logging implementation and to change the directory location for the logfiles, please edit the logback.xml in the src\main\resources\ folder

4. indexdir property of the searchservice.properties file in the searchservice module needs to be edited to specify the respective index directory where the files were indexed and stored by Apache Lucene. This is the location which will be used to perform the search.

5. These modules uses maven as the build framework and all the required dependencies are defined in the respective pom.xml files

6. Build the modules in the order given below,

	-> searchservice
	-> searchfacade
	-> searchrest-app
	
Section C: Steps to run the Application
----------------------------------------

a. Create a Apache Tomcat web server instance  in the Eclipse IDE and deploy the searchrest-app web appication and start the server
b. Access the restful service by accessing the http url http://localhost:8080/searchrest-app/search using any restful client. We can use the google's Postman restful client to test the service.
c. Open the Postman app and select "Post" as the http method, and in the body section, select "raw" and choose the content type as JSON (application/json) and enter the search query to be performed in the format higlighted below,

{
"query":"<<text to be searched>>"
}

Note: <<text to be searched>> needs to be replaced with the text to be searched