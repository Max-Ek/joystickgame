package se.umu.c17mea.joystickgame.game.utils;

public final class VectorUtil {

    private VectorUtil(){} // Do not instantiate.

    public static double[] toVector(double srcX, double srcY, double destX, double destY) {
        return new double[] {destX - srcX, destY - srcY};
    }

    public static double[] vectorNormalize(double x, double y) {
        double sum = Math.abs(x) + Math.abs(y);
        return new double[] {x/sum, y/sum};
    }
}
