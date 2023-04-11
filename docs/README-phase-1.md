## `DESIGN REQUIREMENTS`
----

**USER STORIES FOR AN ANONYMOUS USER PERSONA**

#### `ANONYMOUS USER PERSONA` 

-  As an anonymous user,

- I want to be able to navigate through the app without having to provide personal information,

- In order for me to post, like, edit and update messages all while being anonymous


#### `TEST CASES FOR ANONYMOUS USER:` 
-  CASE:  Anonymous Message Posting Functionality

-  OBJECTIVE:  Verify that the anonymous message posting functionality works correctly and meets the acceptance criteria specified in the user story.

- STEPS:  See if anonymous user can type a message in the message box and post it to website; See if their message shows up on the website/mobile; See if their message can be seen by others

- ENVIROMENT:  Include access to the message posted by the anonymous user and a test database to store the messages

- DATA:  Sample message to post

- PASS CRITERIA:  Posting message should have no errors, posted message in the correct location on the website, posted message visible to others

- TESTING TYPE: Manual testing, involves verifying user interface functionality and interaction. Automated testing for when needed to validate that the message is stored in the database after posting.


 ___

**USER STORIES FOR THE ADMIN PERSONA**

#### `ADMIN USER PERSONA` 

- As a admin developer,

- I want to be able to create a backdoor in order to access the database,

- In order for the developers to check the database status to ensure that everything is working fine



#### `TEST CASES FOR ADMIN:` 
-  CASE:  Admin Message Posting Functionality

-  OBJECTIVE:  Verify that the admin message posting functionality works correctly and meets the acceptance criteria specified in user story.

- STEPS: Log into the website as admin (later down the road); Can type and post messages in the correct location for others to see. Posted messages can be edited or deleted by the admin user

- ENVIROMENT: Test database used to store the messages, Access to messages on the website, admin user account(later)

- PASS CRITERIA: Messages should be able to post, delete and edit without errors. Messages should be visible to other users

- TESTING TYPE: Manual testing for verifying admin user interface functionality and interaction, automated for storing database after posting


__________
__________
__________

**`DIAGRAM 1`** :
**SYSTEM DIAGRAM**

![](https://i.ibb.co/2tQshNn/system-diagram.png)
__________
__________
__________




**`DIAGRAM 2`** :
**ENTITY RELATIONSHIP DIAGRAM**
![](https://i.ibb.co/M8G658Q/entity-relationship-diagram.png)
__________
__________
__________



**`DIAGRAM 3`** :
**FINITE STATE MACHINE**
![](https://i.ibb.co/89wKhsD/finite-state-machine.png)
__________
__________
__________

-----------
### **ROUTES**
#### `LISTING OF THE ROUTES, THEIR PURPOSE AND THE FORMAT OF ANY PASSED OBJECT`

    A. Messages > Like 
        Purpose: Allows the anonymous user to like any existing message
        Object Format: None

    B. Messages > Post > Message ID > Timestamp
        Purpose: Allows the anonymous user to post a message 
        Object format : “Message : Enter your message here ”
        AFTER POSTING
        “Message ID: ……………” 
	    “ Message: : EFJHSDSAKAAAS ”
	    “TimeStamp : 	“

    C. Messages > Delete 
        Purpose:  Allows the anonymous user to delete any existing message
        Object Format: None

    D. Messages > Update  >  Message ID > Timestamp 
        Purpose: Allows the anonymous user to update any existing message 
        Object Format: “Message ID: ……………” 
	    “ Message: : EFJHSDSAKAAAS ”
	    AFTER UPDATING MESSAGE
	    “Message ID: ……………” 
	    “ Message: : EFJHSDSAKAAAS ”
	    “TimeStamp : 	“

    E. Like > Message
	Purpose: Allows the anonymous user to like any existing message
	Object Format: None




### **DRAWINGS** 
#### `MOCK WEB USER INTERFACE`
![](https://i.ibb.co/grKBdkf/web-interface.png)
__________
__________
__________

#### `MOCK MOBILE USER INTERFACE`
![](https://i.ibb.co/6XmKfv4/mobile-interface.png)
__________
__________
__________



### `DESCRIPTION OF TESTS FOR EACH ROLE`
**WEB:**
 *CASE*: Anonymous Message Displaying Functionality

 OBJECTIVE: Verify basic decoration of the web page shows and the anonymous message displaying functionality works correctly; meets the acceptance criteria specified in user story. 

 STEPS: Showing the basic decoration of web page; Display the each of anonymous posts with like or dislike button for each idea; Addition button can worked and show the new post

 ENVIROMENT: Test web used to show the messages, Access to messages on the website, and mobile app(later)

 CRITERIA: Messages should be able to display with like, dislike buttons. Each new post could show after being anonymously added. 
 
 TYPE: Manual testing for verifying web interface functionality and interaction, automated show the idea after posting or adding and status of each idea after users selecting. 


**MOBILE:**

- **FLUTTER TEST**:
The file containing all the test objects is testmain_test.dart in the test folder. It will test the testmain.dart file in the lib folder. The issue we have currently is that when running the test, it does not allow any Http request from the server, so a hard code test data is needed for this part. Once you go into the folder of the mobile application, you can run “flutter test” in the terminal to run the test.



**BACKEND:**

-  *CASE*: Routes that Users use to “POST” and “like” messages along with retrieving  information about messages and number of likes

- Objective: Verify database connection, database saves posted message data in SQL,returns messages and likes as JSON. Launch 

- Steps:  Create a database and connect to web, create a message, make sure all message data is saved inside a Database. Verify that user can update, delete, and like messages. Once again, verify that all updates are saved to Database
Environment: Test web and database, make sure that web can access data from database and database can save data from web

- Criteria: Backend is successfully saving all message data and returning message data

- Type: Testing each routes functionality, each function and interesting inputs

- *CASE*: Backend is launched inside dokku

- Objective: Verify backend is launching correctly inside of dokku

- Steps: Open app on dokku and make sure that it is connected to Database

- Environment: backend and dokku-backend

- Criteria: App is successfully connected to backend and using the correct routes

- Type: Manually opening app on Dokku and making sure that Data is the same for messages
