package se.umu.c17mea.joystickgame.game.objects.creatures;

import se.umu.c17mea.joystickgame.game.objects.CircleObject;

public abstract class Projectile extends CircleObject {
    protected Projectile(double basePositionX, double basePositionY, double radius) {
        super(basePositionX, basePositionY, radius);
    }
}
