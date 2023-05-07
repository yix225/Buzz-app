
import React, { useState } from "react";
import { Link } from 'react-router-dom';
import "./Messages.css";

function Messages() {
  
  // State variable to store comment text for a specific message
  const [commentText, setCommentText] = useState("");

  // Function to handle form submit event for adding a message
  const handleFormSubmit = (event) => {
    event.preventDefault();
    const messageText = event.target.message.value;
    setMessages([...messages, { text: messageText, votes: 0, comments: [] }]);
    event.target.reset();
  };

  // Function to handle upvote event for a specific message
  const handleUpvote = (index) => {
    const updatedMessages = [...messages];
    updatedMessages[index].votes++;
    setMessages(updatedMessages);
  };

  // Function to handle downvote event for a specific message
  const handleDownvote = (index) => {
    const updatedMessages = [...messages];
    updatedMessages[index].votes--;
    setMessages(updatedMessages);
  };

  // Function to handle input change event for comment text
  const handleCommentTextChange = (event) => {
    setCommentText(event.target.value);
  };

  // Function to handle form submit event for adding a comment
  const handleCommentSubmit = (event, index) => {
    event.preventDefault();
    const updatedMessages = [...messages];
    updatedMessages[index].comments.push(commentText);
    setMessages(updatedMessages);
    setCommentText("");
  };
  return (
    <div>
      <h1>Messages</h1>
      <form onSubmit={handleFormSubmit}>
        {/* <label htmlFor="message-input">New Message:</label> */}
        <input
          type="text"
          id="message-input"
          name="message"
          placeholder="Enter message"
          required
        />
        <button type="submit">Post</button>
      </form>
      <ul>
        {messages.map((message, index) => (
          <li key={index} style={{ color: "black" }}>
            {message.text}
            <button onClick={() => handleUpvote(index)}>Upvote</button>
            <button onClick={() => handleDownvote(index)}>Downvote</button>
            <span style={{ fontSize: "smaller", color: "lightpink" }}>
              Votes: {message.votes}
            </span>

            <form onSubmit={(event) => handleCommentSubmit(event, index)}>
              <label
                htmlFor={`comment-input-${index}`}
                style={{ fontSize: "14px" }}
              >
                Leave a comment:
              </label>
              <input
                type="text"
                id={`comment-input-${index}`}
                name="comment"
                placeholder="Type a comment"
                value={commentText}
                onChange={handleCommentTextChange}
                required
              />
              <button type="submit">Submit</button>
            </form>
            <ul>
              {message.comments.map((comment, commentIndex) => (
                <li key={commentIndex} className="comment">
                  {comment}
                </li>
              ))}
            </ul>
          </li>
        ))}
      </ul>
    </div>
  );
}
export default Messages;
