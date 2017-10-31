<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="ie6 ielt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="ie7 ielt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html lang="en"> <!--<![endif]-->
<head>
<meta charset="utf-8">
<title>Paper Stack</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/css/login.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/static/jquery/jquery-1.9.1.min.js"></script>
</head>
<body>
<script type="text/javascript">
	$(function(){
		$('#loginbtn').click(function() {
			alert("1111");
		    var param = {
		        username : $("#username").val(),
		        password : $("#password").val()
		    };
		    $.ajax({ 
		        type: "post", 
		        url: "<%=request.getContextPath()%>" + "/submit.do", 
		        data: param, 
		        success: function(data) { 
		            if(data.st == -1){
		                alert(data.msg);
		            }else{
		                //登录成功
		                window.location.href = "<%=request.getContextPath()%>" +  "/index.do";
		            }
		        },
		        error: function(data) { 
		            alert("调用失败...."); 
		        }
		    });
		});
	})
	
</script>
<div class="container">
	<section id="content">
		<form >
			<h1>登录</h1>
			<div>
				<input type="text" placeholder="Username" name = "username" required="" id="username" />
			</div>
			<div>
				<input type="password" placeholder="Password" name = "password" required="" id="password" />
			</div>
			<div>
				<input type="button" id = "loginbtn"  value="Log in" />
				<a href="#">Lost your password?</a>
				<a href="#">Register</a>
			</div>
		</form><!-- form -->
		<div class="button">
		</div><!-- button -->
	</section><!-- content -->
</div><!-- container -->
</body>
</html>