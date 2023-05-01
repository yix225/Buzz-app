import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { GoogleOAuthProvider } from "@react-oauth/google";
import { GoogleLogin } from "@react-oauth/google";
import { useNavigate } from "react-router-dom";
import Profile from "./component/UserProfile/Profile";
import Messages from "./component/Messages/Messages";
import HomePage from "./component/Homepage/Homepage";
import {login} from "./component/routes/routes";

function App() {
  const navigate = useNavigate();
  let sessionId;
  return (
    <GoogleOAuthProvider clientId="926558226206-ppmn3bk4ckvrtaq6hun9kpi034sde366.apps.googleusercontent.com">
      <div>
        <h1>Sign in to the Buzz App</h1>
        <GoogleLogin
          onSuccess={(credentialResponse) => {
            console.log(credentialResponse);
            sessionId = login(credentialResponse)
            navigate("/Home");
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
        <Route path="/GetAllIdea" element={<Messages />} />
        <Route path="/Home" element={<HomePage />} /> 
      </Routes>
    </Router>
  );
}

export default Main;
