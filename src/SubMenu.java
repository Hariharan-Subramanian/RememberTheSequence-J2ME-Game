
import java.io.IOException;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Canvas;

public class SubMenu extends Canvas {

    /** Creates a new instance of SubMenu */
    public MainMenu MainClass;
    private static byte selectIndex;
    private static byte optionIndex;
    private static byte s = 1;
    private static byte s1;
    public Image menuText[] = new Image[4];
    public Image imgFont[] = new Image[2];
    public Image title;
    public boolean hasContinue = false, onoffs, onoffv, showoption = false;
    MenuLable butContinue, butNewGame, butOption, butHelp, butAbout, butExit;
    MenuLable butSoundon, butSoundoff, butVibrateon, butVibrateoff;
    MenuLable buttonHelpData;
    private static final byte MODE_MENU = 6;
    private static final byte MODE_OPTION = 2;
    private static final byte MODE_HELP = 3;
    private static final byte MODE_ABOUT = 4;
    private static final byte MODE_EXIT = 5;
    public static final byte KEY_LEFT_SOFT = -6, KEY_RIGHT_SOFT = -7;
    public int mode = MODE_MENU;
    public String helpString;
    Image help, option, about, helpText, aboutText, optionBg;
    Image imggameTitle, imgPot;
    int KEY = 0;

    public SubMenu(MainMenu view) {
        MainClass = view;
        try {

            imgFont[0] = Image.createImage("/res/font00.png");
            imgFont[1] = Image.createImage("/res/font2.png");
            helpText = Image.createImage("/res/helptext.png");
            aboutText = Image.createImage("/res/About text.png");
            optionBg = Image.createImage("/res/optionbg.png");
            imggameTitle = Image.createImage("/res/game name.png");
            imgPot = Image.createImage("/res/for game.png");;

        } catch (Exception e) {
        }


    }
    private final byte[] fontWidth = {
        3, 4, 9, 7, 12, 9, 3, 5, 5, 7, 9, 4, 5, 3, 5, 7,
        5, 7, 7, 7, 7, 7, 7, 7, 7, 3, 4, 8, 9, 8, 6, 11,
        8, 7, 8, 8, 8, 8, 8, 8, 5, 6, 7, 6, 9, 8, 9, 7,
        9, 8, 7, 7, 8, 7, 11, 7, 7, 7, 5, 5, 5, 9, 8, 4,
        7, 7, 6, 7, 7, 6, 7, 7, 3, 4, 7, 3, 9, 7, 7, 7,
        7, 5, 6, 5, 7, 7, 9, 7, 7, 6, 6, 3, 6, 9, 0, 0
    };

    public void drawStringBlk(String str, Graphics g, int xpos, int ypos) {
        int fnt, fontX = xpos;
        str = str.toUpperCase();
        byte cr[] = str.getBytes();
        for (int i = 0; i < cr.length; i++) {
            fnt = cr[i] - 33;
            switch (cr[i]) {
                case 32:
                    fontX += 3;
                    break;
                case 10:
                    ypos += 18;
                    fontX = xpos;
                    break;
                default:
                    g.setClip(fontX, ypos, fontWidth[fnt], 12);
                    g.drawImage(imgFont[1], fontX - (fnt >> 4) * 12, ypos - (fnt - ((fnt >> 4) << 4)) * 12, 20);
                    fontX += fontWidth[fnt];
                    break;
            }
        }
    }

