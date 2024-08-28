<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Login"%>
<%
Login loginUser = (Login) session.getAttribute("loginUser");
String operation_message = (String) session.getAttribute("operation_message");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メニュー画面</title>
<link rel="stylesheet" href="././css/style.css">
</head>
<body class="page_type1">
    
	<h1>ToDoタスク管理</h1>
	<div class="contents_type1">
	<%
	if (operation_message != null) {
	%>
	<%=operation_message%><br>
	<br>
	<%
	session.removeAttribute("operation_message");
	}
	%>

	<!-- TodolistServlet > todolist.jsp -->
	
		<p><a href="TodolistServlet">TODOタスク一覧</a></p>
		<p><a href="InsertServlet">TODOタスク登録</a></p>
	
	<!-- LogoutServletで処理してからlogin.jspに遷移 -->
	<br>
	<p><button>
		<a href="LogoutServlet" style="text-decoration: none;">ログアウト</a>
	</button></p>
	</div>
	<br>
		ログインユーザー名：<%=loginUser.getUser_id()%></p>
</body>
</html>