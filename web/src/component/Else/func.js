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
// import axios from 'axios';
// function send_req(responsePayload){
//     const url = 'http://2023sp-team-m.dokku.cse.lehigh.edu/login'; // Replace with your own API endpoint
//     const data = {
//       token: 'johndoe@example.com'
//     }; // Replace with the data you want to send
//     fetch(url, {
//       method: 'PUT',
//       headers: {
//         'Content-Type': 'application/json'
//       },
//       body: JSON.stringify(data)
//     })
//       .then(response => {
//         if (!response.ok) {
//           throw new Error('Network response was not ok');
//         }
//         return response.json();
//       })
//       .then(data => {
//         console.log('Response:', data);
//       })
//       .catch(error => {
//         console.error('Error:', error);
//       });
// }