import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class LoginAuth extends JFrame implements ActionListener {
    JTextField userField;
    JPasswordField passField;
    JButton loginBtn;
    JLabel msgLabel;
    Connection con;

    LoginAuth() {
        setTitle("User Login");
        setSize(300, 200);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new JLabel("Username:"));
        userField = new JTextField(15);
        add(userField);

        add(new JLabel("Password:"));
        passField = new JPasswordField(15);
        add(passField);

        loginBtn = new JButton("Login");
        add(loginBtn);

        msgLabel = new JLabel();
        add(msgLabel);

        loginBtn.addActionListener(this);

        try {
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/userdb", "root", "12M16sr1976");
        } catch (Exception e) {
            msgLabel.setText("DB Connection Failed!");
        }

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                msgLabel.setText("Login Successful!");
            } else {
                msgLabel.setText("Invalid Credentials!");
            }
        } catch (Exception e) {
            msgLabel.setText("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new LoginAuth();
    }
}
