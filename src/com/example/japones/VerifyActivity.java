package com.example.japones;


public class VerifyActivity {
 
	private int question;
	private int answer;
		
	public VerifyActivity(int question, int answer) {
		super();
		this.question = question;
		this.answer = answer;
	}

	public boolean verifyAnswer(){
		return (question == answer);
	}
}
