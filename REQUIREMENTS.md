# System Requirements and Dependencies

## Prerequisites

### Required Software

| Software | Version | Purpose |
|----------|---------|---------|
| **Java JDK** | 17 or higher | Runtime environment |
| **Maven** | 3.6+ | Build tool and dependency management |
| **MySQL** | 8.0+ | Database server |
| **Git** | 2.x+ | Version control |

### Optional (Recommended)

| Tool | Purpose |
|------|---------|
| **IntelliJ IDEA** | IDE (Community or Ultimate) |
| **Eclipse IDE** | Alternative IDE |
| **VS Code** | Lightweight editor with Java extensions |
| **MySQL Workbench** | Database management GUI |
| **Postman** | API testing tool |

---

## Installation Instructions

### 1. Java JDK Installation

**macOS:**
```bash
brew install openjdk@17
```

**Windows:**
- Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)
- Run installer and set JAVA_HOME environment variable

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

**Verify Installation:**
```bash
java -version
javac -version
```

### 2. Maven Installation

**macOS:**
```bash
brew install maven
```

**Windows:**
- Download from [Apache Maven](https://maven.apache.org/download.cgi)
- Extract and add to PATH

**Linux (Ubuntu/Debian):**
```bash
sudo apt install maven
```

**Verify Installation:**
```bash
mvn -version
```

### 3. MySQL Installation

**macOS:**
```bash
brew install mysql
brew services start mysql
```

**Windows:**
- Download from [MySQL Downloads](https://dev.mysql.com/downloads/installer/)
- Run installer with default settings

**Linux (Ubuntu/Debian):**
```bash
sudo apt install mysql-server
sudo systemctl start mysql
```

**Verify Installation:**
```bash
mysql --version
```

---

## Project Dependencies (Maven)

All dependencies are managed through `pom.xml`. They will be automatically downloaded when you build the project.

### Spring Boot Dependencies

```xml
<!-- Core Spring Boot -->
- spring-boot-starter-web (3.2.3)
- spring-boot-starter-data-jpa (3.2.3)
- spring-boot-starter-thymeleaf (3.2.3)
- spring-boot-starter-validation (3.2.3)

<!-- Database -->
- mysql-connector-j (runtime)

<!-- Development Tools -->
- spring-boot-devtools (runtime, optional)
- lombok (optional)

<!-- Testing -->
- spring-boot-starter-test (test scope)
```

### Complete Dependency List

| Dependency | Version | Scope | Purpose |
|------------|---------|-------|---------|
| spring-boot-starter-web | 3.2.3 | compile | Web application support |
| spring-boot-starter-data-jpa | 3.2.3 | compile | JPA/Hibernate ORM |
| spring-boot-starter-thymeleaf | 3.2.3 | compile | Template engine |
| spring-boot-starter-validation | 3.2.3 | compile | Bean validation |
| mysql-connector-j | Latest | runtime | MySQL JDBC driver |
| lombok | Latest | compile | Reduce boilerplate code |
| spring-boot-devtools | Latest | runtime | Development tools |
| spring-boot-starter-test | 3.2.3 | test | Testing framework |

---

## Environment Setup

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/disaster-management.git
cd disaster-management
```

### 2. Configure MySQL Database

Create database:
```sql
CREATE DATABASE disaster_management;
CREATE USER 'disaster_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON disaster_management.* TO 'disaster_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configure Application

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.username=disaster_user
spring.datasource.password=your_password
```

### 4. Build the Project

```bash
mvn clean install
```

This will:
- Download all dependencies
- Compile the code
- Run tests
- Package the application

### 5. Run the Application

```bash
mvn spring-boot:run
```

Or run the JAR file:
```bash
java -jar target/disaster-management-1.0.0.jar
```

### 6. Access the Application

Open browser: `http://localhost:8080`

---

## IDE Setup

### IntelliJ IDEA

1. **Import Project:**
   - File → Open → Select project directory
   - IntelliJ will auto-detect Maven project

2. **Enable Lombok:**
   - Settings → Plugins → Install "Lombok"
   - Settings → Build → Compiler → Annotation Processors → Enable

3. **Run Configuration:**
   - Run → Edit Configurations → Add New → Spring Boot
   - Main class: `com.disaster.management.DisasterManagementApplication`

### Eclipse

1. **Import Project:**
   - File → Import → Maven → Existing Maven Projects
   - Select project directory

2. **Enable Lombok:**
   - Download lombok.jar
   - Run: `java -jar lombok.jar`
   - Select Eclipse installation

3. **Run:**
   - Right-click project → Run As → Spring Boot App

### VS Code

1. **Install Extensions:**
   - Extension Pack for Java
   - Spring Boot Extension Pack
   - Lombok Annotations Support

2. **Open Project:**
   - File → Open Folder → Select project directory

3. **Run:**
   - Press F5 or use Spring Boot Dashboard

---

## Troubleshooting

### Common Issues

**Issue:** `JAVA_HOME not set`
```bash
# macOS/Linux
export JAVA_HOME=$(/usr/libexec/java_home)
# Add to ~/.zshrc or ~/.bash_profile

# Windows
setx JAVA_HOME "C:\Program Files\Java\jdk-17"
```

**Issue:** `Maven not found`
```bash
# Verify PATH includes Maven bin directory
echo $PATH
```

**Issue:** `Cannot connect to MySQL`
- Verify MySQL is running: `mysql -u root -p`
- Check credentials in application.properties
- Ensure database exists: `SHOW DATABASES;`

**Issue:** `Port 8080 already in use`
```properties
# Change port in application.properties
server.port=8081
```

**Issue:** `Dependencies not downloading`
```bash
# Force update
mvn clean install -U

# Clear Maven cache
rm -rf ~/.m2/repository
mvn clean install
```

---

## Development Workflow

### 1. Make Changes
Edit code in `src/main/java` or `src/main/resources`

### 2. Build
```bash
mvn clean compile
```

### 3. Run Tests
```bash
mvn test
```

### 4. Package
```bash
mvn package
```

### 5. Run
```bash
mvn spring-boot:run
```

---

## Production Deployment

### Build Production JAR

```bash
mvn clean package -DskipTests
```

### Run Production

```bash
java -jar target/disaster-management-1.0.0.jar \
  --spring.profiles.active=prod \
  --server.port=80
```

### Docker Deployment (Optional)

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/disaster-management-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

---

## System Requirements Summary

### Minimum Requirements
- **RAM:** 2 GB
- **Disk:** 500 MB
- **CPU:** Dual-core processor
- **OS:** Windows 10/11, macOS 10.15+, Ubuntu 20.04+

### Recommended Requirements
- **RAM:** 4 GB or more
- **Disk:** 1 GB or more
- **CPU:** Quad-core processor
- **OS:** Latest version

---

## Additional Resources

- [Spring Boot Documentation](https://spring.boot.io/docs)
- [Spring Data JPA Guide](https://spring.io/guides/gs/accessing-data-jpa/)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Maven Documentation](https://maven.apache.org/guides/)

---

## Support

For issues or questions:
1. Check the documentation in `README.md`
2. Review `SETUP_GUIDE.txt`
3. Check GitHub Issues
4. Contact the development team

---

**Last Updated:** March 30, 2026
