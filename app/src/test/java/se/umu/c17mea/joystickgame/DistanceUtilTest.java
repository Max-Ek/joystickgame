package se.umu.c17mea.joystickgame;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import se.umu.c17mea.joystickgame.game.utils.DistanceUtil;

public class DistanceUtilTest {

    @Test
    public void testDistance() {
        Assert.assertTrue(DistanceUtil.distance(50,52, 50, 57) < 10);
    }

    @Test
    public void testVectorNormalize() {
        double x = -5.0;
        double y = -4.123;
        double[] res = DistanceUtil.vectorNormalize(x,y);
        System.out.println("x : " + res[0] + " y : " + res[1]);
        Assert.assertTrue(Math.abs(res[0]) + Math.abs(res[1]) < 1.01);
    }


}
