import React, { useState,useEffect } from 'react';
import axios from 'axios';
import './MessageList.css';



function MessageList(){
  // Fetch the messages data from the API
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');
  const [newSubject, setNewSubject] = useState('');
  const [lastLikeTimestamp, setLastLikeTimestamp] = useState(null);
  //const [newLike, setNewLikes] = useState(0);
//console.log("Yess");
  const getMessages = async () => {
    axios
      .get("http://2023sp-team-m.dokku.cse.lehigh.edu/messages")
      .then((res) => {
        console.log(res.data.mData);
        setMessages(res.data.mData);
      })
      .catch((err) => {
        console.log(err);
        
      });
      console.log("Yess");
  }; useEffect(() => {
      getMessages();
    }, [] );

// input the text
  const handleChange = (e) => {
    setNewMessage(e.target.value);
  };
  const handleSubjectChange = (e) => {
    setNewSubject(e.target.value);
  };
// add the message 
  const addMessage = async () => {
    const message = {
      mSubject: newSubject,
      mMessage: newMessage,
      mLikes: 0,
      mCreated: new Date().toString(),
    };

    try {
      const response = await axios.post(
        "http://2023sp-team-m.dokku.cse.lehigh.edu/messages",
        message
      );
      setMessages([...messages, response.data]);
      setNewMessage('');
      setNewSubject('');
      console.log("New message added");
      getMessages();
    } catch (error) {
      console.log(error);
    }
  };
// sumbit the add message
  const handleSubmit = (e) => {
    e.preventDefault();
    addMessage();
    console.log("New message added");
  };

// delete message
  const deleteMessage = async (mId) => {
    //console.log('Deleting message with id:', id);
    const updatedMessages = [...messages];
    updatedMessages.splice(`${mId} `, 1);
    setMessages(updatedMessages);
    axios
    .delete(`http://2023sp-team-m.dokku.cse.lehigh.edu/messages/${mId}`)
    .then(() => {
      console.log(`post ${mId} deleted`);
      //fetchMessages();
      getMessages();
    })
    .catch((err) => {
      console.log(err);
    });
   
  };

// edit message
  const editMessage = async (index, newText) => {
    const messageId = messages[index].mId;
    const messageSubject = messages[index].mSubject;
    const updatedMessage = {
      mId: messageId,
      mSubject: messageSubject,
      mMessage: newText,
      mLikes: messages[index].mLikes,
    };
    try {
      const response = await axios.put(
        `http://2023sp-team-m.dokku.cse.lehigh.edu/messages/${messageId}`,
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
    console.log('Cannot like again within 3 seconds');
    return;
  }

  const updatedMessage = {
    ...messages[index],
    mLikes: updatedLikeCount,
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
    console.log('Message updated successfully!');
  } catch (error) {
    console.log(error);
  }
};



  // const dislikeMessage = async(index) => {
  //   const likeCount = messages[index].mLikes-1;
  //   const messageId = messages[index].mId;
  //   const updatedMessages = [...messages];
  //   const updatedMessage = {
  //     ...messages[index],
  //     mLikes: likeCount,
      
  //   };
  //   //console.log(updatedMessage); 
  //   updatedMessages[index] = updatedMessage;
  //   // const updatedMessages = messages.map((message) =>
  //   //   message.mId === messageId ? updatedMessage : message
  //   // );
  //   console.log(updatedMessages);
  //     try {
  //       const response = await axios.put(
  //         `http://2023sp-team-m.dokku.cse.lehigh.edu/messages/${messageId}`,
  //         updatedMessage
  //       );
  //       console.log(`message ${messageId} liked`);
  //       console.log(updatedMessages);
  //       console.log(response.data);
  //       setMessages(updatedMessages); // Update state with updatedMessages
  //       //getMessages();
  //       console.log('Message updated successfully!');
  //     } catch (error) {
  //       console.log(error);
  //     }
  // };


  return (

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
      
      <ul className = "message-list">
      {messages.map((message, index) => (
          <li key={message.mId} className = "message-input">
              <span className="message-item">{message.mSubject}: {message.mMessage}</span>
            <div className = "message-actions">
              <span className = "message-likes">{message.mLikes} likes</span>
              <button onClick={() => likeMessage(index)}>Like</button>
              {/* <button onClick={() => dislikeMessage(index)}>Dislike</button>  */}
              <button onClick={() => deleteMessage(message.mId)}>Delete</button>
              <button onClick={() => editMessage(index, prompt('Enter new text:'))}>Edit</button>
       
            </div>
          </li>
        ))}
      </ul>
     
    </div>
  );
}

export default MessageList;
