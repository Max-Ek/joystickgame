package se.umu.c17mea.joystickgame.game.objects.creatures;

import se.umu.c17mea.joystickgame.game.objects.CircleObject;
import se.umu.c17mea.joystickgame.game.utils.VectorUtil;

public abstract class Projectile extends CircleObject {

    public static final double DEFAULT_VELOCITY = 40;
    public static final double DEFAULT_MAX_RANGE = 1000;

    private double range;
    private double angle;
    private double travelDistance = 0;

    protected Projectile(double basePositionX, double basePositionY, double radius, double angle,
                         double range) {
        super(basePositionX, basePositionY, radius);
        this.angle = angle;
        this.range = range;
    }

    public void update() {
        move(DEFAULT_VELOCITY);
    }

    protected void move(double velocity) {
        double[] vec = VectorUtil.toVector(angle);
        double[] vecNormalized = VectorUtil.normalize(vec[0], vec[1]);
        basePositionX += velocity * vecNormalized[0];
        basePositionY += velocity * vecNormalized[1];
        travelDistance += velocity;
    }

    public boolean shouldStop() {
        return travelDistance >= range;
    }

    public double getAngle() {
        return angle;
    }
}
