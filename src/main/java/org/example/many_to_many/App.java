package org.example.many_to_many;

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
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Course.class)
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
                            AddStudent(factory);
                            System.out.println(divider);
                            break;
                        case 2:
                            AddCourse(factory);
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
                            ShowAllStudents(factory);
                            System.out.println(divider);
                            break;
                        case 2:
                            ShowAllCourses(factory);
                            System.out.println(divider);
                            break;
                        case 3:
                            ShowStudentCourses(factory);
                            System.out.println(divider);
                            break;
                        case 4:
                            ShowCourseStudents(factory);
                            System.out.println(divider);
                            break;
                        case 5:
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
                            UpdateStudentData(factory);
                            System.out.println(divider);
                            break;
                        case 2:
                            UpdateCourseData(factory);
                            System.out.println(divider);
                            break;
                        case 3:
                            AddStudentToCourse(factory);
                            System.out.println(divider);
                            break;
                        case 4:
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
                            DeleteStudent(factory);
                            System.out.println(divider);
                            break;
                        case 2:
                            DeleteCourse(factory);
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
        System.out.print("[1] Add student\n" +
                "[2] Add course\n" +
                "[3] Exit\n\n" +
                "Your choice -> ");
    }

    private static void ShowReadMenu() {
        System.out.print("[1] Show all students\n" +
                "[2] Show all courses\n" +
                "[3] Show all student's courses\n" +
                "[4] Show all students on course\n" +
                "[5] Exit\n\n" +
                "Your choice -> ");
    }

    private static void ShowUpdateMenu() {
        System.out.print("[1] Update student data\n" +
                "[2] Update course data\n" +
                "[3] Add student to course\n" +
                "[4] Exit\n\n" +
                "Your choice -> ");
    }


    private static void ShowDeleteMenu() {
        System.out.print("[1] Delete student" +
                "\n[2] Delete course" +
                "\n[3] Exit" +
                "\n\nYour choice -> ");
    }

    private static void AddStudent(SessionFactory factory) {
        System.out.print("\nEnter student's last name: ");
        String lastName = scannerStr.next();

        System.out.print("\nEnter student's first name: ");
        String firstName = scannerStr.next();

        Student student = new Student(lastName, firstName);

        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(student);
        session.getTransaction().commit();
    }

    private static void AddCourse(SessionFactory factory) {
        System.out.print("\nEnter course name: ");
        String name = scannerStr.nextLine();

        Course course = new Course(name);

        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(course);
        session.getTransaction().commit();
    }

    private static void ShowAllStudents(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Student> students = session.createQuery("from Student").list();
        students.forEach(System.out::println);

        session.getTransaction().commit();
    }

    private static void ShowAllCourses(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Course> courses = session.createQuery("from Course").list();
        courses.forEach(System.out::println);

        session.getTransaction().commit();
    }

    private static void ShowStudentCourses(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Student> students = session.createQuery("from Student").list();
        students.forEach(System.out::println);

        System.out.print("\nEnter student id to show his/her courses: ");
        int studentId = scannerInt.nextInt();
        Student student = session.get(Student.class, studentId);

        System.out.println("\n" + student.getLastName() + " " + student.getFirstName() + "'s courses:");
        student.getCourses().forEach(System.out::println);

        session.getTransaction().commit();
    }

    private static void ShowCourseStudents(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Course> courses = session.createQuery("from Course").list();
        courses.forEach(System.out::println);

        System.out.print("\nEnter course id to show all its students: ");
        int courseId = scannerInt.nextInt();
        Course course = session.get(Course.class, courseId);

        System.out.println("\n[" + course.getCourseName() + "] students list:");
        course.getStudents().forEach(System.out::println);

        session.getTransaction().commit();
    }

    private static void UpdateStudentData(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Student> students = session.createQuery("from Student").list();
        students.forEach(System.out::println);

        System.out.print("\nEnter student id to update his/her data: ");
        int studentId = scannerInt.nextInt();
        Student student = session.get(Student.class, studentId);

        System.out.print("\nUpdate:" +
                "\n[1] Last name" +
                "\n[2] First name" +
                "\n[3] Exit" +
                "\n\nYour choice: ");

        int choice = scannerInt.nextInt();
        switch (choice) {
            case 1:
                System.out.print("\nEnter new last name: ");
                String newLastName = scannerStr.next();

                student.setLastName(newLastName);
                break;
            case 2:
                System.out.print("\nEnter new first name: ");
                String newFirstName = scannerStr.next();

                student.setFirstName(newFirstName);
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

    private static void UpdateCourseData(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Course> courses = session.createQuery("from Course").list();
        courses.forEach(System.out::println);

        System.out.print("\nEnter course id to update its data: ");
        int courseId = scannerInt.nextInt();
        Course course = session.get(Course.class, courseId);

        System.out.print("\nUpdate:" +
                "\n[1] Course name" +
                "\n[2] Exit" +
                "\n\nYour choice: ");

        int choice = scannerInt.nextInt();
        switch (choice) {
            case 1:
                System.out.print("\nEnter new course name: ");
                String newCourseName = scannerStr.nextLine();

                course.setCourseName(newCourseName);
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

    private static void AddStudentToCourse(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Student> students = session.createQuery("from Student").list();
        List<Course> courses = session.createQuery("from Course").list();

        students.forEach(System.out::println);

        System.out.print("\nEnter student id to add to course: ");
        int studentId = scannerInt.nextInt();
        Student student = session.get(Student.class, studentId);

        courses.forEach(System.out::println);

        System.out.print("\nEnter course id to add student: ");
        int courseId = scannerInt.nextInt();
        Course course = session.get(Course.class, courseId);

        student.addCourse(course);

        session.getTransaction().commit();
    }

    private static void DeleteStudent(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Student> students = session.createQuery("from Student").list();

        students.forEach(System.out::println);

        System.out.print("\nEnter student id to delete: ");
        int studentId = scannerInt.nextInt();
        Student student = session.get(Student.class, studentId);

        session.delete(student);
        session.getTransaction().commit();
    }

    private static void DeleteCourse(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Course> courses = session.createQuery("from Course").list();

        courses.forEach(System.out::println);

        System.out.print("\nEnter course id to delete: ");
        int courseId = scannerInt.nextInt();
        Course course = session.get(Course.class, courseId);

        session.delete(course);
        session.getTransaction().commit();
    }
}
