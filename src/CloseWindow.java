import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import sun.audio.AudioPlayer;

/**
 *
 * @author hrkalona
 */
public class CloseWindow extends Thread {
  private double ms; //The time that the window stays up
  private int briefcases_to_be_removed; //Briefcases left for the level
  private Deal ptr;  //A ptr to the Deal instance
  private JLabel banker_is_offering; //Graphics related variables
  private JLabel the_offer;
  private String converted_offer;
  private JLabel black_stripe;
  private JLabel button[];
  private JLabel briefcase;
  private Random generator;
  private int i;

    public CloseWindow(double seconds, int briefcases_to_be_removed, Deal ptr) {
        this.ms = seconds * 1000;
        this.briefcases_to_be_removed = briefcases_to_be_removed;
        this.ptr = ptr;
        this.generator = new Random(System.currentTimeMillis());
    }

    public void run () {

        long time = System.currentTimeMillis();

        while(System.currentTimeMillis() - time < this.ms) {}

        ptr.setEnabled(true);
        ptr.getBriefcaseOpenedWindow().dispose();

        if(briefcases_to_be_removed == 0) {
            ptr.setStateOfGame(StateOfGame.BANKER_OFFER);
            
            File file = new File("./Graphics/Game/the_banker_is_calling.bmp");
            try {
                Image image = ImageIO.read(file);
                ImageIcon Icon = new ImageIcon(image);
                ptr.getMessage().setIcon(Icon);
                SwingUtilities.updateComponentTreeUI(ptr.getMessage());
            }
            catch(IOException ex) {}

            ImageIcon Icon = new ImageIcon("./Graphics/Game/banker_calling.gif");
            ptr.getBankerPhone().setIcon(Icon);
            SwingUtilities.updateComponentTreeUI(ptr.getBankerPhone());

            try {
                Thread.currentThread().sleep(650);
            }
            catch(InterruptedException ex) {}

            ptr.playWav("./Sounds/banker_calling.wav");

            try {
                Thread.currentThread().sleep(1350);
            }
            catch(InterruptedException ex) {}

            file = new File("./Graphics/Game/banker_not_calling.bmp");
            try {
                Image image = ImageIO.read(file);
                Icon = new ImageIcon(image);
                ptr.getBankerPhone().setIcon(Icon);
                SwingUtilities.updateComponentTreeUI(ptr.getBankerPhone());
            }
            catch(IOException ex) {}

            file = new File("./Graphics/Game/black.bmp");
            try {
                Image image = ImageIO.read(file);
                Icon = new ImageIcon(image);
                ptr.getMessage().setIcon(Icon);
                SwingUtilities.updateComponentTreeUI(ptr.getMessage());
            }
            catch(IOException ex) {}

            //Initiate the Banker

            ptr.setEnabled(false);
            ptr.setOfferWindow(new JFrame("Banker's Offer"));
            ptr.getOfferWindow().setUndecorated(true);
            ptr.getOfferWindow().getRootPane().setBorder(BorderFactory.createLineBorder(Color.white, 4));
            int offer_window_width = 273, offer_window_height = 284;
            ptr.getOfferWindow().setSize(offer_window_width, offer_window_height);
            ptr.getOfferWindow().setLocation((int)(ptr.getLocation().getX() + ptr.getSize().getWidth() / 2) - (offer_window_width / 2), (int)(ptr.getLocation().getY() + ptr.getSize().getHeight() / 2) - (offer_window_height / 2));
            ptr.getOfferWindow().getContentPane().setBackground(Color.BLACK);
            ptr.getOfferWindow().setResizable(false);
            ptr.getOfferWindow().setLayout(new FlowLayout());
            ptr.getOfferWindow().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            file = new File("./Graphics/Game/banker_offering.bmp");
            try {
                Image image = ImageIO.read(file);
                Icon = new ImageIcon(image);
                banker_is_offering = new JLabel(Icon, JLabel.CENTER);
                ptr.getOfferWindow().add(banker_is_offering);
            }
            catch(IOException ex) {}

            converted_offer = ptr.getBanker().Offer();

            if(converted_offer.equals("exchange")) {
                the_offer = new JLabel("" + converted_offer, JLabel.CENTER);
                the_offer.setFont(new Font("default", Font.BOLD, 30));
                the_offer.setForeground(Color.YELLOW);
                ptr.getOfferWindow().add(the_offer);
            }
            else {
                the_offer = new JLabel("        $ " + converted_offer + "         ", JLabel.CENTER);
                the_offer.setFont(new Font("default", Font.BOLD, 30));
                the_offer.setForeground(Color.YELLOW);
                ptr.getOfferWindow().add(the_offer);
            }

            file = new File("./Graphics/Game/black_stripe.bmp");
            try {
                Image image = ImageIO.read(file);
                Icon = new ImageIcon(image);
                black_stripe = new JLabel(Icon, JLabel.CENTER);
                ptr.getOfferWindow().add(black_stripe);
            }
            catch(IOException ex) {}

            button = new JLabel[2];

            file = new File("./Graphics/Game/button_0.bmp");
            try {
                Image image = ImageIO.read(file);
                Icon = new ImageIcon(image);
                button[0] = new JLabel(Icon, JLabel.CENTER);
                addLabelListener(0);
                ptr.getOfferWindow().add(button[0]);
            }
            catch(IOException ex) {}

            file = new File("./Graphics/Game/button_1.bmp");
            try {
                Image image = ImageIO.read(file);
                Icon = new ImageIcon(image);
                button[1] = new JLabel(Icon, JLabel.CENTER);
                addLabelListener(1);
                ptr.getOfferWindow().add(button[1]);
            }
            catch(IOException ex) {}

            ptr.getOfferWindow().setVisible(true);

            if(!converted_offer.equals("exchange") && converted_offer.length() > 6) {
                ptr.setTheme(ptr.playWav("./Sounds/big_money_picked_cue.wav"));
            }
            else {
                ptr.setTheme(ptr.playWav("./Sounds/case_picked_cue.wav"));
            }
        }
        else {
            ptr.setStateOfGame(StateOfGame.PLAY); 
        }
        
    }

