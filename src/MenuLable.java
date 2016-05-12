import java.io.IOException;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class MenuLable{
    int x;
    int y;
    int width;
    int height;
    
    static final byte RECTANGLE=1;
    static final byte SPRITE=2;
    byte buttonState=0;
    byte collisionType=RECTANGLE;//SPRITE;
    
    Image imgButton[];
    Sprite pointerSprite;
    
    int buttonColor[]={0x000000,0xff0000};
    
    String name="button";
    int nameColor=0x000000;
    int nameX,nameY;
    static byte noOfButton;
    
    boolean isMovable;
    int touchX,touchY;
    
    public void setXY(int x,int y){
        this.x=x;
        this.y=y;
    }
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }
    public void setWidth(int width){
        this.width=width;
    }
    public void setHeight(int height){
        this.height=height;
    }
    
    public MenuLable(){
         imgButton=new Image[2];
    }
    public MenuLable(int x,int y,int width,int height,int color1,int color2){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.buttonColor[0]=color1;
        this.buttonColor[1]=color2;
        collisionType=RECTANGLE;
        noOfButton++;
        name="button"+noOfButton;
        nameX=x+5;
        nameY=y+5;
    }
    
    public MenuLable(int x,int y,String imagePath1,String imagePath2){
        this.x=x;
        this.y=y;
        if(imgButton==null){
            imgButton=new Image[2];
        }
        try {
            imgButton[0]=Image.createImage(imagePath1);
        } catch (Exception ex) {
        }
        try {
            imgButton[1]=Image.createImage(imagePath2);
        } catch (Exception ex) {
        }
        if(imgButton[1]==null && imgButton[0]!=null){
            imgButton[1]=imgButton[0];
        }
        if(imgButton[0]!=null){
            this.width=imgButton[0].getWidth();
            this.height=imgButton[0].getHeight();
        }else if(imgButton[1]!=null){
            this.width=imgButton[1].getWidth();
            this.height=imgButton[1].getHeight();
        }
        collisionType=RECTANGLE;//SPRITE;
        noOfButton++;
        name="button"+noOfButton;
    }
    public MenuLable(int x,int y,Image imagePath1,Image imagePath2){
        this.x=x;
        this.y=y;
        if(imgButton==null){
            imgButton=new Image[2];
        }
        try {
            imgButton[0]=imagePath1;
        } catch (Exception ex) {
        }
        try {
            imgButton[1]=imagePath2;
        } catch (Exception ex) {
        }
        if(imgButton[1]==null && imgButton[0]!=null){
            imgButton[1]=imgButton[0];
        }
        if(imgButton[0]!=null){
            this.width=imgButton[0].getWidth();
            this.height=imgButton[0].getHeight();
        }else if(imgButton[1]!=null){
            this.width=imgButton[1].getWidth();
            this.height=imgButton[1].getHeight();
        }
        collisionType=RECTANGLE;//SPRITE;
        noOfButton++;
        name="button"+noOfButton;
    }
    
    public void setBeforePressed(String imagePath1){
        if(imgButton==null){
            imgButton=new Image[2];
        }
        try {
            imgButton[0]=Image.createImage(imagePath1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.width=imgButton[0].getWidth();
        this.height=imgButton[0].getHeight();
    }
    public void setAfterPressed(String imagePath2){
        if(imgButton==null){
            imgButton=new Image[2];
        }
        try {
            imgButton[1]=Image.createImage(imagePath2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void setCollisionType(byte type){
        collisionType=type;
    }
    public void setMovable(boolean mov){
        isMovable=mov;
    }
    public boolean isPressed(int x1,int y1){
        boolean result=false;
        if(buttonState==0){
            if(collisionType==SPRITE && imgButton!=null){
                if(pointerSprite==null){
                    pointerSprite=new Sprite(Image.createImage(2,2));
                }
                pointerSprite.setPosition(x1,y1);
                if(imgButton[0]!=null){
                    result=(pointerSprite.collidesWith(imgButton[0],x,y,false));
                }else{
                    result=(pointerSprite.collidesWith(imgButton[1],x,y,false));
                }
            }else { //RECTANGLE
                result=(x1>x && x1<x+width && y1>y && y1<y+height);
            }
            if(result){
                buttonState=1;
                touchX=x1;
                touchY=y1;
            }
        }
        return result;
    }
    public boolean isReleased(int x1,int y1){
         if(buttonState==1){ //already pressed
              buttonState=0;
              isPressed(x1,y1);
              if(buttonState==1){
                   buttonState=0;
                   return true;
              }else{
                   return false;
              }
         }else{
              return false;
         }
    }
    public void isDragged(int x1,int y1){
        if(isMovable){
            if(buttonState==1){
                x+=(x1-touchX);
                y+=(y1-touchY);
                nameX+=(x1-touchX);
                nameY+=(y1-touchY);
                touchX=x1;
                touchY=y1;
            }
        }
    }
    public void paint(Graphics g){
        if(imgButton!=null){
            if(imgButton[buttonState]!=null){
               g.drawImage(imgButton[buttonState],x,y,g.TOP|g.LEFT);
              //  g.drawImage(imgButton[buttonState],x,y,g.TOP|g.HCENTER);

            }
        }else{
             if(buttonColor[buttonState]!=-1){
                  g.setColor(buttonColor[buttonState]);
                  g.fillRect(x,y,width,height);
             }
            g.setColor(nameColor);
            g.drawString(name,nameX,nameY,g.TOP|g.LEFT);
        }
    }
    public void paint(Graphics g,int x,int y){
        if(imgButton!=null){
            if(imgButton[buttonState]!=null){
                g.drawImage(imgButton[buttonState],x,y,g.TOP|g.LEFT);
                //g.drawImage(imgButton[buttonState],x,y,g.TOP|g.HCENTER);

            }
        }else{
             if(buttonColor[buttonState]!=-1){
                  g.setColor(buttonColor[buttonState]);
                  g.fillRect(x,y,width,height);
             }
            g.setColor(nameColor);
            g.drawString(name,nameX,nameY,g.TOP|g.LEFT);
        }
    }
}