package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Login;
import model.TodoDAO;
import model.TodoList;

/**
 * Servlet implementation class OrderbyServlet
 */
@WebServlet("/OrderbyServlet")
public class OrderbyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		int orderby_number = Integer.parseInt(request.getParameter("orderby_number"));
		
		try {

			//セッションスコープオブジェクトの取得
			HttpSession session = request.getSession();
			Login loginUser = (Login)session.getAttribute("loginUser");
			
			//TodoDAOインスタンス作成(DB接続)
			TodoDAO dao = new TodoDAO();
			
			//DBからSELECT文でリスト全要素を取得
			ArrayList<TodoList> todoList = dao.orderBy(loginUser,orderby_number);

			//リスト画面に遷移
			request.setAttribute("todoList", todoList);
			request.setAttribute("message", "リスト取得成功");
			request.getRequestDispatcher("/WEB-INF/view/todolist.jsp").forward(request, response);

		} catch (Exception e) {
			//何らかの理由で失敗したらエラー文を渡してタスク一覧に遷移。
			e.printStackTrace();
			request.setAttribute("error_message", "リスト取得失敗　");
			request.getRequestDispatcher("/WEB-INF/view/todolist.jsp").forward(request, response);
		}

	}
}
