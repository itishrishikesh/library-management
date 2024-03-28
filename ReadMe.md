# Library Management Software

## Features
- Users can view books in the library
  - If no books are present then empty list returned
  - If book are present then list of books is returned
- Users can borrow a book from the library
  - If book is present in library
  - User can borrow the book
  - A user can only borrow 2 books at a time
- Users can borrow a copy of the book.
- Users can return a book.

## API
- `/api/books` [GET] Returns the list of books
- `/api/users/{userId}/borrow/{bookId}` [POST] Book is borrowed based on `userId` and `bookId`.
- `/api/users/{userId}/return/{bookId}` [POST] Book is returned based on `userId` and `bookId`.

### Assumptions
- No user sign-up or login is handled.
- No frontend application is built, only backend APIs.
- No state information or location information about book is handled.

### Structure of code
- The code is primarily divided into 4 segments,
  1. Controller
  2. Entity
  3. Repository
  4. Service
- TDD approach is followed, and unit tests are written for,
  1. Controller
  2. Service
- Integration tests are written for,
  1. APIs

**Note:** Test coverage only covers positive scenarios for now.