function callback(response){
    const responsePayload = decodeJwtResponse(response.credential);
    console.log("ID: "+responsePayload.sub);
    console.log("Email: "+responsePayload.email);
    send_req(responsePayload);
}
function decodeJwtResponse(data){
    let tokens = data.split(".");
    return JSON.parse(atob(tokens[1]));
}
function send_req(responsePayload){
    console.log("Yes");
    const url = 'http://2023sp-team-m.dokku.cse.lehigh.edu/login'; // Replace with your own API endpoint
    const data = {
      mysubject: 'mysubject',
      mymessage: responsePayload.sub,
      
    }; // Replace with the data you want to send
    fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })
      .then(response => {
        console.log("Yess");
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        else{
            console.log(response.body);
            console.log("Yesss");
        }
        return response.json();
      })
      .then(data => {
        console.log('Response:', data);
      })
      .catch(error => {
        console.error('Error:', error);
      });
    

}