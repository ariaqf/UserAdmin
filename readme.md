# Users API
## Disclaimer
This is an API that was made as part of an interview process. The endpoints described in this API are:
/user/create
/user/login
/user/{uid}

The tests were made so that they guarantee the business rules and not the communication rules so if it's needed to change to a CLI API or any other communication type it may be done easily.

## Create Endpoint
The create endpoint was made to receive a POST request with the body as the following JSON:

    {
     "name": "João da Silva",
     "email": "joao@silva.org",
     "password": "hunter2",
     "phones": [
         {
             "number": "987654321",
             "ddd": "21"
         }
     ]
    }
 
 Afterwards, if the user doesn't exist yet it will return a status 201 with the user object json as bellow:
 
     {
         "uid": "c2cf4b90-46fb-4cae-8069-ea159879b7ed",
         "name": "João da Silva",
         "email": "joao@silva.org",
         "password": "wpFbpzehjKCaRlyBEBfRytVIJy0wb3f9K1Lh7ktJizI=",
         "salt": "624yiKthag+NwtiL1VG1/g==",
         "created": "2020-05-05T03:43:08.024",
         "modified": "2020-05-05T03:43:08.023",
         "lastLogin": "2020-05-05T03:43:08.024",
         "token": "49bdedd5-dec9-4ee5-af4c-09bcd223b71b",
         "phones": [
                {
                     "number": "987654321",
                     "ddd": "21"
                }
         ]
     }
Otherwise it will return an error msg with either a http code 400 if the request doesn't have all the required parameters or a 422 if the email is already associated to a user in the database.

## Login
The Login endpoint accepts a json of the form:

    {
        "email": "joao@silva.org",
        "password": "hunter2"
    }
 
And returns a http status 200 and user object in the body as follows:

    {
         "uid": "c2cf4b90-46fb-4cae-8069-ea159879b7ed",
         "name": "João da Silva",
         "email": "joao@silva.org",
         "password": "wpFbpzehjKCaRlyBEBfRytVIJy0wb3f9K1Lh7ktJizI=",
         "salt": "624yiKthag+NwtiL1VG1/g==",
         "created": "2020-05-05T03:43:08.024",
         "modified": "2020-05-05T03:43:08.023",
         "lastLogin": "2020-05-05T03:43:08.024",
         "token": "49bdedd5-dec9-4ee5-af4c-09bcd223b71b",
         "phones": [
                {
                     "number": "987654321",
                     "ddd": "21"
                }
         ]
     }

If the email or password aren't both correct the return will be a 401 http status and an error message. 

## Profile
For the Profile request, the UID must be suplied in the URL. Also, to access a given profile you must also send an Authorization header containing a "Bearer <token>". If the token an uid don't match a 401 http status will be returned otherwise it will return a User Object:

    {
         "uid": "c2cf4b90-46fb-4cae-8069-ea159879b7ed",
         "name": "João da Silva",
         "email": "joao@silva.org",
         "password": "wpFbpzehjKCaRlyBEBfRytVIJy0wb3f9K1Lh7ktJizI=",
         "salt": "624yiKthag+NwtiL1VG1/g==",
         "created": "2020-05-05T03:43:08.024",
         "modified": "2020-05-05T03:43:08.023",
         "lastLogin": "2020-05-05T03:43:08.024",
         "token": "49bdedd5-dec9-4ee5-af4c-09bcd223b71b",
         "phones": [
                {
                     "number": "987654321",
                     "ddd": "21"
                }
         ]
     }
