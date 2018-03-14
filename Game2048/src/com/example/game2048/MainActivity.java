package com.example.game2048;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tvScore;
	private int score = 0;
	
	//提供一个单例设计模式给别的类去调用该类中的处理分数的方法
    private static MainActivity mainActivity = null;
    public MainActivity(){
        mainActivity = this;
    }
    public static MainActivity getMainActivity(){
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tvScore = (TextView) findViewById(R.id.tvScore);
    }
    public void scoreClear(){
    	score = 0;
    	showScore();
    }
    public void showScore(){
    	tvScore.setText(score+"");
    }
    public void addScore(int s){
    	score+=s;
    	showScore();
    }
    
}
