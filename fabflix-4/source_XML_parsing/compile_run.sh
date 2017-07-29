#!/bin/bash

javac -d classes -classpath ../WEB-INF/lib/servlet-api.jar:../WEB-INF/lib/mysql-connector-java-5.0.8-bin.jar:../WEB-INF/lib/javax.json-1.0.jar src/movies/*.java src/*.java
java -classpath classes:../WEB-INF/lib/servlet-api.jar:../WEB-INF/lib/mysql-connector-java-5.0.8-bin.jar:../WEB-INF/lib/javax.json-1.0.jar MovieParser
