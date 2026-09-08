# ResolveFlow AI

ResolveFlow AI is a Java Spring Boot based AI-powered issue resolution assistant designed to understand user problems and provide structured, practical solutions.

## Features

- AI-powered problem analysis
- Structured step-by-step responses
- Local AI integration using Ollama
- Conversation history storage using MongoDB
- Conversation history viewer
- Clear history functionality
- Input validation
- Error handling
- Responsive web interface
- Fully local and free AI setup

## Tech Stack

### Backend

- Java 21
- Spring Boot 4
- Spring Web
- Spring Data MongoDB
- Bean Validation
- Maven

### AI

- Ollama
- Qwen 2.5 0.5B

### Database

- MongoDB

### Frontend

- HTML
- CSS
- JavaScript

## Architecture

```text
User
  |
  v
Frontend
HTML + CSS + JavaScript
  |
  v
Spring Boot REST API
  |
  +------> Ollama / Qwen AI
  |
  +------> MongoDB
```

## Main API Endpoints

### Generate AI Response

```http
POST /api/ai/generate
```

Example request:

```json
{
  "prompt": "My internet is not working. What should I check first?"
}
```

### Get Conversation History

```http
GET /api/ai/history
```

### Clear Conversation History

```http
DELETE /api/ai/history
```

## Project Structure

```text
src/main/java/com/nehemiah/resolveflowai

controller/
    AiController.java
    HealthController.java
    IssueController.java

model/
    AiConversation.java
    Issue.java

repository/
    AiConversationRepository.java
    IssueRepository.java

service/
    AiConversationService.java
    IssueService.java
    OllamaService.java

exception/
    GlobalExceptionHandler.java
```

Frontend files:

```text
src/main/resources/static

index.html
style.css
app.js
```

## Local Setup

### Requirements

Install:

- Java 21
- MongoDB
- Ollama

Pull the AI model:

```bash
ollama pull qwen2.5:0.5b
```

Start MongoDB.

Start Ollama:

```bash
ollama serve
```

Run the Spring Boot application:

```bash
./mvnw spring-boot:run
```

For Windows PowerShell:

```powershell
.\mvnw spring-boot:run
```

Open:

```text
http://localhost:8080
```

## Configuration

Example `application.properties`:

```properties
spring.application.name=resolveflow-ai
spring.data.mongodb.uri=mongodb://localhost:27017/resolveflowdb
server.port=8080
ollama.base-url=http://localhost:11434
ollama.model=qwen2.5:0.5b
```

## AI Flow

1. User enters a problem in the web interface.
2. JavaScript sends the prompt to the Spring Boot REST API.
3. Spring Boot forwards the request to the local Ollama model.
4. ResolveFlow AI generates a structured response.
5. The prompt and response are saved in MongoDB.
6. Users can view previous conversations through the History panel.
7. Users can delete stored history using the Clear History feature.

## Why This Project

ResolveFlow AI demonstrates practical integration of:

- Java backend development
- REST API development
- AI model integration
- MongoDB persistence
- Frontend and backend integration
- Validation and exception handling
- Local AI deployment

## Future Improvements

- User authentication
- Multiple chat sessions
- Searchable conversation history
- Better AI model support
- Streaming AI responses
- Docker deployment
- Automated testing

## Author

Hari

B.Sc. Computer Science  
Java Full Stack Developer
