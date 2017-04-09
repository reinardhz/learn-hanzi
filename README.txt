<encoding>UTF-8 BOM</encoding>
This is a project that primary function is to monitor the progress of the learned Chinese characters.
There are only 1 actor in this system, which is Admin. This project is under development.

The General Requirements:
* Admin can add the category of the chinese characters (hanzi).
* Admin can input hanzi to database.
* Each hanzi belong to one specific group name.
* Each hanzi could be sort by date added.
* Admin cannot add the already inputted hanzi.


Technology:
* Eclipse Neon.
* Embedded Jetty Server 9.3.17.v20170317
* Spring MVC 4.3.7.RELEASE
* Hibernate 5.2.9.Final
* PostgreSQL 9.5.1
* HTML 5
* Javascript
* Bootstrap (make it mobile safe)

Note: 
1. Client request all hanzi data.
2. Convert All Java entity into JSON, put it into JSON Array.
3. Send data back to client.
4. Read the json, convert into table.
5. Add input box down the page.
6. If user input, then click add button, then use ajax to check whether the inputted data is already exist?
If exist, show warning, if not, add to database.

UI:
* show the current time and the time zone, to make sure, javascript insert hanzi with the right timestamp

These are the important steps, to develop this application:

1. Install Java 8.

2. Edit Installed JRE in Eclipse to use java 1.8:
a. Navigate to Windows-> Preference->Java->Installed JRE
b. Edit the current jdk to jdk version 1.8 (path of the jdk home)

c. Navigate to Windows-> Preference->Java->Installed JRE -> Executions Environtment
d. click JavaSE-1.8, then tick on the "JDK 1.8", on the "Compatible JRE" Section.

e. Navigate to Windows-> Preference->Java->Compiler->
f. Change the Compile Compliance Level to 1.8, then click ok.

3. Set the project encoding to UTF-8
a. Right click on the project root -> Click "properties" -> Choose Resources, Choose "Other" radio button -> Choose "UTF-8" from drop down list -> Click "OK" button.

4. Copy the maven war plugin dependency in the pom.xml.

5. Copy the jetty maven plugin dependency in the pom.xml.

6. To package the web app as .war file and run the war:
a. Right click on the project root -> Run as -> Maven build...
b. On the main tab, input:
Name: clean jetty run
Base directory: [your current project directory]
Goals: mvn clean org.eclipse.jetty:jetty-maven-plugin:9.2.21.v20170120:run-war -Djetty.port=9097

c. Click the "Common" tab
d. On the encoding section, click "Other" radio button -> Choose "UTF-8" from drop-down list.
e. Click "Apply" button to save the changes

7. Now you can use "clean jetty run" to clean, package then run the war file of your web application.

8. Use PostMan to test the web service.


===This are steps to backup the database===
1. Open cmd, and go to the directory that contains file "pg_dump.exe".
Example syntax: cd "C:\Program Files\PostgreSQL\9.5\bin"

2. Type: "pg_dump -h [hostname] -p [listening port number] -U [username] -d [database name] > [filename]"
Example syntax: "pg_dump -h localhost -p 5432 -U learnhanzi -d learnhanzi_database > E:\backup.sql"

3. Press enter, then type the password, then press enter again.

4. System will create new file name "backup.sql" in E drive, that contains backup in learnhanzi_database.


===This are steps to restore the database from backup file===
1. Create username and database name that same as the previous backup file from.

2. Open cmd, and go to the directory that contains file "psql.exe".

3. Type: "psql -h [hostname] -p [listening port number] -U [username] -d [database name] < [backup filename]"
Example: "psql -h localhost -p 5432 -U learnhanzi - d learnhanzi_database < E:\backup.sql"

4. Press enter, then type the password, then press enter again.

5. System will execute the backup file to create all database object for backup file.
===============================================================

Note:
* PostgreSql data type mapping to JDBC type, read: https://documentation.progress.com/output/DataDirect/DataDirectCloud/index.html#page/queries/postgresql-data-types.html
* JDBC data type mapping to Java data type, read: https://www.cis.upenn.edu/~bcpierce/courses/629/jdkdocs/guide/jdbc/getstart/mapping.doc.html

