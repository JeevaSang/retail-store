###Retail Store 
1. Import as a existing maven project

2. Open terminal and navigate the project folder

3. Run as "mvn clean install" this will run test cases and generate coverage report

4. To check coverage report - Naviagate to project-> target -> site -> jacoco and open index.html file

4. To start the application, run "mvn spring-boot:run" in terminal

5. Launch following url in browser http://localhost:8080/swagger-ui.html

6. Once launched, Scroll to bottom of page 

7. Provide following to test the API
	a) Product.json available under src/test/resources folder as the POST body
	b) Any of following ("REG01,REG02,REG03,REG04") as user_id.
	




