# 🚀 Quick Start Guide

Get your Disaster Management System up and running in 5 minutes!

## Prerequisites Checklist

- [ ] Java 17+ installed
- [ ] Maven 3.6+ installed
- [ ] MySQL 8.0+ installed and running
- [ ] Git installed (for cloning)

## 5-Minute Setup

### 1️⃣ Clone/Download Project (15 seconds)

```bash
git clone https://github.com/YOUR_USERNAME/disaster-management-system.git
cd disaster-management-system
```

Or download ZIP and extract.

### 2️⃣ Setup MySQL Database (30 seconds)

```bash
# Login to MySQL
mysql -u root -p

# Run these commands
CREATE DATABASE disaster_management;
exit;
```

### 3️⃣ Configure Application (30 seconds)

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 4️⃣ Build Project (2 minutes)

```bash
mvn clean install
```

### 5️⃣ Run Application (15 seconds)

```bash
mvn spring-boot:run
```

### 6️⃣ Open Browser (5 seconds)

Navigate to: **http://localhost:8080**

---

## ✅ Success Indicators

You should see:

**In Terminal:**
```
Disaster Relief Management System Started!
Access the application at: http://localhost:8080
```

**In Browser:**
- Homepage with navigation menu
- "Disaster Relief Management System" header
- Feature cards for different modules

---

## 🎯 What to Try First

1. **Register a User**
   - Go to http://localhost:8080/users/register
   - Fill in the form
   - Click Register

2. **View Disasters**
   - Go to http://localhost:8080/disasters
   - Click "Report New Disaster"

3. **Make a Donation**
   - Go to http://localhost:8080/donations/new
   - Fill in donation details

4. **Try REST API**
   ```bash
   curl http://localhost:8080/users/api
   ```

---

## 🐛 Quick Troubleshooting

### Port 8080 in use?
```properties
# Change in application.properties
server.port=8081
```

### MySQL connection error?
- Check MySQL is running: `mysql -u root -p`
- Verify username/password in application.properties

### Build fails?
```bash
# Clean and rebuild
mvn clean install -U
```

---

## 📚 Next Steps

- Read the full [README.md](README.md)
- Check [API Documentation](docs/api/API.md)
- Review [SETUP_GUIDE.txt](SETUP_GUIDE.txt)
- See [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md)

---

## 🆘 Need Help?

1. Check [SETUP_GUIDE.txt](SETUP_GUIDE.txt) for detailed instructions
2. Review [REQUIREMENTS.md](REQUIREMENTS.md) for dependencies
3. Open an issue on GitHub
4. Contact the development team

---

**Ready to start? Let's go! 🚀**
