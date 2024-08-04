import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
class StudentManagementApp extends JFrame
{
	Container c;
	JLabel labName,labNum,labGender,labEmail,labLang;
	JTextField txtName,txtNum,txtEmail;
	JRadioButton rbmale ,rbFemale;
	JButton Submit;
	JCheckBox cjava ,cpython ,cjavascript;
	ButtonGroup genderGroup;
	StudentManagementApp()
	{
		c = getContentPane();
		c.setLayout(null);
		
		labName = new JLabel("Enter Full Name ");
		txtName = new JTextField(50);
		labNum = new JLabel("Enter Mobile no ");
		txtNum = new JTextField(50);
		labEmail = new JLabel("Enter email-id ");
		txtEmail = new JTextField(50);
		labGender = new JLabel("Select your gender: ");
		rbmale = new JRadioButton("Male",true);
		rbFemale = new JRadioButton("Female");
		labLang = new JLabel("Select Programming Lang Known");
		cjava =  new JCheckBox("Java",true);
		cpython =  new JCheckBox("Python");
		cjavascript =  new JCheckBox("Javascript");
		Submit = new JButton("Submit");
	
		Font f = new Font("Arial",Font.BOLD,20);
		labName.setFont(f);
		labNum.setFont(f);
		labEmail.setFont(f);
		labLang.setFont(f);
		labGender.setFont(f);
		Submit.setFont(f);

		txtName.setFont(f);
		txtNum.setFont(f);
		txtEmail.setFont(f);
		rbmale.setFont(f);
		rbFemale.setFont(f);
		cjava.setFont(f);
		cpython.setFont(f);
		cjavascript.setFont(f);

		labName.setBounds(50,50,300,50);
		txtName.setBounds(350,50,300,50);
		labNum.setBounds(50, 120,300,50);
		txtNum.setBounds(350,120,300,50);
		labEmail.setBounds(50, 190,300,50);
		txtEmail.setBounds(350,190,300,50);
		labGender.setBounds(50, 260,300,50);
		rbmale.setBounds(350,260,100,50);
		rbFemale.setBounds(450, 260,300,50);
		labLang.setBounds(50, 330,500,50);
		cjava.setBounds(450, 330,100,50);
		cpython.setBounds(550, 330,100,50);

		cjavascript.setBounds(650, 330,200,50);
		Submit.setBounds(200, 400,300,50);
		


		c.add(labName);
		c.add(txtName);
		c.add(labNum);
		c.add(txtNum);
		c.add(labEmail);
		c.add(txtEmail);
		c.add(labGender);
		c.add(rbmale);
		c.add(rbFemale);
		c.add(labLang);
		c.add(cjava);
		c.add(cpython);
		c.add(cjavascript);
		c.add(Submit);	
		 // Add the radio buttons to the ButtonGroup
        		genderGroup = new ButtonGroup();
        		genderGroup.add(rbmale);
        		genderGroup.add(rbFemale);

                         ActionListener a = (ae) -> {
   			String name = txtName.getText();
    			name = name.trim();
    			if(name.equals(""))
				{
				JOptionPane.showMessageDialog(c,"u did not enter name");
				
				txtName.requestFocus();
				return;
	
			}
			if(! name.trim().matches("[A-Za-z ]+"))
			{
				JOptionPane.showMessageDialog(c,"Name should only contain alphabets");
				
				txtName.requestFocus();
				txtName.setText("");
				return;
	
			}
			 int mobileNum = 0;
           			 try {
                			mobileNum = Integer.parseInt(txtNum.getText().trim());
            			} catch (NumberFormatException ex) {
                			JOptionPane.showMessageDialog(c, "Please enter a valid mobile number.", "Error", JOptionPane.ERROR_MESSAGE);
                			return;
            			}
    			String email = txtEmail.getText();
				email.trim();
				if(email.equals(""))
				{
				JOptionPane.showMessageDialog(c,"u did not Enter Email");
				txtEmail.requestFocus();
				return;
	
				}
			
			// Regular expression for validating email
			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

			// Compile the regex
			Pattern pattern = Pattern.compile(emailRegex);

			// Match the entered email against the regex
			Matcher matcher = pattern.matcher(email);

			if (!matcher.matches()) {
    			JOptionPane.showMessageDialog(c, "Invalid email format");
   			 txtEmail.requestFocus();
    			return;
			}

			
			
    			String gender = "";
			

   	 		if (rbmale.isSelected()) {
        			gender = "Male";
    			}
			 if(rbFemale.isSelected()){
        			gender = "Female";
    			}

    			String lang = "";
    			if (cjava.isSelected()) {
        			lang += "Java, ";
    			}
    			if (cpython.isSelected()) {
        			lang += "Python, ";
    			}
    			if (cjavascript.isSelected()) {
        			lang += "Javascript ";
    			}
			
    			// Remove trailing comma if any
    			if (lang.endsWith(", ")) {
        			lang = lang.substring(0, lang.length() - 2);
    			}
			try{
				
				 DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				String url = "jdbc:mysql://localhost:3306/database_table";
				Connection con = DriverManager.getConnection(url,"username","password");
				
				String sql = "insert into student values (?,?,?,?,?)";
				PreparedStatement pst = con.prepareStatement(sql);

				//String name = name;
				//int mobile_no = mobileNum;
				//String email = email;
				//String gender = gender;
				//String lang = lang;
				pst.setString(1,name);
				pst.setInt(2,mobileNum);
				pst.setString(3,email);
				pst.setString(4,gender);
				pst.setString(5,lang);
				pst.executeUpdate();
				JOptionPane.showMessageDialog(c,"Data Added Successfully ");
				txtName.setText("");
				txtEmail.setText("");
				txtNum.setText("");
				cjava.setSelected(false);
				cpython.setSelected(false);
				cjavascript.setSelected(false);
			}
			catch (SQLException e){
				JOptionPane.showMessageDialog(c,"Error "+e);
			}
    			JOptionPane.showMessageDialog(c, name + " " + mobileNum + " " + email + " " + gender + " " + lang);
			};
			Submit.addActionListener(a);

		setTitle("Student Record Management App");
		setSize(800,600);
		setResizable(false);	
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		

	}
		
}
class P4
{
	public static void main(String args[])
	{
	StudentManagementApp sm = new StudentManagementApp();
	}
}