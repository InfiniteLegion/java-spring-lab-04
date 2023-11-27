package org.example.one_to_one;

import javax.persistence.*;

@Entity
@Table(name = "employee_full_info")
public class EmployeeFullInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "email")
    private String email;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "married")
    private boolean married;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employeeFullInfo")
    private Employee employee;

    public EmployeeFullInfo() {}

    public EmployeeFullInfo(String email, String country, String city, boolean married) {
        this.email = email;
        this.country = country;
        this.city = city;
        this.married = married;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "EmployeeFullInfo{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", married=" + married +
                ", employee=" + employee +
                '}';
    }
}
