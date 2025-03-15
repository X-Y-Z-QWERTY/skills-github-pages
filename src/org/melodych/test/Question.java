package org.melodych.test;

public class Question {
    public static int totalQuests = 0;	//total number of questions
    private final int questNum;			//primary key
    public String query;			//query text
    public String[] answers;		//answer's text (4, from 0 to 3)
    private final int correctAnswer;		//correct answer, from 0 to 3
    private int userAnswer;			//current user's answer
    public Question (int questNum, String query, String[] answers, int correctAnswer) {			//constructor
        totalQuests++;											//increase the total number of questions
        this.questNum = questNum;
        this.query = query;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.userAnswer = -1;										//-1 if there's no userAnswer
    }
    public int getQuestNum () {return questNum;}
    public int getCorrectAnswer () {return correctAnswer;}
    public void setUserAnswer (int usAnsw) {userAnswer = usAnsw;}
    public int getUserAnswer () {return userAnswer;}
    // equals() and hashcode()  have to override?
}
