import React, { useState,useEffect } from 'react';
import axios from 'axios';
import './MessageList.css';



function MessageList(){
  
//   const [messages, setMessages] = useState([]);
//   const [newMessage, setNewMessage] = useState('');
  // Fetch the messages data from the API
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');
  const fetchMessages = async () => {
    try {
      const response = await fetch("https://2023sp-team-m.dokku.cse.lehigh.edu/messages");
      fetch('https://mango-project-216.herokuapp.com/posts', {
        method: 'GET',
        headers: { "Content-Type": "application/json" },
        message: JSON.stringify(messages)
      }).then(() => {
        console.log('fetch data');
      })
      if (response.ok) {
        const data = await response.json();
        setMessages(data.mData);
      } else {
        console.error("Failed to fetch messages data");
      }
    } catch (error) {
      console.error(error);
    }
   
  }; 
  useEffect(() => {
    fetchMessages();
  }, [] );

  

//   const getMessages = () => {
//     //console.log("Yess");
//   //   fetch('https://2023sp-team-m.dokku.cse.lehigh.edu/messages')
//   //     .then(response => response.json())
//   //     .then(data => console.log(data))
//   //     .then(error => error.json());
//   // }
    
//     axios
//       .get("https://2023sp-team-m.dokku.cse.lehigh.edu/messages")
//       .then((res) => {
//         console.log(res.data.mData);
//         setMessages(res.data.mData.map((message) => ({
//           mId: message.mId,
//           mSubject: message.mSubject,
//           mMessage: message.mMessage,
//           mLikes: message.mLikes,
//           mCreated: message.mCreated
//         })));
//       })
//       .catch((err) => {
//         console.log(err);
        
//       });
//   };
  
// //   axios.get('https://2023sp-team-m.dokku.cse.lehigh.edu/messages')
// //   .then(response => console.log(response.data))
// //   .catch(error => console.error(error));
  
// // }

  

//   useEffect(() => {
//     getMessages();
//   }, []);

  const addMessage = () => {
    const message = {
      mMessage: newMessage,
      mLikes: 0,
    };
    setMessages([...messages, message]);
    setNewMessage("");
    axios
      .post("https://2023sp-team-m.dokku.cse.lehigh.edu/messages/", {mData: [message]})
      .then(() => {
        console.log("new post added");
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const deleteMessage = async (id) => {
    //console.log('Deleting message with id:', id);
    const updatedMessages = [...messages];
    updatedMessages.splice(`${id} `, 1);
    setMessages(updatedMessages);
    axios
    .delete(`https://2023sp-team-m.dokku.cse.lehigh.edu/messages/${id}`)
    .then(() => {
      console.log(`post ${id} deleted`);
      fetchMessages();
    })
    .catch((err) => {
      console.log(err);
    });
    // await fetch(`/messages/${id}`, { method: 'DELETE' });
    // const remainingMessages = messages.filter(message => message.id !== id);
    // setMessages(remainingMessages);

   
  };

  const editMessage = (index, newText) => {
    const updatedMessages = [...messages];
    updatedMessages[index].mMessage = newText;
    setMessages(updatedMessages);
    const id = updatedMessages[index].mId;
    axios
      .put(`https://2023sp-team-m.dokku.cse.lehigh.edu/messages/${id}`, updatedMessages[index])
      .then(() => {
        console.log(`post ${id} edited`);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const likeMessage = (index) => {
    const updatedMessages = [...messages];
    updatedMessages[index].mLikes++;
    setMessages(updatedMessages);
    const id = updatedMessages[index].mId;
    axios
      .put(`2023sp-team-m.dokku.cse.lehigh.edu/messages/${id}`, updatedMessages[index])
      .then(() => {
        console.log(`post ${id} liked`);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  const dislikeMessage = (index) => {
    const updatedMessages = [...messages];
    updatedMessages[index].mLikes--;
    setMessages(updatedMessages);
    const id = updatedMessages[index].mId;
    axios
      .put(`2023sp-team-m.dokku.cse.lehigh.edu/messages/${id}`, updatedMessages[index])
      .then(() => {
        console.log(`post ${id} liked`);
      })
      .catch((err) => {
        console.log(err);
      });
  };
//Define the Message interface to describe the structure of the messages

  

  
     
  
  // // Define the CRUD operations
  // const addMessage = async (message) => {
  //   try {
  //     const response = await fetch("https://2023sp-team-m.dokku.cse.lehigh.edu/messages", {
  //       method: "POST",
  //       headers: {
  //         "Content-Type": "application/json",
  //       },
  //       body: JSON.stringify({ mData: message }),
  //     });
  //     if (response.ok) {
  //       const data = await response.json();
  //       setMessages([...message, data.mData]);
  //     } else {
  //       console.error("Failed to add message");
  //     }
  //   } catch (error) {
  //     console.error(error);
  //   }
  // };
  
  // const editMessage = async (messageId, newMessage) => {
  //   try {
  //     const response = await fetch(`https://2023sp-team-m.dokku.cse.lehigh.edu/messages/${message.mId}`, {
  //       method: "PUT",
  //       headers: {
  //         "Content-Type": "application/json",
  //       },
  //       body: JSON.stringify({mMessage:message}),
  //     });
  //     if (response.ok) {
  //       const data = await response.json();
  //       const updatedMessages = message.map((message) => {
  //         if (message.mId === data.mId) {
  //           return {
  //             ...message,
  //             mData: data.mData,
  //           };
  //         } else {
  //           return message;
  //         }
  //       });
  //       setMessages(updatedMessages);
  //     } else {
  //       console.error("Failed to edit message");
  //     }
  //   } catch (error) {
  //     console.error(error);
  //   }
  // };
  // const deleteMessage = async (messageId) => {
  //   try {
  //     const response = await fetch(`https://2023sp-team-m.dokku.cse.lehigh.edu/messages/${messageId}`, {
  //       method: "DELETE",
  //     });
  //     if (response.ok) {
  //       setMessages(message.filter((m) => m.mId !== messageId));
  //     } else {
  //       console.error("Failed to delete message");
  //     }
  //   } catch (error) {
  //     console.error(error);
  //   }
  // };
  
  // const likeMessage = async (messageId, increment) => {
  //   try {
  //     const response = await fetch(`https://2023sp-team-m.dokku.cse.lehigh.edu/messages/${messageId}/likes`, {
  //       method: "PUT",
  //       headers: {
  //         "Content-Type": "application/json",
  //       },
  //       body: JSON.stringify({
  //         increment:1,
  //       }),
  //     });
  //     if (response.ok) {
  //       const data = await response.json();
  //       const updatedMessages = message.map((message) => {
  //         if (message.mId === data.mId) {
  //           return {
  //             ...message,
  //             mLikes: data.mLikes,
  //           };
  //         } else {
  //           return message;
  //         }
  //       });
  //       setMessages(updatedMessages);
  //     } else {
  //       console.error("Failed to update likes");
  //     }
  //   } catch (error) {
  //     console.error(error);
  //   }
  // };

  // const dislikeMessage = async (messageId, decrement) => {
  //   try {
  //     const response = await fetch(`https://2023sp-team-m.dokku.cse.lehigh.edu/messages/${messageId}/dislikes`, {
  //       method: "PUT",
  //       headers: {
  //         "Content-Type": "application/json",
  //       },
  //       body: JSON.stringify({
  //         decrement:1,
  //       }),
  //     });
  //     if (response.ok) {
  //       const data = await response.json();
  //       const index = message.findIndex((m) => m.mId === data.mId);
  //       message[index].mLikes = data.mLikes;
  //     } else {
  //       console.error("Failed to update likes");
  //     }
  //   } catch (error) {
  //     console.error(error);
  //   }
  // };
  


  
  const handleSubmit = (e) => {
    e.preventDefault();
    const post = addMessage();
    fetch(' ', {
      method: 'POST',
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(post)
    }).then(() => {
      console.log('new post added');
    })
  };

  const handleChange = (e) => {
    setNewMessage(e.target.value);
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <div className="message-box"> 
        <input type="text" value={newMessage} onChange={handleChange} />
        <div className = "send-button">
            <button type="submit">M</button>
        </div>
        </div>
      </form>
      <ul className = "message-list">
        {messages.map((message, index) => (
          <li key={index} className = "message-input">
              <span className = "message-item">{message.mMessage}</span>
            <div className = "message-actions">
              <span className = "message-likes">{message.mLikes} likes</span>
              <button onClick={() => likeMessage(index)}>Like</button>
              <button onClick={() => dislikeMessage(index)}>Dislike</button> 
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