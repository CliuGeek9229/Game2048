package com.example.game2048;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class GameView extends GridLayout {

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		InitView();
	}

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		InitView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		InitView();
		
	}
	private void InitView(){
		//由于在添加完卡片之后发现在GameView里创建的16个卡片都排成一行了
        //因此在这里设置有多少列
        /**
         * ColumnCount is used only to generate default column/column indices 
         * when they are not specified by a component's layout parameters.
         */
        setColumnCount(4);

        //在给GameView的背景颜色加上颜色
        setBackgroundColor(0xffbbada0);

        //如何创建手势操作呢？
		setOnTouchListener(new View.OnTouchListener() {
			private float startX,startY,offsetX,offsetY;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = event.getX() - startX;
					offsetY = event.getY() - startY;
					
					if(Math.abs(offsetX)>Math.abs(offsetY)){
						if(offsetX<-5){
							swipLeft();
						}
						else if(offsetY>5){
							swipRight();
						}
					}
					else if(Math.abs(offsetY)>Math.abs(offsetX)){
						if(offsetY<-5){
							swipUp();
									
						}
						else if(Math.abs(offsetY)>5){
							swipDown();
						}
					}
					break;
					
				}
				
				return true;
			}
		});
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
		int cardWidth = (Math.min(w, h)-10)/4;
		addCards(cardWidth,cardWidth);
		startGame();
	}
	
	 private void addCards(int cardWidth, int cardHeight) {
	        Card c;
	        for(int i = 0;i<4;i++)
	        	for(int j = 0;j<4;j++){
	        		c = new Card(getContext());
	        		c.setNum(2);
	        		addView(c,cardWidth,cardHeight);
	        		cardsMap[j][i] = c;
	        	}
	    }

	private void swipLeft(){
//        Toast.makeText(getContext(), "向左滑动了", 0).show();
		//Toast.makeText(getContext(), "向左滑动了", 0).show();
	    //以下两行for循环实现了一行一行的遍历，在向左滑动的时候
		boolean merge = false;//控制每滑动一次画面就加一个随机数到画面，也就是在下面所有for循环之后
		
	    for (int y = 0; y < 4; y++) {
	        for (int x = 0; x < 4; x++) {

	            for (int x1 = x+1; x1 < 4; x1++) {
	                //这是在水平上固定了一个格子之后再继续在水平上遍历别的格子，且当格子有数的时候进行以下的操作
	                if (cardsMap[x1][y].getNum()>0) {
	                    //现在判断被固定的格子有没有数字，如果没有数字就进行以下的操作
	                    if (cardsMap[x][y].getNum()<=0) {
	                        //把与被固定的格子的同一水平上的格子的数字赋给被固定的格子
	                        cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
	                        //把值赋给被固定的格子后自己归零
	                        cardsMap[x1][y].setNum(0);
	                        //第二层循环，即同一层的不同列退一格继续循环，这样做的原因是为了继续固定这个格子而去检查与它同一水平上的别的格子的内容，因为其他格子是什么个情况还需要继续在第二层进行判断
	                        x--;
	                        //这个break为了在操作完这固定格子遍历的过程操作完后跳出遍历，因为只要有操作这个条件，就没有继续遍历下去的需要了
	                       merge = true;
	                        // break;

	                    }else if (cardsMap[x][y].equals(cardsMap[x1][y])) {//这层判断是判断相邻两个数相同的情况
	                        cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
	                        cardsMap[x1][y].setNum(0);
	                        MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
	                        merge = true;
	                        //break;
	                    }
	                    break;
	                }
	            }
	        }
	    }
	    if (merge) {
            addRandomNum();
            checkComplete();
        }
	    
    }
    private void swipRight(){
//        Toast.makeText(getContext(), "向右滑动了", 0).show();
    	boolean merge = false;
    	 for (int y = 0; y < 4; y++) {
    	        for (int x = 4-1; x >=0; x--) {

    	            for (int x1 = x-1; x1 >=0; x1--) {
    	                if (cardsMap[x1][y].getNum()>0) {

    	                    if (cardsMap[x][y].getNum()<=0) {
    	                        cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
    	                        cardsMap[x1][y].setNum(0);
    	                        x++;
    	                        merge = true;
    	                    }else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
    	                        cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
    	                        cardsMap[x1][y].setNum(0);
    	                        //break;
    	                        MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
    	                        merge = true;
    	                    }break;
    	                }
    	            }
    	        }
    	    }
    	 if (merge) {
             addRandomNum();
             checkComplete();
         }
    }
    private void swipUp(){
//        Toast.makeText(getContext(), "向上滑动了", 0).show();
    	boolean merge = false;
    	for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {

                for (int y1 = y+1; y1 < 4; y1++) {
                    if (cardsMap[x][y1].getNum()>0) {

                        if (cardsMap[x][y].getNum()<=0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            merge = true;
                            
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            //break;
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }break;
                    }
                }
            }
        }
    	if (merge) {
            addRandomNum();
            checkComplete();
        }
    }
    private void swipDown(){
//        Toast.makeText(getContext(), "向下滑动了", 0).show();
    	boolean merge = false;
    	for (int x = 0; x < 4; x++) {
            for (int y = 4-1; y >=0; y--) {

                for (int y1 = y-1; y1 >=0; y1--) {
                    if (cardsMap[x][y1].getNum()>0) {

                        if (cardsMap[x][y].getNum()<=0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);

                            y++;
                            merge = true;
                            //break;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }break;

                    }
                }
            }
        }
    	if (merge) {
            addRandomNum();
            checkComplete();
        }
    }
	
    
    private void startGame(){
        //清理阶段：初始化阶段
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0);

            }
        }
        //然后就是添加随机数阶段
        addRandomNum();
        addRandomNum();
    }
    private void addRandomNum(){
        //把这个point清空，每次调用添加随机数时就清空之前所控制的指针
        emptyPoints.clear();
        

        //对所有的位置进行遍历：即为每个卡片加上了可以控制的指针
        for(int y = 0;y<4;y++){
            for (int x = 0; x < 4;x++) {
                if(cardsMap[x][y].getNum()<=0){
                    emptyPoints.add(new Point(x,y));//给List存放控制卡片用的指针（通过坐标轴来控制）
                }
            }
        }
        //一个for循环走完我们就从List里取出一个控制指针的point对象
        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        //
        cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);//通过point对象来充当下标的角色来控制存放card的二维数组cardsMap，然后随机给定位到的card对象赋值
    }
    /*
     * 
     * 方法放在滑动手势里，每次滑动是用来判断每个格子的上下左右没有相同的数字，和格子是否为空，以此弹出对话框来提示用户并调用重新开始方法
     */
    private void checkComplete(){

        boolean complete = true;

        ALL:
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {//遍历格子
                    if (cardsMap[x][y].getNum()==0||
                            (x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
                            (x<4-1&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
                            (y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
                            (y<4-1&&cardsMap[x][y].equals(cardsMap[x][y+1]))) {

                        complete = false;
                        break ALL;
                    }
                }
            }

        if (complete) {//通过标记来判断当前格子是否满足条件，满足则弹出对话框，并点击按钮后激活开始方法
            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }

    }

    
    
	private Card[][] cardsMap = new Card[4][4];
	private List<Point> emptyPoints = new ArrayList<Point>();
}
