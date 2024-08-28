// ToDoタスク情報を保持するBean

package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TodoList implements Serializable{
	private int task_id;
	private String task_name;
	private String task_contents;
	private LocalDate task_limitdate;
	private LocalDateTime task_update;
	private String task_user;
	private int task_status;
	
	//コンストラクタ
	public TodoList() {	}
	
	public TodoList(String task_name, String task_contents, 
			LocalDate task_limitdate,LocalDateTime task_update, String task_user,int task_status) {
		this.task_name = task_name;
		this.task_contents = task_contents;
		this.task_limitdate = task_limitdate;
		this.task_update = task_update;
		this.task_user = task_user;
		this.task_status = task_status;
	}
	public TodoList(int task_id, String task_name, String task_contents, 
			LocalDate task_limitdate, LocalDateTime task_update, String task_user,int task_status) {
		this.task_id = task_id;
		this.task_name = task_name;
		this.task_contents = task_contents;
		this.task_limitdate = task_limitdate;
		this.task_update = task_update;
		this.task_user = task_user;
		this.task_status = task_status;
	}
	
	//各フィールドのゲッター
	public void setTask_id(int task_id) { this.task_id = task_id; }
	public void setTask_name(String task_name) { this.task_name = task_name; }
	public void setTask_contents(String task_contents) { this.task_contents = task_contents; }
	public void setTask_limitdate(LocalDate task_limitdate) { this.task_limitdate = task_limitdate; }
	public void setTask_update(LocalDateTime task_update) { this.task_update = task_update; }
	public void setTask_user(String task_user) { this.task_user = task_user; }
	public void setTask_(int task_status) { this.task_status = task_status; }
	
	//各フィールドのセッター
	public int getTask_id() { return task_id; }
	public String getTask_name() { return task_name; }
	public String getTask_contents() { return task_contents; }
	public LocalDate getTask_limitdate() { return task_limitdate; }
	public LocalDateTime getTask_update() { return task_update; }
	public String getTask_user() { return task_user; }
	public int getTask_status() { return task_status; }
	
	
	//リスト画面の表示用にtask_update(LocalDateTime)を省略表示
	public String omitTask_update() {
	return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(getTask_update());
	}
	
	//各画面表示の為に、task_status(int型)を対応する文字に変換して返す
	public String convertTask_status() {
	
		if(task_status == 0) {
			return "未完";
		}else if(task_status == 1) {
			return "完了";
		}
		return "";
	}
}
