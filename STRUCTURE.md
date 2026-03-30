# Project Structure

```
disaster-management/
│
├── .github/                          # GitHub specific files
│   ├── workflows/                    # GitHub Actions CI/CD
│   │   └── build.yml                 # Build and test workflow
│   ├── ISSUE_TEMPLATE/               # Issue templates
│   │   ├── bug_report.md
│   │   └── feature_request.md
│   └── pull_request_template.md      # PR template
│
├── docs/                             # Documentation
│   ├── diagrams/                     # UML and other diagrams
│   │   ├── Class_Diagram.jpeg
│   │   └── State_diagram/
│   └── api/                          # API documentation
│       └── API.md
│
├── src/
│   ├── main/
│   │   ├── java/com/disaster/management/
│   │   │   ├── DisasterManagementApplication.java
│   │   │   │
│   │   │   ├── controller/           # CONTROLLER Layer
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── UserController.java
│   │   │   │   ├── DisasterEventController.java
│   │   │   │   └── DonationController.java
│   │   │   │
│   │   │   ├── model/                # MODEL Layer
│   │   │   │   ├── entity/           # JPA Entities
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── Administrator.java
│   │   │   │   │   ├── Donor.java
│   │   │   │   │   ├── Volunteer.java
│   │   │   │   │   ├── ReliefStaff.java
│   │   │   │   │   ├── Authority.java
│   │   │   │   │   ├── DisasterEvent.java
│   │   │   │   │   ├── ReliefCenter.java
│   │   │   │   │   ├── Resource.java
│   │   │   │   │   ├── ResourceRequest.java
│   │   │   │   │   ├── Allocation.java
│   │   │   │   │   ├── Donation.java
│   │   │   │   │   └── Report.java
│   │   │   │   │
│   │   │   │   ├── enums/            # Enumerations
│   │   │   │   │   ├── UserRole.java
│   │   │   │   │   ├── DisasterType.java
│   │   │   │   │   ├── DisasterStatus.java
│   │   │   │   │   ├── SeverityLevel.java
│   │   │   │   │   ├── DonationType.java
│   │   │   │   │   ├── PaymentMethod.java
│   │   │   │   │   ├── RequestStatus.java
│   │   │   │   │   ├── AllocationStatus.java
│   │   │   │   │   └── AvailabilityStatus.java
│   │   │   │   │
│   │   │   │   └── dto/              # Data Transfer Objects
│   │   │   │
│   │   │   ├── repository/           # Data Access Layer
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── AdministratorRepository.java
│   │   │   │   ├── DonorRepository.java
│   │   │   │   ├── VolunteerRepository.java
│   │   │   │   ├── ReliefStaffRepository.java
│   │   │   │   ├── AuthorityRepository.java
│   │   │   │   ├── DisasterEventRepository.java
│   │   │   │   ├── ReliefCenterRepository.java
│   │   │   │   ├── ResourceRepository.java
│   │   │   │   ├── ResourceRequestRepository.java
│   │   │   │   ├── AllocationRepository.java
│   │   │   │   ├── DonationRepository.java
│   │   │   │   └── ReportRepository.java
│   │   │   │
│   │   │   └── service/              # Business Logic Layer
│   │   │       ├── UserService.java
│   │   │       ├── DisasterEventService.java
│   │   │       ├── DonationService.java
│   │   │       ├── ResourceService.java
│   │   │       └── impl/             # Service Implementations
│   │   │           ├── UserServiceImpl.java
│   │   │           ├── DisasterEventServiceImpl.java
│   │   │           ├── DonationServiceImpl.java
│   │   │           └── ResourceServiceImpl.java
│   │   │
│   │   └── resources/
│   │       ├── templates/            # VIEW Layer (Thymeleaf)
│   │       │   ├── index.html
│   │       │   ├── dashboard.html
│   │       │   ├── users/
│   │       │   │   ├── list.html
│   │       │   │   └── register.html
│   │       │   └── disasters/
│   │       │       └── list.html
│   │       │
│   │       ├── static/               # Static Resources
│   │       │   ├── css/
│   │       │   │   └── style.css
│   │       │   ├── js/
│   │       │   └── images/
│   │       │
│   │       └── application.properties # Application Configuration
│   │
│   └── test/                         # Test Files
│       └── java/com/disaster/management/
│
├── target/                           # Build output (gitignored)
│
├── .gitignore                        # Git ignore rules
├── .idea/                            # IDE files (gitignored)
├── pom.xml                           # Maven dependencies
│
├── README.md                         # Project overview
├── REQUIREMENTS.md                   # System requirements
├── SETUP_GUIDE.txt                   # Quick setup guide
├── MVC_IMPLEMENTATION_SUMMARY.md     # MVC architecture details
├── CONTRIBUTING.md                   # Contribution guidelines
├── LICENSE                           # Project license
└── database-setup.sql                # MySQL setup script
```

