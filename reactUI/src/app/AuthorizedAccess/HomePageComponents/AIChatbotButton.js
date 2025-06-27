import React, { useState, useRef, useEffect } from "react";
import { Form, Button } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faComments } from "@fortawesome/free-solid-svg-icons";
import { getChatResponse } from "../../HTTPComms/HotelAPI";
import { useNavigate } from "react-router-dom";
import RedirectToBookingButton from "./RedirectToBookingButton";

const AIChatbotButton = () => {

  const navigate = useNavigate

  const [open, setOpen] = useState(false);
  const containerRef = useRef(null);
  const bottomMessage = useRef(null);
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState([]);

  // generate random unique string for conversationId
  let conversationId =  Math.random().toString(36).substring(2) + Date.now().toString(36);

  // Close chat when clicking outside
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (containerRef.current && !containerRef.current.contains(event.target)) {
        setOpen(false);
        conversationId =  Math.random().toString(36).substring(2) + Date.now().toString(36); // reset conversationId
        setMessages([]);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  // When conversation is updated, scroll to the most recent message
  useEffect(() => {
    if (bottomMessage.current) {
      bottomMessage.current.scrollIntoView({ behavior: "smooth" });
    }
  }, [messages]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (message.trim()) {
      setMessages([...messages, { text: message, sender: "user" }]);
      setMessage("");
      let botMessage = await getChatResponse(message, conversationId);
      setMessages((prev) => [...prev, { text: botMessage, sender: "bot" }])
    }
  };

  return (
    <div style={styles.container} ref={containerRef}>
      {!open && (
        <button style={styles.button} onClick={() => setOpen(true)}>
          <FontAwesomeIcon icon={faComments} />
        </button>
      )}

      {open && (
        <div style={styles.chatWindow}>
          <div style={styles.header}>
            <strong>AI Chatbot</strong>
            {/* <button onClick={() => setOpen(false)} style={styles.closeBtn}>×</button> */}
          </div>
          <div style={styles.body}>
            {messages.length === 0 && <div style={styles.empty}>Start a conversation...</div>}
            {messages.map((msg, idx) => (
              msg.text.includes("hotelToBook") 
              ?
              <RedirectToBookingButton message={msg} index={idx}/>
              : (<div
                key={idx}
                style={{
                  ...styles.message,
                  alignSelf: msg.sender === "user" ? "flex-end" : "flex-start",
                  backgroundColor: msg.sender === "user" ? "#007bff" : "#e9ecef",
                  color: msg.sender === "user" ? "white" : "black",
                }}
              >
                {msg.text}
              </div>)
            ))}
            <div ref={bottomMessage} />
          </div>
          <Form onSubmit={handleSubmit} style={styles.form}>
            <Form.Control
              type="text"
              placeholder="Type a message..."
              value={message}
              onChange={(e) => setMessage(e.target.value)}
            />
            <Button variant="primary" type="submit" className="mt-2 w-100">Send</Button>
          </Form>
        </div>
      )}
    </div>
  );
};

const styles = {
  container: {
    position: "fixed",
    bottom: "20px",
    right: "20px",
    zIndex: 9999,
  },
  button: {
    width: "60px",
    height: "60px",
    borderRadius: "50%",
    border: "none",
    backgroundColor: "#007bff",
    color: "white",
    fontSize: "24px",
    cursor: "pointer",
    boxShadow: "0 4px 8px rgba(0,0,0,0.2)",
  },
  chatWindow: {
    width: "300px",
    height: "600px",
    backgroundColor: "white",
    border: "1px solid #ddd",
    borderRadius: "10px",
    display: "flex",
    flexDirection: "column",
    boxShadow: "0 6px 12px rgba(0,0,0,0.3)",
  },
  header: {
    padding: "10px 15px",
    backgroundColor: "#007bff",
    color: "white",
    borderTopLeftRadius: "10px",
    borderTopRightRadius: "10px",
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
  },
  closeBtn: {
    background: "none",
    border: "none",
    color: "white",
    fontSize: "20px",
    cursor: "pointer",
  },
  body: {
    flex: 1,
    padding: "10px",
    overflowY: "auto",
    display: "flex",
    flexDirection: "column",
    gap: "8px",
  },
  message: {
    padding: "8px 12px",
    borderRadius: "20px",
    maxWidth: "80%",
  },
  form: {
    padding: "10px",
    borderTop: "1px solid #ddd",
  },
  empty: {
    textAlign: "center",
    color: "#aaa",
    marginTop: "20px",
    fontStyle: "italic",
  },
};

export default AIChatbotButton;
