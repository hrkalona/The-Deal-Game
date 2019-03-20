import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import sun.audio.AudioPlayer;


/**
 *
 * @author hrkalona
 */
public class Deal extends Game  {
  private double[] content_value; //The money contained in the cases, its used for the graphics.
  private ArrayList<Level> levels; //The Levels of the game
  private Banker banker; //The Banker
  private Briefcase[] briefcases_content; //The Briefcases of the game
  private Briefcase my_briefcase; //My briefcase
  private int briefcases_to_be_removed; //How many briefcases are left to be removed on the current round
  private JLabel[] briefcases; //Some graphic related variables
  private JLabel[] money_label;
  private JLabel message;
  private JLabel banker_phone;
  private JLabel chosen_briefcase;
  private JFrame briefcase_opened_window;
  private JPanel low_money;
  private JPanel big_money;
  private JPanel briefcases_panel;
  private JPanel logo_panel;
  private JPanel your_briefcase_panel;
  private JFrame offer_window;
  private JLabel play_again;
  private JLabel briefcase;
  private int i;
  
    

    public Deal() {

        super();
              
    }

    protected void startGame() {

        //creating a list with the available money to fill the cases
        ArrayList<Double> available_money = new ArrayList<Double> (26);

        available_money.add(0.01);
        available_money.add(1.0);
        available_money.add(5.0);
        available_money.add(10.0);
        available_money.add(25.0);
        available_money.add(50.0);
        available_money.add(75.0);
        available_money.add(100.0);
        available_money.add(200.0);
        available_money.add(300.0);
        available_money.add(400.0);
        available_money.add(500.0);
        available_money.add(750.0);
        available_money.add(1000.0);
        available_money.add(5000.0);
        available_money.add(10000.0);
        available_money.add(25000.0);
        available_money.add(50000.0);
        available_money.add(75000.0);
        available_money.add(100000.0);
        available_money.add(200000.0);
        available_money.add(300000.0);
        available_money.add(400000.0);
        available_money.add(500000.0);
        available_money.add(750000.0);
        available_money.add(1000000.0);

        content_value = new double[26];

        content_value[0] = 0.01;
        content_value[1] = 1.0;
        content_value[2] = 5.0;
        content_value[3] = 10.0;
        content_value[4] = 25.0;
        content_value[5] = 50.0;
        content_value[6] = 75.0;
        content_value[7] = 100.0;
        content_value[8] = 200.0;
        content_value[9] = 300.0;
        content_value[10] = 400.0;
        content_value[11] = 500.0;
        content_value[12] = 750.0;
        content_value[13] = 1000.0;
        content_value[14] = 5000.0;
        content_value[15] = 10000.0;
        content_value[16] = 25000.0;
        content_value[17] = 50000.0;
        content_value[18] = 75000.0;
        content_value[19] = 100000.0;
        content_value[20] = 200000.0;
        content_value[21] = 300000.0;
        content_value[22] = 400000.0;
        content_value[23] = 500000.0;
        content_value[24] = 750000.0;
        content_value[25] = 1000000.0;


        briefcases_content = new Briefcase[26];

        for(int temp, i = 0; available_money.size() > 0; i++) {
            temp = generator.nextInt(available_money.size());
            briefcases_content[i] = new Briefcase(available_money.get(temp), i + 1);  //filling the briefcases
            available_money.remove(temp);
        }

        levels = new ArrayList<Level> (9);     //setting the levels
        levels.add(new Level(1, 6));
        levels.add(new Level(2, 5));
        levels.add(new Level(3, 4));
        levels.add(new Level(4, 3));
        levels.add(new Level(5, 2));
        levels.add(new Level(6, 1));
        levels.add(new Level(7, 1));
        levels.add(new Level(8, 1));
        levels.add(new Level(9, 1));

        briefcases_to_be_removed = levels.get(0).getBriefcases();   //how many briefcases need to be removed on the starting level

        //Graphics

        setLayout(new FlowLayout());

        money_label = new JLabel[26];
        briefcases = new JLabel[26];

        low_money = new JPanel();
        low_money.setLayout(new GridLayout(30, 1));
        low_money.setBackground(Color.BLACK);

        big_money = new JPanel();
        big_money.setLayout(new GridLayout(30, 1));
        big_money.setBackground(Color.BLACK);

        briefcases_panel = new JPanel();
        briefcases_panel.setLayout(new GridLayout(11, 11));
        briefcases_panel.setBackground(Color.BLACK);


        for(i = 0; i < money_label.length / 2; i++) {
            file = new File("./Graphics/Money Tags/Active/" + i + ".bmp");
            try {
                image = ImageIO.read(file);
                Icon = new ImageIcon(image);
                money_label[i] = new JLabel(Icon, JLabel.CENTER);
                low_money.add(money_label[i]);
                low_money.add(new JLabel());
                
            }
            catch(IOException ex) {}
        }

        for(i = 13; i < money_label.length; i++) {
            file = new File("./Graphics/Money Tags/Active/" + i + ".bmp");
            try {
                image = ImageIO.read(file);
                Icon = new ImageIcon(image);
                money_label[i] = new JLabel(Icon, JLabel.CENTER);
                big_money.add(money_label[i]);
                big_money.add(new JLabel());
                
            }
            catch(IOException ex) {}
        }

        for(i = 0; i < briefcases.length - 1; i++) {
            file = new File("./Graphics/Briefcases/" + (i + 1) + ".bmp");
            try {
                image = ImageIO.read(file);
                Icon = new ImageIcon(image);
                briefcases[i] = new JLabel(Icon, JLabel.CENTER);
                addLabelListener(i);

                if(i == 0 || i == 5 || i == 10 || i == 15 || i == 20) {
                    briefcases_panel.add(new JLabel());
                }

                briefcases_panel.add(briefcases[i]);

                if(i == 4 || i == 9 || i == 14 || i == 19 || i == 24 ) {
                    briefcases_panel.add(new JLabel());
                    briefcases_panel.add(new JLabel());
                    briefcases_panel.add(new JLabel());
                    briefcases_panel.add(new JLabel());
                    briefcases_panel.add(new JLabel());
                    briefcases_panel.add(new JLabel());
                    briefcases_panel.add(new JLabel());
                    briefcases_panel.add(new JLabel());
                    briefcases_panel.add(new JLabel());
                    briefcases_panel.add(new JLabel());
                    briefcases_panel.add(new JLabel());
                    briefcases_panel.add(new JLabel());
                }

                if(i == 0 || i == 5 || i == 10 || i == 15 || i == 20) {
                    briefcases_panel.add(new JLabel());
                }
                else {
                    if(i == 1 || i == 6 || i == 11 || i == 16 || i == 21) {
                        briefcases_panel.add(new JLabel());
                    }
                    else {
                        if(i == 2 || i == 7 || i == 12 || i == 17 || i == 22) {
                            briefcases_panel.add(new JLabel());
                        }
                        else {
                            if(i == 3 || i == 8 || i == 13 || i == 18 || i == 23) {
                                briefcases_panel.add(new JLabel());
                            }
                        }

                    }
                }
            }
            catch(IOException ex) {}
        }

        briefcases_panel.add(new JLabel());
        briefcases_panel.add(new JLabel());
        briefcases_panel.add(new JLabel());
        briefcases_panel.add(new JLabel());
        briefcases_panel.add(new JLabel());

        file = new File("./Graphics/Briefcases/" + (i + 1) + ".bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            briefcases[i] = new JLabel(Icon, JLabel.CENTER);
            addLabelListener(i);
            briefcases_panel.add(briefcases[i]);
        }
        catch(IOException ex) {}

        briefcases_panel.add(new JLabel());
        briefcases_panel.add(new JLabel());
        briefcases_panel.add(new JLabel());
        briefcases_panel.add(new JLabel());
        briefcases_panel.add(new JLabel());

        logo_panel = new JPanel();
        logo_panel.setBackground(Color.BLACK);

        file = new File("./Graphics/Game/deal_logo_main.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            logo_panel.add(new JLabel(Icon, JLabel.CENTER));
        }
        catch(IOException ex) {}

        
        your_briefcase_panel = new JPanel();
        file = new File("./Graphics/Briefcases/starting_briefcase.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            chosen_briefcase = new JLabel(Icon, JLabel.CENTER);
            your_briefcase_panel.setBackground(Color.BLACK);
            your_briefcase_panel.add(chosen_briefcase);
            file = new File("./Graphics/Game/your_briefcase.bmp");
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            JLabel your_briefcase = new JLabel(Icon, JLabel.CENTER);
            your_briefcase_panel.add(your_briefcase);
        }
        catch(IOException ex) {}


        add(logo_panel);
        add(low_money);
        add(briefcases_panel);
        add(big_money);
        add(your_briefcase_panel);

        file = new File("./Graphics/Game/select_a_briefcase.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            message = new JLabel(Icon, JLabel.CENTER);
            add(message);
        }
        catch(IOException ex) {}

        file = new File("./Graphics/Game/banker_not_calling.bmp");
            try {
                image = ImageIO.read(file);
                Icon = new ImageIcon(image);
                banker_phone = new JLabel(Icon, JLabel.CENTER);
                add(banker_phone);
            }
            catch(IOException ex) {}
         
       
        SwingUtilities.updateComponentTreeUI(this);

        banker = new Banker(briefcases_content);

        state = StateOfGame.CHOOSE_BRIEFCASE;


    }

