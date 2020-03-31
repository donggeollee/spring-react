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
	
	var xhr = new XmlHttpRequest();
	
	function postClick(){
		var obj = {"asdasdas" : "111111"};
		
		$.ajax({
		    url: "<%=request.getContextPath()%>/post/upload",
		    type: 'POST', 
		    contentType : 'application/json', 
		    data : obj,
		    success: function (data) {
		        console.log(data);
		    },
		    error: function onError (error) {
		        console.error(error);
		    }
		});	
	}
	
	</script>
</head>
<body>
<h1> HELLO ${title}..!  </h1>
<button onclick="postClick();">Post Request</button>
</body>
</html>
