import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

enum StateOfGame {START_GAME, CHOOSE_BRIEFCASE, PLAY, OPENING_BRIEFCASE, BANKER_OFFER, ACCEPTED_MONEY, EXCHANGE, END_GAME};

/**
 *
 * @author hrkalona
 */
public abstract class Game  extends JFrame {
  protected JPanel start;      //graphics related variables
  protected JPanel button;
  protected JLabel start_button;
  protected File file;
  protected ImageIcon Icon;
  protected Image image;
  protected AudioStream theme;
  protected Random generator;  //generator for random numbers
  protected StateOfGame state; //The current state of the game

    public Game() {

        //Graphics
        super();
        setSize(1024, 768);
        setTitle("Deal or No Deal");
        setResizable(false);
        setLayout(new FlowLayout());

        getContentPane().setBackground(Color.BLACK);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                System.exit(0);

            }
        });

        start = new JPanel();
        start.setBackground(Color.BLACK);
        button = new JPanel();
        button.setBackground(Color.BLACK);

        file = new File("./Graphics/Game/background.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            start.add(new JLabel(Icon, JLabel.CENTER));
        }
        catch(IOException ex) {}


        file = new File("./Graphics/Game/start_game_blue.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            start_button = new JLabel(Icon, JLabel.CENTER);
            start_button.addMouseListener(new MouseListener() {

                public void mouseClicked(MouseEvent e) {}

                public void mousePressed(MouseEvent e) {

                    remove(start);
                    remove(button);
                    AudioPlayer.player.stop(theme);
                    startGame();

                }

                public void mouseReleased(MouseEvent e) {}

                public void mouseEntered(MouseEvent e) {

                    focusButton();

                }

                public void mouseExited(MouseEvent e) {

                    unfocusButton();

                }

                });

            button.add(start_button);

        }
        catch(IOException ex) {}

        generator = new Random(System.currentTimeMillis());
        int random = generator.nextInt(3);

        //random soundtrack
        if(random == 0) {
            theme = playWav("./Sounds/theme_1.wav");
        }
        else {
            if(random == 1) {
                theme = playWav("./Sounds/theme_2.wav");
            }
            else {
                theme = playWav("./Sounds/theme_3.wav");
            }

        }

        add(start);
        add(button);

        state = StateOfGame.START_GAME;

    }

    /**
     * The method, playWav, plays *.wav sound file.
     * @param path The path of the sound file.
     * @return A pointer to the sound stream.
     */
    protected AudioStream playWav(String path) {

        InputStream sound_stream_in = null;
        try {
            sound_stream_in = new FileInputStream(path);
        }
        catch(FileNotFoundException ex) {}


        AudioStream as = null;
        try {
            as = new AudioStream(sound_stream_in);
        }
        catch(IOException ex) {}

        AudioPlayer.player.start(as);

        return as;

    }

    /*
     * The method, focusButton, changes the image of the start button when focused.
     */
    private void focusButton() {

        file = new File("./Graphics/Game/start_game_orange.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            start_button.setIcon(Icon);
            SwingUtilities.updateComponentTreeUI(start_button);
        }
        catch(IOException ex) {}

        playWav("./Sounds/target_focus.wav");

    }

    /*
     * The method, unfocusButton, changes the image of the start button when unfocused.
     */
    private void unfocusButton() {

        file = new File("./Graphics/Game/start_game_blue.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            start_button.setIcon(Icon);
            SwingUtilities.updateComponentTreeUI(start_button);
        }
        catch(IOException ex) {}

    }

    /**
     * The method, restartGame, resets the game when the user chooses to play again.
     */
    protected void restartGame() {

        start = new JPanel();
        start.setBackground(Color.BLACK);
        button = new JPanel();
        button.setBackground(Color.BLACK);

        file = new File("./Graphics/Game/background.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            start.add(new JLabel(Icon, JLabel.CENTER));
        }
        catch(IOException ex) {}


        file = new File("./Graphics/Game/start_game_blue.bmp");
        try {
            image = ImageIO.read(file);
            Icon = new ImageIcon(image);
            start_button = new JLabel(Icon, JLabel.CENTER);
            start_button.addMouseListener(new MouseListener() {

                public void mouseClicked(MouseEvent e) {}

                public void mousePressed(MouseEvent e) {

                    remove(start);
                    remove(button);
                    AudioPlayer.player.stop(theme);
                    startGame();

                }

                public void mouseReleased(MouseEvent e) {}

                public void mouseEntered(MouseEvent e) {

                    focusButton();

                }

                public void mouseExited(MouseEvent e) {

                    unfocusButton();

                }

                });

            button.add(start_button);

        }
        catch(IOException ex) {}

        generator = new Random(System.currentTimeMillis());
        int random = generator.nextInt(3);

        if(random == 0) {
            theme = playWav("./Sounds/theme_1.wav");
        }
        else {
            if(random == 1) {
                theme = playWav("./Sounds/theme_2.wav");
            }
            else {
                theme = playWav("./Sounds/theme_3.wav");
            }

        }

        add(start);
        add(button);

        SwingUtilities.updateComponentTreeUI(this);

        state = StateOfGame.START_GAME;

    }

     /**
     * The method, startGame, starts the game when the button Start Game is pressed.
     */
    protected abstract void startGame();

    //Accesors, Mutators
    protected AudioStream getTheme() {

       return this.theme;

    }

    protected void setTheme(AudioStream theme) {

        this.theme = theme;

    }

   

}
