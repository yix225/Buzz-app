### **TEAM M** | **TEAM 10**


| NAMES | EMAILS | PHONE NUMBERS | 
| --- | --- | --- |
| Tamilore Adegbaye (Web Developer)	| Email: doa325@lehigh.edu | Phone #: 215-558-8231 |
| David Wang (Backend Developer)         | Email: jiw324@lehigh.edu | Phone #: 669-307-3008 |
| Hien Thi (Product Manager) 			| Email: hnt224@lehigh.edu | Phone #: 215-609-5838 |
| Selase Dzathor (Admin-Cli Developer) 	| Email: skd225@lehigh.edu | Phone #: 601-760-5029 |
| Yinuo Xu (Mobile Developer) 			| Email: yix225@lehigh.edu | Phone #: 484-934-7389 |



---

#### **INSTRUCTIONS ON BUILDING AND RUNNING LOCALLY AND ON DOKKU**
- Open a terminal specifaclly for dokku
- Open another terminal for your local host
- SSH into dokku by running this command : yourUID@ubudokku01.cse.lehigh.edu
- On your local host, run this command : sh local-deploy.sh in the web branch of your web folder


#### Backend & Admin
	1. Run  "mvn clean; mvn package; PORT=8998 DATABASE_URL=postgres://<User>:<Pass>@<Url> mvn exec:java" on respective branches
	
#### Web
	1. View the web front end by running npm start in your terminal in the web folder of our team repository
	2. During testing and development, we can make changes/view the web front end code by pointing our browser to http://localhost:3000/
	
#### Mobile
	1. Navigate to flutter_application_1 folder
	2. Type "flutter run" in comment line in terminal
	3. Run Android Emulator and go to Debug mode.
--- 




#### **CONCISE DESCRIPTION OF THE FUNCTIONALITY IN THIS TAGGED RELEASE**
The Buzz app is a social messaging platform that enables users to post anonymous messages, like and unlike messages posted by other users, as well as edit or delete each other's messages. Users are able to use the application without having to create an account or log in order to maintain the anonymous nature of the platform. It allows users to share their thoughts and opinions without revealing their identity.
Now, new users can sign in with Google authentication, and this will allow them to fill out their profile information. In addition, users can also comment under someone's idea in order to express their opinions. 

#### **MISSING FUNCTIONALITIES**
1. Launching web to dokku
2. Google authorization on mobile

#### **Developer Documentation**
* backend
	* Requires installing Maven and VS code. Test and run backend by using maven execution to connect to the Database on ElephantSQL.
	* mvn clean; mvn package; PORT=8998 DATABASE_URL=postgres://<User>:<Pass>@<Url> mvn exec:java
	* Path to JavaDocs: team_m/docs/backend/allclasses-index.html	
	* Path to Design and Planning Artifacts: team_m/docs/README-phase1.md
* web
	* Installation needed is the react package by using the npm install(I’ve listed the tutorial for react in the web branch tutorial ). 
	* Building needed using js, css, html for web pages.  In the web code, I mainly used the react package and axios package for CORS Request(get, put, post, delete). 
	* Testing and Running needed Jasmine (i’ve write some UITest , npm run test) or you could use the react to run it (by using “npm start run”)	
	* Path to JsDocs in master branch: team_m/docs/web
	* Path to JsDocs  in web branch:team_m/docs/README-phase1.md
* admin
	* Installation needed is Maven for VSCode.
	* Testing and running admin cli by connecting to an ElephantSQL through maven execution.
	* Type “POSTGRES_IP=janro.db.elephantsql.com POSTGRES_PORT=5432 POSTGRES_USER=<user> POSTGRES_PASS=<password> mvn exec:java”.
	* Use appropriate ip, port, user, and password. You can do this locally and have access to remote ElephantSQL through local IDE. Test multiple inputs by typing “?” to see what the commands do.
	* Path to JavaDocs htmls in master branch: team_m/docs/admin
	* team_m/docs/README-phase1.md
* mobile
	* How to run and test the application:
	* Open Android Studio and start an android emulator.
	* Go to flutter_application_1 folder
	* Run “flutter run” in the command prompt once the vscode detects the android emulator and connects to it. This will run the app, and you can see everything showing in your emulator.
	* Run “flutter test” in the command prompt, this will run the test for flutter app.
	* Path to JavaDocs: team_m/docs/mobile/doc/api/index.html
	* Path to JavaDocs: team_m/docs/README-phase1.md