    /*
     * The method, addLabelListener, creates a listener for every briefcase.
     */
    private void addLabelListener(int k) {

        i = k;
        briefcases[i].addMouseListener(new MouseListener() {
            int temp = i;

            public void mouseClicked(MouseEvent e) {}

            public void mousePressed(MouseEvent e) {

                if(state == StateOfGame.PLAY) {
                    openBriefcase(temp, this);
                }
                else {
                    if(state == StateOfGame.CHOOSE_BRIEFCASE) {
                        chooseYourBriefcase(temp, this);
                    }
                    else {
                        if(state == StateOfGame.EXCHANGE) {
                            exchangeYourBriefcase(temp, this);
                        }
                    }
                }

            }

            public void mouseReleased(MouseEvent e) {}

            public void mouseEntered(MouseEvent e) {

                focusBriefcase(temp, briefcases[temp]);

            }

            public void mouseExited(MouseEvent e) {

                unfocusBriefcase(temp, briefcases[temp]);

            }
        });

    }

     /*
     * The method, chooseYourBriefcase, sets the user's briefcase after he chosen.
     */
    private void chooseYourBriefcase(int temp, MouseListener listener) {

        my_briefcase = briefcases_content[temp];  //set the briefcase

        playWav("./Sounds/your_briefcase_chosen.wav");

        briefcases[temp].removeMouseListener(listener);  //remove the old listener

        file = new File("./Graphics/Briefcases/black.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            briefcases[temp].setIcon(Icon);
            SwingUtilities.updateComponentTreeUI(briefcases[temp]);
        }
        catch(IOException ex) {}

        file = new File("./Graphics/Briefcases/" + my_briefcase.getNumber() + ".bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            chosen_briefcase.setIcon(Icon);
            SwingUtilities.updateComponentTreeUI(chosen_briefcase);
        }
        catch(IOException ex) {}

        file = new File("./Graphics/Game/" + levels.get(0).getBriefcases() + "_briefcases.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            message.setIcon(Icon);
            SwingUtilities.updateComponentTreeUI(message);
        }
        catch(IOException ex) {}

        state = StateOfGame.PLAY;

    }

