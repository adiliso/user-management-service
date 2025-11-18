# User Management Service

A Spring Boot microservice for managing users with Kafka event-driven messaging and PostgreSQL database.

---

## Setup & Run Instructions

### Prerequisites
- Docker & Docker Compose installed
- Java 21 (for local development)
- Gradle (for building locally)

### Run with Docker Compose

1. Clone the repository:

```bash
git clone <https://github.com/adiliso/user-management-service.git>
cd <user-management-service>
````

2. Build and start the services:

```bash
docker compose up --build
```

This will start:

* PostgreSQL on port `5433`
* Kafka + Zookeeper on ports `9092` (host) and `29092` (internal)
* User Management Service on port `8080`

3. To stop services:

```bash
docker compose down
```

### Environment Variables

```text
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=user_db

SPRING_DATASOURCE_URL=jdbc:postgresql://db_user:5432/user_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
```

---

## Example API Calls

### Create User

```bash
# CREATE user (returns 201 Created)
curl -X POST https://adiliso.alakx.com/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Adil Ismayilov",
    "email": "adiliso@example.com",
    "phone": "+994501231212",
    "role" : "USER"
  }'
# Expected: HTTP/1.1 201 Created
```

### Get All Users

```bash
# GET all users (pageable)
# page (0-based), size
curl -X 'GET' \
  'https://adiliso.alakx.com/api/v1/users?pageNumber=0&pageSize=10' \
  -H 'accept: */*'
# Expected: HTTP/1.1 200 OK
# Response: JSON page with content, totalElements, totalPages, etc.
```


### Get User by ID

```bash
# GET user by id
curl -X 'GET' \
  'https://adiliso.alakx.com/api/v1/users/1' \
  -H 'accept: */*'
# Expected: HTTP/1.1 200 OK
# Response: single UserResponse JSON
```

### Update User

```bash
# UPDATE user (returns 200 OK)
curl -X 'PUT' \
  'https://adiliso.alakx.com/api/v1/users/1' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "Adiliso",
  "email": null,
  "phone": "+944501001010",
  "role": null
}'
# Expected: HTTP/1.1 200 OK
```

### Delete User

```bash
# DELETE user (returns 204 No Content)
curl -X 'DELETE' \
  'https://adiliso.alakx.com/api/v1/users/1' \
  -H 'accept: */*'
# Expected: HTTP/1.1 204 No Content
```

---

## Kafka Events

The service produces events for each user operation:

| Event Type   | Topic Name     |
| ------------ | -------------- |
| User Created | `user.created` |
| User Updated | `user.updated` |
| User Deleted | `user.deleted` |


---

## Deployed Base URL

```text
https://adiliso.alakx.com/swagger-ui/index.html
```

---

## Logging

* Application logs can be viewed in the container logs:

```bash
docker compose logs -f app
```

* Kafka logs:

```bash
docker compose logs -f kafka
```

---