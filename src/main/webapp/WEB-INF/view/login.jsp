<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Login"%>
<%
Login result = (Login) request.getAttribute("result");
String operation_message = (String) request.getAttribute("operation_message");
String error_message = (String) request.getAttribute("error_massage");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="././css/style.css">
<title>メニュー画面</title>
</head>

<body class="page_type1">
	<h1>ToDoタスク管理</h1>
	<div class="contents_type1">
		<br>
		<!-- ログイン失敗時の処理 -->
		<%
		if (result != null) {
			if (result.getLogin_fiag() == 1) {
		%>
		IDまたはパスワードに不備があります <br>
		<%
		}
		}
		%>
		<!-- 前画面の操作メッセージがあれば -->
		<%if (operation_message != null) {%>
		<%=operation_message%><br>
		<%
		}
		%>
		<!-- なにかしらのerrorがあるとき -->
		<%
		if (error_message != null) {
		%>
		<%=error_message%><br>
		<%
		}
		%>
		<br>

		<!-- 入力フォーム -->
		<form action="LoginServlet" method="post">

			<p>
				ユーザーID <input type="text" name="user_id" required>
			</p>

			<p>
				パスワード <input type="password" name="user_pass" required>
			</p>

			
			<p><button type="submit">ログイン</button></p>
		</form>

		<br> <a href="NewAccountServlet">新規アカウント作成</a> <br>



		<p>動作確認用</p>
		ユーザID：admin <br> パスワード：pass <br>
		<p>でログインしてください</p>

		新しいアカウントを作成して、動作確認していただくことも可能です。<br> <br>
	</div>
	<br>
	<br>
	<a
		href="https://adaptive-board-5a2.notion.site/Todo-JAVA-JSP-MVC-SQL-CRUD-3988c0464e6c40459fa7b8154187b442?pvs=74"
		target="_blank">アプリ概要説明ページ(別タブ)</a>


</body>
</html>