     /*
     * The method, exchangeYourBriefcase, sets the user's briefcase after he exchanged his old.
     */
    private void exchangeYourBriefcase(int temp, MouseListener listener) {

        AudioPlayer.player.stop(theme);
        playWav("./Sounds/your_briefcase_chosen.wav");

        file = new File("./Graphics/Briefcases/" + (temp + 1) + ".bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            chosen_briefcase.setIcon(Icon);
            SwingUtilities.updateComponentTreeUI(chosen_briefcase);
        }
        catch(IOException ex) {}

        file = new File("./Graphics/Briefcases/" + my_briefcase.getNumber() + ".bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            briefcases[my_briefcase.getNumber() - 1].setIcon(Icon);
            addLabelListener(my_briefcase.getNumber() - 1);
            SwingUtilities.updateComponentTreeUI(briefcases[my_briefcase.getNumber() - 1]);
        }
        catch(IOException ex) {}

        briefcases[temp].removeMouseListener(listener); //remove the old listener

        file = new File("./Graphics/Briefcases/black.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            briefcases[temp].setIcon(Icon);
            SwingUtilities.updateComponentTreeUI(briefcases[temp]);
        }
        catch(IOException ex) {}

        my_briefcase = briefcases_content[temp];  //set the new listener

        if(!levels.isEmpty()) {  //Done exchange, more levels to go.
            file = new File("./Graphics/Game/" + briefcases_to_be_removed + "_briefcases.bmp");
            try {
                image = ImageIO.read(file);
                Icon = new ImageIcon(image);
                message.setIcon(Icon);
                SwingUtilities.updateComponentTreeUI(message);
            }
            catch(IOException ex) {}

            if(levels.size() < 5) {
                boolean result = generator.nextBoolean();
                if(result) {
                    theme = playWav("./Sounds/thinking_1.wav");
                }
                else {
                    theme = playWav("./Sounds/thinking_2.wav");
                }
            }

            state = StateOfGame.PLAY;
        }
        else {   //Done exchange, no more levels to go.
            state = StateOfGame.END_GAME;
            
            file = new File("./Graphics/Game/black.bmp");
            try {
                image = ImageIO.read(file);
                Icon = new ImageIcon(image);
                message.setIcon(Icon);
                SwingUtilities.updateComponentTreeUI(message);
            }
            catch(IOException ex) {}

            setEnabled(false);
            offer_window = new JFrame("Open your briefcase");
            offer_window.setUndecorated(true);
            int offer_window_width = 273, offer_window_height = 284;
            offer_window.setSize(offer_window_width, offer_window_height);
            offer_window.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (offer_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (offer_window_height / 2));
            offer_window.getContentPane().setBackground(Color.BLACK);
            offer_window.setResizable(false);
            offer_window.setLayout(new FlowLayout());
            offer_window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            offer_window.getRootPane().setBorder(BorderFactory.createLineBorder(Color.white, 4));

            file = new File("./Graphics/Game/last_exchange.bmp");
            try {
                image = ImageIO.read(file);
                Icon = new ImageIcon(image);
                JLabel last_exchange = new JLabel(Icon, JLabel.CENTER);
                offer_window.add(last_exchange);
            }
            catch(IOException ex) {}

        
            file = new File("./Graphics/Briefcases/" + my_briefcase.getNumber() + ".bmp");
            try {
                image = ImageIO.read(file);
                Icon = new ImageIcon(image);
                briefcase = new JLabel(Icon, JLabel.CENTER);
                briefcase.addMouseListener(new MouseListener() {

                    public void mouseClicked(MouseEvent e) {}

                    public void mousePressed(MouseEvent e) {

                        openBriefcaseEnd();

                    }

                    public void mouseReleased(MouseEvent e) {}

                    public void mouseEntered(MouseEvent e) {

                        focusBriefcase(my_briefcase.getNumber() - 1, briefcase);

                    }

                    public void mouseExited(MouseEvent e) {

                        unfocusBriefcase(my_briefcase.getNumber() - 1, briefcase);

                    }
                });
                offer_window.add(briefcase);
            }
            catch(IOException ex) {}
            
            offer_window.setVisible(true);
        }
        

    }


