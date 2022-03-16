package se.umu.c17mea.joystickgame;

import org.junit.Assert;
import org.junit.Test;

import java.util.Vector;

import se.umu.c17mea.joystickgame.game.utils.VectorUtil;

public class VectorUtilTest {

    /**
     * Tests normalize().
     */
    @Test
    public void testVectorNormalize() {
        double x = -5.0;
        double y = -4.123;
        double[] res = VectorUtil.normalize(x,y);
        System.out.println("x : " + res[0] + " y : " + res[1]);
        Assert.assertTrue(Math.abs(res[0]) + Math.abs(res[1]) < 1.01);
    }

    /**
     * Tests positions to vector (toVector()).
     */
    @Test
    public void testToVector() {
        double[] vec = VectorUtil.toVector(50,50, 52, 57);
        Assert.assertTrue(vec[0] == 2 && vec[1] == 7);
    }

}
