//ToDoタスク管理データベースへのアクセスを行う

package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TodoDAO {

	// TODO 自動生成されたメソッド・スタブ
	private final String JDBC_URL = "jdbc:postgresql://localhost:5432/todotask";
	private final String DB_USER = "postgres";
	private final String DB_PASS = "sql";

	//---------------------------------------------------------------------
	//新規アカウント登録(INSERT文)
	
	public void accountCreate(Login login) throws SQLException{
		//JDBCドライバを読み込む
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// エラーハンドリング
			System.out.println("JDBCドライバ関連エラー");
			e.printStackTrace();
		}

		// データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			// INSERT文の準備
			String sql = "INSERT INTO login (user_id,user_pass) VALUES (?,?)";;
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			pStmt.setString(1,login.getUser_id());
			pStmt.setString(2,login.getUser_pass());
			
			//INSERT文を実行
			pStmt.executeUpdate();

		} catch (SQLException e) {
			// エラーハンドリング
			System.out.println("新規アカウント作成　INSERT文　実行失敗");
			e.printStackTrace();
		}
	}
	
	//---------------------------------------------------------------------
	//ログイン時アカウント探索操作(SELECT文)
	
	public ArrayList<Login> findByLogin(Login login) throws SQLException {

		// 複数のユーザ情報を格納するため、Beanを格納する配列を作成
		ArrayList<Login> resultList = new ArrayList<>();

		//JDBCドライバを読み込む
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// エラーハンドリング
			System.out.println("JDBCドライバ関連エラー");
			e.printStackTrace();
		}

		// データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			// SELECT文の準備
			String sql = "SELECT user_id,user_pass FROM login WHERE user_id = ? AND user_pass = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, login.getUser_id());
			pStmt.setString(2, login.getUser_pass());

			// SELECTを実行
			ResultSet rs = pStmt.executeQuery();

			// SELECT文の結果をArrayListに格納
			while (rs.next()) {
				String user_id = rs.getString("user_id");
				String user_pass = rs.getString("user_pass");
				Login userInfo = new Login(user_id, user_pass);
				resultList.add(userInfo);
			}

		} catch (SQLException e) {
			// エラーハンドリング
			System.out.println("ログイン　SELECT文　実行失敗");
			e.printStackTrace();

		}
		//取得したユーザーデーターを含むArrayListを返す
		return resultList;
	}
	
	//---------------------------------------------------------------------
	//アカウント作成時重複確認操作(SELECT文)
		
		public ArrayList<String> findByAccount(String user_id) throws SQLException {

			// 複数のユーザ情報を格納するため、Beanを格納する配列を作成
			ArrayList<String> resultList = new ArrayList<>();

			//JDBCドライバを読み込む
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				// エラーハンドリング
				System.out.println("JDBCドライバ関連エラー");
				e.printStackTrace();
			}

			// データベース接続
			try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

				// SELECT文の準備
				String sql = "SELECT user_id FROM login WHERE user_id = ?";
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setString(1, user_id);

				// SELECTを実行
				ResultSet rs = pStmt.executeQuery();

				// SELECT文の結果をArrayListに格納
				while (rs.next()) {
					String getUser_id = rs.getString("user_id");
					resultList.add(getUser_id);
				}

			} catch (SQLException e) {
				// エラーハンドリング
				System.out.println("ログイン　SELECT文　実行失敗");
				e.printStackTrace();

			}
			//取得したユーザーデーターを含むArrayListを返す
			return resultList;
		}

	//------------------------------------------------------------------------
	//Todoリスト取得(SELECT文)
	
	public ArrayList<TodoList> getList(Login login) throws SQLException {

		// 複数のtodoを格納するため、Beanを格納する配列を作成
		ArrayList<TodoList> get_todolist = new ArrayList<>();

		//JDBCドライバを読み込む
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// エラーハンドリング
			System.out.println("JDBCドライバ関連エラー");
			e.printStackTrace();
		}

		// データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			// SELECT文の準備(デフォルトの表示順番は期限 (降順))
			String sql = "SELECT * FROM todolist WHERE user_id = ? ORDER BY task_status, task_limitdate DESC";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,login.getUser_id());
			
			//SELECT文を実行して、結果を代入する
			ResultSet results = pStmt.executeQuery();
			
			
			while (results.next()) {
				int task_id = results.getInt("task_id");
				String task_name = results.getString("task_name");
				String task_contents = results.getString("task_contents").replaceAll("\n", "<br>");
				LocalDate task_limitdate = results.getDate("task_limitdate").toLocalDate();
				LocalDateTime task_update = results.getTimestamp("task_update").toLocalDateTime();
				String task_user = results.getString("task_user");
				int task_status = results.getInt("task_status");

				//DBから読み込んだタスクデータをTodoListのインスタンスフィールドにセット
				TodoList todo = new TodoList(task_id, task_name, task_contents, task_limitdate, task_update, task_user,
						task_status);
				//postを仮引数のlistに追加していく。
				get_todolist.add(todo);
			}

		} catch (SQLException e) {
			// エラーハンドリング
			System.out.println("リスト取得　SELECT文　実行失敗");
			e.printStackTrace();

		}
		//取得したタスクデーターを含むArrayListを返す
		return get_todolist;
	}
	
	//------------------------------------------------------------------------
	//並び替えてTodoリスト取得(SELECT文+ORDER BY句)
	
	public ArrayList<TodoList> orderBy(Login login, int orderby_number) throws SQLException {

			// 複数のtodoを格納するため、Beanを格納する配列を作成（戻り値）
			ArrayList<TodoList> get_todolist = new ArrayList<>();

			//JDBCドライバを読み込む
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				// エラーハンドリング
				System.out.println("JDBCドライバ関連エラー");
				e.printStackTrace();
			}

			// データベース接続
			try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

				//SELECT文の準備
				String sql = "";
	
				//選択したoptionに割り振られた数値によってSQL文を選択
				switch (orderby_number) {
				case 1 : sql = "SELECT * FROM todolist WHERE user_id = ? ORDER BY task_limitdate ASC";
						break;
				case 2 : sql = "SELECT * FROM todolist WHERE user_id = ? ORDER BY task_limitdate DESC";
						break;
				case 3 : sql = "SELECT * FROM todolist WHERE user_id = ? ORDER BY task_status ASC ,task_limitdate DESC";
						break;
				case 4 : sql = "SELECT * FROM todolist WHERE user_id = ? ORDER BY task_status DESC ,task_limitdate DESC";
						break;
				}
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setString(1,login.getUser_id());
				
				//SELECT文を実行して、結果を代入する
				ResultSet results = pStmt.executeQuery();

				while (results.next()) {
					int task_id = results.getInt("task_id");
					String task_name = results.getString("task_name");
					String task_contents = results.getString("task_contents").replaceAll("\n", "<br>");
					LocalDate task_limitdate = results.getDate("task_limitdate").toLocalDate();
					LocalDateTime task_update = results.getTimestamp("task_update").toLocalDateTime();
					String task_user = results.getString("task_user");
					int task_status = results.getInt("task_status");

					//DBから読み込んだタスクデーターをTodoListのインスタンスフィールドにセット
					TodoList todo = new TodoList(task_id, task_name, task_contents, task_limitdate, task_update, task_user,
							task_status);
					//取得したタスクをArrayListに追加していく。
					get_todolist.add(todo);
				}

			} catch (SQLException e) {
				// エラーハンドリング
				System.out.println("リスト取得　SELECT文　実行失敗");
				e.printStackTrace();

			}
			//取得したタスクデーターを含むArrayListを返す
			return get_todolist;
		}
	
	//------------------------------------------------------------------------
	//IDを指定して一つのtodoを取得(SELECT文)
		
	public TodoList getTodo(int id) throws SQLException {

		TodoList get_todo = null;

		//JDBCドライバを読み込む
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// エラーハンドリング
			System.out.println("JDBCドライバ関連エラー");
			e.printStackTrace();
		}

		// データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			// SELECT文の準備
			String sql = "SELECT * FROM todolist WHERE task_id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1,id);
			
			//SELECT文を実行して、結果を代入する
			ResultSet results = pStmt.executeQuery();
			
			while (results.next()) {
				int task_id = results.getInt("task_id");
				String task_name = results.getString("task_name");
				String task_contents = results.getString("task_contents").replaceAll("\n", "<br>");
				LocalDate task_limitdate = results.getDate("task_limitdate").toLocalDate();
				LocalDateTime task_update = results.getTimestamp("task_update").toLocalDateTime();
				String task_user = results.getString("task_user");
				int task_status = results.getInt("task_status");

				//DBから読み込んだタスク内容をTodoListのインスタンスフィールドにセット
				TodoList tem = new TodoList(task_id, task_name, task_contents, task_limitdate, task_update, task_user,
						task_status);
				//返り値用のインスタンスに代入
				get_todo = tem;
			}

		} catch (SQLException e) {
			// エラーハンドリング
			System.out.println("単体取得　SELECT文　実行失敗");
			e.printStackTrace();

		}
		// 取得したタスクデーターをセットしたインスタンスを返す
		return get_todo;
	}

	//------------------------------------------------------------------------
	//タスク新規登録(INSERT文)

	public void todoCreate(Login login ,TodoList todoList) throws SQLException {

		//JDBCドライバを読み込む
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// エラーハンドリング
			System.out.println("JDBCドライバ関連エラー");
			e.printStackTrace();
		}

		// データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			// INSERT文の準備
			String sql = "INSERT INTO todolist (task_name, user_id,task_contents,task_limitdate,task_update,task_user,task_status) VALUES (?,?,?,?,?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, todoList.getTask_name());
			pStmt.setString(2, login.getUser_id());
			pStmt.setString(3, todoList.getTask_contents());
			pStmt.setDate(4, Date.valueOf(todoList.getTask_limitdate()));
			pStmt.setTimestamp(5, Timestamp.valueOf(todoList.getTask_update()));
			pStmt.setString(6, todoList.getTask_user());
			pStmt.setInt(7, todoList.getTask_status());

			//INSERT文を実行
			pStmt.executeUpdate();

		} catch (SQLException e) {
			// エラーハンドリング
			System.out.println("タスク新規登録　INSERT文　実行失敗");
			e.printStackTrace();
		}
	}

	//------------------------------------------------------------------------
	//タスク編集(UPDATE文)

		public void todoUpdate(TodoList todoList) throws SQLException {

			//JDBCドライバを読み込む
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				// エラーハンドリング
				System.out.println("JDBCドライバ関連エラー");
				e.printStackTrace();
			}

			// データベース接続
			try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

				// UPDATE文の準備
				String sql = "UPDATE todolist SET task_name = ?, task_contents = ?, task_limitdate = ?, task_update = ?, task_user = ?, task_status = ? WHERE task_id = ?";
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setString(1, todoList.getTask_name());
				pStmt.setString(2, todoList.getTask_contents());
				pStmt.setDate(3, Date.valueOf(todoList.getTask_limitdate()));
				pStmt.setTimestamp(4, Timestamp.valueOf(todoList.getTask_update()));
				pStmt.setString(5, todoList.getTask_user());
				pStmt.setInt(6, todoList.getTask_status());
				pStmt.setInt(7,todoList.getTask_id());
				//UPDATE文を実行
				pStmt.executeUpdate();

			} catch (SQLException e) {
				// エラーハンドリング
				System.out.println("UPDATE文　実行失敗");
				e.printStackTrace();
			}
		}
		
		//------------------------------------------------------------------------
		//タスク削除(DELETE文)

			public void todoDelete(int task_id) throws SQLException {

				//JDBCドライバを読み込む
				try {
					Class.forName("org.postgresql.Driver");
				} catch (ClassNotFoundException e) {
					// エラーハンドリング
					System.out.println("JDBCドライバ関連エラー");
					e.printStackTrace();
				}

				// データベース接続
				try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

					// UPDATE文の準備
					String sql = "DELETE FROM todolist WHERE task_id = ?";
					PreparedStatement pStmt = conn.prepareStatement(sql);
					
					pStmt.setInt(1,task_id);
					//DELETE文を実行
					pStmt.executeUpdate();

				} catch (SQLException e) {
					// エラーハンドリング
					System.out.println("DELETE文　実行失敗");
					e.printStackTrace();
				}
			}
}
