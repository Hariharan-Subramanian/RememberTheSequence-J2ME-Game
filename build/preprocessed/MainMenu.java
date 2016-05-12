/*
 * MainMenu.java
 *
 * Created on April 12, 2011, 11:08 AM
 *
 * @version 0.0.1
 */

import java.io.InputStream;
import java.util.Random;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.media.*;
import javax.microedition.media.control.VideoControl;
import javax.microedition.media.control.VolumeControl;
import javax.microedition.rms.RecordStore;

public class MainMenu extends GameCanvas implements Runnable {
    //public static MainMIDlet midlet;

    public static RemMIDlet midlet;
    public Thread gameThread;
    public static int WIDTH, HEIGHT, GAME_WIDTH, GAME_HEIGHT, GAME_MODE;
    public static long startTime, duration;
    private static Random rand;
    boolean showInfo;
    private static int KEY = 0, counter = 0;
    public long sTime, time, pauseTime;
    private static boolean isPaused;
    private static final int KEY_LEFT_SOFT = -6, KEY_RIGHT_SOFT = -7;
    public static byte TOP_LEFT = 20;
    public MenuLable buttonHome;
    //MODES
    public static final byte Comp_LOGO = 0;
    public static final byte GAME_SPLASH = 1;
    public static final byte GAME_MENU = 6; //2
    public static final byte GAME_PLAY = 7; //3
    //GAME_MENU IMAGES
    public static Image imgComplogo;
    public static Image imgSplash;
    public static Image imgGameMenuBg;
    //public GamePlay gameView;
    public GamePlay canvas;
    public SubMenu menuView;
    public boolean vibrateEnabled = true, soundEnabled = true;
    private Player mp0;

    public MainMenu(RemMIDlet m) {
        super(false);
        setFullScreenMode(true);
        midlet = m;
        rand = new Random(System.currentTimeMillis());

        WIDTH = 400;
        HEIGHT = 240;

        GAME_WIDTH = 400;
        GAME_HEIGHT = 240;

        resetGameMode(Comp_LOGO);

        //gameView  = new GamePlay(this);
        canvas = new GamePlay(this);
        menuView = new SubMenu(this);

        getSettings();

        startThread();
    }

    private void startThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void showGameView() {
        int previousMode = GAME_MODE;
        GAME_MODE = GAME_PLAY;
        //canvas.resetHomeButton();
        buttonHome.setXY(GAME_WIDTH-(buttonHome.width+4), 3);
        //canvas.reset();
        counter = 0;
    }

    public void showMenu(boolean hasContinue) {
        menuView.hasContinue = false;
        int previousMode = GAME_MODE;
        GAME_MODE = GAME_MENU;
        menuView.loadImages();
        counter = 0;
    }

    public void resetGameMode(int toMode) {
        int previousMode = GAME_MODE;
        GAME_MODE = toMode;
        counter = 0;
        switch (GAME_MODE) {
            case Comp_LOGO:
                try {
                    imgComplogo = Image.createImage("/res/nextwavelogo.png");
                    buttonHome = new MenuLable(0, 0, "/res/home0.png", "/res/home1.png");
                      buttonHome.setXY(GAME_WIDTH-buttonHome.width, 3);
                    
                   
                } catch (Exception e) {
                }
                break;
            case GAME_SPLASH:
                try {
                    imgComplogo = null;
                    imgSplash = Image.createImage("/res/splash.png");
                    imgGameMenuBg = Image.createImage("/res/menu-screen.png");
                   
                } catch (Exception e) {
                }
                break;
            case GAME_MENU:
                if (menuView.butNewGame == null) {
                    menuView.reset(false);
                } else {
                    menuView.reset(true);
                }
                break;
            case GAME_PLAY:
                //canvas.resetMode(GAME_MODE);
                canvas.reset();
                break;
        }
    }

