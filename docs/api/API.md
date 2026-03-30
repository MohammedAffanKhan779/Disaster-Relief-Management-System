# API Documentation

## Base URL
```
http://localhost:8080
```

---

## User Management APIs

### Get All Users
```http
GET /users/api
```

**Response:**
```json
[
  {
    "userId": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "1234567890",
    "role": "DONOR"
  }
]
```

### Get User by ID
```http
GET /users/api/{id}
```

**Parameters:**
- `id` (path) - User ID

**Response:**
```json
{
  "userId": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "1234567890",
  "role": "DONOR"
}
```

### Create User
```http
POST /users/api
```

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "1234567890",
  "password": "password123",
  "role": "DONOR"
}
```

**Response:** `201 Created`

---

## Disaster Event APIs

### Get All Disasters
```http
GET /disasters/api
```

### Create Disaster
```http
POST /disasters/api
```

**Request Body:**
```json
{
  "type": "EARTHQUAKE",
  "severity": "CRITICAL",
  "status": "ACTIVE",
  "location": "Delhi",
  "date": "2026-03-20",
  "description": "Magnitude 6.5"
}
```

---

## Donation APIs

### Get All Donations
```http
GET /donations/api
```

### Create Donation
```http
POST /donations/api
```

**Request Body:**
```json
{
  "type": "MONEY",
  "amount": 5000.00,
  "paymentMethod": "UPI",
  "date": "2026-03-15",
  "description": "Emergency relief fund"
}
```

---

## Testing with cURL

```bash
# Get all users
curl http://localhost:8080/users/api

# Create disaster
curl -X POST http://localhost:8080/disasters/api \
  -H "Content-Type: application/json" \
  -d '{"type":"FLOOD","severity":"HIGH","status":"ACTIVE","location":"Mumbai","date":"2026-03-15","description":"Heavy rainfall"}'
```
