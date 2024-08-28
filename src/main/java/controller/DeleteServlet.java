package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.TodoDAO;


@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	
	//tododetail.jsp > this > todolist.jsp
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		//タスクIDを受け取ってDBのタスクIDの行内容を削除
		int task_id = Integer.parseInt(request.getParameter("task_id"));
		
		try {
			
		//TodoDAOインスタンス作成(DB接続)
		TodoDAO dao = new TodoDAO();
		//DELETE文で削除
		dao.todoDelete(task_id);
		
		} catch (Exception e) {
			//何らかの理由で失敗したらリスト画面にエラー文を渡してリスト画面に遷移。
			e.printStackTrace();
			request.setAttribute("error_message", "削除失敗　" + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/view/tododetail.jsp").forward(request, response);
		}
		
		//TodolistServletにてリスト取得して遷移
		HttpSession session = request.getSession();
		session.setAttribute("operation_message", "タスク削除成功");
		request.getRequestDispatcher("/TodolistServlet").forward(request, response);
	}

}
