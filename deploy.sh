#!/bin/bash



rm -rfv ${TOMCAT_HOME}/webapps/*;

mvn clean package;

echo "copy war to webapps"
cp -rfv ./target/*.war ${TOMCAT_HOME}/webapps;


#停tomcat并执行SQL
#service tomcat stop
bash ${TOMCAT_HOME}/bin/shutdown.sh;
sleep 2
killall -9 java


echo "start tomcat"
rm -rf ${TOMCAT_HOME}/work
#service tomcat start
#bash ${TOMCAT_HOME}/bin/startup.sh;
bash ${TOMCAT_HOME}/bin/catalina.sh jpda start;
sleep 2
echo "start tomcat done..........." 

tail -f ${TOMCAT_HOME}/logs/catalina.out;
