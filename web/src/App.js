import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { GoogleOAuthProvider } from "@react-oauth/google";
import { GoogleLogin } from "@react-oauth/google";
import { useNavigate } from "react-router-dom";
import Profile from "./Profile";
import Messages from "./component/Messages"; // Import the Messages component

//import ProfileBar from "./ProfileBar"; // Import the ProfileBar component

function App() {
  const navigate = useNavigate();

  const handleProfileClick = () => {
    navigate("/profile");
  };


  return (
    <GoogleOAuthProvider clientId="926558226206-ppmn3bk4ckvrtaq6hun9kpi034sde366.apps.googleusercontent.com">
      <div>
        <h1>Sign in to the Buzz App</h1>
        <GoogleLogin
          onSuccess={(credentialResponse) => {
            console.log(credentialResponse);
            navigate("/profile");
          }}
          onError={() => {
            console.log("Login Failed");
          }}
        />
      </div>
    </GoogleOAuthProvider>
  );
}

function NotFound() {
  return (
    <div>
      <h2>Page Not Found</h2>
      <p>The page you are looking for does not exist.</p>
    </div>
  );
}

function Main() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<App />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/GetAllIdea" element={<Messages />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Router>
  );
}

export default Main;
