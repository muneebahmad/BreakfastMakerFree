/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algorithmi.maker.breakfast.scenes;

import android.app.Activity;

import com.algorithmi.maker.breakfast.data.SharedData;
import com.algorithmi.maker.breakfast.free.main.R;
import com.ivtc.qctu171657.AdListener;
import com.ivtc.qctu171657.MA;
import com.muneebahmad.microsun.hndl.ActionHandler;
import com.muneebahmad.microsun.interfaces.HudLayerInterface;
import com.muneebahmad.microsun.layers.HudLayer;
import com.muneebahmad.microsun.util.ParticleSmoke;
import com.wiyun.engine.actions.Action;
import com.wiyun.engine.actions.Animate;
import com.wiyun.engine.actions.CallFunc;
import com.wiyun.engine.actions.DelayTime;
import com.wiyun.engine.actions.FiniteTimeAction;
import com.wiyun.engine.actions.RepeatForever;
import com.wiyun.engine.actions.Sequence;
import com.wiyun.engine.nodes.Animation;
import com.wiyun.engine.nodes.Animation.IAnimationCallback;
import com.wiyun.engine.nodes.Button;
import com.wiyun.engine.nodes.Director;
import com.wiyun.engine.nodes.Scene;
import com.wiyun.engine.nodes.Sprite;
import com.wiyun.engine.opengl.Texture2D;
import com.wiyun.engine.particle.ParticleSystem;
import com.wiyun.engine.transitions.LeftPushInTransition;
import com.wiyun.engine.transitions.RightPushInTransition;
import com.wiyun.engine.transitions.TransitionScene;
import com.wiyun.engine.types.WYColor3B;
import com.wiyun.engine.types.WYSize;

/**
 *
 * @author muneebahmad
 */
