package com.algorithmi.maker.breakfast.scenes;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.algorithmi.maker.breakfast.data.SharedData;
import com.muneebahmad.microsun.hndl.ActionHandler;
import com.wiyun.engine.dialog.Dialog;
import com.wiyun.engine.dialog.DialogPopupTransition;
import com.wiyun.engine.nodes.Button;
import com.wiyun.engine.nodes.Director;
import com.wiyun.engine.nodes.Label;
import com.wiyun.engine.nodes.Scene;
import com.wiyun.engine.nodes.Sprite;
import com.wiyun.engine.opengl.Texture2D;
import com.wiyun.engine.transitions.RightPushInTransition;
import com.wiyun.engine.transitions.TransitionScene;
import com.wiyun.engine.types.WYColor3B;
import com.wiyun.engine.types.WYSize;
import com.wiyun.engine.utils.TargetSelector;

/**
 *
 * @author muneebahmad
 */
public class MainMenuScene extends Scene {

	private Sprite bg;
	private WYSize wySize;
	private Button playButton;
	private Button moreFunButton;
	private Button rateUsButton;
	private ActionHandler actionHandler;
        private Button endButton;
        private Sprite logo;
        final String app_id = "com.algorithmi.maker.breakfast.free.main";

	/**
     *
     */
    public MainMenuScene() {
		this.wySize = Director.getInstance().getWindowSize();
		this.actionHandler = new ActionHandler();
		SharedData.getInstance().soundsHandler.playBackgroundFinalSound();
		addBackground();
                addLogo();
		addButtons();
	} /* Constructor */

	private void addButtons() {
		playButton = Button.make(Sprite.make(Texture2D.make("ui/button.png")), 
                        Sprite.make(Texture2D.make("ui/button2.png")), this, null, null,
				"onPlayButtonClicked");
		playButton.setPosition(this.wySize.width / 2.0f,
				this.wySize.height / 4.0f);
		addChild(this.playButton);
		this.playButton.autoRelease(true);
                this.playButton.runAction(this.actionHandler.getMoveAction(0.5f, this.playButton.getPositionX(), 
                        this.wySize.height + 200.0f,
                        this.playButton.getPositionX(), this.playButton.getPositionY()));
		// this.playButton.runAction(this.actionHandler.getRepeatingRotateAction(
		// 0.30000001192092895508f, -4));
		// this.playButton.runAction(this.actionHandler.getRepeatingJumpAction(
		// 0.5f, this.playButton.getPositionX(),
		// this.playButton.getPositionY(), 10));

		moreFunButton = Button.make(Sprite.make(Texture2D.make("ui/morefun_button.png")),
				Sprite.make(Texture2D.make("ui/morefun_button2.png")), this, null, null,
                                "onMoreFunButtonClicked");
		this.moreFunButton.setPosition(this.wySize.width / 3.5f,
				this.wySize.height / 7.0f);
		addChild(this.moreFunButton);
		this.moreFunButton.autoRelease(true);
                this.moreFunButton.runAction(this.actionHandler.getMoveAction(0.7f, -250.0f, 
                        this.moreFunButton.getPositionY(), this.moreFunButton.getPositionX(), 
                        this.moreFunButton.getPositionY()));
		// this.moreFunButton.runAction(this.actionHandler
		// .getRepeatingRotateAction(0.30000001192092895508f, 10));
		// this.moreFunButton
		// .runAction(RepeatForever.make(Shake.make(1.0f, 1.0f)));
		rateUsButton = Button.make(Sprite.make(Texture2D.make("ui/rate_us_button.png")),
				Sprite.make(Texture2D.make("ui/rate_us_button2.png")), this, null, null,
                                "onRateUsButtonClicked");
		this.rateUsButton.setPosition(this.wySize.width / 1.40f,
				this.wySize.height / 7.0f);
		addChild(this.rateUsButton);
		this.rateUsButton.autoRelease(true);
                this.rateUsButton.runAction(this.actionHandler.getMoveAction(0.7f, 
                        this.wySize.width + 250.0f, this.rateUsButton.getPositionY(), this.rateUsButton.getPositionX(), 
                        this.rateUsButton.getPositionY()));
		// this.rateUsButton.runAction(this.actionHandler
		// .getRepeatingRotateAction(0.30000001192092895508f, 1));
		// this.rateUsButton.runAction(this.actionHandler.getRepeatingScaleAction(
		// 0.5f, 0.80000001192092895508f, 1.0f));
                
                this.endButton = Button.make(Sprite.make(Texture2D.make("ui/end_button1.png")), 
                        Sprite.make(Texture2D.make("ui/end_button2.png")), this, null, null,"onEndButtonClicked");
                this.endButton.setPosition(this.wySize.width / 2.0f, 
                        this.moreFunButton.getPositionY());
                addChild(this.endButton);
                this.endButton.autoRelease(true);
                this.endButton.runAction(this.actionHandler.getRepeatingJumpAction(
                0.5f, this.endButton.getPositionX(),
		this.endButton.getPositionY(), 10));
	}

	private void addBackground() {
		bg = Sprite.make(Texture2D.make("bgs/background_splash.png"));
		bg.setPosition(this.wySize.width / 2.0f, this.wySize.height / 2.0f);
		bg.setContentSize(this.wySize.width, this.wySize.height);
		bg.setAutoFit(true);
		addChild(bg);
		autoRelease(true);
	}
        
