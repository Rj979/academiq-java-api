-- Fix Database Issues Script
-- This script will:
-- 1. Update user passwords to plain text
-- 2. Clean up duplicate columns
-- 3. Verify table structure

-- Update user passwords to plain text (matching the simplified backend)
UPDATE users SET password = 'admin123' WHERE username = 'admin';
UPDATE users SET password = 'student123' WHERE username = 'student';  
UPDATE users SET password = 'staff123' WHERE username = 'staff';

-- Fix the courses table - remove duplicate academicYear column if it exists
-- Check if the duplicate column exists first
SET @column_exists = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS 
                     WHERE TABLE_SCHEMA = 'AcademiQ_thatnorjar' 
                     AND TABLE_NAME = 'courses' 
                     AND COLUMN_NAME = 'academicYear');

-- Drop the camelCase academicYear column if it exists (keep snake_case academic_year)
SET @sql = IF(@column_exists > 0, 
              'ALTER TABLE courses DROP COLUMN academicYear;', 
              'SELECT "academicYear column does not exist" as message;');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Update any NULL is_active fields to true for users
UPDATE users SET is_active = 1 WHERE is_active IS NULL;

-- Verify the fixes
SELECT 'Password lengths after fix:' as check_type;
SELECT username, LENGTH(password) as password_length FROM users;

SELECT 'User roles:' as check_type;  
SELECT u.username, ur.role FROM users u 
JOIN user_roles ur ON u.id = ur.user_id;

SELECT 'Courses table columns:' as check_type;
SHOW COLUMNS FROM courses LIKE '%academic%';

SELECT 'Data counts:' as check_type;
SELECT 
    (SELECT COUNT(*) FROM users) as user_count,
    (SELECT COUNT(*) FROM user_roles) as role_count,
    (SELECT COUNT(*) FROM courses) as course_count,
    (SELECT COUNT(*) FROM student) as student_count;
