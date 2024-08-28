//ログイン処理を行うサーブレット
package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Login;
import model.TodoDAO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//start > this > login.jsp 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//login画面に遷移するだけ
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/view/login.jsp");
		dispatcher.forward(request, response);
	}

	//login.jsp > this > menu.jsp
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//login.jspからpost(userID,password)を受け取る
		request.setCharacterEncoding("UTF-8");
		String user_id = request.getParameter("user_id");
		String user_pass = request.getParameter("user_pass");

		try {
			//Postパラメーターをbeanインスタンスにセットする
			Login login = new Login(user_id, user_pass);

			//TodoDAOインスタンス作成(DB接続)
			TodoDAO dao = new TodoDAO();

			//DBからパラメーターと一致するユーザーを探してArrayListに入れる
			ArrayList<Login> userList = dao.findByLogin(login);

			// ユーザ情報なし、もしくは複数件の場合はログイン画面に戻る
			if (userList == null || userList.size() != 1) {
				// ログインフラグを立てる(1：NG）
				Login flag = new Login();
				flag.setLogin_flag(1);

				//NGフラグをリクエストスコープに渡して、ログイン画面に遷移
				request.setAttribute("result", flag);
				request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			
			} else {

				// ユーザ情報を保持するためのBeanクラスのインスタンス
				Login loginUser = new Login();

				// ユーザIDとユーザ名をインスタンスフィールドにセットする
				loginUser.setUser_id(userList.get(0).getUser_id());
				loginUser.setUser_pass(userList.get(0).getUser_pass());

				// ユーザー情報をセッションスコープに登録して、MenuSevletにメニュー画面に遷移
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", loginUser);
				session.setAttribute("operation_message", "ログイン成功");
				request.getRequestDispatcher("MenuServlet").forward(request, response);
				
			}

		} catch (Exception e) {
			//何らかの理由で失敗したらログイン画面にエラー文を渡してログイン画面に遷移
			e.printStackTrace();
			request.setAttribute("error_message", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
		}
	}
}
