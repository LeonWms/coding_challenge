# Deesee Coding-Challenge

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

### API Endpoints
- `GET /api/superheroes` - Get all superheroes
**Additional optional parameter*:*
- `GET /api/superheroes?superpowers=flight,strength` - Filter superheroes by powers
- `GET /api/superheroes?encrypted=true` - Get superheroes with encrypted identities
- `GET /api/superheroes?superpowers=flight,strength,encrypted=true` - Get superheroes by power with encrypted identities
- 
- `POST /api/superheroes` - Create a new superhero
- `POST /api/superheroes/batch` - Create multiple superheroes

