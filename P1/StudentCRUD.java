import java.sql.*;
import java.util.Scanner;

/*  
    command tu run
    javac -cp .;mysql-connector-j-9.4.0.jar StudentCRUD.java
    java -cp .;mysql-connector-j-9.4.0.jar StudentCRUD




    MYSQL query
    CREATE DATABASE studentdb;
    USE studentdb;

    CREATE TABLE student (
        id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(100),
        age INT,
        course VARCHAR(100)
    );

 */
public class StudentCRUD {
    static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    static final String USER = "root";
    static final String PASSWORD = "12M16sr1976";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to Database Successfully!");
            do {
                System.out.println("\n===== Student CRUD Menu =====");
                System.out.println("1. Insert Student");
                System.out.println("2. Display Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        insertStudent(con, sc);
                        break;
                    case 2:
                        displayStudents(con);
                        break;
                    case 3:
                        updateStudent(con, sc);
                        break;
                    case 4:
                        deleteStudent(con, sc);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } while (choice != 5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void insertStudent(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter name: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        System.out.print("Enter course: ");
        sc.nextLine();
        String course = sc.nextLine();

        String query = "INSERT INTO student (name, age, course) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, name);
        ps.setInt(2, age);
        ps.setString(3, course);

        int rows = ps.executeUpdate();
        System.out.println(rows + " student(s) inserted.");
    }

    static void displayStudents(Connection con) throws SQLException {
        String query = "SELECT * FROM student";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.println("\nID\tName\tAge\tCourse");
        System.out.println("----------------------------------");
        while (rs.next()) {
            System.out.printf("%d\t%s\t%d\t%s%n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("course"));
        }
    }

    static void updateStudent(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter student ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new name: ");
        String name = sc.nextLine();
        System.out.print("Enter new age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new course: ");
        String course = sc.nextLine();

        String query = "UPDATE student SET name=?, age=?, course=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, name);
        ps.setInt(2, age);
        ps.setString(3, course);
        ps.setInt(4, id);

        int rows = ps.executeUpdate();
        System.out.println(rows + " student(s) updated.");
    }

    static void deleteStudent(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter student ID to delete: ");
        int id = sc.nextInt();

        String query = "DELETE FROM student WHERE id=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);

        int rows = ps.executeUpdate();
        System.out.println(rows + " student(s) deleted.");
    }
}
