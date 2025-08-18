# Dashboard Project (Spring Boot + Vue 3)

This project is an educational demo project I created as an intern to learn the basics of full-stack development. The backend was written in Java Spring Boot, while the frontend was developed using Vue 3 + PrimeVue.

## General Features

- User login (using JWT tokens)
- CRUD operations for city, port, and exchange rate data
- Filtering and sorting features
- Graphical visualization (e.g., the city with the most ports)
- User action logging (log system)

## Backend (Spring Boot)

### Technologies Used:

- Spring Boot
- Spring Security (JWT)
- JPA / Hibernate
- PostgreSQL
- Lombok

### Developed Features:

- Three different entities named `City`, `Port`, and `ExchangeRate` were created. - CRUD operations for each entity are written at the service, controller, and repository layers.
- Role-based authorization (for admin users)
- User-friendly error messages with exception handling
- Specification logic for dynamic filtering and querying
- A simple logging system that records every operation performed in the database

### Logging System:

In the project, CRUD operations performed by users are recorded in the `ActivityLog` table. For example, when a port is deleted or updated, the following information is logged:

- User name
- Table name (e.g., `PORT`)
- Operation type (`CREATE`, `UPDATE`, `DELETE`)
- Description (e.g., "Port deleted: Mersin Port (MRS123)")
- Operation time
- Changed columns, old and new values (in case of update)

Thanks to this system, transactions can now be tracked.

## Frontend (Vue 3 + PrimeVue)

### Technologies Used:

- Vue 3 Composition API
- PrimeVue components (DataTable, Dialog, Calendar, Chart)
- Backend connection with Axios
- JWT token management (Token is stored after login and used in all requests)

### Developed Screens:

- Login screen
- City management (CRUD + filtering)
- Port management (CRUD + city-based filtering)
- Exchange rate management (CRUD + filtering by date range)
- Dashboard screen (total data, graphs, recently added records, etc.)
- Data visualization with bar and line charts

## How to Run?

A separate terminal must be opened within each folder.
