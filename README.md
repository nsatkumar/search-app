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

<ul>
<li> A web module <strong>searchrest-app</strong> which holds the restful service implementation using the Spring Rest Controller
<li> A <strong>searchfacade</strong> java module which acts as a bridge between the searchrest-app and the searchservice java module. This follows the facade design pattern to decouple the web module from the service module and takes care of the orchestration
<li> A <strong>searchservice</strong> java module which is the core module which performs the search using Apache Lucene
</ul>

2. checkout all the 3 projects searchrest-app, searchfacade and searchservice

3. These modules use the logback logging implementation and to change the directory location for the logfiles, please edit the logback.xml in the src\main\resources\ folder under the searchrest-app web application

4. indexdir property of the searchservice.properties file in the searchservice module needs to be edited to specify the respective index directory where the files were indexed and stored by Apache Lucene. This is the location which will be used to perform the search.

5. These modules uses maven as the build framework and all the required dependencies are defined in the respective pom.xml files

6. Build the modules in the order given below,

	<ol>
	<li>searchservice
	<li>searchfacade
	<li>searchrest-app
	</ol>
	
Section C: Steps to run the Application
----------------------------------------

a. Create a Apache Tomcat web server instance  in the Eclipse IDE and deploy the searchrest-app web appication and start the server
b. Access the restful service by accessing the http url http://localhost:8080/searchrest-app/search using any restful client. We can use the google's Postman restful client to test the service. Please note that the "localhost" and "8080" refers to the host and port of the server in which the application is deployed.
c. Open the Postman app and select "Post" as the http method, and in the body section, select "raw" and choose the content type as JSON (application/json) and enter the search query to be performed in the format higlighted below,

{
"query":"$text to be searched$"
}

Note: $text to be searched$ needs to be replaced with the text to be searched

Section D: TO DO
----------------

a. Aggregator pom 
b. Adding some kind of a watcher implementation to watch the data files for any changes and trigger the automatic re-indexing
c. search for phrases

Section E: Alternatives Considered
----------------------------------

1. Initially thought of going with the search implementation using plain Java API but haven't went ahead with that approach due to the points highlighted below,

<ul>
<li> Doesn't want to re-invent search implementation when there are proven search API's already available
<li> Scalability - Enterprise search engine implementations are available which are performance agnostic and scalable
</ul>

2. Elastic Search is definitely another alternative that can be looked up on which provides rest api interfaces to perform the search operations. The only reason why this was not taken up was to come up with a restful service implementation of our own which deals with the Apache Lucene search engine.

