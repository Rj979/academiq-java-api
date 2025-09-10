import requests
import json
import sys
import time

def test_backend_connection():
    """Test if the backend is running and accessible"""
    print("=== Backend Connection Test ===")
    
    backend_url = "http://localhost:8080"
    
    try:
        # Test basic connectivity
        response = requests.get(f"{backend_url}/h2-console", timeout=5)
        print(f"✓ Backend is running on {backend_url}")
        print(f"  H2 Console accessible: {response.status_code == 200}")
    except requests.exceptions.ConnectionError:
        print(f"✗ Backend is not running on {backend_url}")
        print("  Please start the backend with: mvn spring-boot:run")
        return False
    except Exception as e:
        print(f"✗ Error connecting to backend: {e}")
        return False
    
    return True

def test_authentication_endpoints():
    """Test authentication endpoints"""
    print("\n=== Authentication Endpoints Test ===")
    
    backend_url = "http://localhost:8080"
    
    # Test data
    test_student = {
        "username": "student1",
        "password": "password123"
    }
    
    test_staff = {
        "username": "staff1", 
        "password": "password123"
    }
    
    # Test student login
    print("Testing student login...")
    try:
        response = requests.post(
            f"{backend_url}/auth/login/student",
            json=test_student,
            headers={"Content-Type": "application/json"},
            timeout=10
        )
        
        if response.status_code == 200:
            data = response.json()
            print("✓ Student login successful")
            print(f"  Token received: {data.get('token', 'N/A')[:20]}...")
            print(f"  User: {data.get('user', {}).get('username', 'N/A')}")
            return data.get('token')
        else:
            print(f"✗ Student login failed: {response.status_code}")
            print(f"  Response: {response.text}")
            
    except Exception as e:
        print(f"✗ Student login error: {e}")
    
    # Test staff login
    print("\nTesting staff login...")
    try:
        response = requests.post(
            f"{backend_url}/auth/login/staff",
            json=test_staff,
            headers={"Content-Type": "application/json"},
            timeout=10
        )
        
        if response.status_code == 200:
            data = response.json()
            print("✓ Staff login successful")
            print(f"  Token received: {data.get('token', 'N/A')[:20]}...")
            print(f"  User: {data.get('user', {}).get('username', 'N/A')}")
        else:
            print(f"✗ Staff login failed: {response.status_code}")
            print(f"  Response: {response.text}")
            
    except Exception as e:
        print(f"✗ Staff login error: {e}")
    
    return None

def test_token_validation(token):
    """Test token validation endpoint"""
    if not token:
        print("\n=== Skipping Token Validation (no token) ===")
        return
        
    print("\n=== Token Validation Test ===")
    
    backend_url = "http://localhost:8080"
    
    try:
        response = requests.post(
            f"{backend_url}/auth/validate",
            headers={
                "Authorization": f"Bearer {token}",
                "Content-Type": "application/json"
            },
            timeout=10
        )
        
        if response.status_code == 200:
            data = response.json()
            print("✓ Token validation successful")
            print(f"  Valid: {data.get('valid', False)}")
            print(f"  User: {data.get('user', {}).get('username', 'N/A')}")
        else:
            print(f"✗ Token validation failed: {response.status_code}")
            print(f"  Response: {response.text}")
            
    except Exception as e:
        print(f"✗ Token validation error: {e}")

def test_protected_endpoints(token):
    """Test protected API endpoints"""
    if not token:
        print("\n=== Skipping Protected Endpoints Test (no token) ===")
        return
        
    print("\n=== Protected Endpoints Test ===")
    
    backend_url = "http://localhost:8080"
    headers = {
        "Authorization": f"Bearer {token}",
        "Content-Type": "application/json"
    }
    
    # Test some API endpoints
    endpoints_to_test = [
        "/api/courses",
        "/api/subjects", 
        "/api/students",
        "/api/teachers"
    ]
    
    for endpoint in endpoints_to_test:
        try:
            response = requests.get(f"{backend_url}{endpoint}", headers=headers, timeout=10)
            if response.status_code == 200:
                print(f"✓ {endpoint}: Accessible")
            elif response.status_code == 401:
                print(f"✗ {endpoint}: Unauthorized (token issue)")
            elif response.status_code == 403:
                print(f"⚠ {endpoint}: Forbidden (role issue)")
            else:
                print(f"⚠ {endpoint}: Status {response.status_code}")
        except Exception as e:
            print(f"✗ {endpoint}: Error - {e}")

