package se.umu.c17mea.joystickgame.game.utils;

/**
 * Util class for vector calculations.
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public final class VectorUtil {

    private VectorUtil(){} // Do not instantiate.

    public static double euclideanDistance(double srcX, double srcY, double destX, double destY) {
        return Math.sqrt(Math.pow(srcX - destX, 2) + Math.pow(srcY - destY, 2));
    }

    public static double[] toVector(double srcX, double srcY, double destX, double destY) {
        return new double[] {destX - srcX, destY - srcY};
    }

    public static double[] toVector(double angle) {
        return new double[] {Math.cos(angle), Math.sin(angle)};
    }

    public static double[] normalize(double x, double y) {
        double sum = Math.abs(x) + Math.abs(y);
        return new double[] {x/sum, y/sum};
    }

    public static double vectorAngle(double x, double y) {
        return Math.atan2(y, x);
    }

}
