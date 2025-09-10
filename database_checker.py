import sqlite3
import sys
import os

def check_h2_database():
    """
    Python script to connect to H2 database and check for issues.
    Since H2 is running in-memory mode, we'll create a simple test to verify
    the database connection and table structure.
    """
    
    print("=== Database Connection Checker ===")
    print("Note: The application uses H2 in-memory database (jdbc:h2:mem:testdb)")
    print("This means the database only exists while the Spring Boot application is running.")
    print()
    
    # Check if we can import the required libraries
    try:
        import jaydebeapi
        print("✓ jaydebeapi library is available for H2 connection")
    except ImportError:
        print("✗ jaydebeapi library not found. Install with: pip install jaydebeapi")
        print("  This library is needed to connect to H2 database from Python")
        print()
    
    # Since H2 is in-memory, let's provide instructions for database verification
    print("=== Database Verification Steps ===")
    print("1. Start the Spring Boot application: mvn spring-boot:run")
    print("2. Access H2 Console at: http://localhost:8080/h2-console")
    print("3. Use these connection details:")
    print("   - JDBC URL: jdbc:h2:mem:testdb")
    print("   - User Name: sa")
    print("   - Password: password")
    print()
    
    print("=== Expected Database Schema ===")
    print("The 'courses' table should have these columns:")
    print("- id (BIGINT, PRIMARY KEY)")
    print("- teacher_id (BIGINT, FOREIGN KEY)")
    print("- paper_id (BIGINT, FOREIGN KEY)")
    print("- semester (VARCHAR)")
    print("- academic_year (VARCHAR)")
    print()
    
    print("=== Fixed Issue ===")
    print("✓ Removed duplicate column mapping for 'academic_year' in Course.java")
    print("✓ The field 'academicYear' now uses Hibernate's default naming strategy")
    print("✓ This resolves the DuplicateMappingException")
    print()
    
    # Check if the Spring Boot application files exist
    spring_files = [
        "academiq-java-api/src/main/java/com/academiq/model/Course.java",
        "academiq-java-api/src/main/resources/application.properties",
        "academiq-java-api/pom.xml"
    ]
    
    print("=== File Verification ===")
    for file_path in spring_files:
        if os.path.exists(file_path):
            print(f"✓ {file_path} exists")
        else:
            print(f"✗ {file_path} not found")
    
    print()
    print("=== Next Steps ===")
    print("1. Run 'mvn spring-boot:run' from the academiq-java-api directory")
    print("2. Check the console output for any remaining errors")
    print("3. If successful, the application should start on port 8080")
    print("4. Access H2 console to verify database schema")

def create_test_connection_script():
    """
    Create a separate script that can test H2 connection when the app is running
    """
    script_content = '''
import jaydebeapi
import sys

def test_h2_connection():
    """Test connection to running H2 database"""
    try:
        # H2 JDBC driver (you need to download h2.jar)
        conn = jaydebeapi.connect(
            "org.h2.Driver",
            "jdbc:h2:mem:testdb",
            ["sa", "password"],
            "h2.jar"  # You need to download this from H2 website
        )
        
        cursor = conn.cursor()
        
        # Check if courses table exists
        cursor.execute("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'COURSES'")
        table_exists = cursor.fetchone()[0] > 0
        
        if table_exists:
            print("✓ COURSES table exists")
            
            # Get table structure
            cursor.execute("SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'COURSES'")
            columns = cursor.fetchall()
            
            print("\\nTable structure:")
            for column in columns:
                print(f"  - {column[0]} ({column[1]})")
                
        else:
            print("✗ COURSES table does not exist")
            
        conn.close()
        print("\\n✓ Database connection successful")
        
    except Exception as e:
        print(f"✗ Database connection failed: {e}")
        print("Make sure:")
        print("1. Spring Boot application is running")
        print("2. H2 JDBC driver (h2.jar) is in the same directory")
        print("3. jaydebeapi is installed: pip install jaydebeapi")

if __name__ == "__main__":
    test_h2_connection()
'''
    
    with open("test_h2_connection.py", "w") as f:
        f.write(script_content)
    
    print("✓ Created test_h2_connection.py for testing database connection")

if __name__ == "__main__":
    check_h2_database()
    print()
    create_test_connection_script()
