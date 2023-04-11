import React, { useState } from "react";
import "./Profile.css";
import { useNavigate } from "react-router-dom";

function Profile() {
  const [name, setName] = useState("Team M");
  const [email, setEmail] = useState("tmessi2023@gmail.com");
  const [sexualIdentity, setsexualIdentity] = useState(" ");
  const [genderOrientation, setgenderOrientation] = useState(" ");
  const [userDescription, setUserDescription] = useState("Team M on Top :) ");
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

  const handleViewMessagesClick = () => {
    navigate("/messages");
  };

  const handleSignOutClick = () => {
    // Perform sign out operation here
    navigate("/");
  };

  const saveProfile = () => {
    // Send HTTP request to save profile information on server
    fetch("http://2023sp-team-m.dokku.cse.lehigh.edu/profile", {
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
        <h2>Profile</h2>
        <div className="profile-box">
          <h3>Name</h3>
          <input type="text" value={name} onChange={handleNameChange} />
          <h3>Email</h3>
          <input type="email" value={email} onChange={handleEmailChange} />
          <h3>Sexual Identity</h3>
          <input
            type="text"
            value={sexualIdentity}
            onChange={handleSexualIdenityChange}
          />
          <h3>Gender Orientation</h3>
          <input
            type="text"
            value={genderOrientation}
            onChange={handlegenderOrientation}
          />
          <h3>User Description</h3>
          <input
            type="text"
            value={userDescription}
            onChange={handleuserDescription}
          />
          <button onClick={saveProfile}>Save</button>
        </div>
        {/* Additional profile content here */}
      </div>
      <button onClick={() => navigate(-1)}>Go back</button>
      <button onClick={() => navigate("/")}>Home Screen </button>
      <button onClick={handleViewMessagesClick}>View Messages</button>
      <button onClick={handleSignOutClick}>Sign Out</button>
    </div>
  );
}

export default Profile;
