package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AccountCreateLogic;
import model.Login;
import model.TodoDAO;

@WebServlet("/NewAccountServlet")
public class NewAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//新規アカウント作成画面に遷移
		request.getRequestDispatcher("/WEB-INF/view/newaccount.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//newaccount.jspからpost(userID,password)を受け取る
		request.setCharacterEncoding("UTF-8");
		String user_id = request.getParameter("user_id");
		String user_pass = request.getParameter("user_pass");

		//入力の不正を判定するクラスのインスタンス
		AccountCreateLogic logic = new AccountCreateLogic();

		//判定内容メッセージの抽出
		String error_message = logic.accaunt_check(user_id, user_pass);
		
		System.out.println(error_message);
		//エラーメッセージが一文字でもあれば、新規アカウント作成画面を再表示
		if (error_message.length() > 0) {
			request.setAttribute("error_message", error_message);
			request.getRequestDispatcher("WEB-INF/view/newaccount.jsp").forward(request, response);
			return;
		}

		try {
			//Postパラメーターをbeanインスタンスにセットする
			Login login = new Login(user_id, user_pass);

			//TodoDAOインスタンス作成(DB接続)
			TodoDAO dao = new TodoDAO();

			//DBからパラメーターと一致するユーザーを探してArrayListに入れる
			dao.accountCreate(login);

			//登録成功したらログイン画面に遷移
			request.setAttribute("operation_message", "新規アカウント作成成功");
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);

		} catch (Exception e) {
			//何らかの理由で失敗したらエラー文を渡して新規アカウント作成画面を再表示
			e.printStackTrace();
			request.setAttribute("error_message", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/view/newaccount.jsp").forward(request, response);
		}
	}

}
