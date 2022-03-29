import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class PinBall {
    private  Frame frame=new Frame("弹球游戏");

    private final int TABLE_WIDTH=300;
    private final  int TABLE_HEIGHT=400;

    private final int RACKET_WIDTH=60;
    private final  int RACKET_HEIGHT=20;

    private final int BALL_SIZE=16;
//坐标
    private int ballX=120;
    private int ballY=20;
    //速度
    private int speedX=10;
    private int speedY=5;

    //球拍坐标
    private int racketX=120;
    private final int racketY=340;

    //标识当前游戏是否结束
    private boolean isOver=false;
    //定时器
    private Timer timer;
    //画布
    private class MyCanvas extends Canvas{
        @Override
        public void paint(Graphics g) {
            if (isOver) {
                //游戏结束
                g.setColor(Color.BLUE);
                g.setFont(new Font("Times",Font.BOLD,30));
                g.drawString("游戏结束",80,200);

            } else {
                //游戏中

                g.setColor(Color.RED);
//                g.fillOval(ballX,ballY,BALL_SIZE,BALL_SIZE);
                g.fillOval(ballX,ballY,BALL_SIZE,BALL_SIZE);

                g.setColor(Color.PINK);
                g.fillRect(racketX,racketY,RACKET_WIDTH,RACKET_HEIGHT);

            }
        }
    }

MyCanvas drawArea=new MyCanvas();

    public void init(){


        KeyListener listener=new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int keyCode=e.getKeyCode();
                if(keyCode==KeyEvent.VK_LEFT){
                    if(racketX>0){
                    racketX-=20;}
                }
                if(keyCode==KeyEvent.VK_RIGHT){
                    if (racketX<TABLE_WIDTH-RACKET_WIDTH){
                racketX+=20;}
                }
            }
        };
frame.addKeyListener(listener);
        drawArea.addKeyListener(listener);
ActionListener task=new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        if(ballX<=0||ballX>=(TABLE_WIDTH-BALL_SIZE)){
            speedX=-speedX;
        }
        if(ballY<=0||(ballY>=racketY-BALL_SIZE&&ballX>racketX&&ballX<racketX+RACKET_WIDTH)){
            speedY=-speedY;
        }
        if (ballY>racketY+BALL_SIZE&&(ballX<racketX||ballX>racketX-RACKET_WIDTH)){
            //停止定时器
            timer.stop();
            isOver=true;
            drawArea.repaint();
        }
        //更新小球坐标，重绘制界面
        ballX+=speedX;
        ballY+=speedY;
        drawArea.repaint();
    }
};
        timer=new Timer(100,task);
        timer.start();
        drawArea.setPreferredSize(new Dimension(TABLE_WIDTH,TABLE_HEIGHT));
        frame.add(drawArea);
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new PinBall().init();
    }
}
