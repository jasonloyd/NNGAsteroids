package edu.unm.cs523.gasteriods.gui;

import edu.unm.cs523.gasteriods.game.*;
import edu.unm.cs523.gasteriods.game.Game;
import org.newdawn.slick.*;
import org.newdawn.slick.svg.InkscapeLoader;
import org.newdawn.slick.svg.SimpleDiagramRenderer;

/**
 * Renders and Updates the Game.  This is the root Asteroids Game GUI class.
 *
 * @author John Ericksen
 */
public class GameRenderer extends BasicGame implements InputListener {

    private final Game game;
    private final GameInputFactory inputFactory;

    private GameObjectRenderer gameObjectRenderer;
    private SimpleDiagramRenderer imgShip;
    private SimpleDiagramRenderer imgBackground;
    private Color bgcolor;

    public GameRenderer(Game game, GameInputFactory inputFactory) {
        //The title of the game
        super("GAsteroids");
        this.game = game;
        this.inputFactory = inputFactory;
    }

    /**
     * Initalize the game.
     *
     * @param container game container
     * @throws SlickException
     */
    public void init(GameContainer container) throws SlickException {

        game.setInput(inputFactory.buildGameInput(container));

		/* Loads all the vector graphics */
        SimpleDiagramRenderer imgAsteroid = new SimpleDiagramRenderer(InkscapeLoader.load("pictures/asteroid.svg"));
        SimpleDiagramRenderer imgAsteroidMedium = new SimpleDiagramRenderer(InkscapeLoader.load("pictures/asteroid_medium.svg"));
        SimpleDiagramRenderer imgAsteroidSmall = new SimpleDiagramRenderer(InkscapeLoader.load("pictures/asteroid_small.svg"));

        SimpleDiagramRenderer imgShot = new SimpleDiagramRenderer(InkscapeLoader.load("pictures/shot.svg"));
        SimpleDiagramRenderer imgShield = new SimpleDiagramRenderer(InkscapeLoader.load("pictures/shield.svg"));
        imgShip = new SimpleDiagramRenderer(InkscapeLoader.load("pictures/ship.svg"));
        imgBackground = new SimpleDiagramRenderer(InkscapeLoader.load("pictures/background.svg"));

        gameObjectRenderer = new GameObjectRenderer(imgShip, imgShield, imgAsteroid, imgAsteroidMedium, imgAsteroidSmall, imgShot);

        game.restart();

        randomBG();
    }

    private void randomBG() {
        bgcolor = new Color((int) (Math.random() * 128 + 64), (int) (Math.random() * 128 + 64), (int) (Math.random() * 192 + 64));
    }

    /**
     * Draw a frame.
     * @param container game container
     * @param graphics to write to
     */
    public void render(GameContainer container, Graphics graphics) throws SlickException {
        graphics.setBackground(bgcolor);

        imgBackground.render(graphics);

        for (Asteroid asteroid : game.getAsteroids()) {
            asteroid.accept(gameObjectRenderer, graphics);
        }

        for (Shot shot : game.getShots()) {
            shot.accept(gameObjectRenderer, graphics);
        }

        if (game.getPlayer().getLives() > 0) {
            game.getPlayer().accept(gameObjectRenderer, graphics);
        }
        else {
            graphics.setColor(Color.white);
            graphics.drawString("GAME OVER", 350, 150);

            int i = 0;
            for (HighScoreItem hi : game.getHighScores().getScores()) {
                if (hi != null) {
                    graphics.drawString(hi.getName(), 300, 190 + i * 20);
                    graphics.drawString("" + hi.getScore(), 450, 190 + i * 20);
                    i++;
                }
            }
            i++;
            graphics.drawString("Press F2 to start a new game", 300, 190 + i * 20);
            graphics.resetTransform();
        }

        for (int i = 0; i < game.getPlayer().getLives(); i++) {
            graphics.translate((float) 746 - i * 32, (float) 546);
            imgShip.render(graphics);
            graphics.resetTransform();
        }

        graphics.setColor(Color.white);

        String accuracyString = "Accuracy: ";
        if (game.getShotsFired() > 0 && game.getHits() > 0) {
            accuracyString += "" + (int) (((float) game.getHits() / (float) game.getShotsFired()) * 100);
        }
        else {
            accuracyString += "0";
        }

        graphics.drawString(accuracyString + "%", 10, 10);

        String points = "Points: " + game.getPlayer().getPoints();
        if (game.getMultiplier() > 1) {
            points += " x" + game.getMultiplier();
        }

        graphics.drawString(points, 625, 10);
        graphics.drawString("Level " + game.getLevel(), 375, 10);
    }

    /**
     * Update the game state.
     * @param container game container
     * @param delta difference in time
     */
    public void update(GameContainer container, int delta) throws SlickException {
        GameInput input = game.getInput();

        if(input instanceof SlickGameInput){
            SlickGameInput slickInput = (SlickGameInput) input;

            if (slickInput.getEscape()) {
                System.exit(0);
            }
            else if (slickInput.getPause()) {
                if (container.isPaused()) {
                    container.resume();
                }
                else {
                    container.pause();
                }
            } else if (slickInput.getRestart()) {
                game.restart();
                container.resume();
                input.clear();
            }
        }
        else if(game.getPlayer().getLives() == 0){
            System.out.println("Final Score: " + game.getPlayer().getPoints());
            container.exit();
        }

        if (!container.isPaused()) {
            game.handleControls(delta);
        }

        input.clear();

        game.update(delta);
    }
}
