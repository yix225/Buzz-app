### **TEAM M** | **TEAM 10**
| NAMES | EMAILS | PHONE NUMBERS |
| --- | --- | --- |
| Tamilore Adegbaye	| Email: doa325@lehigh.edu | Phone #: 215-558-8231
| David Wang         | Email: jiw324@lehigh.edu | Phone #: 669-307-3008
| Hien Thi 			| Email: hnt224@lehigh.edu | Phone #: 215-609-5838
| Selase Dzathor 	| Email: skd225@lehigh.edu | Phone #: 601-760-5029
| Yinuo Xu 			| Email: yix225@lehigh.edu | Phone #: 484-934-7389 
---
#### **INSTRUCTIONS ON BUILDING AND RUNNING LOCALLY AND ON DOKKU**
- Open a terminal specifaclly for dokku
- Open another terminal for your local host
- SSH into dokku by running this command : yourUID@ubudokku01.cse.lehigh.edu
- On your local host, run this command : sh local-deploy.sh in the web branch of your web folder
--- 
#### **CONCISE DESCRIPTION OF THE FUNCTIONALITY IN THIS TAGGED RELEASE**
The Buzz app is a social messaging platform that enables users to post anonymous messages, like and unlike messages posted by other users, as well as edit or delete each other's messages. Users are able to use the application without having to create an account or log in order to maintain the anonymous nature of the platform. It allows users to share their thoughts and opinions without revealing their identity.
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