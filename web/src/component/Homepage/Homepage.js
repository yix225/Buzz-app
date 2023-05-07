import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from 'react-router-dom';
import { BrowserRouter, Route, Switch } from "react-router-dom";
import "./Homepage.css";

/**
 * MessageList component initializes state variables to store the messages, new message content, and new message subject. It also initializes a state variable to keep track of the timestamp of the last like action.
 * @returns the formate of the addmessage, the button of delete,edit,like, and the title's position
 */
function Homepage() {
  // Fetch the messages data from the API
  const [messages, setMessages] = useState([]); //using the useState to setmessages
  const [newMessage, setNewMessage] = useState("");
  const [newSubject, setNewSubject] = useState("");
  const [lastLikeTimestamp, setLastLikeTimestamp] = useState(null);
  /**
   * The component defines a "getMessages" function that uses the axios library to fetch messages(data) from the API and updates the state variable for messages.(by using the setMessages)
   */
  const getMessages = async () => {
    const cachedMessages = localStorage.getItem("messages");
    if (cachedMessages) {
      setMessages(JSON.parse(cachedMessages));
    } else {
      axios
        .get("https://2023sp-team-m.dokku.cse.lehigh.edu/GetAllIdea")
        .then((res) => {
          console.log(res.data.mData);
          setMessages(res.data.mData);
          localStorage.setItem("messages", JSON.stringify(res.data.mData));
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };
  
  useEffect(() => {
    getMessages();
  }, []);
  
  // const getMessages = async () => {
  //   try {
  //     const response = await fetch("https://2023sp-team-m.dokku.cse.lehigh.edu/GetAllIdea");
  //     if (!response.ok) {
  //       throw new Error(`HTTP error! status: ${response.status}`);
  //     }
  //     const data = await response.json();
  //     console.log(data.mData);
  //     setMessages(data.mData);
  //   } catch (error) {
  //     console.log(error);
  //   }
  // };
  
  /**
   * renders a form that allows users to enter new message content and subject. When the form is submitted, the component calls the addMessage function to create a new message using the content and subject entered by the user.
   * "HandleChange" & "HandleSubjectChange" use for handleing the input of user
   * "addMessage" used for add the input message
   * "HandleSubmited" submit the new message
   * using "axios.post" to post the new message
   */
  // input the text
  const handleChange = (e) => {
    setNewMessage(e.target.value);
  };
  const handleSubjectChange = (e) => {
    setNewSubject(e.target.value);
  };
  const addMessage = async () => {
    const message = {
      mSubject: newSubject,
      mMessage: newMessage,
      mLikes: 0,
      mCreated: new Date().toString(),
    };

    try {
      const response = await axios.post(
        "https://2023sp-team-m.dokku.cse.lehigh.edu/insertIdea",
        message
      );
      setMessages([...messages, response.data]);
      setNewMessage("");
      setNewSubject("");
      console.log("New message added");
      getMessages();
    } catch (error) {
      console.log(error);
      // Display an error message to the user
      alert("Could not add message");
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    addMessage();
  };

  /**
   * edit the message, by using the axios.put
   * @param {*} index the index of message we click edit
   * @param {*} newText the input message we want to edit
   */
  // edit message
  const editMessage = async (index, newText) => {
    const messageId = messages[index].mId;
    const updatedMessage = {
      mId: messageId,
      mMessage: newText,
      mLikes: messages[index].mLikes,
    };
    try {
      const response = await axios.put(
        `https://2023sp-team-m.dokku.cse.lehigh.edu/updateIdea/${messageId}`,
        updatedMessage
      );
      const updatedMessages = messages.map((message) =>
        message.mId === messageId ? response.data : message
      );
      setMessages(updatedMessages);
      getMessages(); // fetch the latest data from backend API
      console.log("message updated");
    } catch (err) {
      console.log(err);
    }
  };
  /**
   * like the message(or dislike, click agin within 3 seconds), using axios.put
   * @param {*} index the index of message we click edit
   * @returns if click agin within 3 seconds, the like number will minus 1
   * lastLikeTimestamp use for reocord the click time
   */
  //click like
  const likeMessage = async (index) => {
    const messageId = messages[index].mId;
    const updatedLikeCount = messages[index].mLikes + 1;

    // Check if previous like was within 3 seconds
    const now = Date.now();
    if (lastLikeTimestamp && now - lastLikeTimestamp < 3000) {
      const updatedMessages = messages.map((message, i) => {
        if (i === index) {
          return {
            ...message,
            mLikes: updatedLikeCount - 2,
          };
        } else {
          return message;
        }
      });
      setMessages(updatedMessages);
      setLastLikeTimestamp(now);
      console.log("Cannot like again within 3 seconds");
      return;
    }


    const updatedMessage = {
      ...messages[index],
      mLikes: messages[index].mId
    };
    const updatedMessages = messages.map((message) =>
      message.mId === messageId ? updatedMessage : message
    );
    setMessages(updatedMessages);
    setLastLikeTimestamp(now);

    try {
      const response = await axios.put(
        `http://2023sp-team-m.dokku.cse.lehigh.edu/messages/${messageId}`,
        updatedMessage
      );
      console.log(`message ${messageId} liked`);
      console.log(updatedMessages);
      console.log(response.data);
      console.log("Message updated successfully!");
    } catch (error) {
      console.log(error);
    }
  };

  function attachFile(index, file) {
    // Update the message object at the specified index to include the attached file
    messages[index].mAttachment = file;
  
    // You can perform additional logic here, such as displaying the attached file to the user or sending it to a server for storage.
  
    console.log(`Attached file "${file.name}" to message at index ${index}`);
  }
  

  return (
    <div>
      <script src="https://accounts.google.com/gsi/client" async defer></script>
      <script src="func.js"></script>
      <h1 className = "Title">Welcome!</h1>
      <br />
      <Link to="/profile" className="profile-link">Go to Profile</Link>
      <div>
        <form onSubmit={handleSubmit}>
          <div className="message-box">
            <label htmlFor="subject">Subject:</label>
            <input
              type="text"
              value={newSubject}
              onChange={handleSubjectChange}
              id="subject"
            />
            <label htmlFor="message">Message:</label>
            <input
              type="text"
              value={newMessage}
              onChange={handleChange}
              id="message"
            />
            <div className="send-button">
              <button type="submit">Add New Message</button>
            </div>
          </div>
        </form>
      </div>
      <div>
        <ul className="message-list">
          {messages.map((message, index) => (
            <li key={message.mId} className="message-input">
              <span className="message-item">
                {message.mSubject}: {message.mMessage}
              </span>
              <div className="message-actions">
                <span className="message-likes">{message.mLikes} likes</span>
                <button onClick={() => likeMessage(index)}>Like</button>
                <button onClick={() => editMessage(index, prompt("Enter new text:"))}>Edit</button>
                <input type="file" onChange={(e) => attachFile(index, e.target.files[0])} />
              </div>
            </li>
          ))}
        </ul>
      </div>
      <br />
      <br />
      <br />
      <br />
      <br />
    </div>
  );
}

export default Homepage;
