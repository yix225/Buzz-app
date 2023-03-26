import { useState } from "react";

const Create = () => {
  const [body, setBody] = useState('');
  const [like, setLike] = useState(0)

  const handleSubmit = (e) => {
    e.preventDefault();
    const post = {body, like };

    fetch('http://2023sp-team-m.dokku.cse.lehigh.edu/messages', {
      method: 'POST',
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(post)
    }).then(() => {
      console.log('new post added');
    })
  }

  return (
    <div className="create">
      <h2>Add a New Message</h2>
      <form onSubmit={handleSubmit}>
        <label>Post body:</label>
        <textarea
          required
          value={body}
          onChange={(e) => setBody(e.target.value)}
        ></textarea>
        <p
          value={like}
          onChange={(e) => setLike(0)}
        >
        </p>
        <button onClick={() => window.location.href = "/"}>Add Message</button>
      </form>
    </div>
  );
}
 
export default Create;