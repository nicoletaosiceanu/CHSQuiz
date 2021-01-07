package com.example.chsquiz;

public class Question {
    public String question, option1, option2, option3, option4, answer;

    public Question(String question, String option1, String option2, String option3, String option4, String answer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
    }

    public Question(){}
    public String returneazaIntrebare() {
        return question;
    }
    public String returneazaOptiunea1() {
        return option1;
    }
    public String returneazaOptiunea2() {
        return option2;
    }
    public String returneazaOptiunea3() {
        return option3;
    }
    public String returneazaOptiunea4() {
        return option4;
    }
    public String getAnswer() {
        return answer;
    }

}