    /*
     * The method, addLabelListener, adds a listener for the deal/no deal buttons.
     */
    private void addLabelListener(int k) {

        i = k;

        button[i].addMouseListener(new MouseListener() {
            int temp = i;

            public void mouseClicked(MouseEvent e) {}

            public void mousePressed(MouseEvent e) {

                if(temp == 1) {
                    noDeal();
                }
                else {
                    if(converted_offer.equals("exchange")) {
                        exchange();
                    }
                    else {
                        acceptedMoney();
                    }
                }

            }

            public void mouseReleased(MouseEvent e) {}

            public void mouseEntered(MouseEvent e) {

                focusButton(temp);

            }

            public void mouseExited(MouseEvent e) {

                unfocusButton(temp);

            }

        });

    }

    /*
     * The method, noDeal, executes when the user chosen no deal.
     */
    private void noDeal() {

        AudioPlayer.player.stop(ptr.getTheme());

        if(!ptr.getLevels().isEmpty()) { //No deal, more levels to go
            ptr.setEnabled(true);
            ptr.getOfferWindow().dispose();
            ptr.setStateOfGame(StateOfGame.PLAY);

            File file = new File("./Graphics/Game/" + ptr.getLevels().get(0).getBriefcases() + "_briefcases.bmp");
            try {
                Image image = ImageIO.read(file);
                ImageIcon Icon = new ImageIcon(image);
                ptr.getMessage().setIcon(Icon);
                SwingUtilities.updateComponentTreeUI(ptr.getMessage());
            }
            catch(IOException ex) {}

            if(ptr.getLevels().size() < 5) {
                boolean result = generator.nextBoolean();
                if(result) {
                    ptr.setTheme(ptr.playWav("./Sounds/thinking_1.wav"));
                }
                else {
                    ptr.setTheme(ptr.playWav("./Sounds/thinking_2.wav"));
                }
            }

        }
        else {   //No deal, no more levels to go
            ptr.setStateOfGame(StateOfGame.END_GAME);
            ptr.getOfferWindow().remove(the_offer);
            ptr.getOfferWindow().remove(black_stripe);
            ptr.getOfferWindow().remove(button[0]);
            ptr.getOfferWindow().remove(button[1]);

            ptr.getOfferWindow().setTitle("Open your briefcase");

            File file = new File("./Graphics/Game/rejected_last_deal.bmp");
            try {
                Image image = ImageIO.read(file);
                ImageIcon Icon = new ImageIcon(image);
                banker_is_offering.setIcon(Icon);
            }
            catch(IOException ex) {}

            file = new File("./Graphics/Game/open_your_briefcase.bmp");
            try {
                Image image = ImageIO.read(file);
                ImageIcon Icon = new ImageIcon(image);
                JLabel what_was_in_your_case = new JLabel(Icon, JLabel.CENTER);
                ptr.getOfferWindow().add(what_was_in_your_case);
            }
            catch(IOException ex) {}

            file = new File("./Graphics/Briefcases/" + ptr.getMyBriefcase().getNumber() + ".bmp");
            try {
                Image image = ImageIO.read(file);
                ImageIcon Icon = new ImageIcon(image);
                briefcase = new JLabel(Icon, JLabel.CENTER);
                briefcase.addMouseListener(new MouseListener() {

                public void mouseClicked(MouseEvent e) {}

                public void mousePressed(MouseEvent e) {

                    ptr.openBriefcaseEnd();

                }

                public void mouseReleased(MouseEvent e) {}

                public void mouseEntered(MouseEvent e) {

                    ptr.focusBriefcase(ptr.getMyBriefcase().getNumber() - 1, briefcase);

                }

                public void mouseExited(MouseEvent e) {

                    ptr.unfocusBriefcase(ptr.getMyBriefcase().getNumber() - 1, briefcase);

                }
            });
            ptr.getOfferWindow().add(briefcase);
        }
        catch(IOException ex) {}


        SwingUtilities.updateComponentTreeUI(ptr.getOfferWindow());
        }

    }

