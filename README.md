# Setting up the application
## Setting up local database
```
mongo
use lunchgowhere 
db.createUser({
    user: "lgwadmin",
    pwd:  passwordPrompt(),   // or cleartext password
    roles: [ { role: "readWrite", db: "lunchgowhere" } ]
})
```

Next, enter a password for the database admin. (`secretpassword`)

Finally, update `src/main/resources/application.properties` to reflect your database details:
```
spring.data.mongodb.authentication-database=lunchgowhere
spring.data.username=lgwadmin
spring.data.password=secretpassword
spring.data.mongodb.database=lunchgowhere
spring.data.mongodb.port=27017
spring.data.mongodb.host=localhost
spring.data.mongodb.auto-index-creation=true
```
## Updating the JWT secrect
This application uses JSON Web Tokens (JWT) to authenticate users. You should update the `application.security.jwt.secret-key` in ``src/main/resources/application.properties`` to another value. A default value is left in this repo for ease of setting up, but it must be changed before deploying to production.

## API documentation
This section lists down all the api that application supports. Text in curl braces denotes that it needs to replace with the appropriate values. Replace `{version}` to `v1` and the `{object}` in the path below:

`api/{version}/{object}`

For example, to use the `user` object, the path will be:
`api/v1/users/....`

# users

| Path    | Method |    Description    |                      Request                       |  Response  |
|:--------|--------|:-----------------:|:--------------------------------------------------:|:----------:|
| /       | GET    |   Get all users   |                                                    | [...Users] |
| /signUp | POST   | Create a new user |  {name:string, username:string, password:string}   |            |
| /update | PUT    | Updates new user  | {name?:string, username?:string, password?:string} |            |

# session

| Path                    | Method |        Description        | Request |                  Response                   |
|:------------------------|--------|:-------------------------:|:-------:|:-------------------------------------------:|
| /                       | GET    |     Gets all session      |         |               [{id: string}]                |
| /create                 | POST   |     Create a session      |         |       {name: string, lunchDate: Date}       |
| /history                | GET    |     Gets all session      |         |               [{id: string}]                |
| /{sessionId}            | GET    |  Gets specified session   |         |                {id: string}                 |
| /{sessionId}/end        | PUT    |  Ends an active session   |         |                {id: string}                 |

# vote

| Path             | Method |         Description         |      Request      |                   Response                    |
|:-----------------|--------|:---------------------------:|:-----------------:|:---------------------------------------------:|
| /                | POST   |         Cast a vote         | searchText:string | [{sessionId: string, restaurantName: string}] |
| /{sessionId}/all | GET    | Get all votes for a session | sessionId:string  |                                               |
| /{sessionId}     | GET    |  Get the requester's vote   | sessionId:string  |                                               |

# restaurant (to be implemented)

| Path            | Method |     Description      |      Request      |    Response    |
|:----------------|--------|:--------------------:|:-----------------:|:--------------:|
| /s={searchText} | GET    | Gets all restaurant  | searchText:string | [{id: string}] |
| /{restaurantId} | POST   | Creates a restaurant |                   |  {id: string}  |
| /history        | GET    |   Gets all session   |                   | [{id: string}] |