public class DrinkSelectionScene extends Scene implements 
        HudLayerInterface, IAnimationCallback {

    private WYSize wySize = Director.getInstance().getWindowSize();
    private Sprite bg;
    private Sprite thermos;
    private HudLayer hudLayer;
    private Sprite switchOffSprite;
    private Button tSwitch;
    private ActionHandler actionHandler;
    private Sprite thermosIngrediantSprite;
    private Sprite pointingArrow;
    private ParticleSystem particleSystem;
    private MA mA;
	private AdListener addCallbackListener;

    
    /**
     *
     */
    public DrinkSelectionScene() {
        autoRelease(true);
        this.actionHandler = new ActionHandler();
        setTouchEnabled(true);
        setContents();
        
        /**
		 * Airplay-Bundled caching
		 * */
		if (mA == null) {
			((Activity) Director.getInstance().getContext()).runOnUiThread(new Runnable() { 
				@Override
				public void run() {
					mA = new MA((Activity) Director.getInstance().getContext(), addCallbackListener, false);
					mA.callSmartWallAd();
				}
			});
		}
    }
    
    private void setContents() {
        addBackground();
        addThermos();
        addThermosIngrediantSprite();
        showPointingArrow();
        addHudLayer();
    }
    
    private void addBackground() {
        this.bg = Sprite.make(Texture2D.make("bgs/bg2.png"));
        this.bg.setPosition(this.wySize.width / 2.0f, this.wySize.height / 2.0f);
        this.bg.setContentSize(this.wySize.width, this.wySize.height);
        this.bg.setAutoFit(true);
        addChild(this.bg);
        this.bg.autoRelease(true);
    }
    
    /*
    
    * */
    
    private void addThermos() {
        this.switchOffSprite = Sprite.make(Texture2D.make("art/switch2.png"));
        this.tSwitch = Button.make(switchOffSprite, switchOffSprite, null, 
                null, this, "onSwitchClicked");
        this.tSwitch.setPosition(100.0f, this.wySize.height / 2.5f - 38.0f);
        addChild(this.tSwitch);
        this.tSwitch.autoRelease(true);
        this.thermos = Sprite.make(Texture2D.make("art/thermos2.png"));
        this.thermos.setPosition(this.wySize.width / 2.0f,
				this.wySize.height / 2.0f - 50.0f);
        addChild(this.thermos);
        this.thermos.autoRelease(true);
    }

    private void addThermosIngrediantSprite() {
        this.thermosIngrediantSprite = Sprite.make(Texture2D.make("art/water_thermos2"));
        this.thermosIngrediantSprite.setPosition(this.thermos.getWidth() / 2.0f - 5.0f, 
                this.thermos.getHeight() / 3.0f);
        this.thermos.addChild(this.thermosIngrediantSprite);
        this.thermosIngrediantSprite.autoRelease(true);
        this.thermosIngrediantSprite.setZOrder(-5);
        
    }
    
    private void showPointingArrow() {
		this.pointingArrow = Sprite.make(Texture2D.make("art/arrow_up.png"));
		this.pointingArrow.setPosition(this.tSwitch.getPositionX(),
				this.tSwitch.getPositionY() + 60.0f);
		addChild(this.pointingArrow);
		this.pointingArrow.autoRelease(true);
		this.pointingArrow.runAction(this.actionHandler
				.getRepeatingBlinkAction(1.0f, 2));
	}
    
    /**
     *
     */
    public void smokeParticleBegin() {
        this.particleSystem = ParticleSmoke.make();
        addChild(this.particleSystem);
        setParticlePosition();
    }
    
    private void setParticlePosition() {
        this.particleSystem.setPosition(75.0f, this.tSwitch.getPositionY() + 135.0f);
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
    
    /**
     *
     * 
     *
     * 
     * */
    
    private void startSmoking() {
        DelayTime delay = DelayTime.make(6.0f);
        FiniteTimeAction[] arrayOfFiniteTimeAction = new FiniteTimeAction[2];
        arrayOfFiniteTimeAction[0] = CallFunc.make(this, "smokeParticleBegin");
        arrayOfFiniteTimeAction[1] = CallFunc.make(this, "runPointer");
        runAction(Sequence.make(delay, arrayOfFiniteTimeAction));
    }
    
    /**
     *
     */
    public void runPointer() {
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
    public void onSwitchClicked() {
        this.switchOffSprite.setDisplayFrame(Sprite.make(Texture2D.make("art/switch2.png"))
				.makeFrame());
        this.pointingArrow.stopAllActions();
        this.pointingArrow.setVisible(false);
        this.tSwitch.setEnabled(false);
        startSmoking();
       // smokeParticleBegin();
        Texture2D tex1 = Texture2D.make("art/water_thermos.png");
        Texture2D tex2 = Texture2D.make("art/water_thermos2.png");
        Texture2D tex3 = Texture2D.make("art/water_thermos3.png");
        Texture2D tex4 = Texture2D.make("art/water_thermos4.png");
        Texture2D tex5 = Texture2D.make("art/water_thermos5.png");
        
        Animation anim = (Animation) new Animation(0, 0.2f, tex1,
                tex3,
                tex4,
                tex5,
                tex1,
                tex2,
                tex3,
                tex4,
                tex5,
                tex1,
                tex2,
                tex3,
                tex4,
                tex5).autoRelease();
        anim.setCallback(this);
        
        Animate a = (Animate) Animate.make(anim).autoRelease();
        thermosIngrediantSprite.runAction((Action) RepeatForever.make(a).autoRelease());
    }
    
    /**
     *
     * @return
     */
    @Override
    public boolean onBackButton() {
        SharedData.getInstance().soundsHandler.playButtonClickSound();
        Director.getInstance().replaceScene((TransitionScene) LeftPushInTransition
                .make(0.5f, new PlateSelectionScene()).autoRelease());
        return true;
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
        this.particleSystem.setVisible(false);
        SharedData.getInstance().soundsHandler.playButtonClickSound();
        Director.getInstance().replaceScene((TransitionScene) RightPushInTransition
                .make(0.5f, new HotDrinkSelectionScene()).autoRelease());
    }

    /*
     * 
    * */

    /**
     *
     * @param i
     * @param i1
     */
    @Override
    public void onAnimationFrameChanged(int i, int i1) {
        
    }

    /**
     *
     * @param i
     */
    @Override
    public void onAnimationEnded(int i) {
        
    }
    
}//end class
