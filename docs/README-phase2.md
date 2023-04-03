## `DESIGN REQUIREMENTS`
----

**USER STORIES FOR THE ADMIN PERSONA**

#### `ADMIN USER PERSONA` 

- As a admin developer,

- I want to be able to create a backdoor in order to access the database,

- In order for the developers to check the database status to ensure that everything is working fine

-  Invalid an idea (inappropriate or redundant) and invalidate a user (using app inappropriately)

- Invalid ideas are not displayed and invalidated user cannot log in



#### `TEST CASES FOR ADMIN:` 
- CASE:  Admin Message Posting Functionality

-  OBJECTIVE:  Verify that the admin message posting functionality works correctly and meets the acceptance criteria specified in user story.

- STEPS: Log into the website as admin (later down the road); Can type and post messages in the correct location for others to see. Posted messages can be edited or deleted by the admin user. Check people’s messages and user information to validate messages and accounts. 

- ENVIRONMENT: Test database used to store the messages, Access to messages on the website, admin user account, access to the user of the application’s information. 

- PASS CRITERIA: Messages should be able to post, delete and edit without errors. Messages should be visible to other users. Should be able to invalidate an idea and message by making them not be able to login and invalidated posts are invisible.

- TESTING TYPE: Manual testing for verifying admin user interface functionality and interaction, automated for storing database after posting. Manual testing for invalidating ideas and users. 


**USER STORIES FOR THE AUTHENTICATED PERSONA**

#### `AUTHENTICATED USER PERSONA`

-As an authenticated user, 

-I want to be able to use my access token to access the application and have access to my 

-profile on the web and mobile. Be able to login or logout whenever I want

-When I’m logged in, I want to be able to up-vote, down-vote, and add comments on ideas

#### `TEST CASES FOR AUTHENTICATED USER:`

-CASE:  Authenticated User Functionalities

-OBJECTIVE: Pass the criterias that an authenticated user can do and have access to the respective functionalities.

-STEPS: Log into the website and mobile as an authenticated user with access token. Can post, like, unlike, add comments on ideas. 

-ENVIRONMENT: Website or mobile application. Have access to messages anyone post to view, like, unlike or comment on. 

-PASS CRITERIA: Users should be able to login and logout whenever they can. Once logged in, they should be able to view, like, unlike or comment on an idea.

-TESTING TYPE: Manual testing for the functionalities that the user can do such as logging in and performing actions on an idea. Automated testing for users using their access token to login. 


__________
__________
__________

**`DIAGRAM 1`** :
**SYSTEM DIAGRAM**

![](https://i.ibb.co/tpTJgtP/Screenshot-1.png)
__________
__________
__________


**`DIAGRAM 2`** :
**ENTITY RELATIONSHIP DIAGRAM**
![](https://i.ibb.co/bQcdcq4/Screenshot-2.png)
__________
__________
__________



**`DIAGRAM 3`** :
**FINITE STATE MACHINE**
![](https://i.ibb.co/dk5mhhk/Screenshot-5.png)
__________
__________
__________


**`DIAGRAM 4`** :
**FINITE STATE MACHINE VALID IDEA**
![](https://i.ibb.co/cTPfnSb/Screenshot-3.png)
__________
__________
__________


**`DIAGRAM 5`** :
**FINITE STATE MACHINE VALID COMMENT**
![](https://i.ibb.co/NpvRwst/Screenshot-4.png)
__________
__________
__________

------------
### **ROUTES**
![](https://i.ibb.co/JkgRXhq/Screenshot-6.png)
__________
__________
__________


### **DRAWINGS** 
#### `MOCK WEB USER INTERFACE`
![](https://i.ibb.co/WD2bNGF/Screenshot-7.png)
__________
__________
__________


#### `MOCK WEB USER PROFILE`
![](https://i.ibb.co/Sxs0L00/Screenshot-8.png)
__________
__________
__________


#### `MOCK MOBILE USER INTERFACE`
![](https://i.ibb.co/z4cbzq0/Screenshot-9.png)
__________
__________
__________


#### `MOCK MOBILE USER PROFILE`
![](https://i.ibb.co/2dWpDhf/Screenshot-10.png)
__________
__________
__________


#### `DESCRIPTION OF TESTS FOR EACH ROLE`
**WEB:**

 *CASE*: clicking on the "Sign in with Google" button redirects the user to the Google login page. Test up-votes and downvotes.

 OBJECTIVE: login page via OAuth, up-votes, down-votes, and support text-only comments on ideas. A user should be able to edit their old posted comment(s). Ideas cannot be edited or deleted. Comments cannot be deleted, only edited. 

 STEPS: Implement the up-vote and down-vote buttons on your web application. Use AJAX requests to send data to the back-end server and update the vote count in real-time. 

 ENVIROMENT: Test web used to show the messages, Access to messages on the website, and mobile app

 CRITERIA: Click on the "Sign in with Google" button, Verify that the user is redirected to the Google login page, Verify that the user is able to enter their Google email and password and also verify that the user is able to successfully log in using their Google account. Implement a text-only comment section on your web application and append them to the page.  Ideas cannot be edited or deleted. Comments cannot be deleted, only edited. 
 
 TYPE: Manual testing for verifying web interface functionality and interaction, automated show the idea after posting or adding and status of each idea after users selecting. Automated showing for upvotes, downvotes, and comments on ideas.


**MOBILE:**

 *CASE*: clicking on the "login" button redirects the user to the Google login page or the email login. Flutter test the comment show and up/down vote

 OBJECTIVE: mobile app should support to display text-only comments for ideas, hande login/logout, up/down votes, and a user profile page

 STEPS: Implement the up-vote and down-vote buttons on your mobile application, sending the change back to the back-end server and update the vote after refresh. 

 ENVIROMENT: Test web used to show the messages, Access to messages on the website, and mobile app.

 CRITERIA: Implement a text-only comment section on your mobile application and append them to the page. Ideas cannot be edited or deleted. Comments cannot be deleted, only edited. 

 TYPE: Manual testing for verifying web interface functionality and interaction, automated show the idea after posting or adding and status of each idea after users selecting. Automated showing for upvotes, downvotes, and comments on ideas.

**BACKEND:**

 *CASE*: Run the database and create something for testing. Google API testing. Store User and sessions ID in local hashtable. Store User information.

 OBJECTIVE: Backend tests include tests on the database functionality. Besides the previous functions, this phase will implement a function on Google login, comment on an idea, and get and update profile information.

 STEPS: Implement the Google API. Store user information such as sessions ID. Create entries on database and use it for testing adding comments, upvoting and downvoting.

 ENVIROMENT: Test web used to show the messages, Access to messages on the website, and mobile app.

 CRITERIA: Do assert to check and make sure the database will return the same object as expected from the SQL statement. Test the google OAuth login and make sure that the backend will return a session id to the frontend. Test whether you can create the hashtable and store the information when need to retrieve it. 

 TYPE: Manual testing for verifying web interface functionality and interaction, automated show the idea after posting or adding and status of each idea after users selecting. Automated showing for upvotes, downvotes, and comments on ideas.


#### `BACKLOG`
    1. Update the likes so changes to it would be reflected in the database
    2. Deploy new web to dokku so that it opens up new web html file and copy that stuff into dokku-backend. Then deploy to dokku.