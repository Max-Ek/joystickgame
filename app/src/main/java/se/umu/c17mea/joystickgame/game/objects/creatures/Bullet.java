package se.umu.c17mea.joystickgame.game.objects.creatures;

import android.content.Context;
import android.graphics.Canvas;

import se.umu.c17mea.joystickgame.game.graphics.Animation;

public class Bullet extends Projectile {

    public static final double VELOCITY = 60;
    public static final double RADIUS = 32;
    public static final double MAX_RANGE = 1300;
    private Animation animation;

    public Bullet(Context context, double basePositionX, double basePositionY, double angle, Animation animation) {
        super(basePositionX, basePositionY, RADIUS, angle, MAX_RANGE);
        this.animation = animation;
    }

    @Override
    public void update() {
        super.move(VELOCITY);
    }

    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas, this);
    }
}
