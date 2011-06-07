package com.pangff;



public class GameThread extends Thread{
	double current;	//记录当前时间
	double currentVD = -1;
	GameView father;		//GameView对象引用
	Ball ball ;
	int sleepSpan = 25;		//休眠时间
	boolean flag = false;	//外层循环
	public GameThread(GameView gameView) {
		// TODO Auto-generated constructor stub
		this.flag = true;
		this.father = gameView;
		father.ballTime = System.nanoTime();
		this.ball = father.ball;
	}
	
	/**
	 * 改变y速度
	 * @param timeSpan
	 */
	private void changeVy(double timeSpan , int derection){
		//如果
		ball.vy +=  ball.ay*timeSpan;
			
		currentVD = derection;
	}
	/**
	 * 改变x速度
	 * @param timeSpan
	 */
	private void changeVx(double timeSpan){
		ball.vx +=  ball.ax*timeSpan;
	}
	
	
	/**
	 * 碰撞速度取反y
	 */
	private void collisionVy(){
		ball.vy +=  -ball.vy;
	}
	
	/**
	 * 碰撞速度取反x
	 */
	private void collisionVx(){
		ball.vx +=  -ball.vx;
	}
	
	/**
	 * 改变x位移
	 * @param timeSpan
	 */
	private void changeSx(double timeSpan,int derection){
		if(derection==0){
			ball.ballX += ball.vx*timeSpan+0.5*ball.ax*timeSpan*timeSpan;
		}else{
			ball.ballX -= ball.vx*timeSpan+0.5*ball.ax*timeSpan*timeSpan;
		}
			
	}
	
	/**
	 * 改变y位移
	 * @param timeSpan
	 */
	private void changeSy(double timeSpan,int derection){
		if(derection==0){
			ball.ballY+= ball.vy*timeSpan+0.5*ball.ay*timeSpan*timeSpan;
		}else{
			ball.ballY -= ball.vy*timeSpan+0.5*ball.ay*timeSpan*timeSpan;
		}
	}
	

	//检查移动小球
	public void checkAndMoveBall() {
		current = System.nanoTime();//获取当前时间，单位为纳秒
		double timeSpan = (double)((current-father.ballTime)/1000/1000/100);//获取从玩家开始到现在水平方向走过的时间
		
		ball.ballX = (int) (ball.vx*timeSpan + (0.5*ball.ax*timeSpan*timeSpan));
		ball.vx = ball.vx + ball.ax*timeSpan;
		
		
		ball.ballY = (int) (ball.vy*timeSpan + (0.5*ball.ay*timeSpan*timeSpan));
		ball.vy = ball.vy + ball.ay*timeSpan;
	}
	
	public boolean checkCollision(){
		int top_x = father.getLeft();
		int top_y = father.getTop();
		int bottom_x = father.getRight();
		int bottom_y = father.getBottom();
		if(ball.ballX<=top_x){
			return true;
		}
		if(ball.ballX<=top_x){
			return true;
		}
		if(ball.ballY<=top_y){
			return true;
		}
		if(ball.ballX+ball.bmpBall.getWidth()>=bottom_x){
			return true;
		}
		if(ball.ballY+ball.bmpBall.getHeight()>=bottom_y){
			return true;
		}
		return false;
	}
	
	//线程执行方法
	public void run(){
		while(flag){
		//移动小球
		checkAndMoveBall();
		father.ballTime = System.nanoTime();
		try{
			Thread.sleep(sleepSpan);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		}
			
	}
}
