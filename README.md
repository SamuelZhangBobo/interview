# Interview - Sam

### 1. How to run this application:
    1.1 Preparations:
        a. Find a SqlServer DB which is connectable from local.
        b. Open the moikiitos project.
        c. Check and execute the sql file ../src/main/resources/init.sql in one of your database, so that to create the required DB SCHEMA and DB tables. 
            (Note: Make sure no existing SCHEMA "align" under your database)
        d. Find and open the configuration file ../src/main/resources/application.yaml, configure your database hostname, database name and database user/password.
            Replacing all the <DB Hostname> to your DB hostname or IP address.
            Replacing all the <Database Name> to your database name.
            Replacing all the <username> to your database username.
            Replacing all the <password> to your database password.

    1.2 Run moikiitos application:
        a. Launch the moikiitos application locally.
        b. Visit http://localhost:8089/moikiitos/doc.html to find all the APIs in swagger.
        c. Open Postman or other tools to try out those APIs
            (Note: Except for below APIs, the other APIs are requiring a token in request header. The request header format - Key: Authorization; Value: Bearer <Token>)
                /moikiitos/user/login
                /moikiitos/user/register
            (Note: The Token will be generated everytime user is log in.)