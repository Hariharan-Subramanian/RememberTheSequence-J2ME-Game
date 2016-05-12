
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.io.*;
import javax.microedition.media.*;
import javax.microedition.rms.*;

public class RemMIDlet extends MIDlet {
     public Display display;
    public GamePlay canvas;
     public MainMenu mainView;
    public RemMIDlet() {
        //canvas=new CarParkingCanvas(this);
        mainView = new MainMenu(this);
        display = Display.getDisplay(this);
        //int a=(int)mainView.duration;
        //display.flashBacklight(a);
        
    }

    public void startApp() {
          //display.setCurrent(canvas);
         display.setCurrent(mainView);
     }
     public void pauseApp() {
     }
     public void destroyApp(boolean unconditional) {
         mainView.setSettings();
     }
     public void exit(){
          destroyApp(true);
          notifyDestroyed();
     }
}

