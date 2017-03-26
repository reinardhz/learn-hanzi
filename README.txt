<encoding>UTF-8 BOM</encodin>
This is a project that primary function is to monitor the progress of the learned Chinese characters.
There are only 1 actor in this system, which is Admin.

The General Requirements:
* Admin can add the category of the chinese characters (hanzi).
* Admin can input hanzi to database.
* Each hanzi belong to one specific group name.
* Each hanzi could be sort by date added.
* Admin cannot add the already inputted hanzi.


Technology:
* tomcat7-maven-plugin
* Spring MVC
* Hibernate 
* PostgreSQL
* HTML
* Javascript.
* Bootstrap (make it mobilesafe)

Note: 
1. Client request all hanzi data.
2. Convert All Java entity into JSON, put it into JSON Array.
3. Send data back to client.
4. Read the json, convert into table.
5. Add input box down the page.
6. If user input, then click add button, then use ajax to check whether the inputted data is already exist?
If exist, show warning, if not, add to database.


This project use "tomcat7-maven-plugin":
<groupId>org.apache.tomcat.maven</groupId>
<artifactId>tomcat7-maven-plugin</artifactId>
<version>2.2</version>


tomcat7-maven-plugin Server is compatible with:
* Java 6 and later (7 and later for WebSocket).
* Servlet 3.0
* JSP 2.2
* Expression Language 2.2

source: (http://tomcat.apache.org/whichversion.html)

This is the important step, to develop this application:

1. Download and install Apache TomEE plume 7.0.3
(http://tomee.apache.org/download-ng.html)

2. Install Tomee in the Eclipse:
(http://tomee.apache.org/tomee-and-eclipse.html)


3. Install Java Development Kit 7 (filename: jdk-7u79-windows-x64.exe)

4. Edit Installed JRE in Eclipse to use java 1.7:
a. Navigate to Windows-> Preference->Java->Installed JRE
b. Edit the current jdk to jdk version 1.7 (path of the jdk home)

c. Navigate to Windows-> Preference->Java->Installed JRE
d. click JavaSE-1.7, then tick on the installed JDK

e. Navigate to Windows-> Preference->Java->Compiler->
f. Change the Compile Compliance Level to 1.7, then click ok.


5. Add JBOSS EAP 6.2.0 Server Runtime to Eclipse Neon:
a. Open Eclipse Neon.
b. Window->Preference->Server->Runtime Environments -> Add..
c. Choose: Red Hat JBoss Middleware->Red Hat JBoss Enterprise Application Platform 6.1+ Runtime
e. Input the Home Directory, to the directory of JBOSS EAP 6.2.0 Server
Example: E:\Program Files\EAP-6.2.0\jboss-eap-6.2
Execution Environtment: Java SE 1.7
Server Base Directory: standalone
Configuration File: standalone.xml
f. Click "Finish" button.

6. Add new server in the Eclipse
a.Window->Show View->Other...->Server->Servers, click "Ok" button.
b. On the servers tab, right click, and choose New->Server
c. Choose "Red Hat JBoss Middleware->Red Hat JBoss Enterprise Application Platform 6.1+ Runtime"
Fill in the Server's host name with: "localhost" and fill in the server name.
Note: It's up to you to input the server name.

d. Click on "local" radio button. (The server is local)
e. Click on "Filesystem and shell operations" radio button. (Controlled by Filesystem and shell operations)
f. Choose the previously add runtime.
g. Click "finish" button.

7. Now you could start and stop the server.

8. To deploy the web application:
a. Start the JBOSS EAP-6.2 server.
b. Copy .war file to deployments folder (Example: "E:\Program Files\EAP-6.2.0\jboss-eap-6.2\standalone\deployments")
c. wait until there is new file with name ending with ".deployed" is appear.

9. To undeploy the already deployed web application:
a. Start the JBOSS EAP-6.2 server.
b. delete the file with name ending with ".deployed"
c. then server will undeploy and create file with name ending with ".undeployed"

10. To replace currently deployed zipped content with a new version and deploy it:
a. Start the JBOSS EAP-6.2 server.
b. Copy the same name .war file to deployments folder.

11. Install JDBC Oracle Driver in maven local repository.
a. Open command line.
b. Execute "mvn install:install-file -Dfile=E:\app\OracleHomeUser1\product\12.1.0\dbhome_1\jdbc\lib\ojdbc7.jar -DgroupId=com.oracle -DartifactId=ojdbc -Dversion=7.0 -Dpackaging=jar"

12. Add to pom.xml these code:
<dependency>
	<groupId>com.oracle</groupId>
  	<artifactId>ojdbc</artifactId>
  	<version>7.0</version>
	<scope>compile</scope>
</dependency>

<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-jdbc</artifactId>
	<version>3.2.18.RELEASE</version>
</dependency>

13. Use PostMan to test the web service.


===This are steps to backup the database===
1. Open cmd, and go to the directory that contains file "pg_dump.exe".

2. Type: "pg_dump -h [hostname] -p [listening port number] -U [username] -d [database name] > [filename]"
Example: "pg_dump -h localhost -p 5432 -U learnhanzi -d learnhanzi_database > E:\backup.sql"

3. Press enter, then type the password, then press enter again.

4. System will create new file name "backup.sql" in E drive, that contains backup in learnhanzi_database.


===This are steps to restore the database from backup file===
1. Create username and database name that same as the previous backup file from.

2. Open cmd, and go to the directory that contains file "psql.exe".

3. Type: "psql -h [hostname] -p [listening port number] -U [username] -d [database name] < [backup filename]"
Example: "psql -h localhost -p 5432 -U learnhanzi - d learnhanzi_database < E:\backup.sql"

4. Press enter, then type the password, then press enter again.

5. System will execute the backup file to create all database object for backup file.