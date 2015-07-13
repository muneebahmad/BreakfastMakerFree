package com.algorithmi.maker.breakfast.scenes;

/**
 * @author muneebahmad
 * @clipart muneebahmad/mumtaz ahmad
 * @management waqar ahmad
 * algorithmi @ microSun 2013. All Rights Reserved.
 * */

import android.view.MotionEvent;

import com.algorithmi.maker.breakfast.free.main.R;
import com.muneebahmad.microsun.hndl.ActionHandler;
import com.muneebahmad.microsun.hndl.SoundsHandler;
import com.muneebahmad.microsun.interfaces.HudLayerInterface;
import com.muneebahmad.microsun.layers.HudLayer;
import com.muneebahmad.microsun.util.ParticlePouringLiquid;
import com.muneebahmad.microsun.util.ParticleSun;
import com.wiyun.engine.actions.Action;
import com.wiyun.engine.actions.CallFunc;
import com.wiyun.engine.actions.FadeTo;
import com.wiyun.engine.actions.FiniteTimeAction;
import com.wiyun.engine.actions.IntervalAction;
import com.wiyun.engine.actions.MoveTo;
import com.wiyun.engine.actions.RotateBy;
import com.wiyun.engine.actions.Sequence;
import com.wiyun.engine.nodes.Button;
import com.wiyun.engine.nodes.Director;
import com.wiyun.engine.nodes.Scene;
import com.wiyun.engine.nodes.Scheduler;
import com.wiyun.engine.nodes.Sprite;
import com.wiyun.engine.nodes.Timer;
import com.wiyun.engine.opengl.Texture2D;
import com.wiyun.engine.particle.ParticleSystem;
import com.wiyun.engine.transitions.LeftPushInTransition;
import com.wiyun.engine.transitions.RightPushInTransition;
import com.wiyun.engine.transitions.TransitionScene;
import com.wiyun.engine.types.WYColor3B;
import com.wiyun.engine.types.WYPoint;
import com.wiyun.engine.types.WYSize;
import com.wiyun.engine.utils.TargetSelector;

/**
 *
 * @author muneebahmad
 */
public class EggFryingScene extends Scene implements HudLayerInterface {

	private final int _EGG_APPEARANCE_LEVEL_1 = 1;
	private final int _EGG_APPEARANCE_LEVEL_2 = 2;
	private final int _EGG_APPEARANCE_LEVEL_3 = 3;
	private final int _EGG_APPEARANCE_LEVEL_4 = 4;
	private final int _EGG_APPEARANCE_LEVEL_5 = 5;
	private final int _EGG_APPEARANCE_LEVEL_6 = 6;
	private final int _EGG_APPEARANCE_LEVEL_7 = 7;
	private final int _EGG_APPEARANCE_LEVEL_8 = 8;
	private final int _EGG_APPEARANCE_LEVEL_9 = 9;
	private final int _MODE_DRAG_STIRRER = 4;
	private final int _MODE_DROP_EGG = 2;
	private final int _MODE_IGNITE_STOVE = 3;
	private final int _MODE_POUR_OIL = 1;
	private int fadingState = 1;
	private TargetSelector mixingSelector;
	private ParticleSystem particleSystem;
        private ParticleSystem particleSystem2;
	private Timer mixingTimer;
	private int mode = -1;
	private WYSize wySize;
	private ActionHandler actionHandler;
	private Sprite bg;
	private Sprite oilBottle;
	private Sprite egg;
	private Sprite dodle;
	private Sprite pan;
	private Sprite oilPan;
	private Sprite basket;
	private HudLayer hudLayer;
        private Sprite stoveButtSprite;
	private Button stoveKnob;
	private Sprite fryEgg;
	private Sprite fryEggHalfCooked;
	private Sprite fryEggFullCooked;
	private Sprite pointingArrow;
	private SoundsHandler soundHandler;

	/**
     *
     */
    public EggFryingScene() {
		autoRelease(true);
		this.wySize = Director.getInstance().getWindowSize();
		soundHandler = new SoundsHandler();
		setTouchEnabled(true);
		this.actionHandler = new ActionHandler();
		this.mode = 1;
		setContents();
	}

	private void addBackground() {
		bg = Sprite.make(Texture2D.make("bgs/egg_frying_bg.png"));
		this.bg.setPosition(this.wySize.width / 2.0f, this.wySize.height / 2.0f);
		this.bg.setContentSize(this.wySize.width, this.wySize.height);
		this.bg.setAutoFit(true);
		addChild(this.bg);
		autoRelease(true);
	}

