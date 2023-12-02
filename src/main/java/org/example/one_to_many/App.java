package org.example.one_to_many;

import org.example.one_to_one.Employee;
import org.example.one_to_one.EmployeeAdditionalInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Date;
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
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Order.class)
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
                            AddCustomer(factory);
                            System.out.println(divider);
                            break;
                        case 2:
                            AddOrder(factory);
                            System.out.println(divider);
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println(oops);
                            System.out.println(divider);
                            break;
                    }
                    break;
                case 2:
                    ShowReadMenu();
                    secondaryChoice = scannerInt.nextInt();

                    switch (secondaryChoice) {
                        case 1:
                            ShowCustomers(factory);
                            System.out.println(divider);
                            break;
                        case 2:
                            ShowCustomerOrders(factory);
                            System.out.println(divider);
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println(oops);
                            System.out.println(divider);
                            break;
                    }
                    break;
                case 3:
                    ShowUpdateMenu();
                    secondaryChoice = scannerInt.nextInt();

                    switch (secondaryChoice) {
                        case 1:
                            UpdateCustomer(factory);
                            System.out.println(divider);
                            break;
                        case 2:
                            UpdateOrder(factory);
                            System.out.println(divider);
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println(oops);
                            System.out.println(divider);
                            break;
                    }
                    break;
                case 4:
                    ShowDeleteMenu();
                    secondaryChoice = scannerInt.nextInt();

                    switch (secondaryChoice) {
                        case 1:
                            DeleteCustomer(factory);
                            System.out.println(divider);
                            break;
                        case 2:
                            DeleteOrder(factory);
                            System.out.println(divider);
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println(oops);
                            System.out.println(divider);
                            break;
                    }
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
        System.out.print("[1] Add customer\n" +
                "[2] Add order\n" +
                "[3] Exit\n\n" +
                "Your choice -> ");
    }

    private static void ShowReadMenu() {
        System.out.print("[1] Show customers\n" +
                "[2] Show customer's orders\n" +
                "[3] Exit\n\n" +
                "Your choice -> ");
    }

    private static void ShowUpdateMenu() {
        System.out.print("[1] Update customer\n" +
                "[2] Update order\n" +
                "[3] Exit\n\n" +
                "Your choice -> ");
    }

    private static void ShowDeleteMenu() {
        System.out.print("[1] Delete customer" +
                "\n[2] Delete order" +
                "\n[3] Exit" +
                "\n\nYour choice -> ");
    }

    private static void AddCustomer(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        System.out.print("\nEnter customer last name: ");
        String lastName = scannerStr.next();

        System.out.print("\nEnter customer first name: ");
        String firstName = scannerStr.next();

        Customer customer = new Customer(lastName, firstName);

        session.save(customer);
        session.getTransaction().commit();
    }

    private static void AddOrder(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Customer> customers = session.createQuery("from Customer", Customer.class).list();
        customers.forEach(System.out::println);

        System.out.print("\nEnter customer id to add order: ");
        int customerId = scannerInt.nextInt();

        Customer customer = session.get(Customer.class, customerId);

        System.out.print("\nEnter order total price: ");
        int price = scannerInt.nextInt();

        Order order = new Order(new Date(), price);
        customer.addOrder(order);
        session.save(order);

        session.getTransaction().commit();
    }

    private static void ShowCustomers(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Customer> customers = session.createQuery("from Customer").list();
        customers.forEach(System.out::println);

        session.getTransaction().commit();
    }

    private static void ShowCustomerOrders(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Customer> customers = session.createQuery("from Customer").list();
        customers.forEach(System.out::println);

        System.out.print("\nEnter customer id to show orders: ");
        int customerId = scannerInt.nextInt();

        Customer customer = session.get(Customer.class, customerId);
        List<Order> orders = customer.getOrders();
        orders.forEach(System.out::println);

        session.getTransaction().commit();
    }

    private static void UpdateCustomer(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Customer> customers = session.createQuery("from Customer ").list();
        customers.forEach(System.out::println);

        System.out.print("\nEnter customer id to update data: ");
        int customerId = scannerInt.nextInt();
        Customer customer = session.get(Customer.class, customerId);

        System.out.println("\nUpdate:" +
                "\n[1] Last name" +
                "\n[2] First name" +
                "\n[3] Exit" +
                "\n\nYour choice: ");

        int choice = scannerInt.nextInt();
        switch (choice) {
            case 1:
                System.out.print("\nEnter new last name: ");
                String newLastName = scannerStr.next();

                customer.setLastName(newLastName);
                break;
            case 2:
                System.out.println("\nEnter new first name: ");
                String newFirstName = scannerStr.next();

                customer.setFirstName(newFirstName);
                break;
            case 3:
                break;
            default:
                System.out.println(oops);
                System.out.println(divider);
                break;
        }

        session.getTransaction().commit();
    }

    private static void UpdateOrder(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Customer> customers = session.createQuery("from Customer ").list();
        customers.forEach(System.out::println);

        System.out.print("\nEnter customer id to update order data: ");
        int customerId = scannerInt.nextInt();

        Customer customer = session.get(Customer.class, customerId);
        List<Order> orders = customer.getOrders();
        orders.forEach(System.out::println);

        System.out.print("\nEnter order id to update data: ");
        int orderId = scannerInt.nextInt();
        Order order = session.get(Order.class, orderId);

        System.out.print("\nUpdate:" +
                "\n[1] Total price" +
                "\n[2] Exit" +
                "\n\nYour choice: ");

        int choice = scannerInt.nextInt();
        switch (choice) {
            case 1:
                System.out.print("\nEnter new total price: ");
                int newPrice = scannerInt.nextInt();

                order.setOrderPrice(newPrice);
                break;
            case 2:
                break;
            default:
                System.out.println(oops);
                System.out.println(divider);
                break;
        }

        session.getTransaction().commit();
    }

    private static void DeleteCustomer(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Customer> customers = session.createQuery("from Customer").list();
        customers.forEach(System.out::println);

        System.out.print("\nEnter customer id to delete: ");
        int customerId = scannerInt.nextInt();
        Customer customer = session.get(Customer.class, customerId);
        List<Order> orders = customer.getOrders();

        for (Order order : orders) {
            session.delete(order);
        }

        session.delete(customer);
        session.getTransaction().commit();
    }

    private static void DeleteOrder(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Customer> customers = session.createQuery("from Customer").list();
        customers.forEach(System.out::println);

        System.out.print("\nEnter customer id to show orders: ");
        int customerId = scannerInt.nextInt();
        Customer customer = session.get(Customer.class, customerId);
        List<Order> orders = customer.getOrders();
        orders.forEach(System.out::println);

        System.out.print("\nEnter order id to delete: ");
        int orderId = scannerInt.nextInt();
        Order order = session.get(Order.class, orderId);

        session.delete(order);
        session.getTransaction().commit();
    }
}