        private void addLogo() {
            this.logo = Sprite.make(Texture2D.make("art/logo_breakfast.png"));
            this.logo.setPosition(this.wySize.width / 2.0f, this.wySize.height - 80.0f);
            addChild(this.logo);
            this.logo.autoRelease(true);
            this.logo.runAction(this.actionHandler.getRepeatingJumpAction(
                0.9f, this.logo.getPositionX(),
		this.logo.getPositionY(), 12));
            //this.logo.runAction(this.actionHandler.getRepeatingScaleAction(0.5f, 0.8f, 1.2f));
            //this.logo.runAction(RepeatForever.make(Shake.make(2.0f, 2.0f)));
        }

	/**
     *
     * @return
     */
        
    @Override
	public boolean onBackButton() {

		showExitDialog("axure.otf", "ui/yes.png", "ui/no.png");
		//super.onBackButton();
		return true;
	}
	

	/**
     *
     * @param paramFloat
     * @param paramObject
     */
    
    public void onNOButton(float paramFloat, Object paramObject) {
		((Dialog) paramObject).dismiss(true);
		setEnabled(true);
	}
	

	/**
     *
     * @param paramFloat
     * @param paramObject
     **/
    
    public void onYESButton(float paramFloat, Object paramObject) {
		((Dialog) paramObject).dismiss(true);
		setEnabled(true);
		Director.getInstance().popScene();
	}
	

	/**
     *
     * @param paramString
     * @param paramString1
     * @param paramString2
     */
    
    protected void showExitDialog(String paramString, String paramString1,
			String paramString2) {
		setEnabled(false);
		Label localLabel1 = Label.make("\n" + "  Are you sure! you want" + "\n to exit? ", 32.0F,
				paramString, false, this.wySize.width - 50.0F);
		localLabel1.setColor(new WYColor3B(0, 0, 0));
		localLabel1.setAlignment(1);
		localLabel1.autoRelease(true);
		Sprite localSprite1 = Sprite.make(Texture2D.make(paramString1));
		localSprite1.setScaleY(1.20000004768371582031F);
		localSprite1.autoRelease(true);
		Label localLabel2 = Label.make("", 30.0F, paramString, false, 0F);
		localLabel2.setColor(new WYColor3B(0, 0, 0));
		localLabel2.setAlignment(1);
		localLabel2.setPosition(this.wySize.width / 2F - this.wySize.width
				/ 8.0F, this.wySize.height / 2F - 40.0F);
		localLabel2.autoRelease(true);
		Sprite localSprite2 = Sprite.make(Texture2D.make(paramString2));
		localSprite2.setScaleY(1.20000004768371582031F);
		localSprite2.autoRelease(true);
		Sprite bg = Sprite.make(Texture2D.make("ui/dialogs.png"));
		bg.autoRelease(true);
		Label localLabel3 = Label.make("", 30.0F, paramString, false, 0F);
		localLabel3.setColor(new WYColor3B(0, 0, 0));
		localLabel3.setAlignment(1);
		localLabel3.setPosition(this.wySize.width / 2F + this.wySize.width
				/ 8.0F, this.wySize.height / 2F - 40.0F);
		localLabel3.autoRelease(true);
		Dialog localDialog = Dialog.make();
		localDialog.setColor(new WYColor3B(0, 0, 0));
		localDialog.setBackground(bg);
		localDialog.setTitle(localLabel1);
		localDialog.setBackgroundPadding(110.0F, 40.0F, 0F, 40.0F);
		Object[] arrayOfObject1 = new Object[2];
		arrayOfObject1[0] = Float.valueOf(0F);
		arrayOfObject1[1] = localDialog;
		TargetSelector localTargetSelector = new TargetSelector(this,
				"onYESButton(float,Object)", arrayOfObject1);
		Object[] arrayOfObject2 = new Object[2];
		arrayOfObject2[0] = Float.valueOf(0F);
		arrayOfObject2[1] = localDialog;
		localDialog.addTwoColumnsButton(localSprite1, localLabel2,
				localTargetSelector, localSprite2, localLabel3,
				new TargetSelector(this, "onNOButton(float,Object)",
						arrayOfObject2));
		localDialog.setTransition(DialogPopupTransition.make());
		localDialog.setBackKeyEquivalentButtonIndex(1);
		localDialog.show(true);
		localDialog.autoRelease(true);
	}/* exit dialog */
	

	/**
     *
     */
    public void onPlayButtonClicked() {
		Director.getInstance().pushScene(
				(TransitionScene) RightPushInTransition.make(0.5f,
						new EggFryingScene()).autoRelease());
	}

	/**
     *
     */
    public void onRateUsButtonClicked() {
		try {
			((Activity) Director.getInstance().getContext())
					.startActivity(new Intent("android.intent.action.VIEW", Uri
							.parse("market://details?id="
									+ "com.algorithmi.maker.breakfast.free.main")));
		} catch (ActivityNotFoundException ae) {

		}
	}

	/**
     *
     */
    public void onMoreFunButtonClicked() {
		try {
			((Activity) Director.getInstance().getContext())
					.startActivity(new Intent("android.intent.action.VIEW", Uri
							.parse("market://search?q=pub:Algorithmi")));
		} catch (ActivityNotFoundException ae) {
                    Log.d("Tag MuneebAhmad", "ActivityNotFoundException", ae);
		}
	}
        
        /**
     *
     */
    public void onEndButtonClicked() {
           showExitDialog("axure.otf", "ui/yes.png", "ui/no.png");
        }

}// end class
