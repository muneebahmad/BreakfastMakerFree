package com.algorithmi.maker.breakfast.scenes;

import android.app.Activity;

import com.algorithmi.maker.breakfast.data.SharedData;
import com.algorithmi.maker.breakfast.free.main.R;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.Gender;
import com.google.ads.InterstitialAd;
import com.muneebahmad.microsun.hndl.ActionHandler;
import com.muneebahmad.microsun.hndl.SoundsHandler;
import com.muneebahmad.microsun.interfaces.HudLayerInterface;
import com.muneebahmad.microsun.layers.HudLayer;
import com.muneebahmad.microsun.util.Core;
import com.wiyun.engine.actions.CallFunc;
import com.wiyun.engine.actions.FiniteTimeAction;
import com.wiyun.engine.actions.MoveTo;
import com.wiyun.engine.actions.Sequence;
import com.wiyun.engine.actions.ease.EaseBounceIn;
import com.wiyun.engine.nodes.Button;
import com.wiyun.engine.nodes.Director;
import com.wiyun.engine.nodes.Scene;
import com.wiyun.engine.nodes.Sprite;
import com.wiyun.engine.opengl.Texture2D;
import com.wiyun.engine.transitions.LeftPushInTransition;
import com.wiyun.engine.transitions.RightPushInTransition;
import com.wiyun.engine.transitions.TransitionScene;
import com.wiyun.engine.types.WYColor3B;
import com.wiyun.engine.types.WYSize;

/**
 *
 * @author muneebahmad
 */
public class ToasterScene extends Scene implements HudLayerInterface {

	private ActionHandler actionHandler;
	private Sprite background;
	private Sprite toaster;
	private Sprite hook;
	private Sprite offButton;
	private Sprite breadToasted;
	private Button tosterOffButton;
	private Button whiteBreadButton;
	private Button brownBreadButton;
	private Sprite pointingArrow;
	private HudLayer hudLayer;
	private WYSize wySize;
	private InterstitialAd interstitial;

	/**
     *
     */
    public ToasterScene() {
		autoRelease(true);
		this.actionHandler = new ActionHandler();
		this.wySize = Director.getInstance().getWindowSize();
		setContents();
		
		/**
		 * Google -> AdMob
		 * */
	}

	private void setContents() {
		addBackground();
		addHudLayer();
		addToastMachine();
		addButtons();
	}

	private void addToastMachine() {
		toaster = Sprite.make(Texture2D.make("art/toaster.png"));
		this.toaster.setPosition(this.wySize.width / 2.0f,
				this.wySize.height / 2.0f - 70.0f);
		addChild(this.toaster, 1);
		this.toaster.autoRelease(true);
		this.hook = Sprite.make(Texture2D.make("art/hook.png"));
		this.hook.setPosition(this.toaster.getOriginX() - this.hook.getWidth()
				/ 2.0f, this.toaster.getPositionY() + (this.hook.getHeight()));
		addChild(this.hook);
		this.hook.autoRelease(true);
		this.offButton = Sprite.make(Texture2D.make("ui/button_toaster.png"));
		this.tosterOffButton = Button.make(this.offButton, this.offButton,
				null, null, this, "onToastButtonClicked");
		this.tosterOffButton.setPosition(this.toaster.getWidth()
				+ 45.0f,
				this.toaster.getPositionY() + this.toaster.getHeight() / 2.0f
						- 150.0f);
		addChild(this.tosterOffButton, 3);
		this.tosterOffButton.setEnabled(false);
		this.tosterOffButton.setZOrder(10);
		this.toaster.autoRelease(true);
	}

