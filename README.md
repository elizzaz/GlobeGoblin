# Globe Goblin

## Project Overview

Globe Goblin is an application designed for travelers who want to add a touch of adventure and fun to their journeys. Users can participate in location-based challenges and track their progress while earning badges based on their achievements.

---

## Features

### Core Features
- **User Management**: Users can register and log in to the application.
- **Challenges**:
  - Participate in predefined challenges based on the travel destination.
  - Challenges have statuses: `ONGOING`, `COMPLETED`, or `FAILED`.
- **Badge System**:
  - Earn badges based on accumulated points.
  - Each badge has a name, description, and a required number of points.
- **Progress Tracking**: Track user progress on challenges and badges.

### Planned Features (Post-Hackathon)
- **Recommendations**: Suggest activities based on location and preferences.
- **Interactive Companion**: Chat-based interface to interact with users and give suggestions.

---

## Tech Stack

### Backend:
- **Language**: Java
- **Framework**: Spring Boot
- **Database**: MySQL (running on Docker)
- **ORM**: JPA (Hibernate)

### Tools:
- **Build Tool**: Maven
- **Version Control**: Git
- **API Testing**: Postman

---

## Installation

### Prerequisites
- Docker and Docker Compose installed.
- Java 17 or later.
- Maven installed.

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repository-url.git
   cd virtual-travel-companion
   ```
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the database with Docker Compose:
   ```bash
   docker-compose up -d
   ```
4. Start the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
5. Access the application at `http://localhost:8080`.

---

## API Endpoints

### User Management
- **POST** `/api/users`
  - Create a new user.

### Challenges
- **POST** `/api/challenges`
  - Create a new challenge.

### User Challenges
- **POST** `/api/user-challenges?userId={userId}&challengeId={challengeId}`
  - Create a new UserChallenge with the status passed in the body.

### Badges
- **GET** `/api/badges`
  - List all available badges.

---

## Database Schema

### Tables
- **User**:
  - `userId` (PK), `email`, `name`, `point`.
- **Challenge**:
  - `challengeId` (PK), `title`, `description`, `pointValue`.
- **UserChallenge**:
  - `id` (PK), `userId` (FK), `challengeId` (FK), `status`.
- **Badge**:
  - `badgeId` (PK), `name`, `description`, `requiredPoint`.

---

## Future Enhancements
- Integration with external APIs for location-based suggestions.
- Enhanced gamification with leaderboards and social sharing.
- Multi-language support for a global audience.

---

## Contributors
- Elisa Morillon

---
