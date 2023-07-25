# Rest-Assured-Maven
Rest assured-maven example

## Prerequisites
These should be installed on your machine:
- java
- maven
- git

## Running the tests 

    mvn clean test

## Run Allure Reports

Install Allure Using Homebrew:

    brew install allure


Make sure you have surefire plugin in your pom xml and then run your tests as normal - 'mvn clean test'
After running the tests then run below command


    allure serve path/to/yuor/target/surefire-reports

    e.g

    Allure serve Users/admin/Documents/my-apps/rest-assured-maven/target/surefire-reports