	private void addItems() {
		oilBottle = Sprite.make(Texture2D.make("art/oil.png"));
		this.oilBottle.setPosition(50.0f + this.oilBottle.getWidth() / 2.0f,
				this.wySize.height - this.oilBottle.getHeight() / 2.0f - 55.0f);
		addChild(this.oilBottle);
		this.oilBottle.setZOrder(5);
		this.oilBottle.autoRelease(true);
		this.oilBottle.runAction(this.actionHandler.getRepeatingJumpAction(
				0.5f, this.oilBottle.getPositionX(),
				this.oilBottle.getPositionY(), 10));
                /**
                 * oilBottle scaling up
                 */
                this.oilBottle.setScale(1.5f);
                this.oilBottle.runAction(this.actionHandler.getRepeatingScaleAction(0.5f, 
                1.4f, 1.5f));

		this.pan = Sprite.make(Texture2D.make("art/pan.png"));
		this.pan.setPosition(this.wySize.width / 2.5f, this.wySize.height / 3.0f);
		addChild(this.pan);
		this.pan.autoRelease(true);
                this.stoveButtSprite = Sprite.make(Texture2D.make("art/knob1.png"));
		this.stoveKnob = Button.make(stoveButtSprite, stoveButtSprite,null, null,
                        this,
				"onStoveKnobClicked");
		this.stoveKnob.setPosition(this.wySize.width - 80.0f,
				this.wySize.height / 1.6f);
		addChild(this.stoveKnob);
		this.stoveKnob.autoRelease(true);
		this.oilPan = Sprite.make(Texture2D.make("art/oilpan.png"));
		this.oilPan.setPosition(this.wySize.width / 2.3f, this.wySize.height
				/ 2.0f - this.oilPan.getHeight() / 3.6f);
		addChild(this.oilPan);
		this.oilPan.setVisible(false);
		this.oilPan.autoRelease(true);
		this.fryEgg = Sprite.make(Texture2D.make("art/fryegg1.png"));
		this.fryEgg.setPosition(this.oilPan.getPositionX(),
				this.oilPan.getPositionY());
		addChild(this.fryEgg, 1);
		this.fryEgg.setAlpha(0);
		this.fryEgg.autoRelease(true);
		this.fryEggHalfCooked = Sprite.make(Texture2D.make("art/fryegg2.png"));
		this.fryEggHalfCooked.setPosition(this.fryEgg.getPositionX(),
				this.fryEgg.getPositionY());
		addChild(this.fryEggHalfCooked, 1);
		this.fryEggHalfCooked.setAlpha(0);
		this.fryEggHalfCooked.autoRelease(true);
		this.fryEggFullCooked = Sprite.make(Texture2D.make("art/fryegg3.png"));
		this.fryEggFullCooked.setPosition(this.fryEgg.getPositionX(),
				this.fryEgg.getPositionY());
		addChild(this.fryEggFullCooked, 1);
		this.fryEggFullCooked.setAlpha(0);
		this.fryEggFullCooked.autoRelease(true);
		egg = Sprite.make(Texture2D.make("art/egg.png"));
		this.egg.setPosition(this.wySize.width - 90.0f, this.wySize.height
				- (this.egg.getHeight() + (this.egg.getHeight() * 2)) / 2.0f
				- 65.0f);
		this.egg.setRotation(-22.5f);
		addChild(this.egg);
		this.egg.autoRelease(true);

	}

	private void addStirrer() {
		this.basket = Sprite.make(Texture2D.make("art/basket.png"));
		this.basket.setPosition(this.egg.getPositionX(),
				this.egg.getPositionY() + 20.0f);
		addChild(this.basket);
		this.basket.autoRelease(true);
		this.dodle = Sprite.make(Texture2D.make("art/dodle2.png"));
		this.dodle.setPosition(this.wySize.width / 2.0f - 30.0f,
				this.basket.getPositionY() - 40.0f);
		this.dodle.setRotation(-45.0f);
		addChild(this.dodle, 2);
                this.dodle.setScale(1.3f);
		this.dodle.autoRelease(true);
	}

	private void setContents() {
		initialiseTimer();
		addBackground();
		addHudLayer();
		addItems();
		addStirrer();
	}

