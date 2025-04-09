# Deesee Coding-Challenge
The challengen was developed with Java 17 and the Spring framework was used.
## Getting started

### Prerequisites (Recommended)

- Java JDK 17
- Maven 3.8

### Installation
1. **Clone the repo**
   ```sh
   git clone https://github.com/LeonWms/coding_challenge.git
   ```
2. **Build the application**
   Using Maven
      ```sh
   mvn clean package
   ```
3. **Run the application**
      ```sh
   java -jar target/deesee-0.0.1-SNAPSHOT.jar
   ```
4. **Verify installation**
      ```sh
   curl http://localhost:8080/api/superheroes
   ```
Application loads superhero data from `src/main/resources/superheroes.json` on startup

**Using Docker**
1. Build the image
      ```sh
   docker build -t deesee .
   ```
2. Run the container
      ```sh
   docker run -p 8080:8080 deesee
   ```



### API Endpoints
- `GET /api/superheroes` - Get all superheroes
<br>**Query Parameters:**<br>
- `GET /api/superheroes?superpowers=flight,strength` - Filter superheroes by powers
- `GET /api/superheroes?encrypted=true` - Get superheroes with encrypted identities
- `GET /api/superheroes?superpowers=flight,strength&encrypted=true` - Get superheroes by power with encrypted identities
<br><br>
- `POST /api/superheroes` - Create a new superhero
- `POST /api/superheroes/batch` - Create multiple superheroes

