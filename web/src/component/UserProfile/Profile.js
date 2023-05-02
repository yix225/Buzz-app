import React, { useState, useEffect } from "react";
import "./Profile.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function Profile() {  
  const GetProfile = async () => {
    axios
      .get("https://2023sp-team-m.dokku.cse.lehigh.edu/getProfile")
      .then((res) => {
        setName(res.data.name);
        setEmail(res.data.email);
        setsexualIdentity(res.data.genderidentity);
        setgenderOrientation(res.data.sexualorientation);
        setUserDescription(res.data.note);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  useEffect(() => {
    GetProfile();
  }, []);

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [sexualIdentity, setsexualIdentity] = useState("");
  const [genderOrientation, setgenderOrientation] = useState("");
  const [userDescription, setUserDescription] = useState("");
  const navigate = useNavigate();
  
  const handleNameChange = (event) => {
    setName(event.target.value);
  };

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };
  const handleSexualIdenityChange = (event) => {
    setsexualIdentity(event.target.value);
  };
  const handlegenderOrientation = (event) => {
    setgenderOrientation(event.target.value);
  };
  const handleuserDescription = (event) => {
    setUserDescription(event.target.value);
  };

  const handleSignOutClick = () => {
    // Perform sign out operation here
    navigate("/");
  };
  
  const saveProfile = () => {
    // Send HTTP request to save profile information on server
    fetch("https://2023sp-team-m.dokku.cse.lehigh.edu/profile", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        name,
        email,
        sexualIdentity,
        genderOrientation,
        userDescription,
      }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to save profile information");
        }
        alert("Profile information saved");
      })
      .catch((error) => {
        console.error(error);
        alert("Failed to save profile information");
      });
  };
  return (
    <div>
      <div className="profile-container">
        <h2 class = "title">Profile</h2>
        <div className="profile-box">
          <ul>
            <li>
              <h3>Name</h3>
              <input type="text" value={name} onChange={handleNameChange} />
            </li>
            <li>
              <h3>Email</h3>
              <input type="text" value={email} onChange={handleEmailChange} />
            </li>
            <li>
              <h3>Sexual Identity</h3>
              <input
                type="text"
                value={sexualIdentity}
                onChange={handleSexualIdenityChange}
              />
            </li>
            <li>
              <h3>Gender Orientation</h3>
              <input
                type="text"
                value={genderOrientation}
                onChange={handlegenderOrientation}
              />
            </li>
            <li>
              <h3>User Description</h3>
              <input
                type="text"
                value={userDescription}
                onChange={handleuserDescription}
              />
            </li>
          </ul>
          <button onClick={saveProfile}>Update</button>
        </div>
      </div>
      <button onClick={() => navigate(-1)}>Go back</button>
      <button onClick={handleSignOutClick}>Sign Out</button>
    </div>
  );
};

export default Profile;