	private void addHudLayer() {
		this.hudLayer = new HudLayer(this);
		this.hudLayer.setContents("ui/arrow_left1.png",
                "ui/arrow_left2.png", "ui/arrow_right1.png",
                "ui/arrow_right2.png", "cheri.ttf",
				WYColor3B.make(255, 255, 255), 36.0f, 0.0f);
		this.hudLayer.updateObjectsVisibility(true, false, false,
				this.wySize.width / 2.0f, this.wySize.height / 8.0f, "bck");
		this.hudLayer.setBackButtonPosition(90.0f, 80.0f);
		addChild(this.hudLayer);
		this.hudLayer.autoRelease(true);
		this.hudLayer.setZOrder(15);
		bringToFront(this.hudLayer);
	}

	private void executeOilPouring() {
		WYColor3B color = WYColor3B.make(255, 213, 63);
		this.particleSystem = ParticlePouringLiquid.make();
		this.particleSystem.setPosition(this.oilBottle.getPositionX()
				+ this.oilBottle.getHeight() / 1.5f,
				this.oilBottle.getPositionY() - 30.0f);
		this.particleSystem.scale(2.1f);
		this.particleSystem.setDuration(1.70000004768371582031f);
		setPouringParticleColor(color.r, color.g, color.b);
		addChild(this.particleSystem);
		bringToFront(this.particleSystem);
		this.particleSystem.setZOrder(0);
		this.particleSystem.autoRelease(true);
		IntervalAction action1 = (IntervalAction) FadeTo.make(2.0f, 0, 255)
				.autoRelease();
		CallFunc func = (CallFunc) CallFunc.make(this, "pourActionCallback")
				.autoRelease();
		FiniteTimeAction[] finiteTimeAction = new FiniteTimeAction[1];
		finiteTimeAction[0] = func;
		IntervalAction action2 = (IntervalAction) Sequence.make(action1,
				finiteTimeAction).autoRelease();
		this.oilPan.runAction(action2);
	}

	private void setPouringParticleColor(int r, int g, int b) {
		this.particleSystem.setStartColorVariance(r / 255.0f, g / 255.0f,
				b / 255.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f);
	}

	private Action getRotateActionWithCallback() {
		IntervalAction action = (IntervalAction) RotateBy.make(
				0.69999998807907104492f, 105.0f).autoRelease();
		CallFunc func = (CallFunc) CallFunc.make(this,
				"rotateObjectActonCallback");
		FiniteTimeAction[] finiteTimeAction = new FiniteTimeAction[1];
		finiteTimeAction[0] = func;
		return ((IntervalAction) Sequence.make(action, finiteTimeAction)
				.autoRelease());
	}

	private void initialiseTimer() {
		Object[] mObject = new Object[1];
		mObject[0] = Float.valueOf(0.0f);
		this.mixingSelector = new TargetSelector(this, "onMixingTick(float)",
				mObject);
		if (this.mixingTimer == null) {
			this.mixingTimer = new Timer(this.mixingSelector,
					0.80000001192092895508f);
		} else {
			Scheduler.getInstance().unschedule(this.mixingTimer);
		}
	}

	private void setSpriteAlpha(int alpha1, int alpha2, int alpha3) {
		this.fryEgg.setAlpha(alpha1);
		this.fryEggHalfCooked.setAlpha(alpha2);
		this.fryEggFullCooked.setAlpha(alpha3);
	}

	private void showPointingArrow() {
		this.pointingArrow = Sprite.make(Texture2D.make("art/arrow_down.png"));
		this.pointingArrow.setPosition(this.stoveKnob.getPositionX(),
				this.stoveKnob.getPositionY() - 60.0f);
		addChild(this.pointingArrow);
		this.pointingArrow.autoRelease(true);
		this.pointingArrow.runAction(this.actionHandler
				.getRepeatingBlinkAction(1.0f, 2));
	}

	/**
     *
     */
    public void eggDroppedCallback() {
		soundHandler.playEggBreakSound();
		this.fryEgg.setAlpha(255);
		this.egg.setDisplayFrame(this.fryEgg.makeFrame());
		showPointingArrow();
		setEnabled(true);
	}

