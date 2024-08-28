package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Login;
import model.TodoDAO;
import model.TodoList;
import model.TodoLogic;

/**
 * Servlet implementation class InsertServlet
 */
@WebServlet("/InsertServlet")
public class InsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//menu.jsp > this > todocreate.jsp
	//todolist.jsp > this > todocreate.jsp
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/view/todocreate.jsp");
		dispatcher.forward(request, response);
	}

	//todocreate.jsp > this > todolist.jsp
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String task_name = request.getParameter("task_name");
		String task_contents = request.getParameter("task_contents");
		String task_limitdate = request.getParameter("task_limitdate");
		String task_user = request.getParameter("task_user");
		int task_status = Integer.parseInt(request.getParameter("task_status"));

		//入力の不正を判定するクラスのインスタンス
		TodoLogic todoLogic = new TodoLogic();

		//判定内容メッセージの抽出
		String error_message = todoLogic.task_check(task_name, task_contents, task_limitdate, task_user, task_status);

		//エラーメッセージが一文字でもあれば、新規作成画面を再表示
		if (error_message.length() > 0) {
			request.setAttribute("error_message", error_message);
			request.getRequestDispatcher("WEB-INF/view/todocreate.jsp").forward(request, response);

		} else {

			//エラーメッセージがなければINSERT文でDBにデーター登録後、タスク一覧に遷移
			try {
				//入力した日時をString型からLocalDate型に変換
				LocalDate parse_limitdete = LocalDate.parse(task_limitdate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				//現在時刻を取得
				LocalDateTime dataTime_now = LocalDateTime.now();
				//受け取ったパラメーターと現在時刻をbeanにセット
				TodoList todolist = new TodoList(task_name, task_contents, parse_limitdete, dataTime_now, task_user,
						task_status);

				//セッションスコープオブジェクトの取得
				HttpSession session = request.getSession();
				Login loginUser = (Login) session.getAttribute("loginUser");

				//TodoDAOインスタンス作成(DB接続)
				TodoDAO dao = new TodoDAO();
				//DBにINSERT文で追加
				dao.todoCreate(loginUser, todolist);

			} catch (Exception e) {
				//何らかの理由で失敗したらエラー文を渡して新規作成画面を再表示
				e.printStackTrace();
				request.setAttribute("error_message", "新規作成失敗　");
				request.getRequestDispatcher("/WEB-INF/view/todocreate.jsp").forward(request, response);
			}

			//TodolistServletにてリスト取得してタスク一覧に遷移
			HttpSession session = request.getSession();
			session.setAttribute("operation_message", "タスク作成成功");
			request.getRequestDispatcher("TodolistServlet").forward(request, response);
		}
	}

}
