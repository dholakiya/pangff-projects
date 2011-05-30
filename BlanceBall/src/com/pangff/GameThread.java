package com.pangff;



public class GameThread extends Thread{
	GameView father;		//GameView对象引用
	Ball ball ;
	int sleepSpan = 25;		//休眠时间
	boolean flag = false;	//外层循环
	public GameThread(GameView gameView) {
		// TODO Auto-generated constructor stub
		this.flag = true;
		this.father = gameView;
		this.ball = father.ball;
	}

	//检查移动小球
	public void checkAndMoveBall() {
		switch(ball.direction){
		case 0:				//方向向上
			ball.ballY -= ball.velocity;		//Y方向上的移动
			if(checkCollision()){					//检测是否发生碰撞
				ball.ballY += ball.velocity;	//若发生碰撞就撤销移动
			}
			break;
		case 1:				//方向右上
			ball.ballY -= ball.velocity;			//Y方向上的移动
			if(checkCollision()){						//检测是否发生碰撞
				ball.ballY += ball.velocity;		//若发生碰撞就撤销移动
			}
			ball.ballX += ball.velocity;			//X方向上的移动
			if(checkCollision()){						//检测是否发生碰撞
				ball.ballX -= ball.velocity;		//若发生碰撞就撤销移动
			}
			break;
		case 2:				//方向向右
			ball.ballX += ball.velocity;			//X方向上的移动
			if(checkCollision()){						//检测是否发生碰撞
				ball.ballX -= ball.velocity;		//若发生碰撞就撤销移动
			}
			break;
		case 3:				//方向右下
			ball.ballX += ball.velocity;			//X方向上的移动
			if(checkCollision()){						//检测是否发生碰撞
				ball.ballX -= ball.velocity;		//若发生碰撞就撤销移动
			}
			ball.ballY += ball.velocity;			//Y方向上的移动
			if(checkCollision()){						//检测是否发生碰撞
				ball.ballY -= ball.velocity;		//若发生碰撞就撤销移动
			}
			break;
		case 4:				//方向向下
			ball.ballY += ball.velocity;			//Y方向上的移动
			if(checkCollision()){						//检测是否发生碰撞
				ball.ballY -= ball.velocity;		//若发生碰撞就撤销移动
			}
			break;
		case 5:				//方向左下
			ball.ballX -= ball.velocity;			//X方向上的移动
			if(checkCollision()){						//检测是否发生碰撞
				ball.ballX += ball.velocity;		//若发生碰撞就撤销移动
			}
			ball.ballY += ball.velocity;			//Y方向上的移动
			if(checkCollision()){						//若发生碰撞就撤销移动
				ball.ballY -= ball.velocity;		//若发生碰撞就撤销移动
			}
			break;
		case 6:				//方向向左
			ball.ballX -= ball.velocity;			//X方向上的移动
			if(checkCollision()){						//若发生碰撞就撤销移动
				ball.ballX += ball.velocity;		//若发生碰撞就撤销移动
			}
			break;
		case 7:				//方向左上
			ball.ballX -= ball.velocity;			//X方向上的移动
			if(checkCollision()){						//若发生碰撞就撤销移动
				ball.ballX += ball.velocity;		//若发生碰撞就撤销移动
			}
			ball.ballY -= ball.velocity;			//Y方向上的移动
			if(checkCollision()){						//若发生碰撞就撤销移动
				ball.ballY += ball.velocity;		//若发生碰撞就撤销移动
			}
			break;
		}
	}
	
	public boolean checkCollision(){
		return false;
	}
	
	//线程执行方法
	public void run(){
		while(flag){
				//移动小球
				checkAndMoveBall();
				try{
					Thread.sleep(sleepSpan);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}			
			
	}
}