	/**
     *
     * @param paramFloat
     */
    public void onMixingTick(float paramFloat) {

		if (this.fadingState == 1) {
			Scheduler.getInstance().schedule(this.mixingTimer);
		}

		if (this.fadingState == _EGG_APPEARANCE_LEVEL_1) {
			setSpriteAlpha(255, 0, 0);
			this.fadingState = 2;
		} else if (this.fadingState == _EGG_APPEARANCE_LEVEL_2) {
			setSpriteAlpha(170, 85, 0);
			this.fadingState = 3;
		} else if (this.fadingState == _EGG_APPEARANCE_LEVEL_3) {
			setSpriteAlpha(85, 170, 0);
			this.fadingState = 4;
		} else if (this.fadingState == _EGG_APPEARANCE_LEVEL_4) {
			setSpriteAlpha(0, 255, 0);
			this.fadingState = 5;
		} else if (this.fadingState == _EGG_APPEARANCE_LEVEL_5) {
			setSpriteAlpha(0, 170, 85);
			this.fadingState = 6;
		} else if (this.fadingState == _EGG_APPEARANCE_LEVEL_6) {
			setSpriteAlpha(0, 110, 100);
			this.fadingState = 7;
		} else if (this.fadingState == _EGG_APPEARANCE_LEVEL_7) {
			setSpriteAlpha(0, 75, 155);
			this.fadingState = 8;
		} else if (this.fadingState == _EGG_APPEARANCE_LEVEL_8) {
			setSpriteAlpha(0, 50, 200);
			this.fadingState = 9;
		} else if (this.fadingState == _EGG_APPEARANCE_LEVEL_9) {
			setSpriteAlpha(0, 0, 255);
			this.fadingState = 10;
		}

		if (this.fadingState > 9) {
                    this.particleSystem2.stopSystem();
                    this.particleSystem2.setVisible(false);
                    this.stoveButtSprite.setDisplayFrame(Sprite.make(Texture2D.make("art/knob1.png"))
				.makeFrame());
			Scheduler.getInstance().unschedule(this.mixingTimer);
			this.hudLayer.updateHudObjectVisibility(true, true, false);
			this.hudLayer.setNextButtonPosition(this.wySize.width - 80.0f,
					80.0f);
			HudLayer mLayer = this.hudLayer;
			this.hudLayer.getClass();
			mLayer.startNextButtonAction(4);
		}

	}

	/**
     *
     */
    public void onStoveKnobClicked() {
		if (this.mode == _MODE_IGNITE_STOVE) {
			this.pointingArrow.stopAllActions();
			this.pointingArrow.setVisible(false);
                        this.stoveButtSprite.setDisplayFrame(Sprite.make(Texture2D.make("art/knob2.png"))
				.makeFrame());
                        this.stoveKnob.setEnabled(false);
			this.oilBottle.runAction(this.actionHandler.getMoveAction(0.5f,
					this.oilBottle.getPositionX(),
					this.oilBottle.getPositionY(), -this.oilBottle.getWidth(),
					this.oilBottle.getPositionY()));
			this.basket.runAction(this.actionHandler.getMoveAction(0.5f,
					this.basket.getPositionX(), this.basket.getPositionY(),
					this.basket.getPositionX() + this.basket.getWidth() + 120.0f,
					this.basket.getPositionY()));
			this.dodle.runAction(this.actionHandler.getMoveAction(0.5f,
					this.dodle.getPositionX(), this.dodle.getPositionY(),
					this.wySize.width / 2.0f, this.wySize.height / 2.0f));
			this.dodle.runAction(RotateBy.make(0.5f, 45.0f));
			this.mode = _MODE_DRAG_STIRRER;
                        fireParticleBegin();
		}
	}

	/**
     *
     */
    public void pourActionCallback() {
		this.oilBottle.stopAllActions();
		this.oilBottle.runAction(MoveTo.make(0.20000000298023223877f,
				this.oilBottle.getPositionX(), this.oilBottle.getPositionY(),
				this.oilBottle.getPositionX() - 100.0f,
				50.0f + this.oilBottle.getPositionY()));
		IntervalAction action = (IntervalAction) RotateBy.make(
				0.69999998807907104492f, -105.0f).autoRelease();
		this.oilBottle.runAction(action);
		setEnabled(true);
		this.egg.runAction(this.actionHandler.getRepeatingJumpAction(0.5f,
				this.egg.getPositionX(), this.egg.getPositionY(), 30));
                this.egg.runAction(this.actionHandler.getRepeatingRotateAction(0.5f, -25));
	}

	/**
     *
     */
    public void rotateObjectActonCallback() {
		executeOilPouring();
	}

