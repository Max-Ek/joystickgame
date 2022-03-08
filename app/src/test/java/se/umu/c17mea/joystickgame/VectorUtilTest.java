package se.umu.c17mea.joystickgame;

import org.junit.Assert;
import org.junit.Test;

import se.umu.c17mea.joystickgame.game.utils.VectorUtil;

public class VectorUtilTest {

    @Test
    public void testDistance() {
        Assert.assertTrue(VectorUtil.distance(50,50, 52, 57) < 10);
    }

    @Test
    public void testVectorNormalize() {
        double x = -5.0;
        double y = -4.123;
        double[] res = VectorUtil.vectorNormalize(x,y);
        System.out.println("x : " + res[0] + " y : " + res[1]);
        Assert.assertTrue(Math.abs(res[0]) + Math.abs(res[1]) < 1.01);
    }

    @Test
    public void testToVector() {
        double[] vec = VectorUtil.toVector(50,50, 52, 57);
        Assert.assertTrue(vec[0] == 2 && vec[1] == 7);
    }


}
