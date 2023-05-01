## `DESIGN REQUIREMENTS`
----

**USER STORIES FOR THE ADMIN PERSONA**

#### `ADMIN USER PERSONA` 

- As an admin developer,

- I want to be able to manage the storage quota of the Buzz App on Google Drive,

- In order to manage the limit of storage used by users,

-  This would be done by adding a feature that list all the pictures/documents

-  This would be done by removing content that doesn’t have any recent activity


#### `TEST CASES FOR ADMIN:` 
- CASE:  Admin Message Posting Functionality
- OBJECTIVE: Verify that admin can manage storage quota by removing least recently accessed documents.
- STEPS: 
    1. Verify that the documents are displayed in the administrative interface with their owners and most recent activity.
    2. Remove the least recently accessed document from the platform.
    3. Verify that the document is no longer displayed in the administrative interface and storage space has been freed up.

- PASS CRITERIA: The administrative interface should display all documents on the platform with their owners and most recent activity, and an administrator should be able to remove the least recently accessed document from the platform.

- TESTING TYPE: Manual testing.
 
**USER STORIES FOR THE AUTHENTICATED PERSONA**

#### `AUTHENTICATED USER PERSONA`

- As an anonymous user,

- I want to be able to upload diagrams, pictures, audio files, videos, slide decks, etc on the  Buzz App 

- In order to prevent restriction to only text based content,

- When I’m logged in, I want to be able to up-vote, down-vote, and add comments on ideas and also add any diagram 

#### `TEST CASES FOR AUTHENTICATED USER:`
- CASE:  Authenticated User Functionalities
- OBJECTIVE: Verify that an anonymous user can upload various types of content to the Buzz app.
- STEPS: Verify that the files are uploaded successfully to the platform.
- ENVIRONMENT: The Buzz app accessed as an anonymous user.
- PASS CRITERIA: The files should be uploaded successfully to the platform without any errors or issues.
- TESTING TYPE: Manual testing.

__________
__________
__________

**`DIAGRAM 1`** :
**SYSTEM DIAGRAM**

![](https://i.ibb.co/hdxt1vK/system-Diagram.png)
__________
__________
__________


**`DIAGRAM 2`** :
**ENTITY RELATIONSHIP DIAGRAM**
![](https://i.ibb.co/vY6py2R/entity-Relationship.png)
__________
__________
__________



**`DIAGRAM 3`** :
**FINITE STATE MACHINE**
![](https://i.ibb.co/kxZVggz/state-machin.png)
__________
__________
__________


**`DIAGRAM 4`** :
**FINITE STATE MACHINE AN IDEA OBJECT**
![](https://i.ibb.co/2q1MxhH/new-state-machin.png)
__________
__________
__________


------------
### **ROUTES**
![](https://i.ibb.co/H282Rm7/routes.png)
__________
__________
__________


### **DRAWINGS** 
#### `MOCK WEB USER INTERFACE`
![](https://i.ibb.co/2MxZjh7/buzz-page.png)
__________
__________
__________


#### `MOCK WEB USER LOGIN`
![](https://i.ibb.co/R2vwZzN/login-page.png)

![](https://i.ibb.co/HVbYH8L/profile.png)
__________
__________
__________

### `MOCK WEB USER UPLOAD FILE`
![](https://i.ibb.co/zmzhzns/upload-maneu.png)

![](https://i.ibb.co/84V9rqz/upload-image.png)

![](https://i.ibb.co/2FCCGKp/upload-link.png)
__________
__________
__________

#### `MOCK MOBILE USER INTERFACE`
![](https://i.ibb.co/gzhv4CD/mobile-main-page.png)
__________
__________
__________


#### `MOCK MOBILE USER PROFILE`
![](https://i.ibb.co/Myzwspp/user-profile.png)
__________
__________
__________

### `MOCK WEB USER UPLOAD FILE`
![](https://i.ibb.co/vZkXRnD/upload-image-and-link.png)
__________
__________
__________

#### `DESCRIPTION OF TESTS FOR EACH ROLE`
**BACKEND:**

- OBJECTIVE: Backend should be able to create routes that would allow for the uploading of a picture or video under a person’s idea. It should allow for users to send optional media and to return those medias. 

- CASE: Front-end sends the type of media it is, picture or video.

- CRITERIA: Verify if the video or picture is saved in Google Drive on a Google services account and use MemCachier to manage the media by sending it for display on the web and mobile.

- STEPS: Implement routes methods for accepting new media depending on the type it is. Also, implement MemCachier for management of frequently used data.



**WEB:**

- OBJECTIVE: Web app should support to display text-only comments for ideas, handle login/logout, up/down votes, and a user profile page. Users should be able to attach files and links to their ideas or comments.

- CASE: user attaches a file 

- CRITERIA: Verify that a user can attach a file, that the file is saved inside that database and that if the file is an image/gif it displays on the website

- STEPS: Implement the attach button on your mobile application, sending the change back to the back-end server the base64 information of the file   

- CRITERIA: Image/gifs appear on the website on the message. File data is saved in the backend

- CASE: user attaches a link

- CRITERIA: Verify that a user can attach a link, the link can be clicked and redirects the user to the website.

- STEPS:Implement the attach button on your mobile application, sending the link to the back-end server                                                          
- CRITERIA: link redirects user to the website



**MOBILE:**

- OBJECTIVE:  mobile app should support to display text-only comments for ideas, handle login/logout, up/down votes, and a user profile page. Users should be able to attach files and links to their ideas or comments.

- CASE: user attaches a file 

- CRITERIA: Verify that a user can attach a file, that the file is saved inside that database and that if the file is an image/gif it displays on the website

- STEPS: 
    Implement the attach button on your mobile application, sending the change back to the back-end server the base64 information of the file 

- CRITERIA: 
    Image/gifs appear on the website on the message. File data is saved in the backend

- CASE: user attaches a link

- CRITERIA: Verify that a user can attach a link, the link can be clicked and redirects the user to the website.

- STEPS:Implement the attach button on your mobile application, sending the link to the back-end server

- CRITERIA: link redirects user to the website




#### `BACKLOG`
- Web: 
    1. Connect the web implementation with backend database
    2. Update the New user's profile pre-filled with available information from Google account 
- Mobile: 
    1. Update the response key from the backend side to finish the Google login authentication.
    2. Updated existing and created new unit test implementations 
- Backend:
    1.Deploy *New* Web to Dokku
