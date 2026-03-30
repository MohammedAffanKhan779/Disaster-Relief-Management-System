# 🚨 Disaster Relief Management System

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-red)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A comprehensive Spring Boot application for managing disaster relief operations, built with MVC architecture and MySQL database.

## 🏗️ MVC Architecture

This application follows the **Model-View-Controller (MVC)** design pattern:

### **MODEL Layer** (Business Logic & Data)
- **Entities** (`model/entity/`): JPA entities representing database tables
  - User, Administrator, Donor, Volunteer, ReliefStaff, Authority
  - DisasterEvent, ReliefCenter, Resource, ResourceRequest
  - Allocation, Donation, Report
- **Enums** (`model/enums/`): Enumeration types for status and types
- **Services** (`service/`): Business logic implementation
- **Repositories** (`repository/`): Data access layer using Spring Data JPA

### **VIEW Layer** (User Interface)
- **Thymeleaf Templates** (`resources/templates/`): HTML views
  - Home page, Dashboard
  - User registration and login forms
  - Disaster event management pages
  - Donation forms and lists
- **Static Resources** (`resources/static/`): CSS and JavaScript files

### **CONTROLLER Layer** (Request Handling)
- **Controllers** (`controller/`): Handle HTTP requests and responses
  - HomeController: Main pages and dashboard
  - UserController: User management and authentication
  - DisasterEventController: Disaster event CRUD operations
  - DonationController: Donation management

## 📋 Prerequisites

Before running the application, ensure you have:

- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code recommended)

## 🚀 Setup Instructions

### 1. Clone/Download the Project

```bash
cd /path/to/Mini_Project
```

### 2. Configure MySQL Database

Create a MySQL database:

```sql
CREATE DATABASE disaster_management;
```

Copy the template configuration file and update with your MySQL credentials:

```bash
cd src/main/resources/
cp application.properties.template application.properties
```

Then edit `application.properties` with your database credentials:

```properties
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

**⚠️ Security Note**: `application.properties` is git-ignored to protect your credentials. Never commit it to GitHub!

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

Or run the main class `DisasterManagementApplication.java` from your IDE.

### 5. Access the Application

Open your browser and navigate to:
- **Homepage**: http://localhost:8080
- **Login**: http://localhost:8080/users/login

### 🎲 Database Seeding (Automatic)

The application **automatically seeds** test data on first run! Check `DATABASE_SEED_SUMMARY.md` for:
- ✅ 13 pre-created users (all roles)
- ✅ 4 disaster events
- ✅ 4 relief centers
- ✅ 12 resources
- ✅ 5 donations
- ✅ 4 resource requests
- ✅ Complete relationship linkage

**Test Credentials** (see DATABASE_SEED_SUMMARY.md for full list):
```
Admin:          admin@disaster.org / admin123
Donor:          john.smith@email.com / donor123
Volunteer:      sarah.wilson@email.com / volunteer123
Relief Staff:   mike.johnson@relief.org / staff123
Authority:      authority@gov.in / authority123
```

### 🎨 Role-Based Dashboards

Each user role sees a **different dashboard** with role-specific features:
- **Admin**: Manage users, approve requests, allocate resources, generate reports
- **Donor**: View disasters, make donations, track donation history
- **Volunteer**: Find opportunities, view assigned tasks, update availability
- **Relief Staff**: Request resources, update inventory, track distributions
- **Authority**: View reports, monitor system overview

## 📁 Project Structure

```
Mini_Project/
├── src/
│   ├── main/
│   │   ├── java/com/disaster/management/
│   │   │   ├── DisasterManagementApplication.java  # Main Spring Boot class
│   │   │   ├── controller/          # CONTROLLER Layer
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── UserController.java
│   │   │   │   ├── DisasterEventController.java
│   │   │   │   └── DonationController.java
│   │   │   ├── model/               # MODEL Layer
│   │   │   │   ├── entity/          # JPA Entities
│   │   │   │   ├── enums/           # Enumerations
│   │   │   │   └── dto/             # Data Transfer Objects
│   │   │   ├── repository/          # Data Access Layer
│   │   │   └── service/             # Business Logic
│   │   │       └── impl/            # Service Implementations
│   │   └── resources/
│   │       ├── templates/           # VIEW Layer (Thymeleaf)
│   │       │   ├── index.html
│   │       │   ├── users/
│   │       │   ├── disasters/
│   │       │   └── donations/
│   │       ├── static/              # CSS, JS, Images
│   │       │   └── css/style.css
│   │       └── application.properties  # Configuration
│   └── test/                        # Test classes
├── pom.xml                          # Maven dependencies
└── README.md                        # This file
```

## 🛠️ Technologies Used

- **Spring Boot 3.2.3** - Application framework
- **Spring Data JPA** - Database connectivity and ORM
- **Spring Web** - REST API and MVC support
- **Thymeleaf** - Server-side template engine
- **MySQL** - Relational database
- **Lombok** - Reduce boilerplate code
- **Maven** - Dependency management

## 📊 Database Schema

The application uses the following main entities:

| Entity | Description |
|--------|-------------|
| **users** | Base user table with inheritance |
| **administrators** | System administrators |
| **donors** | Users who make donations |
| **volunteers** | Relief volunteers |
| **relief_staff** | Staff at relief centers |
| **authorities** | Government/org authorities |
| **disaster_events** | Disaster records |
| **relief_centers** | Relief center locations |
| **resources** | Available resources |
| **resource_requests** | Resource requests |
| **allocations** | Resource allocations |
| **donations** | Donation records |
| **reports** | System reports |

## 🔌 API Endpoints

### User Management
- `GET /users` - List all users
- `POST /users/register` - Register new user
- `GET /users/api` - REST: Get all users
- `GET /users/api/{id}` - REST: Get user by ID
- `POST /users/api` - REST: Create user
- `PUT /users/api/{id}` - REST: Update user
- `DELETE /users/api/{id}` - REST: Delete user

### Disaster Events
- `GET /disasters` - List all disasters
- `GET /disasters/new` - Show create form
- `POST /disasters` - Create disaster event
- `GET /disasters/{id}` - View disaster details
- `GET /disasters/api` - REST: Get all disasters
- `POST /disasters/api` - REST: Create disaster
- `PUT /disasters/api/{id}` - REST: Update disaster
- `DELETE /disasters/api/{id}` - REST: Delete disaster

### Donations
- `GET /donations` - List all donations
- `GET /donations/new` - Show donation form
- `POST /donations` - Process donation
- `GET /donations/api` - REST: Get all donations
- `POST /donations/api` - REST: Create donation

## ⚙️ Configuration

### Application Properties

Key configuration in `application.properties`:

```properties
# Server Configuration
server.port=8080

