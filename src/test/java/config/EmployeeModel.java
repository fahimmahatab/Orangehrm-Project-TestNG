package config;

public class EmployeeModel {
    private String firstName;
    public String lastName;
    public String employeeId;
    public String userName;
    public String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getemployeeId() {
        return employeeId;
    }

    public void setemployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmployeeModel() {

    }
    public EmployeeModel(String firstName, String lastName, String employeeId, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeId= employeeId;
        this.userName = userName;
        this.password = password;
    }
}
