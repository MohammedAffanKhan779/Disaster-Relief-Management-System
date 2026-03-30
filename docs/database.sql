# MySQL Database Setup Script for Disaster Relief Management System

-- Create database
CREATE DATABASE IF NOT EXISTS disaster_management;
USE disaster_management;

-- Note: Tables will be automatically created by Hibernate/JPA
-- This script is for reference and manual setup if needed

-- The following tables will be created:
-- 1. users (base table)
-- 2. administrators
-- 3. donors
-- 4. volunteers
-- 5. relief_staff
-- 6. authorities
-- 7. disaster_events
-- 8. relief_centers
-- 9. resources
-- 10. resource_requests
-- 11. allocations
-- 12. donations
-- 13. reports

-- Sample data insertion (optional - for testing)

-- Sample Relief Centers
-- INSERT INTO relief_centers (name, location, capacity, description) VALUES
-- ('Central Relief Center', 'Mumbai', 500, 'Main relief center for Maharashtra'),
-- ('North District Center', 'Delhi', 300, 'Relief center for North Delhi'),
-- ('Coastal Relief Hub', 'Chennai', 400, 'Relief center for coastal areas');

-- Sample Users (passwords should be hashed in production)
-- INSERT INTO users (name, phone, email, password, role) VALUES
-- ('Admin User', '9876543210', 'admin@disaster.com', 'admin123', 'ADMIN'),
-- ('John Donor', '9876543211', 'john@donor.com', 'donor123', 'DONOR'),
-- ('Jane Volunteer', '9876543212', 'jane@volunteer.com', 'vol123', 'VOLUNTEER');

-- Sample Disaster Events
-- INSERT INTO disaster_events (type, severity, status, location, date, description) VALUES
-- ('FLOOD', 'HIGH', 'ACTIVE', 'Mumbai, Maharashtra', '2026-03-15', 'Heavy rainfall causing floods'),
-- ('EARTHQUAKE', 'CRITICAL', 'ACTIVE', 'Delhi NCR', '2026-03-10', 'Magnitude 6.5 earthquake'),
-- ('CYCLONE', 'MEDIUM', 'UNDER_CONTROL', 'Coastal Tamil Nadu', '2026-02-20', 'Cyclone approaching coast');

-- To reset the database (WARNING: This will delete all data):
-- DROP DATABASE disaster_management;
-- CREATE DATABASE disaster_management;
