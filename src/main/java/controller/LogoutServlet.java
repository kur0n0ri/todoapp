//ログアウト処理を行うサーブレット
package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//セッションスコープオブジェクトの取得
		HttpSession session = request.getSession();

		//セッションスコープがある場合、セッションスコープを破棄
		if (session != null) {
			session.invalidate();
		}

		//ログイン画面に遷移
		request.setAttribute("operation_message", "ログアウト");
		request.getRequestDispatcher("WEB-INF/view/login.jsp").forward(request, response);
	}
}
