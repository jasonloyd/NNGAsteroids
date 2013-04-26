package edu.unm.cs523.gasteriods.gui;

import edu.unm.cs523.gasteriods.game.Asteroid;
import edu.unm.cs523.gasteriods.game.GameObjectVisitor;
import edu.unm.cs523.gasteriods.game.Player;
import edu.unm.cs523.gasteriods.game.Shot;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.svg.SimpleDiagramRenderer;

/**
 * Renders the given visited GameObject.
 *
 * @author John Ericksen
 */
public class GameObjectRenderer implements GameObjectVisitor<Void, Graphics>{

    private final SimpleDiagramRenderer imgPlayer;
    private final SimpleDiagramRenderer imgShield;
    private final SimpleDiagramRenderer imgAsteroidLarge;
    private final SimpleDiagramRenderer imgAsteroidMedium;
    private final SimpleDiagramRenderer imgAsteroidSmall;
    private final SimpleDiagramRenderer imgShot;

    public GameObjectRenderer(SimpleDiagramRenderer imgPlayer,
                              SimpleDiagramRenderer imgShield,
                              SimpleDiagramRenderer imgAsteroidLarge,
                              SimpleDiagramRenderer imgAsteroidMedium,
                              SimpleDiagramRenderer imgAsteroidSmall,
                              SimpleDiagramRenderer imgShot) {
        this.imgPlayer = imgPlayer;
        this.imgShield = imgShield;
        this.imgAsteroidLarge = imgAsteroidLarge;
        this.imgAsteroidMedium = imgAsteroidMedium;
        this.imgAsteroidSmall = imgAsteroidSmall;
        this.imgShot = imgShot;
    }

    public Void visit(Player player, Graphics graphics) {
        if (!player.isDead()) {
            graphics.translate(player.getLocation().getX(), player.getLocation().getY());
            graphics.rotate(0, 0, player.getDirection() + 90);
            if (player.isInvulnerable()) {
                imgShield.render(graphics);
            }
            else {
                imgPlayer.render(graphics);
            }

            imgPlayer.render(graphics);
            graphics.resetTransform();
        }

        return null;
    }

    public Void visit(Asteroid asteroid, Graphics graphics) {
        graphics.translate(asteroid.getLocation().getX(), asteroid.getLocation().getY());
        graphics.rotate(0, 0, asteroid.getDirection() + 90);
        if(asteroid.getType() == Asteroid.AsteroidType.SMALL){
            imgAsteroidSmall.render(graphics);
        }
        else if(asteroid.getType() == Asteroid.AsteroidType.MEDIUM){
            imgAsteroidMedium.render(graphics);
        }
        else if(asteroid.getType() == Asteroid.AsteroidType.LARGE){
            imgAsteroidLarge.render(graphics);
        }
        graphics.resetTransform();

        return null;
    }

    public Void visit(Shot shot, Graphics graphics) {
        graphics.translate(shot.getLocation().getX(), shot.getLocation().getY());
        graphics.rotate(0, 0, shot.getDirection() + 90);
        imgShot.render(graphics);
        graphics.resetTransform();

        return null;
    }
}
