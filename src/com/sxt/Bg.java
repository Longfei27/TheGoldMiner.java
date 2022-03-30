package src.com.sxt;

import java.awt.*;

public class Bg {

    //level
    static int level = 1;
    //goal
    int goal = level*5;
    //Score
    static int count = 0;
    //water
    static int waterNum = 3;
    //water state ; True: working
    static boolean waterFlag = false;
    //start time
    long startTime;
    //end time
    long endTime;
    //water price
    int price = (int) (Math.random()*10);
    //Enter Store?  False not
    boolean shop = false;


    Image bkg = Toolkit.getDefaultToolkit().getImage("imgs/bkg.jpg");
    Image sky = Toolkit.getDefaultToolkit().getImage("imgs/sky.jpg");
    Image people = Toolkit.getDefaultToolkit().getImage("imgs/people.png");
    Image water = Toolkit.getDefaultToolkit().getImage("imgs/water.png");



    void paintSelf(Graphics g){
        g.drawImage(sky, 0,0,null);
        g.drawImage(bkg, 0,200,null);
        switch (GameWin.state){
            case 0:
                drawWord(g,80,Color.green,"ready",200,400);
                break;
            case 1:
                g.drawImage(people, 310,50,null);
                drawWord(g,30,Color.black,"Score: "+count,30,150);
                g.drawImage(water, 450,40,null);
                drawWord(g,30,Color.black,"*"+waterNum,510,70);
                drawWord(g,20,Color.black,level+"level",30,60);
                drawWord(g,30,Color.black,"Goal"+goal,30,110);
                endTime = System.currentTimeMillis();
                long tim = 20-(endTime-startTime)/1000;
                drawWord(g,30,Color.black,"Time"+(tim>0?tim:0),520,150);
                break;
            case 2:
                g.drawImage(water, 300,400,null);
                drawWord(g,30,Color.black,"Price: "+price,300,500);
                drawWord(g,20,Color.black,"Buy?",300,550);
                if(shop){
                    count = count-price;
                    waterNum++;
                    shop=false;
                    GameWin.state=1;
                    startTime=System.currentTimeMillis();
                }
                break;
            case 3:
                drawWord(g,80,Color.cyan,"Game Over",250,250);
                drawWord(g,80,Color.cyan,"Score: "+count,250,450);

                break;
            case 4:
                drawWord(g,80,Color.red,"You Win",250,250);
                drawWord(g,80,Color.red,"Score: "+count,250,450);
                break;
            default:
        }
    }
    //True time end    False counting
    boolean gameTime(){
        long tim = (endTime-startTime)/1000;
        if(tim>20){return true;}
        return false;
    }

    //reset element
    void reGame(){
        //level
        int level = 1;
        //goal
        goal = level*5;
        //Score
        count = 0;
        //water
        waterNum = 3;
        //water state ; True: working
        waterFlag = false;
    }

    public static void drawWord(Graphics g,int size,Color color,String str,int x,int y){
        g.setColor(color);
        g.setFont(new Font("仿宋",Font.BOLD,size));
        g.drawString(str,x,y);
    }
}
