# Spring Boot, JPA, MVC, Security, Validation, AOP, Hibernate, MySQL, JWT, Maven, Lombok, JUnit, Mockito, Thymeleaf, HATEOAS, REST API
Restful API for social network

## Setup

**1. Clone the application**

```bash
https://github.com/MarcinCzekaj00/Spring-Social.git
```

**2. Create Mysql database**
```bash
create database social_network;
```
**3. Change mysql username and password**

+ open `src/main/resources/application.properties`
+ change `spring.datasource.username` and `spring.datasource.password`

**4. Run the app using maven**

```bash
mvn spring-boot:run
```

**5. If you want to fill the database with ready test data:**
+ **After the first launch** of the application, copy the file `data/data.sql` and paste it into `src/main/resources/`
+ Rerun the application or ```mvn compile```

The app will start running at <http://localhost:8080> but only <http://localhost:8080/register> has GUI where you can create your own user to login

**6. If you use Postman:**
+ Enter Request URL <http://localhost:8080/login>
+ Set method POST
+ Enter the "username" and "password" as you registered
+ Copy your token from Headers -> Authorization
+ Paste it into Authorization -> Bearer Token -> Token (Your token is valid for 2h!)

**7. Enjoy!**

Pssst! If you want to have access to all possibilities, use username "admin" and password "password" :)




## Explore Rest APIs

### Auth

| Method | Url | Decription |
| ------ | --- | ---------- | 
| POST   | /login | Sign up |

Example:

```json
{
	"username": "admin", 
        "password":"password"
}
```
### Account info

| Method | Url | Decription |
| ------ | --- | ---------- | 
| GET   | /account | Account info only for logged in users |


### User

| Method | Url | Description |
| ------ | --- | ----------- |
| GET    | /users | Get all users (only for admin) |
| GET    | /users/{id} | Get user by id |
| GET    | /users/{id}/info | Get user info by id (only for admin) |
| POST    | /users | Add user | 
| PUT    | /users | Edit user |
| PUT    | /users/{id} | Edit single user |
| Delete    | /users/{id} | Delete user by id with user's details, posts and comments|

Example POST /users
```json
{
  "firstName": "Daemon",
  "lastName": "Kocmyrzowski",
  "email": "test341@wp.pl"
}
```

Example PUT /users
```json
{
  "userId": 5,
  "firstName": "New First Name",
  "lastName": "Kocmyrzowski",
  "email": "test341@wp.pl"
}
```

Example PUT /users/{id}
```json
{
  "firstName": "Another Name",
  "lastName": "Kocmyrzowski",
  "email": "test341@wp.pl"
}
```

### User details

| Method | Url | Description |
| ------ | --- | ----------- |
| GET    | /users/{userId}/details | Get user's details |
| POST    | /users/{userId}/details | Add user's details |
| PUT    | /users/{userId}/details | Edit user's details |
| DELETE    | /users/{userId}/details | Delete user's details |

Example POST /users/{userId}/details
```json
{
  "dateOfBirth": "1995-02-01T00:00:00",
  "phoneNumber": 847253841
}
```

Example PUT /users/{userId}/details
```json
{
  "phoneNumber": 572837327
}
```

### Relationship

| Method | Url | Description |
| ------ | --- | ----------- |
| GET    | /users/{userId}/friends | Get user's friends (shows only not deleted users) |
| POST    | /users/{userId}/friends | Add friend |
| DELETE    | /users/{userId}/friends | Delete user's friend |

Example POST /users/{userId}/friends

First get the user you want to add, then paste
```json
{
  "userId": 6,
  "firstName": "John",
  "lastName": "Travis",
  "email": "test4214@wp.pl",
  "friends": [],
  "posts": []
}
```

Example DELETE /users/{userId}/friends

First get the user you want to delete, then paste
```json
{
  "userId": 6,
  "firstName": "John",
  "lastName": "Travis",
  "email": "test4214@wp.pl",
  "friends": [],
  "posts": []
}
```

### Post

| Method | Url | Description |
| ------ | --- | ----------- |
| GET    | /posts | Get all posts without comments |
| GET    | /posts/comments | Get all posts with comments |
| GET    | /posts/{id} | Get single post with comments |
| POST   | /posts | Add new post |
| PUT    | /posts | Edit post |
| PUT    | /posts/{id} | Edit single post |
| DELETE    | /posts/{id} | Delete post with comments |


Example POST /posts

```json
{
  "content":"New Post!!",
  "timeCreated":"2020-02-02T00:00:00"
}
```

Example PUT /posts

```json
{
  "postId": 4,
  "content": "Sorry guys!!",
  "timeCreated": "2020-02-02T00:00:00"
}
```

Example PUT /posts/{id}

```json
{
  "content": "Thats correct!!",
  "timeCreated": "2020-02-02T00:00:00"
}
```

### Comment

| Method | Url | Description |
| ------ | --- | ----------- |
| GET    | /comments | Get all comments |
| GET    | /posts/{postId}/comments | Get comments from post |
| GET    | /posts/{postId}/comments/{commentId} | Get single comment from post |
| POST   | /posts/{postId}/comments | Add new comment |
| PUT    | /posts/{postId}/comments | Edit comment |
| PUT    | /posts/{postId}/comments/{commentId} | Edit single comment |
| DELETE    | /posts/{id} | Delete comment |

Example POST /posts/{postId}/comments

```json
{
  "content": "New comment!",
  "timeCreated": "2020-02-02T00:00:00"
}
```

Example PUT /posts/{postId}/comments

```json
{
  "commentId": 10,
  "postId": 4,
  "content": "Edited comment!!!!!",
  "timeCreated": "2020-02-02T00:00:00"
}
```

Example PUT /posts/{postId}/comments/{commentId}

```json
{
  "content": "Edited comment one again :)",
  "timeCreated": "2020-02-02T00:00:00"
}
```

## More about project
* src/main/java/aspect - contains one class to show AOP knowledge (except Advices class in exception package)
* src/main/java/exception - contains exceptions __ONLY__ for comments and posts. I will add the rest soon
* src/test/java - contains just example integration tests for controller and service. Also contains example unit tests for controller helpers