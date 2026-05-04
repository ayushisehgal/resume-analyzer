import React, { useState } from "react";

function App() {
  const [file, setFile] = useState(null);
  const [output, setOutput] = useState("");

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleUpload = async () => {
    if (!file) {
      alert("Please select a file");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
      const response = await fetch("http://localhost:8080/api/resume/upload", {
        method: "POST",
        body: formData,
      });

      const data = await response.text();  // backend returns text
      setOutput(data);

    } catch (error) {
      console.error(error);
      setOutput("Error connecting to backend");
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Resume Analyzer</h2>

      <input type="file" onChange={handleFileChange} />
      <br /><br />

      <button onClick={handleUpload}>Upload Resume</button>

      <h3>Result:</h3>
      <pre style={{ whiteSpace: "pre-wrap" }}>{output}</pre>
    </div>
  );
}

export default App;