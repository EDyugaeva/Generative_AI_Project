# Bookstore app

## Task

Implement a RESTful API for a simple online bookstore using Spring and Hibernate.
The API should allow users to perform CRUD operations on books, authors, and genres.
Books should have a title, author, genre, price, and quantity available.
Users should be able to search for books by title, author, or genre.
Use Hibernate to persist data to a relational database.

## How to run

To run db: docker-compose up --build

To run project: mvn spring-boot:run

app will be available on http://localhost:8080/swagger-ui/index.html#/

## Endpoints

### Book Controller:

- POST: /books
Create a new book.
Request Body: Book object.
Response: Created book with status 201.
- GET: /books
Get a list of all books.
Response: List of books with status 200.
- GET: /books/{id}
Get a book by its unique ID.
Path Variable: ID of the book.
Response: Book with status 200 if found, 404 if not found.
- PUT: /books/{id}
Update a book by its ID.
Path Variable: ID of the book to be updated.
Request Body: Updated book object.
Response: Updated book with status 200 if successful, 404 if the book with the given ID is not found.
- DELETE: /books/{id} 
Delete a book by its ID.
Path Variable: ID of the book to be deleted.
Response: No content with status 204 if successful, 404 if the book with the given ID is not found.
- GET: /books/searchByTitle
Search books by title.
Request Parameter: Title of the book to search for.
Response: List of books with status 200.
- GET: /books/searchByAuthor
Search books by author.
Request Parameters: First name and last name of the author.
Response: List of books with status 200.
- GET: /books/searchByGenre
Search books by genre.
Request Parameter: Genre of the book.
Response: List of books with status 200.
### Author Controller:

- POST: /authors
Create a new author.
Request Body: Author object.
Response: Created author with status 201.
GET: /authors
- Get a list of all authors.
Response: List of authors with status 200.
- GET: /authors/{id}
Get an author by its unique ID.
Path Variable: ID of the author.
Response: Author with status 200 if found, 404 if not found.
- PUT: /authors/{id}
Update an author by its ID.
Path Variable: ID of the author to be updated.
Request Body: Updated author object.
Response: Updated author with status 200 if successful, 404 if the author with the given ID is not found.
- DELETE: /authors/{id}
Delete an author by its ID.
Path Variable: ID of the author to be deleted.
Response: No content with status 204 if successful, 404 if the author with the given ID is not found.

### Genre Controller:

- POST: /genre
Create a new genre.
Request Body: Genre object.
Response: Created genre with status 201.
- GET: /genre
Get a list of all genres.
Response: List of genres with status 200.
- GET: /genre/{id}
Get a genre by its unique ID.
Path Variable: ID of the genre.
Response: Genre with status 200 if found, 404 if not found.
- PUT: /genre/{id}
Update a genre by its ID.
Path Variable: ID of the genre to be updated.
Request Body: Updated genre object.
Response: Updated genre with status 200 if successful, 404 if the genre with the given ID is not found.
- DELETE: /genre/{id}
Delete a genre by its ID.
Path Variable: ID of the genre to be deleted.
Response: No content with status 204 if successful, 404 if the genre with the given ID is not found.

## Code quality

| Parameter | result |
|:---------:| :---: |
|test coverage (service and controller classes) | 100% |

### questions from the task

- Was it easy to complete the task using AI? _**yes**_
- How long did task take you to complete? (Please be honest, we need it to gather anonymized statistics) _**5 hours**_
- Was the code ready to run after generation? _**mostly**_
- What did you have to change to make it usable? _**correct some small mistakes, configuration**_
- Which challenges did you face during completion of the task? _**spring configuration(different version compatibility)**_
- Which specific prompts you learned as a good practice to complete the task? _**any specific, I tried different**_


chatGPT logs:### Code quality

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
