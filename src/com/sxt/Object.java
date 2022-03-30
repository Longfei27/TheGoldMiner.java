package src.com.sxt;

import java.awt.*;

public class Object {
    //coordinates
    int x;
    int y;

    //width height
    int width;
    int height;

    //image
    Image img;

    //tag
    boolean tag;

    //quan
    int m;

    //score
    int count;

    //type1 gold or 2 rock
    int type;

    void paintSelf(Graphics g){
        g.drawImage(img,x,y,null);
    }

    public int getWidth() {
        return width;
    }

    public Rectangle getRec(){
        return new Rectangle(x,y,width,height);
    }
}
