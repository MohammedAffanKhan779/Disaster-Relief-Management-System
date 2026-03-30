# GitHub Repository Setup Guide

This guide will help you create a GitHub repository and push your project.

## Step 1: Create GitHub Account

If you don't have a GitHub account:
1. Go to https://github.com
2. Click "Sign up"
3. Follow the registration process

## Step 2: Create a New Repository

### Option A: Using GitHub Website

1. **Login to GitHub**
   - Go to https://github.com and sign in

2. **Create New Repository**
   - Click the "+" icon in top right corner
   - Select "New repository"

3. **Repository Settings**
   - **Name:** `disaster-management-system`
   - **Description:** `Spring Boot application for Disaster Relief Management with MVC architecture and MySQL database`
   - **Visibility:** Public (or Private if preferred)
   - **DO NOT** initialize with README (we already have one)
   - **DO NOT** add .gitignore (we already have one)
   - Click "Create repository"

### Option B: Using GitHub CLI

```bash
# Install GitHub CLI (if not installed)
# macOS:
brew install gh

# Windows:
winget install --id GitHub.cli

# Authenticate
gh auth login

# Create repository
gh repo create disaster-management-system --public --source=. --remote=origin
```

## Step 3: Initialize Local Git Repository

Open terminal in your project directory:

```bash
cd /Users/mohammedaffankhan/Documents/College/Subjects/Lab_and_Assignments/OOAD/Mini_Project

# Initialize git (if not already done)
git init

# Check git status
git status
```

## Step 4: Configure Git (First Time Only)

```bash
# Set your name
git config --global user.name "Your Name"

# Set your email (use your GitHub email)
git config --global user.email "your.email@example.com"

# Verify configuration
git config --list
```

## Step 5: Add Files to Git

```bash
# Add all files
git add .

# Check what's staged
git status

# If you see unwanted files, update .gitignore and run:
git rm -r --cached .
git add .
```

## Step 6: Create First Commit

```bash
# Commit with a message
git commit -m "Initial commit: Complete Spring Boot MVC project with MySQL integration"
```

## Step 7: Connect to GitHub Remote

Replace `YOUR_USERNAME` with your actual GitHub username:

```bash
# Add remote repository
git remote add origin https://github.com/YOUR_USERNAME/disaster-management-system.git

# Verify remote
git remote -v
```

## Step 8: Push to GitHub

```bash
# Push to main branch
git branch -M main
git push -u origin main
```

If prompted for credentials:
- **Username:** Your GitHub username
- **Password:** Use a Personal Access Token (not your password)

### Creating Personal Access Token

1. Go to GitHub → Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Click "Generate new token" (classic)
3. Give it a name: "Disaster Management Project"
4. Select scopes: `repo` (all repo permissions)
5. Click "Generate token"
6. **COPY THE TOKEN** (you won't see it again)
7. Use this token as your password when pushing

## Step 9: Verify on GitHub

1. Go to `https://github.com/YOUR_USERNAME/disaster-management-system`
2. You should see all your files
3. Check that README.md displays properly

## Step 10: Add Collaborators (Optional)

If working in a team:

1. Go to your repository on GitHub
2. Click "Settings"
3. Click "Collaborators and teams"
4. Click "Add people"
5. Enter their GitHub username or email
6. Select permission level
7. Click "Add"

## Common Git Commands

### Daily Workflow

```bash
# Check status
git status

# Add changes
git add .

# Commit changes
git commit -m "Description of changes"

# Push to GitHub
git push

# Pull latest changes
git pull
```

### Branch Management

```bash
# Create new branch
git checkout -b feature/new-feature

# Switch branch
git checkout main

# List branches
git branch

# Merge branch
git checkout main
git merge feature/new-feature

# Delete branch
git branch -d feature/new-feature
```

### Undo Changes

```bash
# Discard unstaged changes
git checkout -- filename

# Undo last commit (keep changes)
git reset --soft HEAD~1

# Undo last commit (discard changes)
git reset --hard HEAD~1
```

## Repository Structure on GitHub

After pushing, your repository will show:

```
YOUR_USERNAME/disaster-management-system
├── 📁 .github/                    # GitHub templates
├── 📁 docs/                       # Documentation
├── 📁 src/                        # Source code
├── 📄 .gitignore                  # Git ignore rules
├── �� README.md                   # Project overview
├── 📄 REQUIREMENTS.md             # Dependencies
├── 📄 SETUP_GUIDE.txt            # Setup instructions
├── 📄 LICENSE                     # License
└── 📄 pom.xml                     # Maven config
```

## Adding Badges to README

Add these at the top of your README.md:

```markdown
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Maven](https://img.shields.io/badge/Maven-3.6+-red)
![License](https://img.shields.io/badge/License-MIT-yellow)
```

## Enable GitHub Actions (Optional)

GitHub Actions will automatically run when you push code:

1. The `.github/workflows/build.yml` file is already configured
2. It will automatically:
   - Build your project
   - Run tests
   - Generate reports
3. Check the "Actions" tab on GitHub to see results

## Protecting Main Branch (Optional)

To prevent direct pushes to main:

1. Go to Settings → Branches
2. Click "Add rule"
3. Branch name pattern: `main`
4. Check "Require pull request reviews before merging"
5. Click "Create"

## Troubleshooting

### Problem: "Permission denied (publickey)"

**Solution:** Use HTTPS instead of SSH
```bash
git remote set-url origin https://github.com/YOUR_USERNAME/disaster-management-system.git
```

### Problem: "Repository not found"

**Solution:** Check the repository name and your permissions
```bash
# Verify remote URL
git remote -v

# Update if wrong
git remote set-url origin https://github.com/YOUR_USERNAME/correct-repo-name.git
```

### Problem: "Failed to push some refs"

**Solution:** Pull first, then push
```bash
git pull origin main --rebase
git push origin main
```

### Problem: Large files causing push to fail

**Solution:** Remove from git and add to .gitignore
```bash
git rm --cached path/to/large/file
echo "path/to/large/file" >> .gitignore
git commit -m "Remove large file"
git push
```

## Next Steps

After setting up GitHub:

1. ✅ Add a description to your repository
2. ✅ Add topics/tags (spring-boot, java, mysql, mvc, disaster-management)
3. ✅ Create a Project board for task tracking
4. ✅ Enable Issues for bug tracking
5. ✅ Add a Wiki for additional documentation
6. ✅ Invite team members as collaborators
7. ✅ Star your own repository 😊

## Useful GitHub URLs

- **Your Repository:** `https://github.com/YOUR_USERNAME/disaster-management-system`
- **GitHub Desktop:** https://desktop.github.com (GUI alternative)
- **GitHub CLI:** https://cli.github.com
- **Git Documentation:** https://git-scm.com/doc

---

**Congratulations! Your project is now on GitHub! 🎉**

Share your repository URL with your team and instructors!
