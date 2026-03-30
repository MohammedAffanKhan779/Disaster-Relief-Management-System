# Contributing to Disaster Relief Management System

Thank you for considering contributing to this project! This document provides guidelines for contributing.

## How to Contribute

### Reporting Bugs

If you find a bug, please create an issue with:
- Clear description of the bug
- Steps to reproduce
- Expected vs actual behavior
- Screenshots (if applicable)
- Your environment (OS, Java version, MySQL version)

### Suggesting Features

Feature requests are welcome! Please:
- Check if the feature already exists
- Provide a clear use case
- Explain why it would be beneficial

### Code Contributions

1. **Fork the Repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/disaster-management.git
   ```

2. **Create a Feature Branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make Your Changes**
   - Follow the existing code style
   - Add comments to complex logic
   - Update documentation if needed

4. **Test Your Changes**
   ```bash
   mvn test
   mvn spring-boot:run
   ```

5. **Commit Your Changes**
   ```bash
   git add .
   git commit -m "Add: brief description of changes"
   ```

6. **Push to Your Fork**
   ```bash
   git push origin feature/your-feature-name
   ```

7. **Create a Pull Request**
   - Go to the original repository
   - Click "New Pull Request"
   - Select your branch
   - Provide a clear description

## Code Style Guidelines

### Java Code
- Follow standard Java naming conventions
- Use meaningful variable and method names
- Add JavaDoc comments for classes and public methods
- Keep methods short and focused
- Use Lombok annotations where appropriate

### Example:
```java
/**
 * Service for managing disaster events
 */
@Service
public class DisasterEventService {
    
    /**
     * Retrieves all active disaster events
     * @return List of active disasters
     */
    public List<DisasterEvent> getActiveDisasters() {
        // Implementation
    }
}
```

### Commit Messages
- Use clear, descriptive messages
- Start with a verb (Add, Fix, Update, Remove)
- Examples:
  - `Add user authentication feature`
  - `Fix donation amount validation`
  - `Update disaster event controller`

## Project Structure

```
src/main/java/com/disaster/management/
├── controller/      # Add new controllers here
├── service/         # Add business logic here
├── repository/      # Add data access here
├── model/
│   ├── entity/      # Add new entities here
│   └── enums/       # Add new enums here
```

## Testing

- Write unit tests for new features
- Ensure existing tests pass
- Aim for good test coverage

```bash
# Run tests
mvn test

# Run with coverage
mvn test jacoco:report
```

## Documentation

- Update README.md for major changes
- Add inline comments for complex logic
- Update API documentation if you add/modify endpoints

## Questions?

Feel free to open an issue for any questions or discussions!

Thank you for contributing! 🎉
