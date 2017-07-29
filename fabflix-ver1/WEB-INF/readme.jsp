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
sudo javac -d ../classes -classpath ../lib/servlet-api.jar:../lib/mysql-connector-java-5.0.8-bin.jar movies/*.java

To recompile war file tomcat7 directory:
cd
cd ../../var/lib/tomcat7/webapps/fabflix/
sudo jar cvf ../fabflix.war *

To recompile class files from ubunutu home directory:
cd
cd fabflix/fabflix/WEB-INF/sources/
sudo javac -d ../classes -classpath ../lib/servlet-api.jar:../lib/mysql-connector-java-5.0.8-bin.jar movies/*.java

To recompile war file from ubunutu home directory:
cd
cd fabflix/fabflix
sudo jar cvf ../fabflix.war *

USE WinScp and retrieve fabflix.war from directory fabflix
Log into the tomcat manager, upload and deploy the war file.
http://52.36.197.4:8080/manager/html
</pre>
</div>
</div>
</body>
</html>