    public void playSound() {
        if (soundEnabled) {
            try {
                if (mp0 == null) {
                    InputStream Is0 = getClass().getResourceAsStream("/res/hit.mid");
                    mp0 = Manager.createPlayer(Is0, "audio/mid");
                    mp0.prefetch();
                }
                if (mp0.getState() == Player.STARTED) {
                    mp0.stop();
                }
                mp0.start();
            } catch (Exception e) {
            }
        }
    }

    public void vibrate() {
        if (vibrateEnabled) {
            Display.getDisplay(midlet).vibrate(300);
        }
    }

    protected void hideNotify() {
        if (GAME_MODE == GAME_PLAY) {
            menuView.hasContinue = true;
            resetGameMode(6);
        }
    }

    public final void run() {
        Thread currentThread = Thread.currentThread();
        while (gameThread == currentThread) {
            startTime = System.currentTimeMillis();
            try {
                counter++;
                switch (GAME_MODE) {
                    case Comp_LOGO:
                        if (counter > 50) {
                            resetGameMode(GAME_SPLASH);

                        }
                        break;
                    case GAME_SPLASH:
                        if (counter > 50) {
                            resetGameMode(GAME_MENU);
                        }
                        break;
                    case GAME_MENU:
                        menuView.run();
                        //resetGameMode(GAME_PLAY);
                        break;
                    case GAME_PLAY:
                        canvas.run();
                        //gameView.run();
                        break;
                }
                repaint();
                duration = System.currentTimeMillis() - startTime;
                midlet.display.flashBacklight(200);
                if (duration < 25L) {
                    Thread.sleep(25L - duration);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void paint(Graphics g) {
        if (gameThread != null) {
            switch (GAME_MODE) {
                case Comp_LOGO:
                    g.setColor(0xffffff);
                    g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                    g.drawImage(imgComplogo, ((400 - 160) >> 1), ((240 - 85) >> 1), TOP_LEFT);

                    break;
                case GAME_SPLASH:
                    g.drawImage(imgSplash, 0, 0, g.TOP | g.LEFT);
                    break;
                case GAME_MENU:
                    g.drawImage(imgGameMenuBg, 0, 0, g.TOP | g.LEFT);
                    menuView.paint(g);
                    break;
                case GAME_PLAY:
                    canvas.paint(g);
                    break;
            }
        }
    }

    protected void keyPressed(int keyCode) {
        switch (GAME_MODE) {
            case GAME_MENU:
                menuView.keyPressed(keyCode);
                break;
            case GAME_PLAY:
                canvas.keyPressed(keyCode);
                break;
        }
    }

    protected void keyReleased(int keyCode) {
        switch (GAME_MODE) {
            case GAME_MENU:
                menuView.keyReleased(keyCode);
                break;
            case GAME_PLAY:
                //canvas.keyReleased(keyCode);
                break;
        }
    }

    protected void keyRepeated(int keyCode) {
        switch (GAME_MODE) {
            case GAME_MENU:
                menuView.keyRepeated(keyCode);
                break;
            case GAME_PLAY:
                //canvas..keyRepeated(keyCode);
                break;
        }
    }

    public void pointerPressed(int x, int y) {
        switch (GAME_MODE) {
            case GAME_MENU:
                menuView.pointerPressed(x, y);
                break;
            case GAME_PLAY:
                canvas.pointerPressed(x, y);
                break;
        }
    }

    public void pointerReleased(int x, int y) {
        switch (GAME_MODE) {
            case GAME_MENU:
                menuView.pointerReleased(x, y);
                break;
            case GAME_PLAY:
                canvas.pointerReleased(x, y);
                break;
        }
    }

    public void pointerDragged(int x, int y) {
        switch (GAME_MODE) {
            case GAME_PLAY:
                //canvas.pointerDragged(x,y);
                break;
        }
    }
    // Supporting variable and methods
    public final short sinValue[] = {
        0, 286, 572, 857, 1143, 1428, 1713, 1997, 2280, 2563, 2845, 3126, 3406, 3686, 3964,
        4240, 4516, 4790, 5063, 5334, 5604, 5872, 6138, 6402, 6664, 6924, 7182,
        7438, 7692, 7943, 8192, 8438, 8682, 8923, 9162, 9397, 9630, 9860, 10087,
        10311, 10531, 10749, 10963, 11174, 11381, 11585, 11786, 11982, 12176,
        12365, 12551, 12733, 12911, 13085, 13255, 13421, 13583, 13741, 13894,
        14044, 14189, 14330, 14466, 14598, 14726, 14849, 14968, 15082, 15191,
        15296, 15396, 15491, 15582, 15668, 15749, 15826, 15897, 15964, 16026,
        16083, 16135, 16182, 16225, 16262, 16294, 16322, 16344, 16362, 16374, 16382, 16384
    };
    public final short[] tanValue = {
        0, 18, 36, 54, 72, 90, 108, 126, 144, 162, 181, 199, 218, 236, 255, 274, 294, 313, 333, 353, 373, 393, 414, 435, 456, 477, 499, 522, 544, 568, 591, 615, 640, 665, 691, 717, 744, 772, 800, 829, 859, 890, 922, 955, 989, 1024
    };

    public int sin(int degree) {
        if (degree < 0) {
            degree = (degree % 360) + 360;
        } else if (degree > 360) {
            degree %= 360;
        }
        return ((degree < 91 ? sinValue[degree] : (degree < 181 ? sinValue[180 - degree] : (degree < 271 ? -sinValue[degree - 180] : -sinValue[360 - degree]))));
    }

    public int cos(int degree) {
        return sin(90 - degree);
    }

    public int distance(int ox, int oy, int tx, int ty) {
        int ang = angle(ox, oy, tx, ty);
        int temp = ((ang + 45) % 360) / 90, dist = 0;
        if (temp == 0 || temp == 2) {
            dist = ((tx - ox) << 14) / cos(ang);
        } else {
            dist = ((ty - oy) << 14) / sin(ang);
        }
        return Math.abs(dist);
    }

    public int angle(int ox, int oy, int tx, int ty) {
        int dx = Math.abs(tx - ox), dy = Math.abs(ty - oy), result = 0;
        int ratio = (dx >= dy) ? ((dy << 10) / dx) : ((dx << 10) / dy);
        switch (ratio) {
            case 0:
                result = ratio;
                break;
            case 1024:
                result = 45;
                break;
            default:
                if (ratio < 181) {
                    result = 1;
                } else if (ratio < 353) {
                    result = 10;
                } else if (ratio < 544) {
                    result = 19;
                } else if (ratio < 772) {
                    result = 28;
                } else {
                    result = 37;
                }
                for (; ratio > tanValue[result]; result++) {
                }
        }
        result = (dx >= dy) ? result : 90 - result;
        if (tx < ox) {
            result = 180 - result;
        }
        if (ty < oy) {
            result = 360 - result;
        }
        return result;
    }

    public void getSettings() {
        try {
            RecordStore rs = RecordStore.openRecordStore("SETTING", true);
            byte[] set = {1, 1};
            if (rs.getNumRecords() == 0) {
                rs.addRecord(set, 0, set.length);
                soundEnabled = true;
                vibrateEnabled = true;
            } else {
                set = rs.getRecord(1);
                soundEnabled = (set[0] == 1);
                vibrateEnabled = (set[1] == 1);
            }
            rs.closeRecordStore();
            rs = null;
        } catch (Exception e) {
        }
    }

    public void setSettings() {
        try {
            RecordStore rs = RecordStore.openRecordStore("SETTING", true);
            byte[] set = {(byte) (soundEnabled ? 1 : 0), (byte) (vibrateEnabled ? 1 : 0)};
            rs.setRecord(1, set, 0, set.length);
            rs.closeRecordStore();
            rs = null;
        } catch (Exception e) {
        }
    }
}
