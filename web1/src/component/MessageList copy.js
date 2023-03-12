import React, { useState,useEffect } from 'react';
import axios from 'axios';
import './MessageList.css';


interface Message {
  mId: number;
  mSubject: string;
  mMessage: string;
  mLikes: number;
  mCreated: string;
}

interface ApiResponse {
  mStatus: string;
  mData: Message[];
}
function MessageList() {
  //console.log("Yess");

  const [messages, setMessages] = useState<Message[]>([]);
  const [newMessage, setNewMessage] = useState<Message>({
  
  const getMessages = () => {
    //console.log("Yess");
  //   fetch('https://2023sp-team-m.dokku.cse.lehigh.edu/messages')
  //     .then(response => response.json())
  //     .then(data => console.log(data))
  //     .then(error => error.json());
  // }
    
    axios
      .get("https://2023sp-team-m.dokku.cse.lehigh.edu/messages/")
      .then((res) => {
        console.log(res.data.mData);
        setMessages(res.data.mData);
      })
      .catch((err) => {
        console.log(err);
        
      });
  };
  
//   axios.get('https://2023sp-team-m.dokku.cse.lehigh.edu/messages')
//   .then(response => console.log(response.data))
//   .catch(error => console.error(error));
  
// }

  

  useEffect(() => {
    getMessages();
  }, []);

  const addMessage = () => {
    const message = {
      mContent: newMessage,
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
      getMessages();
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
    updatedMessages[index].mContent = newText;
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
      .put(`2023sp-team-m.dokku.cse.lehigh.edu/messages${id}`, updatedMessages[index])
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
      .put(`2023sp-team-m.dokku.cse.lehigh.edu/messages${id}`, updatedMessages[index])
      .then(() => {
        console.log(`post ${id} liked`);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  
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
              <span className = "message-item">{message.mContent}</span>
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