     /*
     * The method, exchange, executes when the user chosen deal on the exchange offer.
     */
    private void exchange() {

        ptr.setStateOfGame(StateOfGame.EXCHANGE);
        ptr.setEnabled(true);
        ptr.getOfferWindow().dispose();
        AudioPlayer.player.stop(ptr.getTheme());

        ptr.setTheme(ptr.playWav("./Sounds/exchange.wav"));

        File file = new File("./Graphics/Game/exchange.bmp");
        try {
            Image image = ImageIO.read(file);
            ImageIcon Icon = new ImageIcon(image);
            ptr.getMessage().setIcon(Icon);
            SwingUtilities.updateComponentTreeUI(ptr.getMessage());
        }
        catch(IOException ex) {}

    }

    /*
     * The method, acceptedMoney, executes when the user chosen deal on the money offer.
     */
    private void acceptedMoney() {

        ptr.setStateOfGame(StateOfGame.ACCEPTED_MONEY);
        AudioPlayer.player.stop(ptr.getTheme());
        ptr.getOfferWindow().remove(black_stripe);
        ptr.getOfferWindow().remove(button[0]);
        ptr.getOfferWindow().remove(button[1]);

        ptr.getOfferWindow().setTitle("DEAL!");

        File file = new File("./Graphics/Game/accepted_money.bmp");
        try {
            Image image = ImageIO.read(file);
            ImageIcon Icon = new ImageIcon(image);
            banker_is_offering.setIcon(Icon);
        }
        catch(IOException ex) {}

        file = new File("./Graphics/Game/open_your_briefcase.bmp");
        try {
            Image image = ImageIO.read(file);
            ImageIcon Icon = new ImageIcon(image);
            JLabel what_was_in_your_case = new JLabel(Icon, JLabel.CENTER);
            ptr.getOfferWindow().add(what_was_in_your_case);
        }
        catch(IOException ex) {}

        file = new File("./Graphics/Briefcases/" + ptr.getMyBriefcase().getNumber() + ".bmp");
        try {
            Image image = ImageIO.read(file);
            ImageIcon Icon = new ImageIcon(image);
            briefcase = new JLabel(Icon, JLabel.CENTER);
            briefcase.addMouseListener(new MouseListener() {

                public void mouseClicked(MouseEvent e) {}

                public void mousePressed(MouseEvent e) {

                    ptr.openBriefcaseEnd();

                }

                public void mouseReleased(MouseEvent e) {}

                public void mouseEntered(MouseEvent e) {

                    ptr.focusBriefcase(ptr.getMyBriefcase().getNumber() - 1, briefcase);

                }

                public void mouseExited(MouseEvent e) {

                    ptr.unfocusBriefcase(ptr.getMyBriefcase().getNumber() - 1, briefcase);

                }
            });
            ptr.getOfferWindow().add(briefcase);
        }
        catch(IOException ex) {}


        SwingUtilities.updateComponentTreeUI(ptr.getOfferWindow());

        ptr.setTheme(ptr.playWav("./Sounds/accepted_money.wav"));

    }

    /*
     * The method, focusButton, changes the image of the Deal/No Deal button when focused.
     */
    private void focusButton(int temp) {

        File file = new File("./Graphics/Game/button_" + temp + "_chosen.bmp");
        try {
            Image image = ImageIO.read(file);
            ImageIcon Icon = new ImageIcon(image);
            button[temp].setIcon(Icon);
            SwingUtilities.updateComponentTreeUI(button[temp]);
        }
        catch(IOException ex) {}

        ptr.playWav("./Sounds/target_focus.wav");

    }

    /*
     * The method, unfocusButton, changes the image of the Deal/No Deal button when unfocused.
     */
    private void unfocusButton(int temp) {

        File file = new File("./Graphics/Game/button_" + temp + ".bmp");
        try {
            Image image = ImageIO.read(file);
            ImageIcon Icon = new ImageIcon(image);
            button[temp].setIcon(Icon);
            SwingUtilities.updateComponentTreeUI(button[temp]);
        }
        catch(IOException ex) {}

    }



   


}

