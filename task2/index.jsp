<%@ page import="java.sql.*" %>
<html>
<head>
	   <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/icon.jpg">
    	<!-- Or for other image types -->
    	<!-- <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/path/to/your/favicon.png"> -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">	
    <title>Student Feedback App</title>
    <style>
        body { 
    font-family: Arial, sans-serif;
	background: rgb(2,0,36);
	background: linear-gradient(90deg, rgba(2,0,36,1) 0%, rgba(0,212,255,1) 51%, rgba(9,9,121,1) 96%);
	 font-size: 20px; }
        h5 {
            border-radius: 20px;
	padding:10px;
	margin:20px 0;
            background-color: black;
            width: 50%;
            color: yellow;
        }
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
            let name = document.getElementById("name");
            const rate = document.getElementById("rate");
            let msg = document.getElementById("msg");

            if (name.value == "") {
                alert("You did not Enter Name");
                name.focus();
                return false;
            } else if (name.value.trim() == "") {
                alert("Name cannot be only space");
                name.innerHTML = " ";
                name.focus();
                return false;
            } else if (!name.value.match(/^[A-Za-z ]+$/)) {
                alert("Name should contain alphabets");
                name.innerHTML = " ";
                name.focus();
                return false;
            }

            if (rate.value == "") {
                alert("You did not Enter Rating");
                rate.focus();
                return false;
            } else if (rate.value < 1 || rate.value > 5) {
                alert("Enter a valid rating between 1 and 5.");
                rate.focus();
                return false;
            }
        }

        // Function to clear the thank you message after a delay
        function clearMessage() {
            setTimeout(function() {
                document.getElementById("msg").innerHTML = "";
            }, 5000); // Adjust the delay as needed (5000ms = 5 seconds)
        }
    </script>
</head>
<body>
    <center>
        
        <form onsubmit="return check()" method="Post">
	<h5>Student Feedback App</h5>
            <input type="text" class="form-control" name="name" placeholder="Enter Name" id="name"/>
            <br></br>
            <input type="number" class="form-control" name="rating" placeholder="Enter feedback rating" id="rate"/>
            <br></br>
            <textarea name="feedback" class="form-control"  placeholder="Enter your Feedback" rows=3  required></textarea>
            <br><br>
            <input type="submit" class="btn btn-primary" name="btn"/>
        </form>
    </center>
    <%
    if (request.getParameter("btn") != null) {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = "jdbc:mysql://localhost:3306/database_name";
            Connection con = DriverManager.getConnection(url, "username", "password");
            String sql = "INSERT INTO feedback (name, rate, reviews) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, request.getParameter("name"));
            pst.setInt(2, Integer.parseInt(request.getParameter("rating")));
            pst.setString(3, request.getParameter("feedback"));
            pst.executeUpdate();
            String msg = "Thank You for Your feedback";
    %>
            <center><h2 id="msg"><%= msg %></h2></center>
            <script>clearMessage();</script>
    <%
            con.close();
        } catch (Exception e) {
            out.println("Error " + e);
        }
    }
    %>
</body>
</html>
