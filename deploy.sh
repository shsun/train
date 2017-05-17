#!/bin/bash

clear;

echo "1--------->> $TOMCAT_HOME";
echo "2--------->>${TOMCAT_HOME}";



sudo rm -rf $TOMCAT_HOME/webapps/*;

sudo rm -rf target;
sudo mvn clean package;

echo "copy war to webapps"
cp -rfv ./target/*.war $TOMCAT_HOME/webapps;


#停tomcat并执行SQL
#service tomcat stop
sudo bash $TOMCAT_HOME/bin/shutdown.sh;
sleep 2
killall -9 java


echo "start tomcat"
rm -rf $TOMCAT_HOME/work
sudo chmod 777 $TOMCAT_HOME/bin/*.sh;
#service tomcat start
#bash $TOMCAT_HOME/bin/startup.sh;
sudo bash $TOMCAT_HOME/bin/catalina.sh jpda start;
sleep 2
echo "start tomcat done..........." 

tail -f $TOMCAT_HOME/logs/catalina.out;