## Key Directories

### `/src/main/java/com/disaster/management/`
Main application code following MVC architecture:
- **controller/** - HTTP request handlers
- **model/** - Data models and business logic
- **repository/** - Database access layer
- **service/** - Business logic layer

### `/src/main/resources/`
Application resources:
- **templates/** - Thymeleaf HTML templates (Views)
- **static/** - CSS, JS, images
- **application.properties** - Configuration

### `/docs/`
Project documentation:
- **diagrams/** - UML diagrams
- **api/** - API documentation

### `/.github/`
GitHub specific files:
- **workflows/** - CI/CD pipelines
- **ISSUE_TEMPLATE/** - Issue templates
- **pull_request_template.md** - PR template

## File Count
- **Java Files:** 48
- **HTML Templates:** 5+
- **Configuration:** 2
- **Documentation:** 7+
- **Total Files:** 70+

## Technology Stack

| Layer | Technology |
|-------|------------|
| **Backend** | Spring Boot 3.2.3 |
| **Frontend** | Thymeleaf + HTML5 + CSS3 |
| **Database** | MySQL 8.0+ |
| **ORM** | Spring Data JPA / Hibernate |
| **Build Tool** | Maven |
| **Java Version** | 17+ |

## MVC Architecture

```
┌─────────────────────────────────────────────────┐
│                   VIEW LAYER                    │
│  (Thymeleaf Templates + HTML + CSS)             │
│  • index.html                                   │
│  • dashboard.html                               │
│  • users/*, disasters/*                         │
└─────────────────┬───────────────────────────────┘
                  │
┌─────────────────▼───────────────────────────────┐
│              CONTROLLER LAYER                   │
│  (Spring MVC Controllers)                       │
│  • HomeController                               │
│  • UserController                               │
│  • DisasterEventController                      │
│  • DonationController                           │
└─────────────────┬───────────────────────────────┘
                  │
┌─────────────────▼───────────────────────────────┐
│               MODEL LAYER                       │
│  ┌───────────────────────────────────────────┐  │
│  │  Business Logic (Service Layer)          │  │
│  │  • UserService                           │  │
│  │  • DisasterEventService                  │  │
│  │  • DonationService                       │  │
│  └───────────────┬───────────────────────────┘  │
│                  │                              │
│  ┌───────────────▼───────────────────────────┐  │
│  │  Data Access (Repository Layer)          │  │
│  │  • UserRepository                        │  │
│  │  • DisasterEventRepository               │  │
│  │  • DonationRepository                    │  │
│  └───────────────┬───────────────────────────┘  │
│                  │                              │
│  ┌───────────────▼───────────────────────────┐  │
│  │  Domain Model (Entity Layer)             │  │
│  │  • User, Donor, Volunteer                │  │
│  │  • DisasterEvent                         │  │
│  │  • Donation, Resource                    │  │
│  └───────────────────────────────────────────┘  │
└─────────────────┬───────────────────────────────┘
                  │
┌─────────────────▼───────────────────────────────┐
│                  DATABASE                       │
│            MySQL (disaster_management)          │
│  • users, disaster_events, donations, etc.      │
└─────────────────────────────────────────────────┘
```

---

This structure follows industry best practices and provides a clean separation of concerns.
