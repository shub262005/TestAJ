import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class StudentGUI extends JFrame implements ActionListener {
    JTextField idField, nameField, ageField;
    JButton insertBtn, updateBtn, deleteBtn, viewBtn;
    JTextArea output;
    Connection con;
    
    StudentGUI() {
        setTitle("Student CRUD");
        setSize(400, 400);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new JLabel("ID:"));
        idField = new JTextField(10);
        add(idField);

        add(new JLabel("Name:"));
        nameField = new JTextField(10);
        add(nameField);

        add(new JLabel("Age:"));
        ageField = new JTextField(10);
        add(ageField);

        insertBtn = new JButton("Insert");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        viewBtn = new JButton("View All");

        add(insertBtn);
        add(updateBtn);
        add(deleteBtn);
        add(viewBtn);

        output = new JTextArea(10, 30);
        add(new JScrollPane(output));

        insertBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        viewBtn.addActionListener(this);

        try {
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/studentdb", "root", "12M16sr1976");
        } catch (Exception e) {
            output.setText("DB Connection Failed!");
        }

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == insertBtn) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO student(name, age) VALUES(?, ?)");
                ps.setString(1, nameField.getText());
                ps.setInt(2, Integer.parseInt(ageField.getText()));
                ps.executeUpdate();
                output.setText("Inserted Successfully!");

            } else if (ae.getSource() == updateBtn) {
                PreparedStatement ps = con.prepareStatement("UPDATE student SET name=?, age=? WHERE id=?");
                ps.setString(1, nameField.getText());
                ps.setInt(2, Integer.parseInt(ageField.getText()));
                ps.setInt(3, Integer.parseInt(idField.getText()));
                ps.executeUpdate();
                output.setText("Updated Successfully!");

            } else if (ae.getSource() == deleteBtn) {
                PreparedStatement ps = con.prepareStatement("DELETE FROM student WHERE id=?");
                ps.setInt(1, Integer.parseInt(idField.getText()));
                ps.executeUpdate();
                output.setText("Deleted Successfully!");

            } else if (ae.getSource() == viewBtn) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM student");
                output.setText("ID\tName\tAge\n");
                while (rs.next()) {
                    output.append(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\n");
                }
            }
        } catch (Exception e) {
            output.setText("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new StudentGUI();
    }
}
