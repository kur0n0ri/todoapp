<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規アカウント登録</title>
<link rel="stylesheet" href="././css/style.css">
</head>
<body class="page_type1">
	<h1>新規アカウント登録</h1>
	<div class="contents_type1">

		<!-- なにかしらのerrorがあるとき -->
		<%
		String error_message = (String) request.getAttribute("error_message");
		if (error_message != null) {
		%>
		<p><%=error_message%></p>
		<%
		}
		%>
		<br>
		<form action="NewAccountServlet" method="post">
			<p>
				新規ユーザーID <input type="text" name="user_id"> (半角英数字 8 - 64文字)
			</p>
			<p>
				新規パスワード <input type="password" name="user_pass"> (半角英数字 8 -
				64文字)
			</p>
			<button type="submit">新規作成</button>
		</form>

		<br> <a href="LoginServlet">ログイン画面</a>
	</div>
</body>
</html>