package com.muneebahmad.microsun.util;

/**
 * @author muneebahmad * 
 * {@value} Core -> Util<br />< br />
 * */

import java.util.Random;

import com.wiyun.engine.actions.Action;
import com.wiyun.engine.actions.FiniteTimeAction;
import com.wiyun.engine.actions.MoveByAngle;
import com.wiyun.engine.actions.RepeatForever;
import com.wiyun.engine.actions.Sequence;


/**
 *
 * @author muneebahmad
 */
public class Core {

	/**
     *
     */
    public static final int BREAD_BROWN = 0;
	/**
     *
     */
    public static final int BREAD_WHITE = 1;
	/**
     *
     */
    public static final int BREAT_NONE = 2;
	/**
     *
     */
    public static final int GRID_MODE_DRINK = 2;
	/**
     *
     */
    public static final int GRID_MODE_ADDON = 5;
	/**
     *
     */
    public static final int GRID_MODE_CUP = 3;
	/**
     *
     */
    public static final int GRID_MODE_NONE = 0;
	/**
     *
     */
    public static final int GRID_MODE_PLATE = 1;
	/**
     *
     */
    public static int breadType;
	/**
     *
     */
    public static int gridModeCurrent = -1;

	static {
		breadType = -1;
	}

	/**
     *
     * @param paramInt1
     * @param paramInt2
     * @return
     */
    public static int getRendom(int paramInt1, int paramInt2) {
		Random mRandom = new Random();
		mRandom.setSeed(System.currentTimeMillis());
		return (paramInt1 + mRandom.nextInt(1 + paramInt2 - paramInt1));
	}

	/**
     *
     * @param paramFloat1
     * @param paramFloat2
     * @return
     */
    public static float getRandomFloatWithRange(float paramFloat1,
			float paramFloat2) {
		Random mRandom = new Random();
		mRandom.setSeed(System.currentTimeMillis());
		return (paramFloat1 + mRandom.nextFloat() * (paramFloat2 - paramFloat1));
	}

	/**
     *
     * @param duration
     * @param degree
     * @param velocity
     * @param x
     * @param y
     * @param delta
     * @return
     */
    public static Action getRepeatingAngularAction(float duration, int degree,
			int velocity, float x, float y, float delta) {
		MoveByAngle moveByAngle = (MoveByAngle) MoveByAngle.make(duration,
				degree, velocity).autoRelease();
		moveByAngle.setPinPoint(x, y);
		moveByAngle.setPinAngleDelta(delta);
		MoveByAngle moveByAngle2 = (MoveByAngle) MoveByAngle.make(duration,
				degree, -velocity).autoRelease();
		moveByAngle2.setPinPoint(x, y);
		moveByAngle2.setPinAngleDelta(delta);
		MoveByAngle moveByAngle3 = (MoveByAngle) moveByAngle2.reverse()
				.autoRelease();
		MoveByAngle moveByAnlge4 = (MoveByAngle) moveByAngle.reverse()
				.autoRelease();
		FiniteTimeAction[] finiteTimeAction = new FiniteTimeAction[3];
		finiteTimeAction[0] = moveByAnlge4;
		finiteTimeAction[1] = moveByAngle2;
		finiteTimeAction[2] = moveByAngle3;
		return ((Action) RepeatForever.make(
				(Sequence) Sequence.make(moveByAngle, finiteTimeAction)
						.autoRelease()).autoRelease());
	}
}// end class
