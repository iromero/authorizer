<h1>Implementation for the Authorizer</h1> 

<h2>Instructions to build the application</h2>
<h3>Prerequisites</h3>
You will need to have maven 3 and java 8 installed to be able to build the application.

<ul>
<li>Simple Instructions to install maven 3+: https://maven.apache.org/install.html</li>

<li>Simple instructions to install java 8: https://www.java.com/en/download/help/linux_x64_install.xml</li> 
</ul>


<h3>Building the application</h3>
Open a terminal and in the root of the application folder:

mvn clean package

<h2>Instructions to run the application</h2>

There are two ways to run and test the application.

In the root of the application folder you  can run the application in one of the two following ways:

<ol>
<li>java -jar target/Authorizer-1.0-shaded.jar < fileContainingJsons
</li>
<li>java -jar target/Authorizer-1.0-shaded.jar</li>
</ol>

<h3>First way</h3>
The first way process a file containing bank operations in json format , once per line. The program will no process more inputs after it finish to process the file. For example:

java -jar target/Authorizer-1.0-shaded.jar < /usr/data/severalTransactions

<h3>Second way</h3>
In The second way once you execute the application you can start entering in a manual way the json text that you want to process. The program will finish one you enter the word "stop". For example:

java -jar target/Authorizer-1.0-shaded.jar

{"account": {"active-card": true, "available-limit": 100}}

<h2>Relevant code design choices</h2>
<ul>
<li>For the collection list I choose to use io.vavr.collection.List due that this implements the immutabilty instead of use java.util.List that is mutable.
</li>
<li>
It use a good amount of interfaces. There is not inherance in the code.
</li>
<li>
It use the composition instead of the inherance for the classes AccountOperation and TransactionOperation and that is a good practice "Favor the composition over the inherance".
</li>
<li>
In all of the code implementation it makes uses of the immutability.
</li>
<li>
To favor the immutability I build a object for a counter (NumberOfTransactionsCounter) instead of use a mutable variable. 
</li>
<li>
There is a class for each business rule. The classes for the bussiness rule implements an interface, that helps with the new rules that can appear in the future due that you just will need to create a class with the new business rule and add it to a list of business rules that validates an account or a transaction operation.
</li>
<li>
All the fields of the classes are private and final to avoid that those changes once the object is created. Also there are not setters in the classes. All of that helps to the immutability.
</li>
<li>
Classes that contains data that needs to be compared implements equals and hasCode methods. Also the toString method is implemented in a good amount of classes and that helps a lot in debug process. 
</li>
<li>It uses a double dispatcher pattern to use the correct service to validate a transaction or an account operation.
</li>
<li>
It uses a factory pattern to serialize/deserialize to/from json for the account and transaction operations.
</li>
<li>
All of the code was build using TDD. It helps a lot with the design and refactoring.
</li>
<li>
When running the tests with coverage the resuls are 95% for classes and 85% for methods. 
</li>
<li>There is good amount of unit tests as well as a integration test like ProcessInputOperationServiceTest that test several componets working togeher.
</li>
</ul>




