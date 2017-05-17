#!/bin/bash
#sleep 90
export LANG=en_US.UTF-8
export LC_ALL=en_US.UTF-8
tagName=$1
DATE_START=`/bin/date "+%Y-%m-%d %T"`
DATE_BACKUP=`/bin/date "+%Y%m%d"`
dir_base='/opt/bootstrap'
deploy_name='bootstrap'
dir_git='/opt/bootstrap/git_src'
dir_backup='/opt/bootstrap/backup'
dir_deploy='/opt/bootstrap/bootstrap'
dir_mvntarget='/opt/bootstrap/git_src/target/bootstrap'

#下载代码
#cd /opt/bootstrap
#git XXXX git_src
#更新代码
echo "${DATE_START} start pull code..."
cd ${dir_git}
rm -rf ${dir_git}/target

git pull
#git branch branch_$tagName $tagName
#git checkout branch_$tagName
echo "pull code over"

mvn clean install -Dmaven.test.skip=true
echo "mvn install over"

#将备份的配置文件-复制到发布后的文件夹中
\cp -r ${dir_backup}/** ${dir_mvntarget}/

#备份文件
echo "backup..."
cd ${dir_base}
tar -cf tars/sysname_${DATE_BACKUP}.tar ${deploy_name}


#停tomcat并执行SQL
service tomcat stop
sleep 10
killall -9 java
#mysql -h IP -u"user" -p"password" bootstrap  < /opt/bootstrap/db_sql/db.sql

rm -rf ${dir_deploy}
mkdir ${dir_deploy}

#复制文件到运行环境
\cp -r ${dir_mvntarget}/** ${dir_deploy}/
rm -rf ${dir_git}/target

echo "启动本机"
rm -rf /opt/apache-tomcat-8.0.20/work
service tomcat start
sleep 10
echo "本机tomcat已经启动" 

echo "升级完成"