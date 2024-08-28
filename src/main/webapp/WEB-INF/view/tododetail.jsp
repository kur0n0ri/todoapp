<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Login, model.TodoList"%>
<%
Login loginUser = (Login) session.getAttribute("loginUser");
TodoList todoList = (TodoList) request.getAttribute("todoList");
String error_message = (String) request.getAttribute("error_message");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>【変更･削除】</title>
<link rel="stylesheet" href="././css/style.css">
</head>
<body class="page_type3">
	<h1>ToDoタスク管理</h1>
	<div class="contents_type3">
		<h3>【変更･削除】</h3>
		<!-- エラーメッセージを受け取っていれば表示する -->
		<%
		if (error_message != null) {
		%>
		<p><%=error_message%></p>
		<%
		}
		%>

		<form action="InsertServlet" method="post">
			<table style="width: 90%;">
				<input name="task_id" type="hidden" value="<%=todoList.getTask_id()%>"></td>
				<tr>
					<td style="width: 20px;">タスク名称</td>
					<td style="width: 80%;"><input type="text" name="task_name"
						value="<%=todoList.getTask_name()%>" style="width: 98%;"></td>
				</tr>
				<tr>
					<td>タスク内容</td>
					<td><textarea name="task_contents"
							style="width: 98%; height: 80px"><%=todoList.getTask_contents()%></textarea>
				</tr>
				<tr>
					<td>タスク期限</td>
					<td><input type="date" name="task_limitdate"
						value="<%=todoList.getTask_limitdate()%>"></td>
				</tr>
				<tr>
					<td>タスク担当者</td>
					<td><input type="text" name="task_user"
						value="<%=todoList.getTask_user()%>"></td>
				</tr>
				<tr>
					<td>タスク状況</td>
					<td><select name="task_status">
							<!-- セレクトボタンの初期表示をtask_statusに対応した文字列にする -->
							<%
							String selected = "selected";
							if (todoList.getTask_status() == 0) {
							%>
							<option value="0" <%=selected%>>未完</option>
							<option value="1">完了</option>
							<%
							} else if (todoList.getTask_status() == 1) {
							%>
							<option value="0">未完</option>
							<option value="1" <%=selected%>>完了</option>
							<%
							}
							%>
					</select></td>
				</tr>
			</table>
			<br>

			<button type="submit" formaction="UpdateServlet" >変更する</button>

			<button type="submit" formaction="DeleteServlet" >削除する</button>
		</form>

		<br><a href="TodolistServlet">タスク一覧へ</a>
	</div>
	
		<br><br>ログインユーザー名：<%=loginUser.getUser_id()%></p>
</body>
</html>