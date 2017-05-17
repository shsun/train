本文档将讲述如何将AC中配置的项目配置到本地eclipse、数据库。

一、资料下载

如果配置的AC项目的ExtJS3、ExtJS6，下载的AC项目中不包含ExtJS包，必须要手动下载并添加到对应目录下。

如果配置的AC项目的material、bootstrap，下载的AC项目中已包含相关包，可直接运行。

下载地址：https://yun.baidu.com/s/1o8vrR3G
包含如下资料：
1. ext-3.4.1.zip ==> ExtJS3.4
2. ext-6.2.0-gpl.zip ==> ExtJS6.2
3. ext-6.2.0-gpl-ac.zip ==> AC示例使用的ExtJS6.2包，与ext-6.2.0-gpl.zip的差别在于：删除了没用到的主题，并增加了AC自定义的主题。
4. extjs-docs-6-2 ==> ExtJS6.2 API文档
5. material-1.1.1.zip ==> angular material 1.1.1版本
需要看例子的话，直接解压后，把解压后的文件夹放到tomcat的webapps目录下，然后启动tomcat后直接访问相关项目即可，如：
----localhost:8080/ext-3.4.1
----localhost:8080/extjs6
----localhost:8080/extjs6-doc
二、extjs 3配置

1. 解压ext-3.4.1.zip
2. 将解压出的文件夹放到[src/main/webapp/WEB-INF/resources]内，并将文件夹名改为extjs
3. 复制文件前，先将项目-->属性-->project facets中的java script属性取消勾选，不然验证JS会把eclipse卡死
4. 取消勾选时需要先右键unlock，然后在取消勾选
5. 解压后复制到上述路径，需要将文件夹名改为extjs
三、extjs 6配置

1. 解压ext-6.2.0-gpl-ac.zip 2. 将解压出的文件夹放到[src/main/webapp/WEB-INF/resources]内，并将文件夹名改为extjs6
3. 复制文件前，先将项目-->属性-->project facets中的java script属性取消勾选，不然验证JS会把eclipse卡死
4. 取消勾选时需要先右键unlock，然后在取消勾选
5. 解压后复制到上述路径，需要将文件夹名改为extjs6
四、AC项目配置步骤

1. 下载项目并且解压
2. 数据库初始化方法：
----2.1. 新建mysql 数据库，请用各种方式
----2.2. mysql登录
----2.3. create database 数据库名
----2.4. use 数据库名
----2.5. source src/doc/init.sql
3. 导入项目到eclipse
----File --> Import --> existing maven projects --> 选择解压的文件夹 --> 完成
4. 数据库配置在: src/main/resources/spring/applicationContext.xml
5. 将项目添加到eclipse后启动tomcat，访问项目，如：
----http://localhost:8080/bootstrap
----http://localhost:8080/extjs
----注意发布项目名称eclipse的的项目名，不一定是解压后的文件夹名称
