Setting up local database
```
mongo
use lunchgowhere 
db.createUser({
    user: "lgwadmin",
    pwd:  passwordPrompt(),   // or cleartext password
    roles: [ { role: "readWrite", db: "lunchgowhere" } ]
})
```

Then, enter a password for the database admin. (`secretpassword`)

## API documentation
This section lists down all the api that application supports. Text in curl braces denotes that it needs to replace with the appropriate values. Replace `{version}` to `v1` and the `{object}` in the path below:

`api/{version}/{object}`

For example, to use the `user` object, the path will be:
`api/v1/users/....`

# users

| Path    | Method |    Description    |                      Request                       |  Response  |
|:--------|--------|:-----------------:|:--------------------------------------------------:|:----------:|
| /       | GET    |   Get all users   |                                                    | [...Users] |
| /create | POST   | Create a new user |  {name:string, username:string, password:string}   |            |
| /update | PUT    | Updates new user  | {name?:string, username?:string, password?:string} |            |

# session

| Path                    | Method |        Description        | Request |            Response             |
|:------------------------|--------|:-------------------------:|:-------:|:-------------------------------:|
| /                       | GET    |     Gets all session      |         |         [{id: string}]          |
| /create                 | POST   |     Create a session      |         | {name: string, lunchDate: Date} |
| /history                | GET    |     Gets all session      |         |         [{id: string}]          |
| /{sessionId}            | GET    |  Gets specified session   |         |          {id: string}           |
| /{sessionId}/end        | PUT    |  Ends an active session   |         |                                 |
| /{sessionId}/submission | POST   |   Submits a restaurant    |         |                                 |
|                         | PUT    | Updates a submitted entry |         |                                 |

# restaurant

| Path            | Method |     Description      |      Request      |    Response    |
|:----------------|--------|:--------------------:|:-----------------:|:--------------:|
| /s={searchText} | GET    | Gets all restaurant  | searchText:string | [{id: string}] |
| /{restaurantId} | POST   | Creates a restaurant |                   |  {id: string}  |
| /history        | GET    |   Gets all session   |                   | [{id: string}] |