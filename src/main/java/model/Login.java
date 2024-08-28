//ログイン情報を保持する

package model;

import java.io.Serializable;

public class Login implements Serializable{
	private String user_id;
	private String user_pass;
	private int login_flag; 
	
	//コンストラクタ
	public Login() {}
	
	public Login(String user_id) {
		this.user_id = user_id;
	}
	
	public Login(String user_id,String user_pass) {
		this.user_id = user_id;
		this.user_pass = user_pass;
	}
	
	// ゲッターとセッター(ユーザID)
	public void setUser_id(String user_id) { this.user_id = user_id; }
	public String getUser_id() { return user_id; }
	
	// ゲッターとセッター（パスワード）
	public void setUser_pass(String user_pass) { this.user_pass = user_pass; }
	public String getUser_pass() { return user_pass; }
	
	// ゲッターとセッター（ログイン判定フラグ）
	public void setLogin_flag(int login_flag) { this.login_flag = login_flag; }
	public int getLogin_fiag() { return login_flag; }
}