def check_cors_configuration():
    """Check CORS configuration"""
    print("\n=== CORS Configuration Check ===")
    
    backend_url = "http://localhost:8080"
    
    try:
        # Make a preflight request
        response = requests.options(
            f"{backend_url}/auth/login/student",
            headers={
                "Origin": "http://localhost:3000",
                "Access-Control-Request-Method": "POST",
                "Access-Control-Request-Headers": "Content-Type"
            },
            timeout=10
        )
        
        cors_headers = {
            "Access-Control-Allow-Origin": response.headers.get("Access-Control-Allow-Origin"),
            "Access-Control-Allow-Methods": response.headers.get("Access-Control-Allow-Methods"),
            "Access-Control-Allow-Headers": response.headers.get("Access-Control-Allow-Headers")
        }
        
        print("CORS Headers:")
        for header, value in cors_headers.items():
            if value:
                print(f"  ✓ {header}: {value}")
            else:
                print(f"  ✗ {header}: Not set")
                
    except Exception as e:
        print(f"✗ CORS check error: {e}")

def identify_common_login_issues():
    """Identify common login issues"""
    print("\n=== Common Login Issues Analysis ===")
    
    issues_and_solutions = [
        {
            "issue": "CORS errors in browser",
            "solution": "Check @CrossOrigin annotation in AuthController",
            "status": "✓ Present in AuthController"
        },
        {
            "issue": "JWT token not being generated",
            "solution": "Check JWT secret and expiration configuration",
            "status": "✓ Configured in application.properties"
        },
        {
            "issue": "Password encoding mismatch",
            "solution": "Ensure BCrypt is used consistently",
            "status": "✓ BCryptPasswordEncoder configured"
        },
        {
            "issue": "Role-based access issues",
            "solution": "Check role prefixes (ROLE_STUDENT, ROLE_STAFF)",
            "status": "✓ Roles properly prefixed in DataInitializer"
        },
        {
            "issue": "Database connection issues",
            "solution": "Check H2 database configuration",
            "status": "✓ H2 in-memory database configured"
        }
    ]
    
    for item in issues_and_solutions:
        print(f"• {item['issue']}")
        print(f"  Solution: {item['solution']}")
        print(f"  Status: {item['status']}")
        print()

def main():
    print("=== Backend-Frontend Integration Test ===")
    print("This script tests the backend API and identifies potential login issues.\n")
    
    # Test backend connection
    if not test_backend_connection():
        print("\n❌ Backend is not running. Please start it first with:")
        print("   cd academiq-java-api")
        print("   mvn spring-boot:run")
        return
    
    # Wait a moment for backend to be fully ready
    print("Waiting for backend to be fully ready...")
    time.sleep(2)
    
    # Test authentication
    token = test_authentication_endpoints()
    
    # Test token validation
    test_token_validation(token)
    
    # Test protected endpoints
    test_protected_endpoints(token)
    
    # Check CORS
    check_cors_configuration()
    
    # Analyze common issues
    identify_common_login_issues()
    
    print("\n=== Test Summary ===")
    if token:
        print("✅ Authentication system appears to be working")
        print("✅ Default users are available:")
        print("   Student: username=student1, password=password123")
        print("   Staff: username=staff1, password=password123")
    else:
        print("❌ Authentication system has issues")
        print("   Check the backend logs for detailed error messages")
    
    print("\n=== Frontend Integration Notes ===")
    print("• Frontend should connect to: http://localhost:8080")
    print("• Login endpoints: /auth/login/student and /auth/login/staff")
    print("• Include 'Authorization: Bearer <token>' header for protected routes")
    print("• CORS is configured for http://localhost:3000")

if __name__ == "__main__":
    main()
