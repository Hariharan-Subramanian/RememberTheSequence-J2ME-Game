
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.game.Sprite;

public class GamePlay extends Canvas {
    //public static HattrickMIDlet midlet;

    public static MainMenu mainClass;
    int width;
    int height;
    boolean hasLost;
    int i, x, y, dir, count, minY, maxY, cli;
    private Image bgimg, turn;
    private Image rem, tick, wrong, leve, imgnumber;
    Thread t;
    Random random;
    int[] positionX = {65, 45, 200, 125, 275, 275};
    int[] positionY = {120, 160, 140, 150, 120, 160};
    public static int resumeIndex;
    int state, r;
    static final int state0 = 0;
    static final int state1 = 1;
    static final int state2 = 2;
    static final int state3 = 3;
    int level, ntimes, maxtimes, userkeycount, userin, ballFrame, rabitDownFrame;
    int[] order;
    boolean hasPlayerWon, rab, isplay;
    //Box box[] = new Box[nBox + 2];
    boolean isfirst = true, disp, hasLevel;
    Image next5, press5;
    MenuLable playAgain;
    MenuLable menu;
    MenuLable pots[];
    Image imgbal[] = new Image[13];
    int tempy, score;
    Image imgBigscore, imgScore;

    public GamePlay(MainMenu mainView) {
        state = state3;
        this.mainClass = mainView;
        //setFullScreenMode(true);
        try {
            random = new Random();
            level = 1;
            init();
            width = 400;
            height = 240;

            bgimg = Image.createImage("/res/menu-screen.png");
            turn = Image.createImage("/res/your-turn.png");

            rem = Image.createImage("/res/rember.png");
            tick = Image.createImage("/res/tick.png");
            wrong = Image.createImage("/res/rong.png");

            leve = Image.createImage("/res/level.png");
            imgnumber = Image.createImage("/res/number.png");

            press5 = Image.createImage("/res/press5.png");
            next5 = Image.createImage("/res/next.png");


            playAgain = new MenuLable(0, 0, "/res/playagain0.png", "/res/playagain1.png");
            menu = new MenuLable(0, 0, "/res/menu0.png", "/res/menu1.png");
            playAgain.setXY((width - playAgain.width) >> 1, 160);
            menu.setXY((width - menu.width) >> 1, 200);
            pots = new MenuLable[6];
            pots[0] = new MenuLable(50, 120, "/res/game/pot.png", "/res/game/pot.png");
            pots[1] = new MenuLable(30, 160, "/res/game/pot.png", "/res/game/pot.png");
            pots[2] = new MenuLable(185, 140, "/res/game/pot.png", "/res/game/pot.png");
            pots[3] = new MenuLable(110, 150, "/res/game/pot.png", "/res/game/pot.png");
            pots[4] = new MenuLable(260, 120, "/res/game/pot.png", "/res/game/pot.png");
            pots[5] = new MenuLable(260, 160, "/res/game/pot.png", "/res/game/pot.png");

            int[] positionX = {65, 45, 200, 125, 275, 275};
            int[] positionY = {120, 160, 140, 150, 120, 160};

            for (int i = 0; i < 13; i++) {
                imgbal[i] = Image.createImage("/res/game/ball/" + (i + 1) + ".png");
            }

            imgBigscore = Image.createImage("/res/bigscore.png");;
            imgScore = Image.createImage("/res/score.png");;
            int h = height / 2 - 80 + 4;
            int w = height / 2 - 40 + 4;
            resumeIndex = 0;
            score = 0;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void reset() {

        // System.out.println("reset called");
        level = 1;
        init();
        state = state3;
        isfirst = true;

        ballFrame = 0;
        // rabitSprite.setFrame(ballFrame);
        // hasLost=false;
        // hasPlayerWon = false;
    }

    public void init() {

        int r = randomN(0, positionX.length - 1);
        x = positionX[r] + 14;
        y = positionY[r] + 10; //+ 40 - 22;
        minY = y - 60;
        maxY = y;
        dir = -1;
        count = 0;

        //level=1;
        ntimes = 0;
        maxtimes = level + 1;
        order = new int[maxtimes + 1];
        userkeycount = 0;
        hasPlayerWon = false;
        cli = 20;
        rab = false;

        ballFrame = 0;
        rabitDownFrame = 6;


        hasLevel = false;

        hasLost = false;
        disp = false;
        tempy = -40;


        //isfirst = true;
        //  state=state1;

    }

    public int randomN(int min, int max) {
        return (min + (Math.abs(random.nextInt()) % (max - min)));
    }

    public void paint(Graphics g) {

        g.setColor(120, 220, 98);
        g.drawImage(bgimg, 0, 0, Graphics.TOP | Graphics.LEFT);



//        switch (r) {
//            case 0:
//                g.drawImage(imgbal[ballFrame], x, tempy, 20);
//                break;
//            case 1:
//                g.drawImage(imgbal[ballFrame], x, tempy, 20);
//                break;
//            case 2:
//                g.drawImage(imgbal[ballFrame], x, tempy, 20);
//                break;
//            case 3:
//                g.drawImage(imgbal[ballFrame], x, tempy, 20);
//                break;
//            case 4:
//                g.drawImage(imgbal[ballFrame], x, tempy, 20);
//                break;
//            case 5:
//                g.drawImage(imgbal[ballFrame], x, tempy, 20);
//                break;
//        }
//        
        if (r == 0) {
            g.drawImage(imgbal[ballFrame], x, tempy, 20);
        }
        pots[0].paint(g);
        if (r == 1) {
            g.drawImage(imgbal[ballFrame], x, tempy, 20);
        }
        pots[1].paint(g);
        if (r == 2) {
            g.drawImage(imgbal[ballFrame], x, tempy, 20);
        }
        pots[2].paint(g);
        if (r == 3) {
            g.drawImage(imgbal[ballFrame], x, tempy, 20);
        }
        pots[3].paint(g);
        if (r == 4) {
            g.drawImage(imgbal[ballFrame], x, tempy, 20);
        }
        pots[4].paint(g);
        if (r == 5) {
            g.drawImage(imgbal[ballFrame], x, tempy, 20);
        }
        pots[5].paint(g);
        for (int i = 0; i < pots.length; i++) {
            //pots[i].paint(g);
            g.drawString("" + i, pots[i].x, pots[i].y, 20);
        }

        //Level Dispaly
        g.setColor(0, 250, 0);
        g.drawImage(leve, 35, 10, Graphics.TOP | Graphics.HCENTER);
        drawNumber(level, g, 50, 14);
        switch (state) {
            case state1:
                g.drawImage(rem, (width - rem.getWidth()) >> 1, 200, Graphics.TOP | Graphics.LEFT); //120
                break;
            case state2:
                if (userkeycount == 0) {
                    g.drawImage(turn, (width - turn.getWidth()) >> 1, 40, Graphics.TOP | Graphics.LEFT); //120
                } else if (count < 10) {
                    if (userin == order[userkeycount - 1]) {
                        g.drawImage(tick, positionX[userin] + 15, positionY[userin] - 35, Graphics.TOP | Graphics.LEFT);
                    } else {
                        g.drawImage(wrong, positionX[userin] + 15, positionY[userin] - 30, Graphics.TOP | Graphics.LEFT);
                    }
                }
                break;
            case state3:
                try {
                    if (isfirst == true) {
                        g.drawImage(press5, (width - press5.getWidth()) >> 1, (height - press5.getHeight()) >> 1, Graphics.TOP | Graphics.LEFT);
                    } else if (hasPlayerWon == true) {
                        g.drawImage(tick, positionX[userin] + 10, positionY[userin] - 35, Graphics.TOP | Graphics.LEFT);
                        g.drawImage(next5, (width - next5.getWidth()) >> 1, (height - next5.getHeight()) >> 1, Graphics.TOP | Graphics.LEFT);
                        if (count > 10) {
                            hasLevel = true;
                        }
                    } else if (hasPlayerWon != true) {
                        if (count < 10) {
                            g.drawImage(wrong, positionX[userin] + 10, positionY[userin] - 30, Graphics.TOP | Graphics.LEFT);
                        } else {
                            if (hasLost == true) {
                                disp = true;
                                g.drawImage(imgScore, (width - imgScore.getWidth()) >> 1, ((height - imgScore.getHeight()) >> 1) - 50, Graphics.TOP | Graphics.LEFT);
                                if (level * 50 < 100) {
                                    drawBigNumber(level * 50, g, (width >> 1) - 60, ((height - imgScore.getHeight()) >> 1));
                                } else if (level * 50 >= 100 && level * 50 < 1000) {
                                    drawBigNumber(level * 50, g, (width >> 1) - 70, ((height - imgScore.getHeight()) >> 1));
                                } else if (level * 50 >= 1000) {
                                    drawBigNumber(level * 50, g, (width >> 1) - 100, ((height - imgScore.getHeight()) >> 1));
                                }

                                //    g.drawImage(box1, (width - box1.getWidth()) >> 1, (height - box1.getHeight()) >> 1, Graphics.TOP | Graphics.LEFT);
                                switch (resumeIndex) {
                                    case 0: // PLAY AGAIn
                                        //      g.drawImage(border, (width - border.getWidth()) >> 1, 35+ (height - box1.getHeight()) >> 1 , Graphics.TOP | Graphics.LEFT);
                                        break;
                                    case 1:  // MENU
                                        // g.drawImage(border, (width - border.getWidth()) >> 1, 100+(height - box1.getHeight()) >> 1, Graphics.TOP | Graphics.LEFT);
                                        break;
                                }
                                playAgain.paint(g);
                                menu.paint(g);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        if (hasLost != true) {
            // mainClass.buttonHome.setXY(mainClass.GAME_WIDTH - (mainClass.buttonHome.width+4), 3);
            mainClass.buttonHome.paint(g);
            // System.out.println("here" + mainClass.buttonHome.y);
        }

    }

    public void drawNumber(int number1, Graphics g, int x, int y) {
        if (number1 <= 0) {
            //g.setClip(x + 10, y, 14, 20);
            g.setClip(x + 12, y, 12, 13);
            g.drawImage(imgnumber, x + 12, y, g.TOP | g.LEFT);
            g.setClip(0, 0, width, 240);
            return;
        }
        int temp = number1;
        do {
            x += 12;
            temp /= 10;
        } while (temp > 0);

        do {
            temp = number1 % 10;
            g.setClip(x, y, 12, 13);
            g.drawImage(imgnumber, x - (temp * 12), y, g.TOP | g.LEFT);
            x -= 12;
            number1 /= 10;
        } while (number1 != 0);
        g.setClip(0, 0, width, 240);
    }

    public void drawBigNumber(int number1, Graphics g, int x, int y) {
        if (number1 <= 0) {
            //g.setClip(x + 10, y, 14, 20);
            g.setClip(x + 24, y, 24, 32);
            g.drawImage(imgBigscore, x + 24, y, g.TOP | g.LEFT);
            g.setClip(0, 0, width, 240);
            return;
        }
        int temp = number1;
        do {
            x += 24;
            temp /= 10;
        } while (temp > 0);

        do {
            temp = number1 % 10;
            g.setClip(x, y, 24, 32);
            g.drawImage(imgBigscore, x - (temp * 24), y, g.TOP | g.LEFT);
            x -= 24;
            number1 /= 10;
        } while (number1 != 0);
        g.setClip(0, 0, width, 240);
    }

    public void run() {
        try {
            switch (state) {
                case state1:
                    //   y += dir * 4;
                    if (count % 2 == 0) {
                        ballFrame = (ballFrame + 1) % 13;
                    }
                    if (y > tempy) {
                        tempy += 5;
                    } else {
                        tempy = -25;
                        r = randomN(0, positionX.length - 1);
                        //   rabitSprite.setFrame(0);
                        if (r >= 6) {
                            r = 5;
                        }
                        order[ntimes] = r;

                        System.out.println("r " + r + " order" + order[ntimes] + " ntimes " + ntimes);
                        x = positionX[r] + 14;
                        y = positionY[r] + 40;//-10;// 40 - 22;
                        minY = y - 40;
                        maxY = y;
                        ntimes++;
                        if (ntimes > maxtimes) {
                            state = state2;
                        }
                    }



//                    cli += dir;
//                    if (cli > 15) {
//                        cli = 15;
//                    }
//                    if (y < minY || y > maxY) {
//                        dir *= -1;
//                    }
                    if (count % 40 == 0) {       //25
//                        r = randomN(0, positionX.length);
//                        //   rabitSprite.setFrame(0);
//                        if (r >= 5) {
//                            r = 4;
//                        }
//                        order[ntimes] = r;
//                        x = positionX[r] + 14;
//                        y = positionY[r] + 40;//-10;// 40 - 22;
//
//                        minY = y - 40;
//                        maxY = y;
//                        ntimes++;
//                        if (ntimes > maxtimes) {
//                            state = state2;
//                        }
                    }
                    count++;
                    break;
                case state2:
                    count++;
                    break;
                case state3:
                    count++;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //repaint();
        try {
            synchronized (this) {
                Thread.sleep(50L);
            }
        } catch (Exception e) {
        }
    }

//    }
    public void keyPressed(int key) {
        //System.out.println("keypressed");
        switch (key) {
            case Canvas.KEY_NUM1:
                if (state == state2) {
                    userin = 0;
                    if (userin == 4) {
                        userinput(userin);
                        return;
                    } else {
                        userinput(userin);
                    }
                }
                break;
            case Canvas.KEY_NUM3:
                if (state == state2) {
                    userin = 1;
                    if (userin == 4) {
                        userinput(userin);
                        return;
                    } else {
                        userinput(userin);
                    }
                }
                break;
            case Canvas.KEY_NUM7:
                if (state == state2) {
                    userin = 3;
                    if (userin == 4) {
                        userinput(userin);
                        return;
                    } else {
                        userinput(userin);
                    }
                }
                break;
            case Canvas.KEY_NUM9:
                if (state == state2) {
                    userin = 4;
                    if (userin == 4) {
                        userinput(userin);
                        return;
                    } else {
                        userinput(userin);
                    }
                }
                break;
            case Canvas.KEY_NUM5:
            case 'g':
            case 'G':

                if (state == state2) {
                    userin = 2;
                    if (userin == 4) {
                        userinput(userin);
                        return;
                    } else {
                        userinput(userin);
                    }
                }
                if (state == state3) {
                    if (state == state3) {
//                            if (isfirst != true) {
//                                level++;
//                                if (!hasPlayerWon) {
//                                    //level = 1;
//                                    //mainView.resetGameMode(6);
//                                    level = level - 1;
//                                }
//                            }
                        if (isfirst == true) {
                            init();
                            state = 1;
                            r = randomN(0, positionX.length - 1);
                            //   rabitSprite.setFrame(0);
                            if (r >= 6) {
                                r = 5;
                            }
                            order[ntimes] = r;

                            System.out.println("r " + r + " order" + order[ntimes] + " ntimes " + ntimes);
                            x = positionX[r] + 14;
                            y = positionY[r] + 40;//-10;// 40 - 22;
                            minY = y - 40;
                            maxY = y;
                            ntimes++;
                        } else if (hasPlayerWon == true && hasLevel == true) {

                            level = level + 1;
                            init();
                            state = state1;
                            r = randomN(0, positionX.length - 1);
                            //   rabitSprite.setFrame(0);
                            if (r >= 6) {
                                r = 5;
                            }
                            order[ntimes] = r;

                            System.out.println("r " + r + " order" + order[ntimes] + " ntimes " + ntimes);
                            x = positionX[r] + 14;
                            y = positionY[r] + 40;//-10;// 40 - 22;
                            minY = y - 40;
                            maxY = y;
                            ntimes++;
                            if (ntimes > maxtimes) {
                                state = state2;
                            }
                        }
                        isfirst = false;
                    }
                    return;
                }

                break;
            case 'R':
            case 'r':
                if (state == state2) {
                    userin = 0;
                    userinput(userin);
                }
                break;
            case 'y':
            case 'Y':
                if (state == state2) {
                    userin = 1;
                    userinput(userin);
                }
                break;

            case 'V':
            case 'v':
                if (state == state2) {
                    userin = 3;
                    userinput(userin);
                }
                break;
            case 'n':
            case 'N':
                if (state == state2) {
                    userin = 4;
                    userinput(userin);
                }
                break;
//                if (state == state2) {
//                    userin = key - 49;
//                    count = 0;
//                    if (key - 49 != order[userkeycount]) {
//                        hasLost = true;
//                        state = state3; // user wrong
//                        hasPlayerWon = false;
//
//                    } else {
//                        // user right
//                        if (userkeycount + 1 == maxtimes) {
//                            hasPlayerWon = true;
//                            // hasLost=false;
//                        }
//
//                    }
//                    userkeycount++;
//                    if (userkeycount == maxtimes) {
//                        state = state3;
//                    }
//                    return;
//                }


            // case Canvas.KEY_NUM6:
//            case 16:
//                //    System.out.println("meu");
//                level = 1;
//                reset();
//                isfirst = true;
//                MainClass.resetGameMode(6);
//                break;
//
//            //   case Canvas.KEY_NUM7:
//            // case -5:
//            case 17:
//                if (state == state3) {
//                    if (isfirst != true) {
//                        level++;
//                        if (!hasPlayerWon) {
//                            //level = 1;
//                            //mainView.resetGameMode(6);
//                            level = level - 1;
//                        }
//                    }
//                    isfirst = false;
//                    init();
//                    state = 1;
//                }
//                break;

            case SubMenu.KEY_LEFT_SOFT:

                break;
            case SubMenu.KEY_RIGHT_SOFT:
                mainClass.menuView.hasContinue = true;
                mainClass.resetGameMode(6);
                break;

        }

//        key=getGameAction(key);
//        if (Canvas.UP == key) {
//            if (hasLost == true) {
//                resumeIndex = 0;
//            }
//
//
//        } else if (Canvas.DOWN == key) {
//            if (hasLost == true) {
//                resumeIndex = 1;
//            }
//
//
//        } else if (Canvas.FIRE == key) {
//
//            if (hasLost == true) {
//                hasLost = false;
//                if (resumeIndex == (byte) 0) {
//                    //  keyPressed(Canvas.KEY_NUM7);
//                   if (state == state3) {
//                    if (isfirst != true) {
//                        level++;
//                        if (!hasPlayerWon) {
//                            //level = 1;
//                            //mainView.resetGameMode(6);
//                            level = level - 1;
//                        }
//                    }
//                    isfirst = false;
//                    init();
//                    state = 1;
//                }
//
//                } else if (resumeIndex == (byte) 1) {
//                    level = 1;
//                    reset();
//                    isfirst = true;
//                    MainClass.menuView.hasContinue = false;
//                    MainClass.resetGameMode(6);
//                }
//            }
//            if (state == state3) {
//                //keyPressed(-5);
//               if (state == state3) {
//                    if (isfirst != true) {
//                        level++;
//                        if (!hasPlayerWon) {
//                            //level = 1;
//                            //mainView.resetGameMode(6);
//                            level = level - 1;
//                        }
//                    }
//                    isfirst = false;
//                    init();
//                    state = 1;
//                }
//                return;
//            }
//
//        }


        try {
            switch (getGameAction(key)) {
                case Canvas.UP:
                    //System.out.println("up");

                    if (hasLost == true) {
                        resumeIndex = 0;
                    }

                    break;
                case Canvas.DOWN:
                    //  System.out.println("down");

                    if (hasLost == true) {
                        resumeIndex = 1;
                    }
                    break;
                case Canvas.FIRE:
                    if (disp == true) {
                        if (hasLost == true) {

                            //if (hasPlayerWon == false) {
                            hasLost = false;
                            if (resumeIndex == 0) {
                                //  keyPressed(Canvas.KEY_NUM7);
                                if (state == state3) {
                                    if (isfirst != true) {
                                        level++;
                                        if (!hasPlayerWon) {
                                            //level = 1;
                                            //mainView.resetGameMode(6);
                                            level = level - 1;
                                        }
                                    }
                                    isfirst = false;
                                    init();
                                    state = 1;
                                    // System.out.println("hasLost");
                                }
                                break;

                            } else if (resumeIndex == 1) {
                                level = 1;
                                reset();
                                isfirst = true;
                                mainClass.menuView.hasContinue = false;
                                mainClass.resetGameMode(6);
                            }
                        }
                    }
                    if (state == state3) {
                        //keyPressed(-5);
                        //   System.out.println("state3333333333333");
                        if (state == state3) {
                            if (isfirst != true) {
                                level++;
                                if (!hasPlayerWon) {
                                    //level = 1;
                                    //mainView.resetGameMode(6);
                                    level = level - 1;
                                }
                            }
                            isfirst = false;
                            init();
                            state = 1;
                        }

                        return;
                    }

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void userinput(int u) {

        userin = u;
        count = 0;
        System.out.println("user in" + userin + "   order" + order[userkeycount]);
        //  System.out.println("userkeycount"+userkeycount);
        if (u != order[userkeycount]) {
            hasLost = true;
            hasPlayerWon = false;
            state = state3; // user wrong

            playVibrate();

        } else {
            // user right
            if (userkeycount + 1 == maxtimes) {
                hasPlayerWon = true;
                // hasLost=false;
            }
            playsound();

        }
        userkeycount++;
        if (userkeycount == maxtimes) {
            state = state3;
        }


        return;
    }

    public void pointerPressed(int x, int y) {
        if (mainClass.buttonHome.isPressed(x, y)) {
        } else {
            if (hasLost == true) {
                if (playAgain.isPressed(x, y)) {
                } else if (menu.isPressed(x, y)) {
                }
            }
            if (state == state2) {
                for (int j = 0; j < pots.length; j++) {
                    if (pots[j].isPressed(x, y)) {
                        // keyPressed(j + 49);
                        break;
                    }
                }
            }
        }
    }

    public void pointerReleased(int x, int y) {
        if (hasLost == true) {
            if (playAgain.isReleased(x, y)) {
                hasLost = false;
                mainClass.menuView.hasContinue = false;
                hasLost = false;
                if (state == state3) {
                    if (isfirst != true) {
                        level++;
                        if (!hasPlayerWon) {
                            //level = 1;
                            //mainView.resetGameMode(6);
                            level = level - 1;
                        }
                    }
                    isfirst = false;
                    init();
                    state = 1;
                    // System.out.println("hasLost");
                }
                return;
            } else if (menu.isReleased(x, y)) {
                hasLost = false;
                level = 1;
                reset();
                isfirst = true;
                mainClass.menuView.hasContinue = false;
                mainClass.resetGameMode(6);
                return;
            }
        } else {
            if (mainClass.buttonHome.isReleased(x, y)) {
                mainClass.menuView.hasContinue = true;
                mainClass.resetGameMode(6);
                return;
            }
            if (state == state3) {
                keyPressed(KEY_NUM5);
                //keyPressed(Canvas.KEY_NUM7);
                return;
            }

            for (int j = 0; j < pots.length; j++) {
                if (pots[j].isReleased(x, y)) {
                    userinput(j);
                    System.out.println("j" + j);
                    break;
                }
            }
        }
    }

    public void playVibrate() {
        mainClass.vibrate();
    }

    public void playsound() {
        mainClass.playSound();
    }

    protected void showNotify() {
    }

    protected void hideNotify() {
    }
}
