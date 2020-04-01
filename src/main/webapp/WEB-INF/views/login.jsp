<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<script
  	src="https://code.jquery.com/jquery-3.4.1.min.js" 
  	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
  	crossorigin="anonymous"></script>
	<script type="text/javascript">
	$(function(){
		
		$("#loginForm").submit(function(e){
			e.preventDefault(); 
			
			var loginObj = {
				username : $("#username").val(),
				password : $("#password").val(),
			};
			
			console.log(loginObj);
			
			$.ajax({
			    url: "<%=request.getContextPath()%>/user/auth/login",
			    type: 'POST', 
			    contentType : 'application/json', 
			    data : JSON.stringify(loginObj),
			    success: function (data) {
			        console.log(data);
			    },
			    error: function (error) {
			        console.error(error);
			    }
			});	
			
		});
		
		
		
		
		
	})
	</script>
	
</head>
<body>
<h1> LOGIN </h1>
	<form id="loginForm">
		<p>
			<label for="username">email : </label>
			<input type="text" id="username" name="username"></input>
		</p>
		<p>
			<label for="password">password : </label>
			<input type="password" id="password" name="password"></input>
		</p>
		<input type="submit" value="로그인" /> 
	</form>

</body>
</html>