     /*
     * The method, openBriefcase, opens the a new briefcase.
     */
    private void openBriefcase(int temp, MouseListener listener) {

        state = StateOfGame.OPENING_BRIEFCASE;

        setEnabled(false);

        AudioPlayer.player.stop(theme);

        playWav("./Sounds/opening_briefcase.wav");

        try {
            Thread.currentThread().sleep(1690);
        }
        catch(InterruptedException ex) {}

        file = new File("./Graphics/Briefcases/black.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            briefcases[temp].setIcon(Icon);
            SwingUtilities.updateComponentTreeUI(briefcases[temp]);
        }
        catch(IOException ex) {}

        if(briefcases_content[temp].getValue() < 1000) {
            playWav("./Sounds/small_amount_gone.wav");
            
        }
        else {
            if(briefcases_content[temp].getValue() < 100000) {
                playWav("./Sounds/big_amount_gone.wav");
            }
            else {
                playWav("./Sounds/very_big_amount_gone.wav");
            }
            
        }

        for(i = 0; i < briefcases_content.length; i++) {
            if(briefcases_content[temp].getValue() == content_value[i]) {
                briefcases_content[temp].setOpen();
                briefcases[temp].removeMouseListener(listener);
                file = new File("./Graphics/Money Tags/Faded/" + i + ".bmp");
                try {
                    image = ImageIO.read(file);
                    Icon = new ImageIcon(image);
                    money_label[i].setIcon(Icon);
                    SwingUtilities.updateComponentTreeUI(money_label[i]);
                    break;
                }
                catch(IOException ex) {}
            }
        }

        briefcase_opened_window = new JFrame("Opening case number " + (temp + 1));
        briefcase_opened_window.setUndecorated(true);
        briefcase_opened_window.getRootPane().setBorder(BorderFactory.createLineBorder(Color.white, 4));
        int briefcase_opened_window_width = 272, briefcase_opened_window_height = 320;
        briefcase_opened_window.setSize(briefcase_opened_window_width, briefcase_opened_window_height);
        briefcase_opened_window.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (briefcase_opened_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (briefcase_opened_window_height / 2));
        briefcase_opened_window.getContentPane().setBackground(Color.BLACK);
        briefcase_opened_window.setResizable(false);
        briefcase_opened_window.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        briefcase_opened_window.setLayout(new FlowLayout());
 
        file = new File("./Graphics/Briefcases/" + i + "_open.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            JLabel open_briefcase = new JLabel(Icon, JLabel.CENTER);
            briefcase_opened_window.add(open_briefcase);
            file = new File("./Graphics/Briefcases/briefcase_" + (temp + 1) + "_contains.bmp");
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            JLabel briefcase_contains = new JLabel(Icon, JLabel.CENTER);
            briefcase_opened_window.add(briefcase_contains);
        }
        catch(IOException ex) {}

        briefcase_opened_window.setVisible(true);

        briefcases_to_be_removed--;

        if(briefcases_content[temp].getValue() < 1000) {
            CloseWindow window = new CloseWindow(1.9, briefcases_to_be_removed, this);
            window.start();
        }
        else {
            if(briefcases_content[temp].getValue() < 100000) {
                CloseWindow window = new CloseWindow(2.6, briefcases_to_be_removed, this);
                window.start();
            }
            else {
                CloseWindow window = new CloseWindow(1.5, briefcases_to_be_removed, this);
                window.start();
            }
        }

        if(briefcases_to_be_removed == 0) {  //The level is done
            levels.remove(0);
            if(!levels.isEmpty()) {
                briefcases_to_be_removed = levels.get(0).getBriefcases();
            }
            file = new File("./Graphics/Game/black.bmp");
            try {
                image = ImageIO.read(file);
                Icon = new ImageIcon(image);
                message.setIcon(Icon);
                SwingUtilities.updateComponentTreeUI(message);
            }
            catch(IOException ex) {}
        }
        else {
            file = new File("./Graphics/Game/" + briefcases_to_be_removed + "_briefcases.bmp");
            try {
                image = ImageIO.read(file);
                Icon = new ImageIcon(image);
                message.setIcon(Icon);
                SwingUtilities.updateComponentTreeUI(message);
            }
            catch(IOException ex) {}
        }

    }

