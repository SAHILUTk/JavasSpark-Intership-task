import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


class View extends JFrame
{
	Container c;
	JLabel labResult ,labhead;
	TextArea taData;
	JButton btnSave , btnback ;
	
	View()
	{
		c = getContentPane();
		FlowLayout fl = new FlowLayout();
		c.setLayout(null);
	
		labResult = new JLabel("Student Management App ");
		labhead = new JLabel("View Student Record");
		
		btnSave = new JButton("Save");
		btnback = new JButton("Back to main Page");
		taData = new TextArea(20,50);
		
		
		
				
		Font f = new Font("Roboto", Font.PLAIN, 25);
		labResult.setFont(f);
		labhead.setFont(f);
		taData.setFont(f);
		//btnSave.setFont(f);
		btnback.setFont(f);

		labResult.setBounds(200,30,400,50);
		labhead.setBounds(210,70,400,50);
		taData.setBounds(100,200,400,300);
	

		//btnSave.setBounds(100,580,150,50);
		btnback.setBounds(300,580,250,50);
		
		c.add(labhead);
		c.add(labResult);
		c.add(taData);	
		//c.add(btnSave);
		c.add(btnback);
			
		 	try {
                		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                		String url = "jdbc:mysql://localhost:3306/db_name";
                		Connection con = DriverManager.getConnection(url, "user", "passw");

                		Statement stmt = con.createStatement();
			String sql = "select * from student_management";
			ResultSet rs = stmt.executeQuery(sql);
                		StringBuffer sb = new StringBuffer();
			while(rs.next())
				sb.append("rno: "+rs.getInt(1) + " Name: " + rs.getString(2) + " Sub1: "+ rs.getInt(3) + " Sub2: "+rs.getInt(4)+" Sub3: "+rs.getInt(5)+"\n==========\n" );
                			taData.setText(sb.toString());

                
                		rs.close();
                		con.close();
            			}
			 catch (SQLException e) {
                			JOptionPane.showMessageDialog(c, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            			}
        			
		
		ActionListener add =(ae) -> {
			main e = new main();
			 this.dispose(); // Close the current window
		};

		btnback.addActionListener(add);

		setTitle("Student Management App");
		setSize(700,900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		}

		
	}

