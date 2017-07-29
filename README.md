# Fabflix #

***

This README would normally document whatever steps are necessary to get your application up and running.

### Useful commands ###
Run Project 2 on AWS

    cd ../../var/lib/tomcat7/webapps/fabflix/WEB-INF/sources
    sudo javac -d ../classes -classpath ../lib/servlet-api.jar:../lib/mysql-connector-java-5.0.8-bin.jar movies/*.java

    cd ../../var/lib/tomcat7/webapps/fabflix/
    sudo jar cvf ../fabflix.war *

    tail -f -n100 catalina.2016-04-17.log 


Run JDBC program:

    java -classpath <currentDirectory>:<connectorDirectory> <programExecutable>

Compile source code into destination folder:

    javac *.java -d ../bin/

Run program in Ubuntu:

    ## change into App directory first
    cd fablix/App
    java -classpath ./:../mysql-connector-java-5.0.8/mysql-connector-java-5.0.8-bin.jar DriverProgram

### mySQL commands ###
Running in shell:

    mysql -u root -p
    mysql < yourFile.sql
    mysql db_name < yourFile.sql (if file doesn't specify a database)
    mysql -u root -p db_name < yourFile.sql
    
Running in mySQL:
    
    ## change directory to where sql files are
    cd fablix
    source yourFile.sql

### Apache Tomcat ###

    sudo service tomcat7 start
    sudo service tomcat7 stop 
    sudo apt-get install tomcat7
    xdg-open http://localhost ## not sure what this is. Found on piazza

### Ubuntu Server ###

SSH into Amazon ubuntu server:

    ssh -i Desktop/cs122b-key.pem.txt ubuntu@52.36.197.4

Windows Login:

    ubuntu@ec2-52-36-197-4.us-west-2.compute.amazonaws.com

AWS Ubuntu Instance:

    ## user:admin password:cs122b
    http://52.36.197.4:8080
    http://52.36.197.4:8080/manager/html

Public IP:
    
    52.36.197.4

Public DNS:

    ec2-52-36-197-4.us-west-2.compute.amazonaws.com

Instance ID:

    i-1eb281d9

### Unix commands ###

    sudo apt-get update
    sudo apt-get install <program>