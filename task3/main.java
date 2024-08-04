import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class main extends JFrame
{
	Container c;
	JLabel labResult;
	JButton btnAdd , btnView , btndel , btnupdate ;
	
	main()
	{
		c = getContentPane();
		c.setLayout(null);
		
		labResult = new JLabel("Student Management App ");
		btnAdd = new JButton("Add Record");
		btnView = new JButton("View Records");
		btnupdate = new JButton("Update Record");
		btndel = new JButton("Delete Record");
		
				
		Font f = new Font("Cambria",Font.BOLD, 20);
		labResult.setFont(f);	
		btnAdd.setFont(f);
		btnView.setFont(f);
		btnupdate.setFont(f);
		btndel.setFont(f);
		
		labResult.setBounds(100,50,400,50);
		btnAdd.setBounds(100,130,200,50);
		btnView.setBounds(100,210,200,50);
		btnupdate.setBounds(100,290,200,50);
		btndel.setBounds(100,370,200,50);
		
		c.add(labResult);	
		c.add(btnAdd);
		c.add(btnView);
		c.add(btnupdate);
		c.add(btndel);
		
		ActionListener add =(ae) -> {
			Add a = new Add();
			this.dispose(); // Close the current window
		};
		btnAdd.addActionListener(add);
		ActionListener view =(ae) -> {
			View v = new View();
			this.dispose(); // Close the current window
		};
		btnView.addActionListener(view);
		ActionListener del =(ae) -> {
			delete d = new delete();
			this.dispose(); // Close the current window
		};
		btndel.addActionListener(del);

		ActionListener update =(ae) -> {
			update u = new update();
			this.dispose(); // Close the current window
		};
		btnupdate.addActionListener(update);
		setTitle("Student Management App");
		setSize(500,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		}

		
	}

