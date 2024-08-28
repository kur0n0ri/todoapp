//ToDoタスク一覧処理を行うサーブレット menu.jsp → this →　todolist.jsp
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

@WebServlet("/TodolistServlet")
public class TodolistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//todo一覧をセッションに登録してtodolist画面に遷移する
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		try {
			//セッションスコープオブジェクトの取得
			HttpSession session = request.getSession();
			Login loginUser = (Login)session.getAttribute("loginUser");
			
			//TodoDAOインスタンス作成(DB接続)
			TodoDAO dao = new TodoDAO();
			//DBからSELECT文でリスト全要素を取得
			ArrayList<TodoList> todoList = dao.getList(loginUser);

			//リスト画面に遷移
			request.setAttribute("todoList", todoList);
			request.setAttribute("message", "リスト取得成功");
			request.getRequestDispatcher("/WEB-INF/view/todolist.jsp").forward(request, response);

		} catch (Exception e) {
			//何らかの理由で失敗したらエラー文を渡してタスク一覧に遷移。
			e.printStackTrace();
			request.setAttribute("message", "リスト取得失敗　");
			request.getRequestDispatcher("/WEB-INF/view/todolist.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//InsertServlet,DeleteServlet,UpdateServletの処理完了後にthis.doGet()でリスト取得してタスク一覧に遷移
		doGet(request,response);
	}
}
