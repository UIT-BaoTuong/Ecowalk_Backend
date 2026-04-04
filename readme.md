# Ecowalk - Walking Tracking Application

Ecowalk is a mobile application that helps users track, record, and manage outdoor walking and running activities. The project focuses on providing accurate route, speed, and energy-consumption metrics to encourage an active and healthy lifestyle.

## Report Slides

[Final Report Slides](https://docs.google.com/presentation/d/1rNpZ0r186bGMIFk0gnLJQ991Z4K-iy4f/edit?slide=id.p9#slide=id.p9)

## Team Members

- Nguyen Duy Bao Tuong - 23521750
- Duong Thanh Huyen - 23520659
- Doan Thanh Thao - 23521466

Supervisor: Ms. Tran Hong Nghi

## Main Features

- Activity tracking: Records distance (km), duration, speed, and calories burned based on GPS data.
- Interactive map: Displays the traveled route on OpenStreetMap (OSM).
- User management: Supports registration, login, and personal data security.
- Workout history: Stores and displays previous walking/running sessions.
- Leaderboard (in development): Tracks user rankings within the community.

## Technology Stack

### Backend (Spring Boot)

- Language: Java
- Framework: Spring Boot 3.x
- Security: Spring Security and JSON Web Token (JWT) for stateless authentication
- Database: PostgreSQL (deployed on Neon)
- ORM: Spring Data JPA and Hibernate 6.x
- Supporting libraries: Project Lombok, Hibernate Types (for JSON/Array handling)

### Frontend (Android)

- Language: Java and XML for UI
- Map: OpenStreetMap (OSM)
- Architecture: Standard client-server model, communicating via RESTful API (HTTP/JSON)

## Application Interface

The application includes the following main screens:

- Login/Register/Reset Password: User authentication with strict input validation rules
<img width="320" height="711" alt="Image" src="https://github.com/user-attachments/assets/ae9722f5-1880-45e6-ac3b-d6af83a2fa06" />
<img width="320" height="711" alt="Image" src="https://github.com/user-attachments/assets/0da1f241-ca39-4bb0-9265-ca0130b618da" />
<img width="357" height="793" alt="Image" src="https://github.com/user-attachments/assets/a80e5954-e29e-4c4b-aa3d-15bfabbfffef" />
- Main Dashboard: Overview of activity data, feed, and personal profile
<img width="358" height="794" alt="Image" src="https://github.com/user-attachments/assets/8d0055fd-bff2-49bd-9323-2236c80f42df" />

- Leaderboard: Ranking by distance
<img width="358" height="794" alt="Image" src="https://github.com/user-attachments/assets/b7c643b4-4f9b-4182-a63e-fd7b3ca61548" />

- Tracking Screen: Displays the real-time map and movement metrics
<img width="358" height="794" alt="Image" src="https://github.com/user-attachments/assets/c2a9ea78-1fce-466e-a7fb-0048155fe538" />

## Future Development

- Add more community features: challenges and a more detailed leaderboard
- Integrate with wearable devices (Smartwatch, Google Fit, Apple Health)
- Apply Machine Learning to personalize training goals based on historical data