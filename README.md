# Ema
Event Management System

Our project helps local communities to organize events throughout the year such as: workshops, seminars, social gatherings, parties and so on. 

The project uses :
> Java 17  
> SpringBoot 3.1.4  
> PostgreSQL  
> Maven  
> Mockito  
> Thymeleaf  
> Lombok  
> SendGrid  
> Swagger


	! Important !  
	
	Make sure that you have (if not -> create) the following file : main/resources/application.properties   
	This file shall contain the DB credentials and the keys for  SendGrid API (see https://sendgrid.com/ )   

	spring.datasource.url = jdbc:postgresql://localhost:5432/YOURDATABASE
	spring.datasource.username=YOUR_USERNAME
	spring.datasource.password=YOUR_PASSWORD
	spring.datasource.driver-class-name=org.postgresql.Driver
	spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
	
    app.sendgrid.key=YOUR_KEY 
    app.sendgrid.templateId=YOUR_KEY
