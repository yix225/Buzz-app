import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { GoogleOAuthProvider } from "@react-oauth/google";
import { GoogleLogin } from "@react-oauth/google";
import { useNavigate } from "react-router-dom";
import Profile from "./component/UserProfile/Profile";
import Messages from "./component/Messages/Messages";
import HomePage from "./component/Homepage/Homepage";
import {login} from "./component/routes/routes";
let sessionId;
function App() {
  const navigate = useNavigate();
  return (
    <GoogleOAuthProvider clientId="926558226206-ppmn3bk4ckvrtaq6hun9kpi034sde366.apps.googleusercontent.com">
      <div>
        <h1>Sign in to the Buzz App</h1>
        <GoogleLogin
          onSuccess={(credentialResponse) => {
            console.log(credentialResponse);
            sessionId = login(credentialResponse)
            sessionId = 123456;
            console.log(sessionId);
            navigate(`/Home/?param=${sessionId}`);
          }}
          onError={() => {
            console.log("Login Failed");
          }}
        />
      </div>
    </GoogleOAuthProvider>
  );
}

function Main() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<App />} />
        <Route path="/profile" element={<Profile />} />
        <Route path= "/Home" element={<HomePage />} /> 
        <Route path="/message/:messageId" element={<Messages />} />
      </Routes>
    </Router>
  );
}

export default Main;
