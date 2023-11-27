# Todo list app

## TASK :

Create a RESTful API to manage a simple todo list application using Spring Boot, Hibernate, and MySQL.
The application should allow users to create, read, update, and delete todo items.
Each item should have a title and a description.
Use Hibernate to persist the items in the database.

## How to run

To run db:
docker-compose up --build

To run project:
mvn spring-boot:run

## Endpoints

POST: /tasks - create task
GET: /tasks - get all tasks
GET: /tasks/{id} - get task by id
DELETE: /tasks/{id} - delete task by id
PUT: /tasks/{id} - update task by id


### Code quality

| Parameter | result |
|:---------:| :---: |
|test coverage (service and controller classes) | 100% |

### questions from the task

- Was it easy to complete the task using AI? _yes_
- How long did task take you to complete? (Please be honest, we need it to gather anonymized statistics) _3 hours_
- Was the code ready to run after generation? _mostly_
- What did you have to change to make it usable? _correct some small mistakes, reorganise some constants_
- Which challenges did you face during completion of the task? _run db with docker (I don't have mySql workbench installed) and testing didn't worked from start_
- Which specific prompts you learned as a good practice to complete the task? _any specific, I tried different_


chatGPT logs:
https://chat.openai.com/share/531beeb6-a6bd-4dc9-b46d-d9cdc260f1e9