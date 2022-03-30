package src.com.sxt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GameWin extends JFrame {

    //0 pre-start 1 playing 2 shop 3 fail 4 success
     static int state;
    //store gold and stone
    List<Object> objectList = new ArrayList<>();
    Bg bg = new Bg();
    Line line = new Line(this);

    {
        //
        boolean isPlace = true;
        for (int i = 0; i < 6; i++) {
            double random = Math.random();
            Gold gold;//store current gold
            if(random<0.3){gold = new GoldMini();}
            else if (random<0.7){gold = new Gold();}
            else {gold = new Gold3();}

            for(Object obj:objectList){
                if(gold.getRec().intersects(obj.getRec())){
                    isPlace = false;
                    i--;
                }
            }
            if(isPlace){objectList.add(gold);}
            else {isPlace = true;i--;}
        }
        for (int i = 0; i < 1; i++) {
            Rock rock = new Rock();
            for(Object obj:objectList){
                if(rock.getRec().intersects(obj.getRec())){
                    isPlace = false;
                    i--;
                }
            }
            if(isPlace){objectList.add(rock);}
            else {isPlace = true;i--;}
        }
    }

    Image offScreenImage;

    void launch(){
        this.setVisible(true);
        this.setSize(768,1000);
        this.setLocationRelativeTo(null);
        this.setTitle("The Gold Miner");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                switch (state){
                    case 0:
                        if(e.getButton()==3){
                            bg.startTime = System.currentTimeMillis();
                            state=1;
                        }
                        break;
                    case 1:
                    // rolling
                    if(e.getButton()==1&&line.state==0){line.state=1;}
                    // grab and back
                    if(e.getButton()==3&&line.state==3&&Bg.waterNum>0){
                        Bg.waterFlag=true;
                        Bg.waterNum--;
                    }
                    break;
                    case 2:
                        if(e.getButton()==1){
                            bg.shop=true;
                        }
                        if(e.getButton()==3){
                            state=1;
                            bg.startTime=System.currentTimeMillis();
                        }
                        break;
                    case 3:
                    case 4:
                        if(e.getButton()==1){
                            state=0;
                            bg.reGame();
                            line.reGame();
                        }
                        break;
                    default:
                }

            }
        });

        while (true){
            repaint();
            nextLevel();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //next level
    public void nextLevel(){
        if(bg.gameTime() && state ==1){
            if(Bg.count >= bg.goal){
                if(Bg.level==5){state=4;}
                else {
                    state=2;
                    Bg.level++;
                    }

            }else {state = 3;}
            dispose();
            GameWin gameWin = new GameWin();
            gameWin.launch();
        }
    }
    @Override
    public void paint(Graphics g) {
        offScreenImage = this.createImage(768,1000);
        Graphics gImage = offScreenImage.getGraphics();
        bg.paintSelf(gImage);
        if(state==1){
            for(Object obj:objectList){
                //绘制物体
                obj.paintSelf(gImage);
            }
            line.paintSelf(gImage);
        }
        g.drawImage(offScreenImage,0,0,null);
    }

    public static void main(String[] args) {
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }
}
