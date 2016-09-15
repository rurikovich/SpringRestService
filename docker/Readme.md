1) Запускаем Mysql
docker run --name mysql -e MYSQL_ROOT_PASSWORD="1-1040" -e MYSQL_DATABASE=jaxrs -p 3306:3306 -d mysql --character-set-server=utf8 --collation-server=utf8_general_ci


2) Запускаем tomcat

docker run --name tomcat  -p 80:8080 --link mysql:mysql -v "/home/rurik/tomcat/tomcat-users.xml:/usr/local/tomcat/conf/tomcat-users.xml" -v "/home/rurik/tomcat/webapps:/usr/local/tomcat/webapps" -d tomcat:8.0