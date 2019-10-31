运行说明

环境依赖
   java：1.8.0+ ; 数据库: MYSQL 5.5 + /SqlServer 2008 +/oracle 12C +
   WebServer： tomcat 7.0+


1. 导入docs下面对应的数据库的sql 文件
    修改配置文件 src\main\resources\dbconfig.properties 


2.用IntelliJ IDEA 打开项目所在的目录
  在IntelliJ IDEA中 ，菜单 Run->Edit Configuation... -> Add Tomcat Server ->Tomcat Congfigration (local) 
  切换 deployment 点 + 选择 artifacts
   
  
  编译通过后，会自动打开浏览器
  在浏览器中输入 http://localhost:8080/CoolJava 访问
  系统默认的用户名密码: admin/admin
  备注： 如果报类错误，需要在idea中，在pom.xml文件上点右键,然后点 Maven->Reimport
