package com.example.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {

	public Card(Context context) {
		super(context);
		
		label = new TextView(getContext());
		label.setTextSize(32);
		
		label.setBackgroundColor(0x33ffffff);
		//把放在控件里的文本居中处理
		label.setGravity(Gravity.CENTER);
		
		LayoutParams lp = new LayoutParams(-1,-1);  //填满整个富集容器
		//给每个textView的左和上设置margin，右和下就不需要了
        lp.setMargins(10, 10, 0, 0);
		addView(label, lp);
		
		setNum(0);
	}
	
	private TextView label;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
		if(num<=0){
			label.setText("");
		}
		else{
		
			label.setText(num+"");
		
		}
	}
	private int num = 0;
	public boolean equals(Card o){
		return getNum()==o.getNum();
	}
	
	
	

}
