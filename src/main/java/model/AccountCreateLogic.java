package model;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AccountCreateLogic {

	public String accaunt_check(String user_id, String user_pass) {
		//判定が当たればmessage変数に追加していく
		String message = "";
		
		//ユーザーID 空白はエラー
		if (user_id.length() == 0) {
			message += "ユーザーIDが空白です<br>";
		}else if (user_id.length() < 8) {
		//ユーザーID 8文字以上
		message += "ユーザーIDが短すぎます(8文字以上)<br>";
		}else if (user_id.length() > 64) {
		//ユーザーID 64文字以内
		message += "ユーザーIDが長すぎます(64文字以内)<br>";
		}
		
		//ユーザーID 半角英数のみ
		if(!(Pattern.matches("^[0-9a-zA-Z]*$",user_id))) {
			message += "ユーザーIDに半角英数字以外が含まれています<br>";
		}
		
		//すでに存在指定しているユーザーIDを探す
		try {
			//TodoDAOインスタンス作成(DB接続)
			TodoDAO dao = new TodoDAO();
			//DBからパラメーターと一致するユーザーを探してArrayListに入れる
			ArrayList<String> userList = dao.findByAccount(user_id);

			if (userList.size() >= 1) {
				message += "同じアカウント名が存在しています:"+ user_id +"<br>";
			}

		} catch (Exception e) {
			e.printStackTrace();
			message += "アカウント名探索に失敗しました<br>";
		}

		//パスワード 空白はエラー
		if (user_pass.length() == 0) {
			message += "パスワードが空白です<br>";
		}else if (user_id.length() < 8) {
		//ユーザーID 8文字以上
		message += "パスワードが短すぎます(8文字以上)<br>";
		}else if (user_pass.length() > 64) {
		//パスワード 64文字以内
		message += "パスワードが長すぎます(64文字以内)<br>";
		}
		
		//パスワード 半角英数のみ
		if(!(Pattern.matches("^[0-9a-zA-Z]*$",user_pass))) {
			message += "ユーザーIDに半角英数字以外が含まれています:"+ user_pass +"<br>";
		}
		
		return message;
	}
}
