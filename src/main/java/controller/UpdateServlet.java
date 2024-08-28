package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.TodoDAO;
import model.TodoList;
import model.TodoLogic;

@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//todolist.jsp > this > tododetail.jsp
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		int task_id = Integer.parseInt(request.getParameter("task_id"));

		//編集画面に渡すためのBeanクラス
		TodoList todoList = null;
		
		try {
			
			TodoDAO dao = new TodoDAO();
			//IDを指定してタスクを取得
			todoList = dao.getTodo(task_id);

		} catch (Exception e) {
			//何らかの理由で失敗したらtodolist.jspにエラー文を渡してタスク一覧に遷移。
			e.printStackTrace();
			request.setAttribute("error_message", "task_id" + task_id + "取得失敗　");
			request.getRequestDispatcher("/WEB-INF/view/todolist.jsp").forward(request, response);
		}

		//ID指定して取得したTODOをスコープにセットして編集画面に遷移
		request.setAttribute("todoList", todoList);
		request.getRequestDispatcher("WEB-INF/view/tododetail.jsp").forward(request, response);
	}

	//tododetail.jsp > this > todolist.jsp
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		//fromの内容を受け取ってSQLのタスクIDの内容をUPDATEする
		int task_id = Integer.parseInt(request.getParameter("task_id"));
		String task_name = request.getParameter("task_name");
		String task_contents = request.getParameter("task_contents");
		String task_limitdate = request.getParameter("task_limitdate");
		String task_user = request.getParameter("task_user");
		int task_status = Integer.parseInt(request.getParameter("task_status"));

		//入力の不正を判定するクラスのインスタンス
		TodoLogic todologic = new TodoLogic();
		//判定内容メッセージの抽出
		String error_message = todologic.task_check(task_name, task_contents, task_limitdate, task_user, task_status);

		//エラーメッセージが一文字でもあれば、前画面に遷移
		if (error_message.length() > 0) {
			System.out.println("更新失敗" + error_message);
			
			//編集前と同じタスク内容を返す
			try {
			TodoList todoList = new TodoList();
			
			TodoDAO dao = new TodoDAO();
			
			//IDを指定してタスクを取得
			todoList = dao.getTodo(task_id);
			
			request.setAttribute("todoList", todoList);
			request.setAttribute("error_message", error_message);
			request.getRequestDispatcher("WEB-INF/view/tododetail.jsp").forward(request, response);
			} catch (Exception e) {
				//何らかの理由で失敗したらタスク一覧にエラー文を渡して遷移。
				e.printStackTrace();
				request.setAttribute("error_message", "更新失敗　");
				request.getRequestDispatcher("/WEB-INF/view/todolist.jsp").forward(request, response);
			}
			
		}

		//エラーメッセージがなければUPDATE文でDB内のデーター更新後、タスク一覧に遷移
		try {
			//入力した日時をString型からLocalDate型に変換
			LocalDate parse_limitdete = LocalDate.parse(task_limitdate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			//現在時刻を取得
			LocalDateTime datatime_now = LocalDateTime.now();
			//受け取ったパラメーターと現在時刻をbeanにセット
			TodoList todolist = new TodoList(task_id, task_name, task_contents, parse_limitdete, datatime_now,
					task_user,task_status);
			
			//TodoDAOインスタンス作成(DB接続)
			TodoDAO dao = new TodoDAO();
			//DBにUPDATE文でタスクを追加
			dao.todoUpdate(todolist);

		} catch (Exception e) {
			//何らかの理由で失敗したら前画面にエラー文を渡して遷移。
			e.printStackTrace();
			request.setAttribute("error_message", "更新失敗　");
			request.getRequestDispatcher("/WEB-INF/view/todolist.jsp").forward(request, response);
		}
		
		//TodolistServletにてタスクリスト取得して遷移
		HttpSession session = request.getSession();
		session.setAttribute("operation_message", "タスク更新成功");
		request.getRequestDispatcher("/TodolistServlet").forward(request, response);
	}

}
