package src.com.sxt;

import java.awt.*;

public class Line {
    //Line start
    int x = 380;
    int y = 180;

    //end
    int endx = 500;
    int endy = 500;

    //line length
    double length = 100;
    double MIN_length = 100;
    double MAX_length = 750;

    double n = 0;

    //way
    int dir = 1;

    //state 0 waving 1 grab 2 take 3 back
    int state;

    //hook
    Image hook = Toolkit.getDefaultToolkit().getImage("imgs/hook.png");

    GameWin frame;

    Line(GameWin frame){this.frame = frame;}

    //
    void logic(){
        for(Object obj:this.frame.objectList){
            if(endx>obj.x && endx<obj.x+obj.width
                    && endy>obj.y && endy<obj.y+obj.height){
                state = 3;
                obj.tag = true;
            }
        }
    }

    void drawLine(Graphics g){
        endx = (int) (x + length*Math.cos(n*Math.PI));
        endy = (int) (y + length*Math.sin(n*Math.PI));
        g.setColor(Color.red);
        g.drawLine(x-1,y,endx-1,endy);
        g.drawLine(x,y,endx,endy);
        g.drawLine(x+1,y,endx+1,endy);
        g.drawImage(hook,endx-36,endy-2,null);
    }

    void paintSelf(Graphics g){

        logic();

        switch (state){
            case 0:
                if(n < 0.1){dir = 1;}
                else if(n > 0.9){dir = -1;}
                n = n + 0.005*dir;
                drawLine(g);
                break;
            case 1:
                if(length < MAX_length) {
                    length = length + 5;
                    drawLine(g);
                }else {state = 2;}
                break;
            case 2:
                if(length > MIN_length) {
                    length = length - 5;
                    drawLine(g);
                }else {state = 0;}
                break;
            case 3:
                int m = 1;
                if(length > MIN_length) {
                    length = length - 2;
                    drawLine(g);
                    for(Object obj: frame.objectList){
                        if(obj.tag){
                            m = obj.m;
                        obj.x = endx-obj.getWidth()/2;
                        obj.y = endy;
                        if(length<=MIN_length) {
                            obj.x = -150;
                            obj.y = -150;
                            obj.tag = false;
                            Bg.waterFlag = false;
                            // add score
                            Bg.count += obj.count;
                            state = 0;
                        }
                        if(Bg.waterFlag){
                            if(obj.type==1){m=1;}
                            if(obj.type==2){
                                obj.x = -150;
                                obj.y = -150;
                                obj.tag = false;
                                Bg.waterFlag = false;
                                state = 2;
                            }
                        }
                        }
                    }
                }
                try {
                    Thread.sleep(m);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
                default:
        }
    }
    void reGame(){
        n=0;
        length=100;
    }
}
