First, generate private and public keys using `/keypair-generation/KeyGeneratorUtil.java`
This will write in `/keypair-generation/keys` the format of `jwt.publickey=###` or `jwt.secretkey=###` in `public-key.txt` and `private-key.txt`
Copy these lines exactly and place them into `/jwtsecurity/src/main/java/resources/secrets.txt`
Finish filling in `secrets.txt` in `/jwtsecurity/src/main/java/resources/secrets.txt`
Finish filling in `secrets.txt` in `/hotelchatbot/src/main/java/resources/secrets.txt`

Running the application:
ensure that there is a postgresql database called `springchatbot` created with `pgvector` extension installed
run `./mvnw spring-boot:run` in terminal:jwtsecurity
run `./mvnw spring-boot:run` in terminal:hotelchatbot
run `npm run build` then `npm start` in terminal:reactUI
Access the frontend at http://localhost:9000

Default login credentials
username: "admin"
password: "admin"