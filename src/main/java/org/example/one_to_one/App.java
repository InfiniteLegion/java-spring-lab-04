package org.example.one_to_one;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class App {
    static Scanner scannerStr = new Scanner(System.in);
    static Scanner scannerInt = new Scanner(System.in);

    public static void main(String[] args) {
        String divider = "===================================", oops = "Oops... Enter correct option";

        SessionFactory factory = new Configuration()
                .configure()
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(EmployeeFullInfo.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();
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
                            AddEmployee(session);
                            break;
                        case 2:
                            AddEmployeeFullInfo(session);
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println(oops);
                            break;
                    }
                    break;
                case 2:
                    ShowReadMenu();
                    secondaryChoice = scannerInt.nextInt();

                    switch (secondaryChoice) {
                        case 1:
                            ShowEmployee(session);
                            break;
                        case 2:
                            ShowEmployeeFullInfo(session);
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println(oops);
                    }
                    break;
                case 3:
                    ShowUpdateMenu();
                    secondaryChoice = scannerInt.nextInt();

                    switch (secondaryChoice) {
                        case 1:
                            UpdateEmployee(session);
                            break;
                        case 2:
                            UpdateEmployeeFullInfo(session);
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println(oops);
                            break;
                    }
                    break;
                case 4:
                    DeleteEmployee(session);
                    break;
                case 5:
                    break;
                default:
                    System.out.println(oops);
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
        System.out.println("[1] Add employee\n" +
                "[2] Add employee full info\n" +
                "[3] Exit\n\n" +
                "Your choice -> ");
    }

    private static void ShowReadMenu() {
        System.out.println("[1] Show employee\n" +
                "[2] Show employee full info by id\n" +
                "[3] Exit\n\n" +
                "Your choice -> ");
    }

    private static void ShowUpdateMenu() {
        System.out.println("[1] Update employee\n" +
                "[2] Update employee full info by id\n" +
                "[3] Exit\n\n" +
                "Your choice -> ");
    }

    private static void AddEmployee(Session session) {
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

    private static void AddEmployeeFullInfo(Session session) {
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

        EmployeeFullInfo employeeFullInfo = new EmployeeFullInfo(email, country, city, isMarried);

        employee.setEmployeeFullInfo(employeeFullInfo);
        employeeFullInfo.setEmployee(employee);

        session.save(employeeFullInfo);
        session.getTransaction().commit();
    }

    private static void ShowEmployee(Session session) {

    }

    private static void ShowEmployeeFullInfo(Session session) {

    }

    private static void UpdateEmployee(Session session) {

    }

    private static void UpdateEmployeeFullInfo(Session session) {

    }

    private static void DeleteEmployee(Session session) {

    }
}
