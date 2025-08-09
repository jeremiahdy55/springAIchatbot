# Hotel Registry with AI chatbot integration

[📄 Project Documentation (PDF)](https://github.com/jeremiahdy55/springAIchatbot/blob/main/Hotel%20Chatbot%20Project%20-%20June%202025.pdf)

Project Duration: June 2025 (one month)

## Table of Contents
- [Project Overview](#1-project-overview)
- [Project Architecture](#2-project-architecture)
- [Installation & Setup](#3-installation--setup)
- [Future Steps](#4-future-steps)

---

## 1. Project Overview

This project implements a microservice-based application that simulates the core functionality of a hotel booking website. Key features include:
 - Hotel Information & Booking Services
    - RESTful APIs for backend processing and data retrieval
    - React.js frontend for simple, component-based user interface
 - Secure Authentication
    - Stateless JWT-based authentication for users to access secured content
 - AI-powered Chatbot Integration
    - An NLP-driven conversational assistant embedded into the platform
    - Leverages basic OpenAI API calls on user queries combined with conversational and/or data context
 - Vector Similarity Search for Data Retrieval
    - Vector embeddings and vector similarity search for retrieving contextually relevant records from the database

## 2. Project Architecture

The project is split into three different microservice applications:
 - [Frontend - React UI](#frontend---react-ui)
 - [Backend - Database Access](#backend---database-access)
 - [JWT Security & Authentication](#jwt-security--authentication)

### Frontend - React UI

The React UI microservice techstack uses *React.js* and its related packages to build a dynamic, component-based UI, enable routing within the single-paged applications (SPA), and integrated *Bootstrap* for styling. Webpack is used to compile and bundle JS, CSS, and other static assets into optimized files that are ready to be served to the browser. *Babel* serves as the JS transpiler, converting modern JS (ES6+ features) and JSX syntax into backwards-compatible ES5 code for a wider range of browser support. 

### Backend - Database Access

The Backend database access microservice is built with Spring Boot and powered by *Spring Data JPA* for database interactions with a *PostgreSQL* instance enhanced by the *pgvector* extension for vector-based similarity search. It uses *Apache HTTP Client 5* to communicate with OpenAI’s APIs for both text responses and embedding generation. The generated embeddings are stored in the database and leveraged for semantic search (using vector cosine similarity), enabling the system to return relevant hotel, room, or booking data for natural language queries. *Spring AI* helps streamline AI model integration, and *Jackson* is used for efficient JSON processing. This service is responsible for creating, retrieving, and managing all hotel, room, and booking records in the system.

### JWT Security & Authentication

The JWT Security microservice is built with Spring Boot and powered by *Spring Security* for authentication and authorization. It uses *Spring Data JPA* with a *PostgreSQL* database for secure and efficient data persistence, and *JJWT* (Java JWT) for JSON Web Token handling. This service acts as a gateway, blocking unauthorized access to database resources and ensuring that every request from the React UI passes through token verification. JWTs are issued and validated using RSA256 signing with public and private keys. When a user logs out, the issued token is added to a “revoked list,” preventing it from being reused.

## 3. Installation & Setup

### Configuring PostgreSQL database

Please ensure that the PostgreSQL database you are using has the `pgvector` extension installed. For help on installing and enabling the extension, please see the [official documentation](https://github.com/pgvector/pgvector)

### Generating and setting private and public keys

Run the Java program at `/keypair-generation/KeyGeneratorUtil.java`. This will generate two files called `/keys/public-key.txt` and `/keys/private-key.txt`.
These will be ignored by Github and will be your unique keys for JWT signing.

### Configuring `secrets.txt` in both `/jwtsecurity` and `/hotelchatbot`

- In `/jwtsecurity/src/main/resources/secrets.txt`:
   - Replace the line `jwt.publickey=<GENERATED PRIVATE KEY>` with the contents of `/keypair-generation/keys/public-key.txt`
   - Replace the line `jwt.privatekey=<GENERATED PUBLIC KEY>` with the contents of `/keypair-generation/keys/private-key.txt`
   - Replace the rest of the values with your local configuration
      - PostgreSQL database username/password
      - Desired expiration time for JWT
   - Change the extension of the file from `.txt` to `.properties` (*it should be now ignored by Github!*)

- In `/hotelchatbot/src/main/resources/secrets.txt`:
   - Replace the placeholder values with your local configuration 
      - PostgreSQL database username/password
      - Desired source email address (*this will be the email that email notifications are sent FROM*)
      - App password for the above email address (*similar to personal access token, generate an app password to allow your email service provider to *trust* your application)
   - Change the extension of the file from `.txt` to `.properties` (*it should be now ignored by Github!*)

### Starting the applications

For each *Spring-Boot* microservice application (`hotelchatbot`, `jwtsecurity`), open a terminal and navigate to the root directory of that MS applciation. Then, run:
```
./mvnw spring-boot:run
```
NOTE: Please start `jwtsecurity` before `hotelchatbot` as some dependencies check that the security and authentication application is running.

For the frontend application (`reactUI`), open a terminal and navigate to `/reactUI`. Then, run:
```
npm install
npm run build
npm start
```

### Accessing the Project Application
The application is accessible at `http://localhost:9000`. You may register a new user if desired. A default user profile is configured with:
- username: `admin`
- password: `admin`

## 4. Future Steps

To expand on this project into a more fully-developed production style application, future steps could include:
- Conversation storage per user, allow user to view past chats (also clear stale chats)
- More robust multi-lingual support (currently, user must prompt for a certain language first)
- Greater hotel and hotel room data variety to test the effectiveness of vector similarity search
- More robust data validation when submitting a booking request