	/**
     *
     * @param event
     * @return
     */
    @Override
	public boolean wyTouchesBegan(MotionEvent event) {
		WYPoint point = Director.getInstance().convertToGL(event.getX(),
				event.getY());
		switch (this.mode) {
		case _MODE_IGNITE_STOVE:
			if ((!(this.dodle.isEnabled()))
					|| (!(this.dodle.getBoundingBoxRelativeToWorld()
							.containsPoint(point)))
					|| (!(this.dodle.getBoundingBoxRelativeToWorld()
							.isIntersect(this.dodle
									.getBoundingBoxRelativeToWorld()))))
				;
			if (this.fadingState <= 9) {
				Scheduler.getInstance().unschedule(this.mixingTimer);
			}
			break;
		case _MODE_POUR_OIL:
			
			if ((this.oilBottle.getBoundingBoxRelativeToWorld()
					.containsPoint(point))) {
			this.oilBottle.stopAllActions();	
			this.oilBottle.runAction(MoveTo.make(0.20000000298023223877F,
					this.oilBottle.getPositionX(),
					this.oilBottle.getPositionY(),
					100.0F + this.oilBottle.getPositionX(),
					this.oilBottle.getPositionY() - 50.0f));
			this.oilBottle.runAction(getRotateActionWithCallback());
			soundHandler.playPouringSound();
			this.oilPan.setVisible(true);
			this.oilPan.setAlpha(00);
			setEnabled(false);
			this.mode = _MODE_DROP_EGG;
                        }
			break;
		case _MODE_DROP_EGG:
			if ((this.egg.getBoundingBoxRelativeToWorld()
					.containsPoint(point))) {
				
			this.egg.stopAllActions();
			bringToBack(this.basket);
			MoveTo localMoveTo = MoveTo.make(0.5F, this.egg.getPositionX(),
					this.egg.getPositionY(), this.fryEgg.getPositionX(),
					this.fryEgg.getPositionY());
			CallFunc localCallFunc = CallFunc.make(this, "eggDroppedCallback");
			FiniteTimeAction[] arrayOfFiniteTimeAction = new FiniteTimeAction[1];
			arrayOfFiniteTimeAction[0] = localCallFunc;
			Sequence localSequence = (Sequence) Sequence.make(localMoveTo,
					arrayOfFiniteTimeAction);
			this.egg.runAction(localSequence);
			setEnabled(false);
			this.mode = _MODE_IGNITE_STOVE;
                        }
			break;
		case _MODE_DRAG_STIRRER:
			Scheduler.getInstance().schedule(this.mixingTimer);
			break;
                default:
                    break;
		}
		return true;
	}

	/**
     *
     * @param event
     * @return
     */
    @Override
	public boolean wyTouchesEnded(MotionEvent event) {
		switch (this.mode) {
		case _MODE_DRAG_STIRRER:
			Scheduler.getInstance().unschedule(this.mixingTimer);
		}
		return true;
	}

	/**
     *
     * @param event
     * @return
     */
    @Override
	public boolean wyTouchesMoved(MotionEvent event) {
		WYPoint point = Director.getInstance().convertToGL(event.getX(),
				event.getY());
		switch (this.mode) {
		case _MODE_DRAG_STIRRER:
			if ((this.dodle.getBoundingBoxRelativeToWorld()
					.containsPoint(point))) {
				
			this.dodle.setPosition(point);
                        }
			break;
                default:
                    break;
		}
		return true;
	}
        private void fireParticleBegin() {
            this.particleSystem2 = ParticleSun.make();
            this.pan.addChild(this.particleSystem2);
            setParticlePosition();
        }
        
        private void setParticlePosition() {
            this.particleSystem2.setPosition(this.pan.getWidth() / 2.0f, 
                    this.pan.getHeight() / 1.5f);
            this.particleSystem2.setScale(10.0f);
            this.particleSystem2.setZOrder(-1);
        }

	/**
     *
     * @return
     */
    @Override
	public boolean onBackButton() {
		soundHandler.playButtonClickSound();
		Director.getInstance().popSceneWithTransition(
				(TransitionScene) LeftPushInTransition.make(0.5f,
						new MainMenuScene()).autoRelease());
		return true;
	}

	/**
     *
     */
    @Override
	public void onSoftBackButtonClicked() {
		SoundsHandler.getInstance().playButtonClickSound();
		onBackButton();
	}

	/**
     *
     */
    @Override
	public void onSoftNextButtonClicked() {
		Director.getInstance().replaceScene(
				(TransitionScene) RightPushInTransition.make(0.5f,
						new ToasterScene()).autoRelease());

	}

}// end class
