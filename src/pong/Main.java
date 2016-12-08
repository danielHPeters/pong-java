package pong;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import pong.configuration.KeyBindings;
import pong.uiElements.Painter;
import pong.uiElements.GameWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import pong.configuration.KeyBoardActions;
import pong.gameObjcects.Ball;
import pong.gameObjcects.GameArea;
import pong.gameObjcects.Player;

/**
 * Starter class of the pong game this is a simple remake of the classic pong
 * using an object oriented approach
 *
 * @author d.peters
 * @version 2.6
 * @since 12.10.2016
 */
public class Main {

    /**
     *
     */
    private final int width;
    
    /**
     * 
     */
    private final int height;

    /**
     * the game loop
     */
    private final RunGame loop;

    /**
     *
     */
    private final Thread executer;

    /**
     *
     */
    private final GameLogic logic;

    /**
     * the game window
     */
    private final GameWindow window;

    /**
     * the drawing painter containing the game loop and objects
     */
    private final Painter painter;

    /**
     *
     */
    private final ButtonActions btnActions;

    /**
     *
     */
    private final JToggleButton pauseButton;

    /**
     *
     */
    private final JButton restartButton;

    /**
     * initialize the keybindings of the game
     */
    private final KeyBindings keyBindings;

    /**
     *
     */
    private final KeyBoardActions actions;
    
    /**
     * 
     */
    private final GameArea area;

    /**
     *
     */
    private final Ball ball;

    /**
     *
     */
    private final Player pl1, pl2;

    /**
     *
     */
    public List<Player> players;

    /**
     * This is the default constructor
     */
    public Main() {
        this.height = 600;
        this.width = 500;
        this.pl1 = new Player(2 * 5, height / 2);
        this.pl2 = new Player(width - 4 * 5, height / 2);
        this.area = new GameArea(width, height - 95);
        this.players = new ArrayList<>();
        this.players.add(pl1);
        this.players.add(pl2);
        this.ball = new Ball(width / 2, height / 2);
        this.window = new GameWindow(width, height);
        this.painter = new Painter(width, players, ball);
        this.actions = new KeyBoardActions();
        this.keyBindings = new KeyBindings(painter, players, actions);
        this.logic = new GameLogic(area, players, ball, painter);
        this.loop = new RunGame(painter, logic);
        this.executer = new Thread(loop);
        this.btnActions = new ButtonActions(loop, executer);
        this.pauseButton = new JToggleButton("Pause");
        this.pauseButton.addItemListener(btnActions.pauseListener());
        this.restartButton = new JButton("Restart");
        this.restartButton.addActionListener(btnActions.restartListener());
        this.window.add(painter, BorderLayout.CENTER);
        this.window.add(pauseButton, BorderLayout.SOUTH);
        this.window.add(restartButton, BorderLayout.NORTH);
        this.window.pack();
    }

    /**
     * starts the game loop
     */
    public void start() {
        this.window.setVisible(true);
        this.executer.start();
    }

    /**
     * starts the event dispatch thread and starts the game
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {

                UIManager.setLookAndFeel(
                        "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

            } catch (ClassNotFoundException | InstantiationException
                    | IllegalAccessException | UnsupportedLookAndFeelException e) {
            }

            Main pong = new Main();
            SwingUtilities.updateComponentTreeUI(pong.window);
            pong.start();

        });

    }
}