    /**
     * The method, focusBriefcase, changes the image of the briefcase when focused.
     * @param temp Briefcase index
     * @param ptr Graphic ptr
     */
    public void focusBriefcase(int temp, JLabel ptr) {

        file = new File("./Graphics/Briefcases/" + (temp + 1) + "_selected.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            ptr.setIcon(Icon);
            SwingUtilities.updateComponentTreeUI(ptr);
        }
        catch(IOException ex) {}

        playWav("./Sounds/target_focus.wav");

    }

     /**
     * The method, unfocusBriefcase, changes the image of the briefcase when unfocused.
     * @param temp Briefcase index
     * @param ptr Graphic ptr
     */
    public void unfocusBriefcase(int temp, JLabel ptr) {

        file = new File("./Graphics/Briefcases/" + (temp + 1) + ".bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            ptr.setIcon(Icon);
            SwingUtilities.updateComponentTreeUI(ptr);
        }
        catch(IOException ex) {}

    }

    /**
     * The method, openBriefcaseEnd, opens the last briefcase left.
     */
    public void openBriefcaseEnd() {

        AudioPlayer.player.stop(theme);

        playWav("./Sounds/opening_briefcase.wav");

        try {
            Thread.currentThread().sleep(1690);
        }
        catch(InterruptedException ex) {}

        setEnabled(true);
        offer_window.dispose();


        if(state == StateOfGame.ACCEPTED_MONEY) {
            if(my_briefcase.getValue() < 1000) {
                playWav("./Sounds/small_amount_gone.wav");
            }
            else {
                if(my_briefcase.getValue() < 100000) {
                    playWav("./Sounds/big_amount_gone.wav");
                }
                else {
                    playWav("./Sounds/very_big_amount_gone.wav");
                }
                
            }
            state = StateOfGame.END_GAME;
        }
        else {
            theme = playWav("./Sounds/accepted_money.wav");
        }

        for(i = 0; i < content_value.length; i++) {
            if(my_briefcase.getValue() == content_value[i]) {
                my_briefcase.setOpen();
                file = new File("./Graphics/Money Tags/Faded/" + i + ".bmp");
                try {
                    image = ImageIO.read(file);
                    Icon = new ImageIcon(image);
                    money_label[i].setIcon(Icon);
                    SwingUtilities.updateComponentTreeUI(money_label[i]);
                    break;
                }
                catch(IOException ex) {}
            }
        }

        setEnabled(false);
        briefcase_opened_window = new JFrame("Opening your case (" + my_briefcase.getNumber() +")");
        briefcase_opened_window.setUndecorated(true);
        briefcase_opened_window.getRootPane().setBorder(BorderFactory.createLineBorder(Color.white, 4));
        int briefcase_opened_window_width = 272, briefcase_opened_window_height = 360;
        briefcase_opened_window.setSize(briefcase_opened_window_width, briefcase_opened_window_height);
        briefcase_opened_window.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (briefcase_opened_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (briefcase_opened_window_height / 2));
        briefcase_opened_window.getContentPane().setBackground(Color.BLACK);
        briefcase_opened_window.setResizable(false);
        briefcase_opened_window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        briefcase_opened_window.setLayout(new FlowLayout());

        briefcase_opened_window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                setEnabled(true);
                briefcase_opened_window.dispose();

            }
        });

        file = new File("./Graphics/Briefcases/" + i + "_open.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            JLabel open_briefcase = new JLabel(Icon, JLabel.CENTER);
            briefcase_opened_window.add(open_briefcase);
            file = new File("./Graphics/Briefcases/briefcase_" + my_briefcase.getNumber() + "_contains.bmp");
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            JLabel briefcase_contains = new JLabel(Icon, JLabel.CENTER);
            briefcase_opened_window.add(briefcase_contains);
        }
        catch(IOException ex) {}

        file = new File("./Graphics/Game/play_again.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            play_again = new JLabel(Icon, JLabel.CENTER);
            play_again.addMouseListener(new MouseListener() {

                public void mouseClicked(MouseEvent e) {}

                public void mousePressed(MouseEvent e) {

                    AudioPlayer.player.stop(theme);
                    setEnabled(true);
                    briefcase_opened_window.dispose();
                    remove(logo_panel);
                    remove(low_money);
                    remove(big_money);
                    remove(briefcases_panel);
                    remove(your_briefcase_panel);
                    remove(message);
                    remove(banker_phone);
                    restartGame();

                }

                public void mouseReleased(MouseEvent e) {}

                public void mouseEntered(MouseEvent e) {

                    File file = new File("./Graphics/Game/play_again_chosen.bmp");
                    try {
                        image = ImageIO.read(file);
                        ImageIcon Icon = new ImageIcon(image);
                        play_again.setIcon(Icon);
                        SwingUtilities.updateComponentTreeUI(play_again);
                    }
                    catch(IOException ex) {}

                    playWav("./Sounds/target_focus.wav");

                }

                public void mouseExited(MouseEvent e) {

                    File file = new File("./Graphics/Game/play_again.bmp");
                    try {
                        image = ImageIO.read(file);
                        ImageIcon Icon = new ImageIcon(image);
                        play_again.setIcon(Icon);
                        SwingUtilities.updateComponentTreeUI(play_again);
                    }
                    catch(IOException ex) {}
                }
            });
            briefcase_opened_window.add(play_again);
        }
        catch(IOException ex) {}


        briefcase_opened_window.setVisible(true);


        file = new File("./Graphics/Game/thank_you.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            message.setIcon(Icon);
            SwingUtilities.updateComponentTreeUI(message);
        }
        catch(IOException ex) {}

    }

    //Accesors, Mutators
    public JLabel getMessage() {

        return this.message;

    }

    public StateOfGame getStateOfGame() {

        return this.state;

    }

    public void setStateOfGame(StateOfGame state) {

        this.state = state;

    }

    public Briefcase getMyBriefcase() {

        return this.my_briefcase;

    }
  
    public JLabel getBankerPhone() {

        return this.banker_phone;

    }

    public Banker getBanker() {

        return this.banker;

    }

    public JFrame getBriefcaseOpenedWindow() {

        return this.briefcase_opened_window;

    }


    public JFrame getOfferWindow() {

        return this.offer_window;
        
    }

    public void setOfferWindow(JFrame window) {

        this.offer_window = window;

    }

    public ArrayList<Level> getLevels() {

        return this.levels;

    }

    public static void main(String[] args) {

        Deal the_deal = new Deal();
        the_deal.setVisible(true);

    }

}
