import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class delete extends JFrame {
    Container c;
    JLabel labhead, labrno;
    JTextField txtrno;
    JButton btnDelete, btnback;

    delete() {
        c = getContentPane();
        c.setLayout(null);

        labhead = new JLabel("Delete Student Record");
        labrno = new JLabel("Enter Roll-no ");
        txtrno = new JTextField(12);
        btnDelete = new JButton("Delete");
        btnback = new JButton("Back to main Page");

        Font f = new Font("Roboto", Font.PLAIN, 30);

        labhead.setFont(f);
        labrno.setFont(f);
        txtrno.setFont(f);
        btnDelete.setFont(f);
        btnback.setFont(f);
	
	labhead.setBounds(50,70,400,50);

	labrno.setBounds(50,130,200,50);
	txtrno.setBounds(50,170,200,50);

	btnDelete.setBounds(50,280,150,50);
	btnback.setBounds(250,280,250,50);

        c.add(labhead);
        c.add(labrno);
        c.add(txtrno);
        c.add(btnDelete);
        c.add(btnback);

        btnDelete.addActionListener(ae -> {
            int roll_no;
            try {
                roll_no = Integer.parseInt(txtrno.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(c, "Please enter a valid Roll-no.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                String url = "jdbc:mysql://localhost:3306/database_name";
                Connection con = DriverManager.getConnection(url, "user", "pass");

                String sql = "DELETE FROM student_management WHERE roll_no = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, roll_no);
                int rowsAffected = pst.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(c, "Data Deleted Successfully");
                } else {
                    JOptionPane.showMessageDialog(c, "No record found with the given Roll-no.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                txtrno.setText("");
                pst.close();
                con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(c, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnback.addActionListener(ae -> {
            main e = new main();
            this.dispose(); // Close the current window
        });

        setTitle("Student Management App");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new delete());
    }
}