	private void addBackground() {
		background = Sprite.make(Texture2D.make("bgs/bg3.png"));
		this.background.setPosition(this.wySize.width / 2.0f,
				this.wySize.height / 2.0f);
		this.background.setContentSize(this.wySize.width, this.wySize.height);
		this.background.setAutoFit(true);
		addChild(this.background);
		this.background.autoRelease(true);
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

	private void addBreadToasted() {
		switch (Core.breadType) {
		case 0:
			this.breadToasted = Sprite.make(Texture2D.make("art/bread2.png"));
			break;
		case 1:
			this.breadToasted = Sprite.make(Texture2D.make("art/bread.png"));
			break;
		}
		this.breadToasted.setPosition(this.toaster.getPositionX(),
				this.toaster.getPositionY());
		addChild(this.breadToasted);
		this.breadToasted.autoRelease(true);
		showPointingArrow();
		this.whiteBreadButton.stopAllActions();
		this.brownBreadButton.stopAllActions();
	}

	private void addButtons() {
		this.whiteBreadButton = Button.make(Sprite.make(Texture2D.make("ui/bread_button.png")),
				Sprite.make(Texture2D.make("ui/bread_button.png")), this, null, null, "onWhiteBreadSelected");
		this.whiteBreadButton.setPosition(this.wySize.width / 2.0f - this.whiteBreadButton.getWidth(),
				this.wySize.height - this.whiteBreadButton.getHeight() * 2);
		addChild(this.whiteBreadButton);
		this.whiteBreadButton.autoRelease(true);
		this.brownBreadButton = Button.make(Sprite.make(Texture2D.make("ui/bread_button2.png")),
				Sprite.make(Texture2D.make("ui/bread_button2.png")), this, null, null, "onBrownBreadSelected");
		this.brownBreadButton.setPosition(this.wySize.width / 2.0f
				+ this.brownBreadButton.getWidth(), this.wySize.height
				- this.brownBreadButton.getHeight() * 2);
		addChild(this.brownBreadButton);
		this.brownBreadButton.autoRelease(true);
		this.whiteBreadButton.runAction(this.actionHandler
				.getRepeatingJumpAction(0.5f,
						this.whiteBreadButton.getPositionX(),
						this.whiteBreadButton.getPositionY(), 10));
		this.whiteBreadButton.runAction(this.actionHandler
				.getRepeatingScaleAction(0.5f, 0.80000001192092895508f, 1.0f));
		this.whiteBreadButton.runAction(this.actionHandler
				.getRepeatingRotateAction(0.5f, 15));

		this.brownBreadButton.runAction(this.actionHandler
				.getRepeatingJumpAction(0.5f,
						this.brownBreadButton.getPositionX(),
						this.brownBreadButton.getPositionY(), 10));
		this.brownBreadButton.runAction(this.actionHandler
				.getRepeatingScaleAction(0.5f, 1.0f, 0.80000001192092895508f));
		this.brownBreadButton.runAction(this.actionHandler
				.getRepeatingRotateAction(0.5f, -15));
	}

	private void showPointingArrow() {
		this.pointingArrow = Sprite.make(Texture2D.make("art/arrow_down.png"));
		this.pointingArrow.setPosition(this.tosterOffButton.getPositionX(),
				this.tosterOffButton.getPositionY() - 60.0f);
		addChild(this.pointingArrow, 2);
		this.pointingArrow.autoRelease(true);
		this.pointingArrow.runAction(this.actionHandler
				.getRepeatingBlinkAction(1.0f, 2));
	}

	/**
     *
     */
    public void onBrownBreadSelected() {
		if (this.brownBreadButton.isEnabled()) {
			Core.breadType = 0;
			addBreadToasted();
			this.brownBreadButton.setVisible(false);
			this.whiteBreadButton.setEnabled(false);
			this.tosterOffButton.setEnabled(true);
		}
	}

	/**
     *
     */
    public void onWhiteBreadSelected() {
		if (this.whiteBreadButton.isEnabled()) {
			Core.breadType = 1;
			addBreadToasted();
			this.whiteBreadButton.setVisible(false);
			this.brownBreadButton.setEnabled(false);
			this.tosterOffButton.setEnabled(true);
		}
	}

	/**
     *
     */
    public void onToastButtonClicked() {
		this.pointingArrow.stopAllActions();
		this.pointingArrow.setVisible(false);
		removeChild(this.pointingArrow, true);
		this.offButton.setDisplayFrame(Sprite.make(Texture2D.make("ui/button_toaster2.png"))
				.makeFrame());
		this.tosterOffButton.setEnabled(false);
		Sprite localSprite = this.hook;
		MoveTo localMoveTo = MoveTo.make(5.0f, this.hook.getPositionX(),
				this.hook.getPositionY(), this.hook.getPositionX(),
				this.hook.getPositionY() - this.toaster.getHeight()
						/ 1.70000004768371582031f);
		FiniteTimeAction[] mFiniteTimeAction = new FiniteTimeAction[1];
		mFiniteTimeAction[0] = CallFunc.make(this, "onProcessToastCompleted");
		localSprite.runAction(Sequence.make(localMoveTo, mFiniteTimeAction));
	}

	/**
     *
     */
    public void onProcessToastCompleted() {
		EaseBounceIn mEaseBounceIn = EaseBounceIn.make(MoveTo.make(0.5f,
				this.breadToasted.getPositionX(),
				this.breadToasted.getPositionY(),
				this.breadToasted.getPositionX(),
				this.breadToasted.getPositionY() + this.toaster.getHeight()
						/ 2.0f));
		this.breadToasted.runAction(mEaseBounceIn);
		setEnabled(true);
		this.whiteBreadButton.setEnabled(false);
		this.brownBreadButton.setEnabled(false);
		this.whiteBreadButton.stopAllActions();
		this.brownBreadButton.stopAllActions();
                this.offButton.setDisplayFrame(Sprite.make(Texture2D.make("ui/button_toaster.png"))
				.makeFrame());
		this.hudLayer.updateHudObjectVisibility(true, true, false);
		this.hudLayer.setNextButtonPosition(this.wySize.width - 80.0f,
			80.0f);
		HudLayer localHudLayer = this.hudLayer;
		this.hudLayer.getClass();
		localHudLayer.startNextButtonAction(4);
	}

	/**
     *
     */
    @Override
	public void onSoftBackButtonClicked() {
		onBackButton();
	}

	/**
     *
     */
    @Override
	public void onSoftNextButtonClicked() {
            SharedData.getInstance().soundsHandler.playButtonClickSound();
            Director.getInstance().replaceScene((TransitionScene) RightPushInTransition.make(0.5f, 
                    new PlateSelectionScene()).autoRelease());
	}

	/**
     *
     * @return
     */
    @Override
	public boolean onBackButton() {
		SoundsHandler.getInstance().playButtonClickSound();
		Director.getInstance().replaceScene(
				(TransitionScene) LeftPushInTransition.make(0.5f,
						new EggFryingScene()).autoRelease());
		return true;
	}

}// end class
