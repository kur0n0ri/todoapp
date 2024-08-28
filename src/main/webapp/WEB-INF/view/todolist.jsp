<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="model.TodoList, model.Login, java.util.ArrayList, java.time.LocalDateTime
"%>
<%
Login loginUser = (Login) session.getAttribute("loginUser");
String message = (String) request.getAttribute("message");
String operation_message = (String) session.getAttribute("operation_message");
String error_message = (String) request.getAttribute("error_message");
ArrayList<TodoList> todoList = (ArrayList<TodoList>) request.getAttribute("todoList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク一覧</title>
<link rel="stylesheet" href="././css/style.css">
</head>
<body class="page_type2">
<h1>Todoタスク一覧</h1>
	<div class="top">
		 <div><a href="MenuServlet">メニューへ</a></div>　　　　　<div>ログインユーザー名：<%=loginUser.getUser_id()%> </div>　　
	</div>
		<form action="OrderbyServlet" method="get" style="margin-top: 4px">
			表示順: <select name="orderby_number">
				<option value="1">期限 (昇順)</option>
				<option value="2">期限 (降順)</option>
				<option value="3">状況 (未完→完了)</option>
				<option value="4">状況 (完了→未完)</option>
			</select> <input type="submit" value="並び替え">
		</form>
		<p><a href="InsertServlet">新規登録へ </a></p> 
	<%
	if (message != null && todoList.size() > 0) {
	%>
	<%=message%><br>
	<%
	}
	%>
	<%
	if (operation_message != null) {
	%>
	<%=operation_message%><br>
	<%
	session.removeAttribute("operation_message");
	}
	%>
	<%
	if (error_message != null) {
	%>
	<%=error_message%><br>
	<%
	}
	%>
	<br>
	<div class="tasks">
		<%
		for (TodoList todo : todoList) {
		%>
		<div class="contents_type2">
			<table <%if (todo.getTask_status() == 0) {%>
				style="background: #fef4f4;"
				<%} else if (todo.getTask_status() == 1) {%>
				style="background: #f0f8ff;" <%}%>>

				<tr>
					<td>タスク名<br> <%=todo.getTask_name()%>
					</td>
				</tr>
				<tr>
					<td>タスク内容<br> <%=todo.getTask_contents()%>
					</td>
				</tr>
				<tr>
					<td>タスク期限 : <%=todo.getTask_limitdate()%></td>
				</tr>
				<tr>
					<td>最終更新日 : <%=todo.omitTask_update()%></td>
				</tr>
				<tr>
					<td>タスク担当 : <%=todo.getTask_user()%></td>
				</tr>
				<tr>

					<td>タスク状況 : <%=todo.convertTask_status()%></td>

				</tr>
				<tr>
					<td><a href="UpdateServlet?task_id=<%=todo.getTask_id()%>">変更・削除へ</a></td>
				</tr>
			</table>
		</div>
		<%
		}
		%>
	</div>
	<br>


</body>
</html>