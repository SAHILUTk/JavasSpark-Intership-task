import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class update extends JFrame
{
	Container c;
	JLabel labResult ,labhead , labrno , labName , labsub1 , labsub2 , labsub3 ;
	JTextField txtrno,txtName , txtsub1 ,txtsub2 ,txtsub3;
	JButton btnSave , btnback ;
	
	update()
	{
		c = getContentPane();
		//FlowLayout fl = new FlowLayout();
		c.setLayout(null);
		labResult = new JLabel("Student Management App ");
		labhead = new JLabel("Update Student Record");
		labrno = new JLabel("Enter Roll-no ");
		txtrno = new JTextField(12);
		labName = new JLabel("Enter Name  ");
		txtName = new JTextField(12);
		labsub1 = new JLabel("Enter Marks of sub1 ");
		txtsub1 = new JTextField(12);
		labsub2 = new JLabel("Enter Marks of sub2 ");
		txtsub2 = new JTextField(12);
		labsub3 = new JLabel("Enter Marks of sub2 ");
		txtsub3 = new JTextField(12);
		btnSave = new JButton("Save");
		btnback = new JButton("Back to main Page");
		labResult.setBounds(200,30,400,50);
	labhead.setBounds(210,70,400,50);

	labrno.setBounds(50,130,200,50);
	txtrno.setBounds(50,170,150,50);

	labName.setBounds(250,130,300,50);
	txtName.setBounds(250,170,300,50);

	labsub1.setBounds(50,230,400,50);
	txtsub1.setBounds(50,270,250,50);

	labsub2.setBounds(50,330,400,50);
	txtsub2.setBounds(50,370,250,50);

	labsub3.setBounds(50,430,400,50);
	txtsub3.setBounds(50,470,250,50);

	btnSave.setBounds(100,580,150,50);
	btnback.setBounds(300,580,250,50);
		
				
		Font f = new Font("Roboto", Font.PLAIN, 25);

		labResult.setFont(f);
		labhead.setFont(f);	
		btnSave.setFont(f);
		btnback.setFont(f);
		labrno.setFont(f);
		txtrno.setFont(f);
		labName.setFont(f);
		txtName.setFont(f);
		labsub1.setFont(f);
		txtsub1.setFont(f);
		labsub2.setFont(f);
		txtsub2.setFont(f);
		labsub3.setFont(f);
		txtsub3.setFont(f);
		
		c.add(labhead);
		c.add(labResult);
		c.add(labrno);
		c.add(txtrno);
		c.add(labName);
		c.add(txtName);
		c.add(labsub1);
		c.add(txtsub1);
		c.add(labsub2);
		c.add(txtsub2);
		c.add(labsub3);
		c.add(txtsub3);	
		c.add(btnSave);
		c.add(btnback);
	
		
            						
		ActionListener us = (ae) ->{
		try{


			int roll_no;
            			try {
                		roll_no = Integer.parseInt(txtrno.getText().trim());
            			} catch (NumberFormatException ex) {
                			JOptionPane.showMessageDialog(c, "Please enter a valid Roll-no.", "Error", JOptionPane.ERROR_MESSAGE);
                			return;
            			}

		            String name = txtName.getText().trim();
            			if (name.isEmpty()) {
                			JOptionPane.showMessageDialog(c, "You did not enter a name", "Error", JOptionPane.ERROR_MESSAGE);
                			txtName.requestFocus();
                			return;
            			}

		            if (!name.matches("[A-Za-z ]+")) {
      		    	JOptionPane.showMessageDialog(c, "Name should only contain alphabets", "Error", JOptionPane.ERROR_MESSAGE);
                		txtName.requestFocus();
                		txtName.setText("");
                		return;
            			}

           			int  marks1,  marks2,  marks3;
            			try {
                 			marks1 = Integer.parseInt(txtsub1.getText().trim());
                 			marks2 = Integer.parseInt(txtsub2.getText().trim());
                			marks3 = Integer.parseInt(txtsub3.getText().trim());
            			} catch (NumberFormatException e) {
                			JOptionPane.showMessageDialog(c, "Please enter valid marks.", "Error", JOptionPane.ERROR_MESSAGE);
                		return;
            			}
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                		String url = "jdbc:mysql://localhost:3306/db_name";
                		Connection con = DriverManager.getConnection(url, "user", "password");
			String sql = "UPDATE student_management SET name = ?, marks1 = ?, marks2 = ?, marks3 = ? WHERE roll_no = ?";
                		PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1,  name);
                		pst.setInt(2, marks1);
                		pst.setInt(3,  marks2);
                		pst.setInt(4,  marks3);
                		pst.setInt(5,  roll_no);
                		pst.executeUpdate();

                		JOptionPane.showMessageDialog(c, "Data Updated Successfully");
                		txtrno.setText("");
                		txtName.setText("");
                		txtsub1.setText("");
                		txtsub2.setText("");
                		txtsub3.setText("");
                		pst.close();
                		con.close();
			main e = new main();
			 this.dispose(); // Close the current window

		}
		catch(SQLException se)
		{
			JOptionPane.showMessageDialog(c, "Database error: " + se.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		};
		btnSave.addActionListener(us);
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