# MySQL Database
spring.datasource.url=jdbc:mysql://localhost:3306/disaster_management
spring.datasource.username=root
spring.datasource.password=root

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Thymeleaf
spring.thymeleaf.cache=false
```

### Hibernate DDL Options

- `create` - Drop and recreate tables (data loss)
- `update` - Update schema without data loss (recommended for development)
- `validate` - Validate schema without changes
- `none` - No schema management

## 🧪 Testing

Run tests with:

```bash
mvn test
```

## ✨ Features

- 🔐 **User Management** - Registration, authentication, role-based access
- 🚨 **Disaster Tracking** - Real-time disaster event management
- 💰 **Donation System** - Process donations with automated receipts
- 📦 **Resource Management** - Track and allocate relief resources
- 🏥 **Relief Centers** - Manage multiple relief center operations
- 📊 **Analytics** - Generate reports and track statistics
- 🔌 **REST API** - Complete RESTful API for integrations
- 📱 **Responsive UI** - Mobile-friendly web interface  

## 👥 Team

**Add your team member details:**

| Name | Student ID | Role |
|------|-----------|------|
| Name 1 | SRN123456 | Developer |
| Name 2 | SRN123457 | Developer |
| Name 3 | SRN123458 | Developer |
| Name 4 | SRN123459 | Developer |

## 📄 License

This project is developed as part of an academic assignment.

## 📚 Documentation

- [Architecture Details](ARCHITECTURE.md) - MVC implementation details
- [Project Structure](STRUCTURE.md) - File organization
- [API Documentation](docs/api/API.md) - REST API endpoints
- [Setup Guide](docs/SETUP.txt) - Detailed setup instructions
- [Quick Start](docs/QUICKSTART.md) - 5-minute setup
- [GitHub Guide](docs/GITHUB.md) - Repository setup

## 📞 Support

- 📖 Check the [documentation](docs/)
- 🐛 Report bugs via [GitHub Issues](../../issues)
- 💬 Contact the development team

---

**Built with ❤️ using Spring Boot and MVC Architecture**
