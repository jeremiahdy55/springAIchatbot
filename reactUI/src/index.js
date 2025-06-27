import 'bootstrap/dist/css/bootstrap.min.css';
import React from "react"
import App from "./app/app"
import * as ReactDOM from "react-dom/client"
import { Provider } from "react-redux";
// import store from "./app/State/store";

const root = ReactDOM.createRoot(document.getElementById("root"));

// in later versions, could possibly use redux-store to store conversationId's
// from a user's past chatbot conversations
// Use those conversationId's to retrieve chat histories
root.render(
    // <Provider store={null}> 
        <App/>
    // </Provider>
)
