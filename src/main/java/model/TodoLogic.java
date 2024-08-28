//ToDoタスク詳細画面の入力チェックを行う
package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TodoLogic {

	public String task_check(String task_name, String task_contents, String task_limitdate, String task_user,
			int task_status) {

		//判定が当たればmessage変数に追加していく
		String message = "";

		//タスク名称 空白はエラー
		if (task_name.length() == 0) {
			message += "タスク名称が空白です<br>";
		}
		//タスク名称 50文字以内
		if (task_name.length() > 50) {
			message += "タスク名称が長すぎます<br>";
		}

		//タスク内容 空白はエラー
		if (task_contents.length() == 0) {
			message += "タスク内容が空白です<br>";
		}
		//タスク内容 100文字以内
		if (task_contents.length() > 100) {
			message += "タスク内容が長すぎます<br>";
		}

		//タスク期限 空白はエラー
		if (task_limitdate.length() == 0) {
			message += "タスク期限が空白です<br>";
		} else {
			//現在日時取得	
			LocalDate date_now = LocalDate.now();
			//入力した日時をString型からLocalDate型に変換
			LocalDate limit_dete = LocalDate.parse(task_limitdate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			//存在しない日付の場合DateTimeParseExceptionが発生するので。例外ハンドリングでエラーメッセージを追加

			//タスク状況が"未着手"(0)または"着手"(1)で、今日より前の日付はエラー
			if (date_now.isAfter(limit_dete) && task_status == 0) {
				message += "タスク期限が過去日付です<br>";
			}
		}

		//タスク担当者 空白はエラー
		if (task_user.length() == 0) {
			message += "タスク担当者が空白です<br>";

		}
		//タスク担当者 100文字以内
		if (task_user.length() > 20) {
			message += "タスク担当者が長すぎます<br>";
		}

		return message;
	}
}
