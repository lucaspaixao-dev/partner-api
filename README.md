# Partner API

This API was develop with: 
  - [Kotlin (JDK 8)](https://kotlinlang.org/)
  - [Ktor](https://ktor.io/)
  - [Koin](https://insert-koin.io/)
  - [MongoDB](https://www.mongodb.com/)
  - [Docker](https://www.docker.com/)
  - [OpenApi 3](https://swagger.io/docs/specification/about/)
  
# How to execute
 - Check if you have installed the [docker-compose](https://docs.docker.com/compose/gettingstarted/) in your computer
 - Execute the follow command in the project root: ```
                                                       $ docker-compose build
                                                       ```
 - Execute the follow command in the project root: ```
                                            $ docker-compose up
                                            ```
 - For test, execute the follow command on terminal: ```
                                        $ curl --location --request GET 'localhost:8182/health-check'
                                        
# Libraries
 - [deteKt](https://github.com/arturbosch/detekt) to code complexity
 - [ktlint](https://github.com/JLLeitschuh/ktlint-gradle) to code style analyze
 - [jacoco](https://gist.github.com/mrsasha/384a19f97cdeba5b5c2ea55f930fccd4) to code coverage  ```
                                                                                                   minimum = 0.80                    ```     

# Tests
 - Unitary Tests for the business rules;
 - Component Test for the contract and expected http responses.    
                                                                                               
# Documentation
Open spec.yml on root project in [open api web](https://editor.swagger.io/)

# Observation
I didn't write the component test for search by latitude and longitude because the library that I have used in this project
doest not support the $geoNear.

https://github.com/bwaldvogel/mongo-java-server/issues/138