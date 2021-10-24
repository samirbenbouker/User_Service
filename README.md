# User_Service
In this project, we protect the backend applications using JWT, Spring Boot, and Spring Security. Implementing JWT access and it will update the tokens.

```
WARNING IN FILE APPLICATION.PROPERTIES ADD USERNAME AND PASSWORD FOR YOUR DATABASE
```

## Path Java Project:
`/src/main/java/com/samirbenbouker/userservice/`

## Endpoints API:
```
PATH: localhost:8080/api/v1/
```

- `GET /users` return all users we found in database.
- `GET /token/refresh` can update the JWT of the registered user.
- `POST /user/save` can save a user in your database passing in Request Body a JSON File.
- `POST /role/save` can save a role in your database passing in Request Body a JSON File.
- `POST /role/addtouser` can add a role in one user passing in Request Body a AppRole Object.

### Get All Users
_This endpoint return all users we found in database_
<table>
  <tr>
    <th>Response: List[AppUser]</th>
  </tr>
  <tr>
    <th>Get /users </th>
  </tr>
  <tr>
    <td>No parameters required</td>
  </tr>
</table>

### Get Refresh Token
_Can update the JWT of the registered user_
<table>
  <tr>
    <th>Response: void</th>
  </tr>
  <tr>
    <th>Get /token/refresh </th>
  </tr>
  <tr>
    <td>No parameters required</td>
  </tr>
</table>

### Post Save User
_This endpoint can save a user in your database passing in Request Body a JSON File_
<table>
  <tr>
    <th colspan="4" >Response: AppUser</th>
  </tr>
  <tr>
    <th colspan="4" >POST /user/save </th>
  </tr>
  <tr>
    <th>Param</th>
    <th>Values</th>
    <th>Description</th>
    <th>Required</th>
  </tr>
  <tr>
    <td>Document</td>
    <td>JSON {field: value, ...}</td>
    <td>Adds the document passed as a parameter. Only one <br/>document can be inserted at a time.</td>
    <td>Yes</td>
  </tr>
</table>

### Post Save Role
_This endpoint can save a role in your database passing in Request Body a JSON File_
<table>
  <tr>
    <th colspan="4" >Response: AppRole</th>
  </tr>
  <tr>
    <th colspan="4" >POST /role/save </th>
  </tr>
  <tr>
    <th>Param</th>
    <th>Values</th>
    <th>Description</th>
    <th>Required</th>
  </tr>
  <tr>
    <td>Document</td>
    <td>JSON {field: value, ...}</td>
    <td>Adds the document passed as a parameter. Only one <br/>document can be inserted at a time.</td>
    <td>Yes</td>
  </tr>
</table>

### Post Add Role To User
_This endpoint can add a role in one user passing in Request Body a AppRole Object_
<table>
  <tr>
    <th colspan="4" >Response: void</th>
  </tr>
  <tr>
    <th colspan="4" >POST /role/addtouser </th>
  </tr>
  <tr>
    <th>Param</th>
    <th>Values</th>
    <th>Description</th>
    <th>Required</th>
  </tr>
  <tr>
    <td>Document</td>
    <td>JSON {field: value, ...}</td>
    <td>Adds the document passed as a parameter. Only one <br/>document can be inserted at a time.</td>
    <td>Yes</td>
  </tr>
</table>

### Get Refresh Token
_you can update the JWT of the registered user_
<table>
  <tr>
    <th>Response: void</th>
  </tr>
  <tr>
    <th>Get /token/refresh </th>
  </tr>
  <tr>
    <td>No parameters required</td>
  </tr>
</table>
