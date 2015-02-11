package com.example.japones;

public class ResultData {

private int lesson;
	private String curse;
	private int questWin = 0;
	private int questLose = 0;
	private JapaneseAdapter mDbHelper;

	public ResultData(int lesson, String curse) {
		super();
		this.lesson = lesson;
		this.curse = curse;
		mDbHelper = new JapaneseAdapter(null);  
	}

	public int getLesson() {
		return lesson;
	}

	public void setLesson(int lesson) {
		this.lesson = lesson;
	}

	public String getCurse() {
		return curse;
	}

	public void setCurse(String curse) {
		this.curse = curse;
	}

	public int getQuestWin() {
		return questWin;
	}

	public void setQuestWin(int questWin) {
		this.questWin = questWin;
	}

	public int getQuestLose() {
		return questLose;
	}

	public void setQuestLose(int questLose) {
		this.questLose = questLose;
	}

	public void incrementWin (){
		this.questWin ++;
	}
	
	public void incrementLose(){
		this.questLose ++;
	}
	
	public void insertBD (){
		mDbHelper.open();
		mDbHelper.insertStadistics(this);
	}
}
