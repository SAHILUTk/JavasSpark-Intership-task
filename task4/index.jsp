<%@ page import="java.sql.*" %>
<%@ page import="javax.mail.*" %>
<%@ page import="javax.mail.internet.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
	   <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/icon.jpg">
    	<!-- Or for other image types -->
    	<!-- <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/path/to/your/favicon.png"> -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">	
    <title>Student Subscription App</title>
    
    <style>
        body { 
	font-family: Arial, sans-serif;
	background: rgb(2,0,36);
	
	 font-size: 20px; }
        h5 {
            border-radius: 20px;
	padding:10px;
	margin:20px 0;
            background-color: black;
            width: 50%;
            color: yellow;
        }
	.alert{
		color: red;	
		width: 50%;
		margin:auto;
	}
	.success{color: green;	}
 	form{
		border:2px solid #000;
		padding:20px;
		width:40%;
		margin:10% auto;
		
		/* From https://css.glass */
		background: rgba(255, 255, 255, 0.2);
		border-radius: 16px;
		box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
		backdrop-filter: blur(5px);
		-webkit-backdrop-filter: blur(5px);
		border: 1px solid rgba(255, 255, 255, 0.3);
		border-radius:15px;
	}
    </style>
    <script>
        function check() {
            let email = document.getElementById("email");
          	var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            let msg = document.getElementById("msg");

            if (email.value == "") {
                alert("You did not Enter Email-id");
                email.focus();
                return false;
            } 
	else if (!email.value.match(emailPattern)) {
                alert("Please enter a valid email address");
                email.innerHTML = " ";
                email.focus();
                return false;
            }
        }
	
        // Function to clear the thank you message after a delay
        function clearMessage() {
            setTimeout(function() {
                document.getElementById("msg").innerHTML = "";
            }, 2000); // Adjust the delay as needed (5000ms = 5 seconds)
        }
    </script>
</head>
<body>
    <center>
        
        <form onsubmit="return check()" method="Post">
	<h5>Kamal Classes App</h5>
            <input type="text" class="form-control" name="email" placeholder="Enter Email-id " id="email" required/>
            <br><br>
            <button type="submit" name="subs"  class="btn me-3 btn-primary">Subscribe</button>
	<button type="submit" name="unsubs" class="btn btn-danger">Un-Subscribe</button>
        </form>
	

    </center>
 
 <%
		Properties p = System.getProperties();
		p.put("mail.smtp.host","smtp.gmail.com");
		p.put("mail.smtp.port",587);
		p.put("mail.smtp.auth",true);
		p.put("mail.smtp.starttls.enable",true);
		
		//aapka un and pw ka authentication

		Session ms = Session.getInstance(p,new Authenticator(){
		public PasswordAuthentication getPasswordAuthentication(){
			String un = "#Enter you Email-id";
			/// password dalo
			String pw = "#Enter Your Password-key#";
			return new PasswordAuthentication(un,pw);
		}
		});		
if (request.getParameter("subs") != null) {						
    try {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        String url = "jdbc:mysql://localhost:3306/data_base_name";
        Connection con = DriverManager.getConnection(url, "user_name", "password");

        // Check if the email already exists in the database
        String checkSql = "SELECT COUNT(*) FROM students WHERE email = (?)";
        PreparedStatement checkPst = con.prepareStatement(checkSql);
        checkPst.setString(1, request.getParameter("email"));
        ResultSet rs = checkPst.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        if (count > 0) {
            // Email already exists
            String msg = "Email already subscribed.";
    %>
            <center><h2 id="msg" class="alert" role="alert"><%= msg %></h2></center>
            <script>clearMessage();</script>
    <%
        } else {
            // Insert the email into the database
            String sql = "INSERT INTO students  VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, request.getParameter("email"));
            pst.setString(2, "Subscribed");
            pst.executeUpdate();
            String msg = "Thanks for your subscription.";
	
	try{
		String email = request.getParameter("email");	
		MimeMessage msgs = new MimeMessage(ms);
		String subject = "Subscription Successfull kamal Classes ";
		String txt = "Thanks For Subscribing Kamal Classes App :"+email;
		msgs.setText(txt);
		msgs.setSubject(subject);
		msgs.setFrom(new InternetAddress("#sender-email"));
		msgs.addRecipient(Message.RecipientType.TO,new InternetAddress(request.getParameter("email")));
		Transport.send(msgs);
		out.println("Email-sent ");
		
	}
	catch(Exception e)
	{
		out.println("Error "+e);
	}
    %>
            <center><h2 id="msg" class="success"><%= msg %></h2></center>
            <script>clearMessage();</script>
    <%
        }

        con.close();
    } catch (SQLException e) {
        out.println("Error " + e);
    }
	
}
String msg = ""; // Declare the message variable

if (request.getParameter("unsubs") != null) {
    try {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        String url = "jdbc:mysql://localhost:3306/database_name";
        Connection con = DriverManager.getConnection(url, "user_name", "password");

        String deleteSql = "DELETE FROM students WHERE email = ?";
        PreparedStatement deletePst = con.prepareStatement(deleteSql);
        deletePst.setString(1, request.getParameter("email"));
        int rowsAffected = deletePst.executeUpdate();

        if (rowsAffected > 0) {
            msg = "You have successfully unsubscribed.";
	try{
		String email = request.getParameter("email");	
		MimeMessage msgs = new MimeMessage(ms);
		String subject = "Subscription Removed From kamal Classes ";
		String txt = "You are Unsubscribed from Kamal Classes App";
		msgs.setText(txt);
		msgs.setSubject(subject);
		msgs.setFrom(new InternetAddress(request.getParameter("email")));
		
		msgs.addRecipient(Message.RecipientType.TO,new InternetAddress(request.getParameter("email")));
		Transport.send(msgs);
		out.println("Email-sent ");
		
	}
	catch(Exception e)
	{
		out.println("Error "+e);
	}
        } else {
            msg = "This Email-id is not subscribed.";
        }
        con.close();
	
    } catch (SQLException e) {
        out.println("Error " + e);
    }
}

%>
<center><h2 id="msg" class="alert" role="alert"><%= msg %></h2></center>
 <script>clearMessage();</script>
</body>
</html>
