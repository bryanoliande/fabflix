<%@page import="
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<%@ page import="movies.*"%>

<%@ include file="header.jsp" %>
<div class="container">
<div class="row"><h1 class="text-center">ReadMe</h1></div>
<div class="row">
<pre>
Ubuntu AWS Instructions

To recompile class files in tomcat7 directory:
cd
cd ../../var/lib/tomcat7/webapps/fabflix/WEB-INF/sources
sudo javac -d ../classes -classpath ../lib/servlet-api.jar:../lib/mysql-connector-java-5.0.8-bin.jar:../lib/javax.json-1.0.jar:../lib/recaptcha4j-0.0.7.jar:../lib/java-json.jar movies/*.java dashboard/*.java recaptcha/*.java fabflixmobile/*.java

To recompile war file tomcat7 directory:
cd
cd ../../var/lib/tomcat7/webapps/fabflix/
sudo jar cvf ../fabflix.war *

To recompile class files from ubunutu home directory:
cd
cd fabflix/fabflix/WEB-INF/sources/
sudo javac -d ../classes -classpath ../lib/servlet-api.jar:../lib/mysql-connector-java-5.0.8-bin.jar:../lib/javax.json-1.0.jar:../lib/recaptcha4j-0.0.7.jar:../lib/java-json.jar movies/*.java dashboard/*.java recaptcha/*.java fabflixmobile/*.java

To recompile war file from ubunutu home directory:
cd
cd fabflix/fabflix
sudo jar cvf ../fabflix.war *

USE WinScp and retrieve fabflix.war from directory fabflix
Log into the tomcat manager, upload and deploy the war file.
http://52.36.197.4:8080/manager/html



To compile and run the log processor:

javac ProcessLog.java
java ProcessLog


Every time you run a Jmeter Test, you must delete the current catalina.out file in order to clear results from past tests:

	cd /var/lib/tomcat7/logs
	sudo rm catalina.out


and restart tomcat: sudo service tomcat7 restart

</pre>
</div>
</div>
</body>
</html>