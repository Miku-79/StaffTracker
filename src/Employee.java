public class Employee {

    private int id;
    private String name;
    private String department;
    private String email;
    private String phoneNumber;
    private int salary;
    private String address;
    private String gender;
    private String DOJ;  //change done

    // Parameterized Constructor
    public Employee(int id, String name, String email,String phoneNumber,
                 int salary, String address,String DOJ, String department, String gender) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.DOJ = DOJ;  //change done
        this.address = address;
        this.gender = gender;
    }

    // Getter methods
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getSalary() { return salary; }
    public String getAddress() { return address; }
    public String getGender() { return gender; }
    public String getDOJ() { return DOJ; }  //change done

    // Setter methods with validation
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a positive integer.");
        }
        this.id = id;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name;
    }

    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty.");
        }
        this.department = department;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number must be 10 digits.");
        }
        this.phoneNumber = phoneNumber;
    }

    public void setSalary(int salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }
        this.salary = salary;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty.");
        }
        this.address = address;
    }

    public void setGender(String gender) {
        if (gender == null ||
            (!gender.equalsIgnoreCase("male") &&
             !gender.equalsIgnoreCase("female") &&
             !gender.equalsIgnoreCase("other"))) {
            throw new IllegalArgumentException("Gender must be 'Male', 'Female', or 'Other'.");
        }
        this.gender = gender;
    }

    public void setDOJ(String DOJ){
        this.DOJ = DOJ;
    }
}
