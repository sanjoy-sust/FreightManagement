# ## Standard Enterprise Application Structure using Spring boot
Simple Spring boot project.

This is a project to find all paths and cost between two locations.

**Used Technology :**

* Spring Boot-1.5.7   
* Spring data jpa  
* Google map api  
* Mysql database  
* Tomcat Web Server  

**To run this project you have to follow below steps:**

1. Setup your favourite java IDE which can run Spring based projects.  
2. Setup mysql server. Skip if you have already.  
3. Setup gradle. Skip if you have already.  
4. Download or clone this project.  

5. Configure Database :  
    a) Connect to your mysql server with your credential. Mine is username: root,Password : admin   
    b) Run DDL from "**FreightManagement/server-config/database/freight_management_ddl.sql**"  
    c) Run DML from "**FreightManagement/server-config/database/freight_management_dml.sql**"  
    d) Thats fine for database setup  
    
6. Configure project : 
    a) Change database properties in "**FreightManagement/src/main/resources/application.properties**". 
    change following data.    
    `spring.datasource.url=jdbc:mysql://localhost:3306/freight_management  `   
    `spring.datasource.username=root`    
    `spring.datasource.password=admin`    
    b) Change on logback file "FreightManagement/src/main/resources/application.properties"
     Change  `<property name="DEV_HOME" value="c:/logs" />` value to your favourate location. Unix user must need to change log location.  
    c) Project setup almost done.  
     
 7. Open command promt. Goto project directory.  
 8. Run following command "**gradle clean build**" . Some test code added. You can browse test result from **{ProjectDir}/FreightManagement/build/reports/tests/test/classes/com.fm.assignment.repository.PathRepositoryTest.html**  
 9. If clean build success then run "**gradle bootrun**".   
 10. I think thats all to setup project.  
    

For more details Please check following URLs:

1. https://github.com/sanjoy-sust/FreightManagement/wiki/1.-Installation-guide  
2. https://github.com/sanjoy-sust/FreightManagement/wiki/2.-Description-and-Improvement-Item  
3. https://github.com/sanjoy-sust/FreightManagement/wiki/3.-API-Document  
4. https://github.com/sanjoy-sust/FreightManagement/wiki/4.-Test-Cases  
5. https://github.com/sanjoy-sust/FreightManagement/wiki/5.-Samples-with-test-Cases  


