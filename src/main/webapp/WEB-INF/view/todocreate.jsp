<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Login"%>
<%
Login loginUser = (Login) session.getAttribute("loginUser");
String error_message = (String) request.getAttribute("error_message");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録</title>
<link rel="stylesheet" href="././css/style.css">
</head>
<body class="page_type3">
	<h1>Todoタスク管理</h1>
	<div class="contents_type3">
		<h3>【新規登録】</h3>
		<!-- エラーメッセージを受け取っていれば表示する -->
		<%
		if (error_message != null) {
		%>
		<p><%=error_message%></p>
		<%
		}
		%>

		<form action="InsertServlet" method="post">
			<table  style="width: 90%">
				<tr>
					<td style="width: 20%">タスク名称</td>
					<td style="width: 80%"><input type="text" name="task_name" style="width: 90%"></td>
				</tr>
				<tr>
					<td>タスク内容</td>
					<td><textarea name="task_contents" value="${param.task_contents}"
							style="width: 90%; height: 80px"></textarea>
				</tr>
				<tr>
					<td>タスク期限</td>
					<td><input type="date" name="task_limitdate"</td>
				</tr>
				<tr>
					<td>タスク担当者</td>
					<td><input type="text" name="task_user" value="自身"></td>
				</tr>
				<tr>
					<td>タスク状況</td>
					<td><select name="task_status">
							<option value="0">未完</option>
							<option value="1">完了</option>
					</select></td>
				</tr>
			</table>
			<br>
			<button type="submit">登録する</button>
		</form>



		<br><a href="TodolistServlet">タスク一覧へ</a>

	</div>
	
	
		<br><br>ログインユーザー名：<%=loginUser.getUser_id()%></p>

</body>
</html>