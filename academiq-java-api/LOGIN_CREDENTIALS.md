# Login Credentials for AcademiQ System

## 🔐 **Admin/Staff Accounts**

### Admin Account
- **Username:** `admin`
- **Password:** `admin123`
- **Role:** ADMIN

### Staff Account  
- **Username:** `staff`
- **Password:** `staff123`
- **Role:** STAFF

### Generic Student Account
- **Username:** `student`
- **Password:** `student123`  
- **Role:** STUDENT

---

## 👥 **Student Accounts (110 Total)**

### Pattern:
- **Username:** Based on roll number (e.g., `cbenu4cse22001`)
- **Password:** Based on roll number pattern (e.g., `cbenu4cs123`)

### Examples:

#### CSE Students:
1. **JANARTHAN S K**
   - Username: `cbenu4cse22001`
   - Password: `cbenu4cs123`

2. **JEEVAN KRISHNA A**
   - Username: `cbenu4cse22002`
   - Password: `cbenu4cs123`

3. **JYOTSNA J**
   - Username: `cbenu4cse22003`
   - Password: `cbenu4cs123`

4. **KAJOL P S**
   - Username: `cbenu4cse22004`
   - Password: `cbenu4cs123`

5. **KARTHIK S S**
   - Username: `cbenu4cse22005`
   - Password: `cbenu4cs123`

#### Other Departments:
- **MEE Students:** `cbenu4mee22001` → `cbenu4me123`
- **CEE Students:** `cbenu4cee22001` → `cbenu4ce123`
- **EEE Students:** `cbenu4eee22001` → `cbenu4ee123`
- **ECE Students:** `cbenu4ece22001` → `cbenu4ec123`
- **ITE Students:** `cbenu4ite22001` → `cbenu4it123`

---

## 📝 **Notes**

1. **Frontend URL:** http://localhost:3000
2. **Backend URL:** http://localhost:8080
3. **Login Endpoint:** POST `/auth/login/{userType}`
4. **All passwords are plain text** (no encryption)
5. **User types:** `student`, `staff`, `admin`

## 🚀 **Quick Test**

You can now login to the frontend with any of these credentials:
- Try `cbenu4cse22001` / `cbenu4cs123` for a student
- Try `admin` / `admin123` for admin access
- Try `staff` / `staff123` for staff access
