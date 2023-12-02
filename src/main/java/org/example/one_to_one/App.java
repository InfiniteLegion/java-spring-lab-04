package org.example.one_to_one;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class App {
    static Scanner scannerStr = new Scanner(System.in);
    static Scanner scannerInt = new Scanner(System.in);
    static String divider = "===================================";
    static String oops = "Oops... Enter correct option";

    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(EmployeeAdditionalInfo.class)
                .buildSessionFactory();

        int mainChoice, secondaryChoice;

        do {
            ShowMainMenu();
            mainChoice = scannerInt.nextInt();

            switch (mainChoice) {
                case 1:
                    ShowAddMenu();
                    secondaryChoice = scannerInt.nextInt();

                    switch (secondaryChoice) {
                        case 1:
                            AddEmployee(factory);
                            break;
                        case 2:
                            AddEmployeeAdditionalInfo(factory);
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println(oops);
                            break;
                    }
                    System.out.println(divider);
                    break;
                case 2:
                    ShowReadMenu();
                    secondaryChoice = scannerInt.nextInt();

                    switch (secondaryChoice) {
                        case 1:
                            ShowEmployees(factory);
                            break;
                        case 2:
                            ShowEmployeeAdditionalInfo(factory);
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println(oops);
                    }
                    System.out.println(divider);
                    break;
                case 3:
                    ShowUpdateMenu();
                    secondaryChoice = scannerInt.nextInt();

                    switch (secondaryChoice) {
                        case 1:
                            UpdateEmployee(factory);
                            break;
                        case 2:
                            UpdateEmployeeAdditionalInfo(factory);
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println(oops);
                            break;
                    }
                    System.out.println(divider);
                    break;
                case 4:
                    DeleteEmployee(factory);
                    System.out.println(divider);
                    break;
                case 5:
                    break;
                default:
                    System.out.println(oops);
                    System.out.println(divider);
                    break;
            }

        } while (mainChoice != 5);
    }

    private static void ShowMainMenu() {
        System.out.print("[1] Add info\n" +
                "[2] Show info\n" +
                "[3] Update info\n" +
                "[4] Delete info\n" +
                "[5] Exit\n\n" +
                "Your choice -> ");
    }

    private static void ShowAddMenu() {
        System.out.print("[1] Add employee\n" +
                "[2] Add employee additional info\n" +
                "[3] Exit\n\n" +
                "Your choice -> ");
    }

    private static void ShowReadMenu() {
        System.out.print("[1] Show employees\n" +
                "[2] Show employee additional info\n" +
                "[3] Exit\n\n" +
                "Your choice -> ");
    }

    private static void ShowUpdateMenu() {
        System.out.print("[1] Update employee\n" +
                "[2] Update employee additional info\n" +
                "[3] Exit\n\n" +
                "Your choice -> ");
    }

    private static void AddEmployee(SessionFactory factory) {
        Session session = factory.getCurrentSession();

        System.out.print("Enter employee last name: ");
        String lastName = scannerStr.nextLine();

        System.out.print("Enter employee first name: ");
        String firstName = scannerStr.nextLine();

        System.out.print("Enter employee department: ");
        String department = scannerStr.nextLine();

        System.out.print("Enter employee salary: ");
        int salary = Integer.parseInt(scannerStr.nextLine());

        System.out.print("Enter employee phone number: ");
        String phoneNumber = scannerStr.nextLine();

        Employee employee = new Employee(firstName, lastName, department, salary, phoneNumber);

        session.beginTransaction();
        session.save(employee);
        session.getTransaction().commit();
    }

    private static void AddEmployeeAdditionalInfo(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Employee> employees = session.createQuery("from Employee", Employee.class).list();

        employees.forEach(System.out::println);

        System.out.print("\nEnter id of employee you want to add full info: ");
        int employeeId = scannerInt.nextInt();
        Employee employee = session.get(Employee.class, employeeId);

        System.out.print("\nEnter employee email: ");
        String email = scannerStr.nextLine();

        System.out.print("\nEnter employee country: ");
        String country = scannerStr.nextLine();

        System.out.print("\nEnter employee city: ");
        String city = scannerStr.nextLine();

        System.out.print("\nIs employee married [y/n]: ");
        String isMarriedStr = scannerStr.next().toLowerCase();
        Boolean isMarried = isMarriedStr.equals("y");

        EmployeeAdditionalInfo employeeAdditionalInfo = new EmployeeAdditionalInfo(email, country, city, isMarried);

        employee.setEmployeeFullInfo(employeeAdditionalInfo);
        employeeAdditionalInfo.setEmployee(employee);

        session.save(employeeAdditionalInfo);
        session.getTransaction().commit();
    }

    private static void ShowEmployees(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Employee> employees = session.createQuery("from Employee", Employee.class).list();

        session.getTransaction().commit();

        employees.forEach(System.out::println);
    }

    private static void ShowEmployeeAdditionalInfo(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Employee> employees = session.createQuery("from Employee", Employee.class).list();

        employees.forEach(System.out::println);

        System.out.print("\nEnter employee id to show employee full info: ");
        int employeeId = scannerInt.nextInt();
        Employee employee = session.get(Employee.class, employeeId);

        EmployeeAdditionalInfo employeeAdditionalInfo = employee.getEmployeeFullInfo();

        System.out.println(employeeAdditionalInfo);

        session.getTransaction().commit();
    }

    private static void UpdateEmployee(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Employee> employees = session.createQuery("from Employee", Employee.class).list();
        employees.forEach(System.out::println);

        System.out.print("\nEnter employee id you want to update data: ");
        int employeeId = scannerInt.nextInt();
        Employee employee = session.get(Employee.class, employeeId);

        System.out.print("\nUpdate employee's:" +
                "\n[1] Last name" +
                "\n[2] First name" +
                "\n[3] Department" +
                "\n[4] Salary" +
                "\n[5] Phone number" +
                "\n[6] Exit" +
                "\n\nYour choice: ");

        int choice = scannerInt.nextInt();

        switch (choice) {
            case 1:
                System.out.print("\nEnter new last name: ");
                String newLastName = scannerStr.next();
                employee.setLastName(newLastName);
                break;
            case 2:
                System.out.print("\nEnter new first name: ");
                String newFirstName = scannerStr.next();
                employee.setFirstName(newFirstName);
                break;
            case 3:
                System.out.print("\nEnter new department: ");
                String newDepartment = scannerStr.next();
                employee.setDepartment(newDepartment);
                break;
            case 4:
                System.out.print("\nEnter new salary: ");
                int newSalary = scannerInt.nextInt();
                employee.setSalary(newSalary);
                break;
            case 5:
                System.out.print("\nEnter new phone number: ");
                String newPhoneNumber = scannerStr.next();
                employee.setPhoneNumber(newPhoneNumber);
                break;
            case 6:
                break;
            default:
                System.out.println(oops);
                System.out.println(divider);
                break;
        }

        session.getTransaction().commit();
    }

    private static void UpdateEmployeeAdditionalInfo(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Employee> employees = session.createQuery("from Employee", Employee.class).list();
        employees.forEach(System.out::println);

        System.out.print("\nEnter employee id you want to update additional info data: ");
        int employeeId = scannerInt.nextInt();
        Employee employee = session.get(Employee.class, employeeId);
        EmployeeAdditionalInfo employeeAdditionalInfo = employee.getEmployeeFullInfo();

        System.out.print("\nUpdate employee's:" +
                "\n[1] Email" +
                "\n[2] Country" +
                "\n[3] City" +
                "\n[4] Married status" +
                "\n[5] Exit" +
                "\n\nYour choice: ");

        int choice = scannerInt.nextInt();

        switch (choice) {
            case 1:
                System.out.print("\nEnter new email: ");
                String newEmail = scannerStr.next();
                employeeAdditionalInfo.setEmail(newEmail);
                break;
            case 2:
                System.out.print("\nEnter new country: ");
                String newCountry = scannerStr.next();
                employeeAdditionalInfo.setCountry(newCountry);
                break;
            case 3:
                System.out.print("\nEnter new city: ");
                String newCity = scannerStr.next();
                employeeAdditionalInfo.setCity(newCity);
                break;
            case 4:
                Boolean boo = employeeAdditionalInfo.isMarried();
                employeeAdditionalInfo.setMarried(!boo);
                break;
            case 5:
                break;
            default:
                System.out.println(oops);
                System.out.println(divider);
                break;
        }

        session.getTransaction().commit();
    }

    private static void DeleteEmployee(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Employee> employees = session.createQuery("from Employee", Employee.class).list();
        employees.forEach(System.out::println);

        System.out.print("\nEnter employee id you want to delete: ");
        int employeeId = scannerInt.nextInt();

        Employee employee = session.get(Employee.class, employeeId);
        EmployeeAdditionalInfo employeeAdditionalInfo = employee.getEmployeeFullInfo();

        session.delete(employeeAdditionalInfo);
        session.delete(employee);
        session.getTransaction().commit();
    }
}
