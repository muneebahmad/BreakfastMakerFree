package com.muneebahmad.microsun.hndl;

import com.wiyun.engine.actions.Action;
import com.wiyun.engine.actions.Blink;
import com.wiyun.engine.actions.FiniteTimeAction;
import com.wiyun.engine.actions.IntervalAction;
import com.wiyun.engine.actions.JumpTo;
import com.wiyun.engine.actions.MoveTo;
import com.wiyun.engine.actions.Repeat;
import com.wiyun.engine.actions.RepeatForever;
import com.wiyun.engine.actions.RotateTo;
import com.wiyun.engine.actions.ScaleTo;
import com.wiyun.engine.actions.Sequence;
import com.wiyun.engine.actions.ease.EaseBounceIn;

/**
 *
 * @author muneebahmad
 */
public class ActionHandler {

	/**
     *
     * @param duration
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @return
     */
    public Action getMoveAction(float duration, float startX, float startY,
			float endX, float endY) {
		return MoveTo.make(duration, startX, startY, endX, endY);
	}

	/**
     *
     * @param duration
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param times
     * @return
     */
    public IntervalAction getMoveBounceInAction(float duration, float startX,
			float startY, float endX, float endY, int times) {
		return ((IntervalAction) Repeat.make(
				EaseBounceIn.make((IntervalAction) MoveTo.make(duration,
						startX, startY, endX, endY).autoRelease()), times)
				.autoRelease());
	}

	/**
     *
     * @param duration
     * @param times
     * @return
     */
    public Action getRepeatingBlinkAction(float duration, int times) {
		return ((Action) RepeatForever.make(
				(IntervalAction) Blink.make(duration, times).autoRelease())
				.autoRelease());
	}

	/**
     *
     * @param duration
     * @param param2
     * @param param3
     * @param height
     * @return
     */
    public Action getRepeatingJumpAction(float duration, float param2,
			float param3, int height) {
		IntervalAction localIntervalAction1 = (IntervalAction) JumpTo.make(
				duration, param2, param3, param2, param3, height, 1)
				.autoRelease();
		IntervalAction localIntervalAction2 = (IntervalAction) localIntervalAction1
				.reverse().autoRelease();
		FiniteTimeAction[] arrayOfFiniteTimeAction = new FiniteTimeAction[1];
		arrayOfFiniteTimeAction[0] = localIntervalAction2;
		return ((Action) RepeatForever.make(
				(IntervalAction) Sequence.make(localIntervalAction1,
						arrayOfFiniteTimeAction).autoRelease()).autoRelease());
	}

	/**
     *
     * @param duration
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @return
     */
    public Action getRepeatingPointingAction(float duration, float startX,
			float startY, float endX, float endY) {
		MoveTo localMoveTo = MoveTo.make(duration, startX, startY, endX, endY);
		IntervalAction localIntervalAction = (IntervalAction) localMoveTo
				.reverse().autoRelease();
		FiniteTimeAction[] arrayOfFiniteTimeAction = new FiniteTimeAction[1];
		arrayOfFiniteTimeAction[0] = localIntervalAction;
		return ((Action) RepeatForever.make(
				(IntervalAction) Sequence.make(localMoveTo,
						arrayOfFiniteTimeAction).autoRelease()).autoRelease());
	}

	/**
     *
     * @param duration
     * @param endAngle
     * @return
     */
    public Action getRepeatingRotateAction(float duration, int endAngle) {
		IntervalAction localIntervalAction1 = (IntervalAction) RotateTo.make(
				duration, 0.0f, endAngle).autoRelease();
		IntervalAction localIntervalAction2 = (IntervalAction) localIntervalAction1
				.reverse().autoRelease();
		IntervalAction localIntervalAction3 = (IntervalAction) RotateTo.make(
				duration, 0.0f, endAngle).autoRelease();
		IntervalAction localIntervalAction4 = (IntervalAction) localIntervalAction3
				.reverse().autoRelease();

		FiniteTimeAction[] arrayOfFiniteTimeAction = new FiniteTimeAction[3];
		arrayOfFiniteTimeAction[0] = localIntervalAction2;
		arrayOfFiniteTimeAction[1] = localIntervalAction3;
		arrayOfFiniteTimeAction[2] = localIntervalAction4;
		return ((Action) RepeatForever.make(
				(IntervalAction) Sequence.make(localIntervalAction1,
						arrayOfFiniteTimeAction).autoRelease()).autoRelease());
	}

	/**
     *
     * @param duration
     * @param startScale
     * @param endScale
     * @return
     */
    public Action getRepeatingScaleAction(float duration, float startScale,
			float endScale) {
		IntervalAction localIntervalAction1 = (IntervalAction) ScaleTo.make(
				duration, startScale, endScale);
		IntervalAction localIntervalAction2 = (IntervalAction) localIntervalAction1
				.reverse().autoRelease();
		FiniteTimeAction[] arrayOfFiniteTimeAction = new FiniteTimeAction[1];
		arrayOfFiniteTimeAction[0] = localIntervalAction2;
		return ((Action) RepeatForever.make(
				(IntervalAction) Sequence.make(localIntervalAction1,
						arrayOfFiniteTimeAction).autoRelease()).autoRelease());
	}
	
}//end class
