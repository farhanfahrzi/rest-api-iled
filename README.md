# API Documentation for Application
## Introduction
- This section of the API documentation covers all the necessary endpoints required to interact with the system's database. The API facilitates operations related to user management, scheduling, attendance, and programming languages handling within the system. 
## Authentication and Authorization.
- Discuss the methods and technologies used for authenticating and authorizing users. Provide instructions on how to authenticate API requests.
## Base URL
- Specify the base URL used for the API calls.
## Endpoints Overview
### User Accounts
- Create User Account
  - POST /user_accounts
  - Payload: username, password, email, is_enable
  - Description: Creates a new user account.
- Get User Account
  - GET /user_accounts/{id}
  - Description: Retrieves details of a specified user account.
- Update User Account
  - PUT /user_accounts/{id}
  - Payload: username, email, is_enable
  - Description: Updates an existing user account.
- Delete User Account
  - DELETE /user_accounts/{id}
  - Description: Deletes a specified user account.
### Roles
- Assign Role to User
  - POST /user_account_roles
  - Payload: user_account_id, role_id
  - Description: Assigns a role to a user account.
- List Roles
  - GET /roles
  - Description: Lists all available roles.
### Trainers
- Add Trainer
  - POST /trainers
  - Payload: user_account_id, name, email, phone_number, address, birth_date
  - Description: Adds a new trainer to the system.
- Get Trainer
  - GET /trainers/{id}
  - Description: Retrieves details of a specified trainer.
### Schedules
- Create Schedule
  - POST /schedules
  - Payload: trainer_id, date, topic, link_schedule, programming_language_id
  - Description: Creates a new training schedule.
- Get Schedule
  - GET /schedules/{id}
  - Description: Fetches details of a specified schedule.
### Attendance
- Record Attendance
  - POST /attendances
  - Payload: schedule_id, date
  - Description: Records attendance for a schedule.
- Get Attendance
  - GET /attendances/{id}
  - Description: Retrieves attendance details for a given schedule.
### Programming Languages
- Programming Languages
  - Add Programming Language
  - POST /programming_languages
  - Payload: name
  - Description: Adds a new programming language to the system.
- List Programming Languages
  - GET /programming_languages
  - Description: Lists all programming languages.
### Questions
- Post Question
  - POST /questions
  - Payload: trainee_id, question, answer, status, schedule_id
  - Description: Submits a question by a trainee.
- Get Question
  - GET /questions/{id}
  - Description: Retrieves a specific question. 
## Error Handling
- Provide a description of common errors and status codes that the API may return, and explain what each code means for better client-side handling.
## Rate Limiting
- Mention if there is any rate limiting in place for the API calls to avoid abuse.
## Conclusion
- End the documentation by providing contact information or links for further support or feedback regarding the API.