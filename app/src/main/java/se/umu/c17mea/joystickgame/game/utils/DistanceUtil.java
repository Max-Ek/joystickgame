package se.umu.c17mea.joystickgame.game.utils;

public final class DistanceUtil {

    private DistanceUtil(){} // Do not instantiate.

    public static double distance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static double[] vectorNormalize(double x, double y) {
        double sum = Math.abs(x) + Math.abs(y);
        return new double[] {x/sum, y/sum};
    }

}
