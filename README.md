# School Grades API - SpringBoot
***
### SUMMARY
***
School Grades API was created with the purpose of building a system where students can
see their own grades and join subjects, teachers can update the grades and staff can add,
update and delete people and subjects.
<br/><br/>

### API LINKS
***
Swagger documentation:
http://localhost:8080/swagger-ui/index.html
<br/><br/>

### IMPLEMENTATIONS
***
- Model Relationships;
- Spring Security with JWT (Authentication/Authorization);
- Swagger;
- Postman Collection;
- Docker Compose.
  <br/><br/>

### METHODS
***
| Request  | Description                                 |
|----------|---------------------------------------------|
| `GET`    | Returns information of one or more records. |
| `POST`   | Used to create new record in DB.            |
| `PUT`    | Updates date from a record.                 |
| `DELETE` | Deletes a record from the DB.               |
<br/>

### RESPONSES
***
| Responses | Description                          |
|-----------|--------------------------------------|
| `200`     | Request executed successfully.       |
| `400`     | Validation errors.                   |
| `403`     | Forbidden Access.                    |
| `404`     | Searched record not found.           |
| `405`     | Method not implemented.              |
| `409`     | Conflict trying to save same record. |
| `500`     | Server error.                        |
<br/>

### AUTHENTICATION - AUTH0
***
Our API uses [AuthO](https://auth0.com/) as a way of authentication/authorization.
<br/><br/>
**Sign Up:**

| Request | Description | Link            |
|---------|-------------|-----------------|
| `POST`  | `SignUp`    | /api/v1/student |
Required JSON body (**Sign Up** endpoint):
```
{
    "firstName": "Example",
    "lastName": "Example",
    "birthDate": "1990-04-23",
    "email": "example@email.com",
    "password": "password"
}
```
**Note:** When someone signs up, it's role is automatically **Student**.

**Login:**

| Request | Description | Link      |
|---------|-------------|-----------|
| `POST`  | `Login`     | /login    |
Required JSON body (**Login** endpoint):
```
{
    "email": "example@email.com",
    "password": "password"
}
```
<br/>

## RESOURCE GROUPS
***
**Note:** All the endpoints that require parameters should be called this way:
```
/api/v1/staff?table=students&id=1
```
Where `table=students` and `id=1` are the parameters.
<br/><br/>

### STUDENT ( /api/v1/student )
***
#### Student Info:
| Request | Description          | Link                |
|---------|----------------------|---------------------|
| `PATCH` | Update Student       | /{studentid}        |
Required JSON body (**Update Student** endpoint):
```
{
    "firstName": "Example",
    "lastName": "Example",
    "birthDate": "1996-07-02",
    "email": "example@email.com",
    "password": "password"
}
```
<br/>

#### Grades:
| Request | Description          | Link                |
|---------|----------------------|---------------------|
| `GET`   | Get Student's Grades | /{studentid}/grades |
<br/>

#### Subjects:
| Request | Description            | Link                  | Parameters           |
|---------|------------------------|-----------------------|----------------------|
| `GET`   | Get Subjects List      | /subjects             |                      |
| `GET`   | Get Student's Subjects | /{studentid}/subjects |                      |
| `PATCH` | Join a Subject         | /join                 | studentid, subjectid |
| `PATCH` | Unjoin a Subject       | /unjoin               | studentid, subjectid |
<br/>

### TEACHER ( /api/v1/teacher )
***
#### Teacher Info:
| Request | Description    | Link         |
|---------|----------------|--------------|
| `PATCH` | Update Teacher | /{teacherid} |
Required JSON body (**Update Teacher** endpoint):
```
{
    "firstName": "Example",
    "lastName": "Example",
    "birthDate": "1996-07-02",
    "email": "example@email.com",
    "password": "password"
}
```
<br/>

#### Grades:
| Request | Description            | Link    | Parameters                      |
|---------|------------------------|---------|---------------------------------|
| `PATCH` | Update Student's Grade | /grades | teacherid, studentid, subjectid |
Required JSON body (**Update Student's Grade** endpoint) (new grade value):
```
20
```
<br/>

#### Subjects:
| Request | Description            | Link                  |
|---------|------------------------|-----------------------|
| `GET`   | Get Teacher's Subjects | /{teacherid}/subjects |
<br/>

#### Students:
| Request | Description             | Link      | Parameters           |
|---------|-------------------------|-----------|----------------------|
| `GET`   | Get Students by Subject | /students | teacherid, subjectid |
<br/>

### STAFF ( /api/v1/staff )
***
#### People:
| Request   | Description                  | Link                                  | Parameters                                    |
|-----------|------------------------------|---------------------------------------|-----------------------------------------------|
| `GET`     | Get People List              | /{table}                              | field, page, pagesize                         |
| `GET`     | Get Person by Id             | /api/v1/staff                         | table, id                                     |
| `GET`     | Get People by Role           | /people/roleid/{roleid}               |                                               |
| `GET`     | Get People by Subject        | /subjects/{subjectid}/table/{table}   |                                               |
| `GET`     | Get Person's Subjects        | /{table}/id/{personid}                |                                               |
| `DELETE`  | Delete Person                | /people/{personid}                    |                                               |
| `PATCH`   | Add Person to a Subject      | /join                                 | table, id (represents person's Id), subjectid |
| `PATCH`   | Remove Person from a Subject | /unjoin                               | table, id (represents person's Id), subjectid |

| Request | Description         | Link          |
|---------|---------------------|---------------|
| `GET`   | Get People by Email | /people/email |
Required JSON body (**Get People by Email** method):
```
example@email.com
```
<br/>

| Request | Description   | Link               |
|---------|---------------|--------------------|
| `POST`  | Add Person    | /people            |
| `PATCH` | Update Person | /person/{personid} |
Required JSON body (**Add Person** and **Update Person** methods):
```
{
    "firstName": "Example",
    "lastName": "Example",
    "birthDate": "1990-04-23",
    "email": "example@email.com",
    "password": "password",
    "roleId": 1
}
```
<br/>

#### Subject:
| Request  | Description       | Link                  |
|----------|-------------------|-----------------------|
| `GET`    | Get Subject List  | /subjects             | 
| `GET`    | Get Subject by Id | /subjects/{subjectid} |
| `DELETE` | Delete Subject    | /subjects/{subjectid} |

| Request | Description    | Link      |
|---------|----------------|-----------|
| `POST`  | Add Subject    | /subjects |
Required JSON body (**Add Subject** method):
```
{
    "subjectName": "Languages"
}
```
<br/>

### PARAMETERS
***
| Parameter | Description                                                        | Allowed Values                                                               |
|-----------|--------------------------------------------------------------------|------------------------------------------------------------------------------|
| table     | Determines the database table to be affected                       | "people", "students", "teachers" or "staff"                                  |
| field     | Determines the field by which the table will be sorted             | "id", "firstName", "lastName", "birthDate", "accountCreationDate" or "email" |
| page      | Determines the page of the list                                    | Any positive integer                                                         |
| pagesize  | Determines how many products are shown by page                     | Any integer between 1 and 100                                                |
<br/>

### ROLES
***
| Role    | Id  |
|---------|-----|
| Student | 1   |
| Teacher | 2   |
| Staff   | 3   |
<br/>

### STRUCTURE
___
The API contains:
* **Command** package - contains the **DTO** (Data Transfer Object) classes;
* **Config** package - contains the **BeansConfig** and **CheckAuth** classes;
* **Controller** package - contains the **Controller** classes used for routes;
* **Converter** package - contains the classes that convert an **Entity** to a
  **DTO** and vice-versa;
* **Dataloader** package - contains a class that implements the interface
  **ApplicationRunner** and populates the database when the program is started;
* **Enums** package - contains the **Enum** classes used through the project;
* **Exception** package - contains the custom exceptions and the **AppExceptionHandler**
  class;
* **Helpers** package - contains some methods used multiple times;
* **Persistence** package - contains the **Model** package and the **Repository**
  package:
    * **Model** package - contains the **Entity** classes;
    * **Repository** package - contains the repository interfaces that allow the
      connection with the **Database**;
* **Security** package - contains the implementation of **JWT**;
* **Service** package - contains the **Service** classes used for business logic;
* **application.yml** file - allows the connection to the **Database**;
* **docker-compose.yml** file - creates the **Database** container;
* **pom.xml** file - contains the dependencies used in the API.