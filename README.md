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