    public void loadImages() {
        try {
            if (butOption == null) {
                butContinue = new MenuLable(0, 0, "/res/continue0.png", "/res/continue1.png");
                butNewGame = new MenuLable(0, 0, "/res/new0.png", "/res/new1.png");
                butOption = new MenuLable(0, 0, "/res/option0.png", "/res/option1.png");
                butHelp = new MenuLable(0, 0, "/res/help0.png", "/res/help1.png");
                butAbout = new MenuLable(0, 0, "/res/about0.png", "/res/about1.png");
                butExit = new MenuLable(300, 220, "/res/exit0.png", "/res/exit1.png");
            }

            if (hasContinue) {
                butContinue.setXY((MainClass.GAME_WIDTH - butContinue.width >> 1), (MainClass.GAME_HEIGHT - 268) >> 1);
                butNewGame.setXY((MainClass.GAME_WIDTH - butNewGame.width >> 1), butContinue.y + 45);
                butOption.setXY((MainClass.GAME_WIDTH - butOption.width >> 1), butNewGame.y + 45);
                butHelp.setXY((MainClass.GAME_WIDTH - butHelp.width >> 1), butOption.y + 45);
                butAbout.setXY((MainClass.GAME_WIDTH - butAbout.width >> 1), butHelp.y + 45);
                // butExit.setXY(butContinue.x,butAbout.y+35);
            } else {
                butNewGame.setXY((MainClass.GAME_WIDTH - butNewGame.width >> 1), (MainClass.GAME_HEIGHT - 268) >> 1);
                butOption.setXY((MainClass.GAME_WIDTH - butOption.width >> 1), butNewGame.y + 45);
                butHelp.setXY((MainClass.GAME_WIDTH - butHelp.width >> 1), butOption.y + 45);
                butAbout.setXY((MainClass.GAME_WIDTH - butAbout.width >> 1), butHelp.y + 45);
                // butExit.setXY(butNewGame.x,butAbout.y+40);
            }
            if (butSoundon == null) {
                butSoundon = new MenuLable(((MainClass.GAME_WIDTH - 105) >> 1)+95, (MainClass.GAME_HEIGHT - 30) >> 1, "/res/soundon0.png", "/res/soundon1.png");
                butSoundoff = new MenuLable(((MainClass.GAME_WIDTH - 105) >> 1)+95, (MainClass.GAME_HEIGHT - 30) >> 1, "/res/soundoff0.png", "/res/soundoff1.png");
                butVibrateon = new MenuLable(((MainClass.GAME_WIDTH - 138) >> 1)+95, 140, "/res/vibrateon0.png", "/res/vibrateon1.png");
                butVibrateoff = new MenuLable(((MainClass.GAME_WIDTH - 138) >> 1)+95, 140, "/res/vibrateoff0.png", "/res/vibrateoff1.png");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public final void run() {
        input();
    }

    public void input() {
        switch (KEY) {
            case KEY_LEFT_SOFT:
                break;

            case KEY_RIGHT_SOFT:
                if (mode == MODE_OPTION || mode == MODE_HELP || mode == MODE_ABOUT) {
                    if (mode == MODE_OPTION) {
                        MainClass.setSettings();
                    }
                    resetMode((byte) 6);
                } else if (mode == MODE_MENU) {
                    MainClass.midlet.exit();
                }

                break;

            case KEY_NUM2://up
                //System.out.println(selectIndex);
                if (mode == MODE_MENU && hasContinue == true) {
                    // selectIndex = (byte) ((selectIndex + (5 - 1)) % 5);
                    selectIndex = (byte) ((selectIndex + (6 - 1)) % 6);
                    KEY = 0;
                } else if (mode == MODE_MENU) {
                    selectIndex = (byte) ((selectIndex + (5 - 1)) % 5);

                }

                if (mode == MODE_OPTION) {
                    //selectIndex = 0;
                    optionIndex = 0;
                }
                break;

            case KEY_NUM8://down
                if (mode == MODE_MENU && hasContinue == true) {
                    //    selectIndex = (byte) ((selectIndex + 1) % 5);
                    selectIndex = (byte) ((selectIndex + 1) % 6);
                } else if (mode == MODE_MENU) {
                    selectIndex = (byte) ((selectIndex + 1) % 5);

                }
                if (mode == MODE_OPTION) {
                    optionIndex = 1;
                }
                KEY = 0;
                break;

            case KEY_NUM4://left
                break;

            case KEY_NUM6://right

                break;
            case KEY_NUM5://fire
                if (mode == MODE_MENU) {
                    if (hasContinue == false) {
                        s1 = (byte) (selectIndex + 1);
                        resetMode(s1);
                    } else {
                        resetMode(selectIndex);
                    }
                } else if (mode == MODE_OPTION) {
                    if (optionIndex == 0) {
                        MainClass.soundEnabled = !MainClass.soundEnabled;
                        onoffs = MainClass.soundEnabled;
                    }
                    if (optionIndex == 1) {
                        MainClass.vibrateEnabled = !MainClass.vibrateEnabled;
                        onoffv = MainClass.vibrateEnabled;
                    }
                }

                break;
        }
        KEY = 0;
    }
    int setx = 90;

    public final void paint(Graphics g) {
        g.drawImage(imggameTitle, 30, 40, 20);
        g.drawImage(imgPot, 70, 155, 20);


        switch (mode) {
            case MODE_MENU: {
                if (hasContinue) {

                    butContinue.setXY((MainClass.GAME_WIDTH - butContinue.width >> 1) + setx, 40);
                    butNewGame.setXY((MainClass.GAME_WIDTH - butNewGame.width >> 1) + setx, butContinue.y + 45);
                    butOption.setXY((MainClass.GAME_WIDTH - butOption.width >> 1) + setx, butNewGame.y + 45);
                    butHelp.setXY((MainClass.GAME_WIDTH - butHelp.width >> 1) + setx, butOption.y + 45);
                    butAbout.setXY((MainClass.GAME_WIDTH - butAbout.width >> 1) + setx, butHelp.y + 45);
                    butExit.setXY((MainClass.GAME_WIDTH - butExit.width >> 1) + setx, butAbout.y + 35);
                    butContinue.paint(g);
                } else {
                    butNewGame.setXY((MainClass.GAME_WIDTH - butNewGame.width >> 1) + setx, 30);
                    butOption.setXY((MainClass.GAME_WIDTH - butOption.width >> 1) + setx, butNewGame.y + 45);
                    butHelp.setXY((MainClass.GAME_WIDTH - butHelp.width >> 1) + setx, butOption.y + 45);
                    butAbout.setXY((MainClass.GAME_WIDTH - butAbout.width >> 1) + setx, butHelp.y + 45);
                    butExit.setXY((MainClass.GAME_WIDTH - butExit.width >> 1) + setx, butAbout.y + 35);

                }
                butNewGame.paint(g);
                butOption.paint(g);
                butHelp.paint(g);
                butAbout.paint(g);
                butExit.paint(g);

                //=============key map===============================
/*
                if (hasContinue) {
                switch (selectIndex) {
                case 0: // Continue
                butContinue.buttonState = 1;
                butContinue.paint(g);
                butContinue.buttonState = 0;
                break;
                case 1:  // NewGame
                butNewGame.buttonState = 1;
                butNewGame.paint(g);
                butNewGame.buttonState = 0;
                break;
                case 2:  // Option
                butOption.buttonState = 1;
                butOption.paint(g);
                butOption.buttonState = 0;
                break;
                case 3:  // Help
                butHelp.buttonState = 1;
                butHelp.paint(g);
                butHelp.buttonState = 0;
                break;
                case 4:   // About
                butAbout.buttonState = 1;
                butAbout.paint(g);
                butAbout.buttonState = 0;
                break;
                case 5:  // Exit
                butExit.buttonState = 1;
                butExit.paint(g);
                butExit.buttonState = 0;
                break;
                }
                } else {
                switch (selectIndex) {
                case 0:  // NewGame
                butNewGame.buttonState = 1;
                butNewGame.paint(g);
                butNewGame.buttonState = 0;
                break;
                case 1:  // Option
                butOption.buttonState = 1;
                butOption.paint(g);
                butOption.buttonState = 0;
                break;
                case 2:  // Help
                butHelp.buttonState = 1;
                butHelp.paint(g);
                butHelp.buttonState = 0;
                break;
                case 3:   // About
                butAbout.buttonState = 1;
                butAbout.paint(g);
                butAbout.buttonState = 0;
                break;
                case 4:  // Exit
                butExit.buttonState = 1;
                butExit.paint(g);
                butExit.buttonState = 0;
                break;
                }
                
                }
                 */
            }
            break;

            case MODE_OPTION:

                g.drawImage(optionBg, ((MainClass.GAME_WIDTH - optionBg.getWidth()) >> 1) + 95, (MainClass.GAME_HEIGHT - optionBg.getHeight() >> 1) + 10, g.LEFT | g.TOP);

                if (MainClass.soundEnabled) {
                    butSoundon.paint(g);
                } else {
                    butSoundoff.paint(g);
                }
                if (MainClass.vibrateEnabled) {
                    butVibrateon.paint(g);
                } else {
                    butVibrateoff.paint(g);
                }
                MainClass.buttonHome.paint(g);
                /*
                switch (optionIndex) {
                case 0:  // NewGame
                if (MainClass.soundEnabled) {
                butSoundon.buttonState = 1;
                butSoundon.paint(g);
                butSoundon.buttonState = 0;
                } else {
                butSoundoff.buttonState = 1;
                butSoundoff.paint(g);
                butSoundoff.buttonState = 0;
                }
                break;
                case 1:  // Option
                if (MainClass.vibrateEnabled) {
                butVibrateon.buttonState = 1;
                butVibrateon.paint(g);
                butVibrateon.buttonState = 0;
                } else {
                butVibrateoff.buttonState = 1;
                butVibrateoff.paint(g);
                butVibrateoff.buttonState = 0;
                }
                break;
                
                default:
                if (onoffs) {
                butSoundon.buttonState = 0;
                } else {
                butSoundoff.buttonState = 0;
                }
                if (onoffv) {
                butVibrateon.buttonState = 0;
                } else {
                butVibrateoff.buttonState = 0;
                
                }
                break;
                }*/
                break;
            case MODE_HELP: {
                 g.drawImage(helpText, ((MainClass.GAME_WIDTH - helpText.getWidth()) >> 1) + 95, (MainClass.GAME_HEIGHT - helpText.getHeight() >> 1) + 10, g.LEFT | g.TOP);
                 MainClass.buttonHome.paint(g);
            }
            break;
            case MODE_ABOUT: {
                g.drawImage(helpText, ((MainClass.GAME_WIDTH - helpText.getWidth()) >> 1) + 95, (MainClass.GAME_HEIGHT - helpText.getHeight() >> 1) + 10, g.LEFT | g.TOP);
                MainClass.buttonHome.paint(g);

            }
            break;
        }
    }

    public void reset(boolean passContin) {
        if (butNewGame == null) {
            loadImages();
        }
    }

    public void resetMode(byte toMode) {
        if (toMode == 0) {//continue
            MainClass.showGameView();
        } else if (toMode == 1) {//newgame
            MainClass.resetGameMode(MainMenu.GAME_PLAY);
        } else if (toMode == 5) {//exit
            MainClass.midlet.exit();
        } else {
            mode = toMode;
        }
        if (mode == MODE_HELP) {
            if (helpString == null) {
                helpString = "\n Note the numbers against the hat to \n remember the seqence  and  also  the \n number of time the rabbit  pops  out \n of  a  specific  hat  and  punch numbers \n accordingly. \n As the  level increases  the game \n gets more complicated.";
            }
        }
        onoffs = MainClass.soundEnabled;
        onoffv = MainClass.vibrateEnabled;
    }

    protected void keyRepeated(int keyCode) {
    }

    protected void keyReleased(int keyCode) {
    }

    protected void keyPressed(int keyCode) {
        if (Math.abs(keyCode) == Math.abs(KEY_LEFT_SOFT) || Math.abs(keyCode) == 21) {
            System.out.println("KEY_LEFT_SOFT");
            KEY = KEY_LEFT_SOFT;

        } else if (Math.abs(keyCode) == Math.abs(KEY_RIGHT_SOFT) || Math.abs(keyCode) == 11) {
            KEY = KEY_RIGHT_SOFT;
        } else if (keyCode == KEY_NUM2) {       //up
            KEY = KEY_NUM2;
        } else if (keyCode == KEY_NUM4) {      //left
            KEY = KEY_NUM4;
        } else if (keyCode == KEY_NUM5) {     //fire
            KEY = KEY_NUM5;
        } else if (keyCode == KEY_NUM6) {      //right
            KEY = KEY_NUM6;
        } else if (keyCode == KEY_NUM8) {      //down
            KEY = KEY_NUM8;


        } else if (keyCode == KEY_STAR) {
        } else {
            switch (getGameAction(keyCode)) {
                case UP:
                    KEY = KEY_NUM2;

                    break;
                case DOWN:
                    KEY = KEY_NUM8;
                    break;
                case LEFT:
                    KEY = KEY_NUM4;
                    break;
                case RIGHT:
                    KEY = KEY_NUM6;
                    break;
                case FIRE:
                    KEY = KEY_NUM5;
                    break;
            }
        }
    }

    protected void pointerPressed(int x, int y) {
        boolean result = false;
        switch (mode) {
            case MODE_MENU: {
                //////////  FOR Button Class    ///////////////////
                if (hasContinue) {
                    result = butContinue.isPressed(x, y);
                }
                if (result == false) {
                    result = butNewGame.isPressed(x, y);
                    if (result == false) {
                        result = butOption.isPressed(x, y);
                        if (result == false) {
                            result = butHelp.isPressed(x, y);
                            if (result == false) {
                                result = butAbout.isPressed(x, y);
                                if (result == false) {
                                    result = butExit.isPressed(x, y);
                                }
                            }
                        }
                    }
                }
            }
            break;
            case MODE_OPTION: {
                if (MainClass.buttonHome.isPressed(x, y) == false) {
                    if (onoffs) {
                        result = butSoundon.isPressed(x, y);
                    } else {
                        result = butSoundoff.isPressed(x, y);
                    }
                    if (onoffv) {
                        result = butVibrateon.isPressed(x, y);
                    } else {
                        result = butVibrateoff.isPressed(x, y);
                    }
                }
            }
            break;
            case MODE_ABOUT:
            case MODE_HELP:
                MainClass.buttonHome.isPressed(x, y);
                break;
        }
    }

    protected void pointerReleased(int x, int y) {
        if (MainClass.buttonHome.isReleased(x, y) && mode != MODE_MENU) {
            resetMode(MODE_MENU);
            return;
        }
        switch (mode) {
            case MODE_MENU: {
                boolean result = false;
                result = butExit.isReleased(x, y);
                if (result) {
                    MainClass.midlet.exit();
                } else {
                    if (hasContinue) {
                        result = butContinue.isReleased(x, y);
                        if (result) {
                            selectIndex = 0;
                            resetMode(selectIndex);
                        }
                    }
                    if (result == false) {
                        result = butNewGame.isReleased(x, y);
                        if (result) {
                            selectIndex = 1;
                            resetMode(selectIndex);
                        } else {
                            result = butOption.isReleased(x, y);
                            if (result) {
                                selectIndex = 2;
                                resetMode(selectIndex);
                            } else {
                                result = butHelp.isReleased(x, y);
                                if (result) {
                                    selectIndex = 3;
                                    resetMode(selectIndex);
                                } else {
                                    result = butAbout.isReleased(x, y);
                                    if (result) {
                                        selectIndex = 4;
                                        resetMode(selectIndex);
                                    }
                                }
                            }
                        }
                    }
                }
                //selectIndex = -1;
            }
            break;
            case MODE_OPTION: {
                boolean result = false;
                if (onoffs) {
                    result = butSoundon.isReleased(x, y);
                    if (result) {
                        MainClass.soundEnabled = !MainClass.soundEnabled;
                        onoffs = MainClass.soundEnabled;
                    }
                } else {
                    result = butSoundoff.isReleased(x, y);
                    if (result) {
                        MainClass.soundEnabled = !MainClass.soundEnabled;
                        onoffs = MainClass.soundEnabled;
                    }
                }
                if (onoffv) {
                    result = butVibrateon.isReleased(x, y);
                    if (result) {
                        MainClass.vibrateEnabled = !MainClass.vibrateEnabled;
                        onoffv = MainClass.vibrateEnabled;
                    }
                } else {
                    result = butVibrateoff.isReleased(x, y);
                    if (result) {
                        MainClass.vibrateEnabled = !MainClass.vibrateEnabled;
                        onoffv = MainClass.vibrateEnabled;
                    }
                }
            }
            break;
        }
    }

    protected void pointerDragged(int x, int y) {
    }

    public boolean hasPoint(int x1, int y1, int x, int y, int w, int h) {
        return (x < x1 && x + w > x1 && y < y1 && y + h > y1);
    }
}
