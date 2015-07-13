/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algorithmi.maker.breakfast.scenes;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.algorithmi.maker.breakfast.data.SharedData;
import com.algorithmi.maker.breakfast.free.main.R;
import com.muneebahmad.microsun.hndl.ActionHandler;
import com.muneebahmad.microsun.interfaces.HudLayerInterface;
import com.muneebahmad.microsun.layers.HudLayer;
import com.muneebahmad.microsun.util.Core;
import com.muneebahmad.microsun.util.ParticleSmoke;
import com.vungle.sdk.VunglePub;
import com.wiyun.engine.actions.CallFunc;
import com.wiyun.engine.actions.DelayTime;
import com.wiyun.engine.actions.FiniteTimeAction;
import com.wiyun.engine.actions.Sequence;
import com.wiyun.engine.nodes.Button;
import com.wiyun.engine.nodes.Director;
import com.wiyun.engine.nodes.RenderTexture;
import com.wiyun.engine.nodes.Scene;
import com.wiyun.engine.nodes.Scheduler;
import com.wiyun.engine.nodes.Sprite;
import com.wiyun.engine.nodes.Timer;
import com.wiyun.engine.opengl.Texture2D;
import com.wiyun.engine.particle.ParticleSystem;
import com.wiyun.engine.transitions.LeftPushInTransition;
import com.wiyun.engine.transitions.PageTurn3DTransition;
import com.wiyun.engine.transitions.TransitionScene;
import com.wiyun.engine.types.WYBlendFunc;
import com.wiyun.engine.types.WYColor3B;
import com.wiyun.engine.types.WYPoint;
import com.wiyun.engine.types.WYRect;
import com.wiyun.engine.types.WYSize;
import com.wiyun.engine.utils.TargetSelector;

/**
 *
 * @author muneebahmad
 */
public class EndGameScene extends Scene implements 
        HudLayerInterface {
    
    private final int _MAX_CLIP_DIVISOR = 70;
    private WYSize wySize;
    private ActionHandler actionHandler;
    private Sprite bg;
    private Sprite plateSprite;
    private HudLayer hudLayer;
    private Sprite bread;
    private Sprite bread2;
    private Sprite addOnSprite;
    private ParticleSystem particleSystem;
    private Sprite cupSprite;
    private Sprite drinkIngrediant;
    private Button camera;
    private Button eat;
    private Button home;
    private Button drink;
    private Bitmap bitmap;
    private int clipDrinkingOffset = 100;
    private TargetSelector drinkingSelector;
    private Timer drinkingTimer = null;
    private boolean isDrinking;
    private boolean isEating;
    private RenderTexture rt;
    private ArrayList<Sprite> spriteItemList;
    private TargetSelector targetSelector;
    private final String _SCREENSHOT_TAKEN = "Snapshot taken! & saved in your Gallery/Album";
    private final String _SCREENSHOT_CANNOT_BE_TAKEN = "Screenshot cannot be taken, "
            + "not enough space!";
    
    /**
     *
     */
    public EndGameScene() {
        this.actionHandler = new ActionHandler();
        this.wySize = Director.getInstance().getWindowSize();
        onRedraw();
        DelayTime localDelayTime = DelayTime.make(1.0f);
        FiniteTimeAction[] finiteTimeAction = new FiniteTimeAction[1];
        finiteTimeAction[0] = CallFunc.make(this, "onDelayedAction");
        runAction(Sequence.make(localDelayTime, finiteTimeAction));
        autoRelease(true);
    }
    
    private void setContents() {
        init();
        addBackground();
        addHudLayer();
        addPlate();
        showPreparedEggBread();
        addButtons();
        addCup();
        addCupIngrediant();
        smokeParticleBegin();
        showAddOn();
    }
    
    private void addBackground() {
        this.bg = Sprite.make(Texture2D.make("bgs/bg_plate.png"));
        this.bg.setPosition(this.wySize.width / 2.0f, this.wySize.height / 2.0f);
        this.bg.setContentSize(this.wySize.width, this.wySize.height);
        this.bg.setAutoFit(true);
        addChild(this.bg);
        this.bg.autoRelease(true);
    }
    
    private void addPlate() {
        this.plateSprite = Sprite.make(Texture2D.make(SharedData.getInstance().player.usedPlate
                .getImageResourceName()));
        this.plateSprite.setPosition(this.wySize.width / 2.0f, this.wySize.height / 2.5f);
        addChild(this.plateSprite);
        this.plateSprite.setScale(0.75f);
        this.plateSprite.autoRelease(true);
    }
    
    private void showAddOn() {
        this.addOnSprite = Sprite.make(Texture2D.make(SharedData.getInstance().player.usedAddOn
                .getImageResourceName()));
        this.addOnSprite.setPosition(this.wySize.width / 1.5f, this.wySize.height / 3.8f);
        addChild(this.addOnSprite);
        this.addOnSprite.setScale(0.60f);
        this.addOnSprite.autoRelease(true);
        this.spriteItemList.add(this.addOnSprite);
    }
    
    private void showPreparedEggBread() {
        this.rt = RenderTexture.make((int) this.wySize.width, 
                (int) this.wySize.height);
        this.rt.setPosition(WYPoint.make(this.wySize.width / 2.0f,
                this.wySize.height / 2.0f));
        addChild(this.rt, 5);
        this.rt.beginRender();
        int b = Core.breadType;
        if (b == 0) {
            bread = Sprite.make(Texture2D.make("art/bread2.png"));
            bread2 = Sprite.make(Texture2D.make("art/bread2.png"));
        }else if (b == 1) {
            bread = Sprite.make(Texture2D.make("art/bread.png"));
            bread2 = Sprite.make(Texture2D.make("art/bread.png"));
        }
        bread.setPosition(this.plateSprite.getPositionX() - 80.0f, 
                this.plateSprite.getPositionY() + 80.0f);
        this.bread.visit();
        this.bread.autoRelease(true);
        this.bread2.setPosition(this.bread.getPositionX(),
                this.bread.getPositionY() - 140.0f);
        this.bread2.setRotation(-25.0f);
        this.bread2.visit();
        this.bread2.autoRelease(true);
        Sprite egg = Sprite.make(Texture2D.make("art/fryegg3.png"));
        egg.setPosition(this.bread.getPositionX() + 150.0f, this.bread.getPositionY() - 40.0f);
        egg.setRotation(90.0f);
        egg.visit();
        egg.autoRelease(true); 
        this.rt.endRender();
    }
    
    private void addCup() {
        this.cupSprite = Sprite.make(Texture2D.make(SharedData.getInstance().
                player.usedCup.getImageResourceName()));
        this.cupSprite.setPosition(this.wySize.width - 130.0f, 
                this.wySize.height / 1.4f);
        addChild(this.cupSprite, 1);
        this.cupSprite.setScale(0.60f);
        bringToFront(this.cupSprite);
        this.cupSprite.autoRelease(true);
        this.cupSprite.setScale(0.5f);
    }
    
    private void addCupIngrediant() {
        this.drinkIngrediant = Sprite.make(Texture2D.make("art/cup_ingrediant.png"));
        this.drinkIngrediant.setPosition(this.cupSprite.getWidth() / 2.3f, 
                this.cupSprite.getHeight() / 1.8f);
        this.cupSprite.addChild(this.drinkIngrediant);
        this.drinkIngrediant.setZOrder(-5);
        this.drinkIngrediant.autoRelease(true);
        this.drinkIngrediant.setColor(SharedData.getInstance().player.usedDrink.
                getColor());
    }
    
    private void smokeParticleBegin() {
        this.particleSystem = ParticleSmoke.make();
        this.cupSprite.addChild(this.particleSystem);
        setParticlePosition();
    }
    
    private void setParticlePosition() {
        this.particleSystem.setPosition(this.cupSprite.getWidth() / 2.0f - 20.0f, 
                this.cupSprite.getHeight() - 40.0f);
        this.particleSystem.setScale(3.0f, 1.0f);
        this.particleSystem.setZOrder(-1);
    }
    
    //CHECK
    private void addButtons() {
        this.camera = Button.make(Sprite.make(Texture2D.make("art/camera1.png")), 
                Sprite.make(Texture2D.make("art/camera2.png")), null, null, 
                this, "onCameraButtonClicked");
        this.camera.setPosition(this.camera.getWidth() / 2.0f, this.wySize.height / 1.2f - 20.0f);
        addChild(this.camera);
        this.camera.autoRelease(true);
        this.eat = Button.make(Sprite.make(Texture2D.make("art/eat1.png")), 
                Sprite.make(Texture2D.make("art/eat2.png")), null, null, 
                this, "onEatButtonClicked");
        this.eat.setPosition(this.camera.getPositionX(), 
                this.camera.getPositionY() + 80.0f);
        addChild(this.eat);
        this.eat.autoRelease(true);
        this.drink = Button.make(Sprite.make(Texture2D.make("art/drink1.png")), 
                Sprite.make(Texture2D.make("art/drink2.png")), null, null,
                this, "onDrinkButtonClicked");
        this.drink.setPosition(this.camera.getPositionX(), 
                this.camera.getPositionY() - 80.0f);
        addChild(this.drink);
        this.drink.autoRelease(true);
        this.home = Button.make(Sprite.make(Texture2D.make("art/home1.png")), 
                Sprite.make(Texture2D.make("art/home2.png")), this, null, null, 
                "onHomeButtonClicked");
        this.home.setPosition(this.wySize.width - 80.0f,
				80.0f);
        addChild(this.home);
        this.home.autoRelease(true);
        this.camera.runAction(this.actionHandler
				.getRepeatingJumpAction(0.7f,
						this.camera.getPositionX(),
						this.camera.getPositionY(), 10));
        /**
		this.camera.runAction(this.actionHandler
				.getRepeatingScaleAction(0.5f, 1.0f, 1.2f));
		this.camera.runAction(this.actionHandler
				.getRepeatingRotateAction(0.5f, 45));
                                * */
         this.eat.runAction(this.actionHandler
				.getRepeatingJumpAction(1.0f,
						this.eat.getPositionX(),
						this.eat.getPositionY(), 10));
         /**
		this.eat.runAction(this.actionHandler
				.getRepeatingScaleAction(0.5f, 1.2f, 1.0f));
		this.eat.runAction(this.actionHandler
				.getRepeatingRotateAction(0.5f, 45));
                                * */
         this.drink.runAction(this.actionHandler
				.getRepeatingJumpAction(0.4f,
						this.drink.getPositionX(),
						this.drink.getPositionY(), 10));
         
         this.home.runAction(this.actionHandler
				.getRepeatingJumpAction(0.5f,
						this.home.getPositionX(),
						this.home.getPositionY(), 10));
         this.home.runAction(this.actionHandler
					.getRepeatingPointingAction(0.5F,
							this.home.getPositionX() - 30.0F,
							this.home.getPositionY(),
							this.home.getPositionX(),
							this.home.getPositionY()));
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
     * @return
     */
    @Override
    public boolean onBackButton() {
        SharedData.getInstance().soundsHandler.playButtonClickSound();
        Director.getInstance().replaceScene((TransitionScene) LeftPushInTransition
                .make(0.5f, new DecorationScene()).autoRelease());
        this.particleSystem.stopAllActions();
        this.particleSystem.setVisible(false);
        return true;
    }
    
    /***
     * 
     * 
     * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
     * $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ chk unsigned $$$$$$$$$$$$$$$$$$$$$$$
     * 
     */
    
    private void init() {
        this.isEating = false;
        this.spriteItemList = new ArrayList();
        this.spriteItemList.clear();
        setTouchEnabled(true);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Float.valueOf(0.0f);
        this.drinkingSelector = new TargetSelector(this, "onDrinkingTick(float)", arrayOfObject);
        if (this.drinkingTimer == null) {
            this.drinkingTimer = new Timer(this.drinkingSelector, 0.10000000149011611938f);
        }
    }
    
    private Bitmap makeBitmap() {
        Bitmap localBitmap;
        localBitmap = null;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            this.bitmap = BitmapFactory.
                    decodeFile(new File(Environment.getExternalStorageDirectory().
                    getPath() + "/AlgorithmiApps/breakfast.png").
                    getAbsolutePath());
            if (this.bitmap == null) {
                this.bitmap = BitmapFactory.decodeResource(Director.getInstance().
                        getContext().getResources(), R.drawable.ic_launcher);
                localBitmap = this.bitmap;
            }
        }
        return localBitmap;
    }
    
    /**
     * 
     * screenshot
     * @return 
     */
   
     
    private boolean takeScreenShot() {
        int i;
        i = 0;
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                if (new File(Environment.getExternalStorageDirectory().
                        getPath() + "/AlgorithmiApps/breakfast_free.png").exists()) {
                    i = 1;
                }
                if (i == 1) {
                    new File(Environment.getExternalStorageDirectory().
                            getPath() + "/AlgorithmiApps/breakfast_free.png").delete();
                    Director.getInstance().makeScreenshot(Environment.getExternalStorageDirectory().
                        getPath() + "/AlgorithmiApps/breakfast_free.png");
                    makeToast(_SCREENSHOT_TAKEN);
                } else {
                    Director.getInstance().makeScreenshot(Environment.getExternalStorageDirectory().
                        getPath() + "/AlgorithmiApps/breakfast_free.png");
                    makeToast(_SCREENSHOT_TAKEN);
                }  
            }else {
                makeToast(_SCREENSHOT_CANNOT_BE_TAKEN);
            }
            this.camera.setEnabled(false);
            
            //removeChild(this.camera, true);
       return true;
    }
    
    
    private void makeToast(final String text) {
        ((Activity) Director.getInstance().getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(Director.getInstance().getContext(), 
                        text, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
            }
        });
    }
    
    /**
     * 
     * 
     * @return 
     */
    
    private void onRedraw() {
        setContents();
        if (SharedData.getInstance().isRedrawingRequired) {
            this.targetSelector = new TargetSelector(this, "delayedRedraw", null);
            schedule(this.targetSelector, 0.10000000149011611938f);
        }
    }
    
    /**
     *
     */
    public void delayedRedraw() {
        removeAllChildren(true);
        setContents();
        unschedule(this.targetSelector);
    }
    
    /**
     *
     */
    public void onDelayedAction() {
        SharedData.getInstance().isRedrawingRequired = true;
        SharedData.getInstance().coatingBaseSceneReference = this;
    }
    
    /**
     *
     * @param paramFloat
     */
    public void onDrinkingTick(float paramFloat) {
        SharedData.getInstance().soundsHandler.palySipSound();
        this.clipDrinkingOffset = (-1 + this.clipDrinkingOffset);
        this.drinkIngrediant.setClipRect(WYRect.
                make(0.0f, 0.0f, this.drinkIngrediant.getWidth(), 
                this.drinkIngrediant.getHeight() / 100.0f * this.clipDrinkingOffset), true);
        if (this.clipDrinkingOffset < 0) {
            Scheduler.getInstance().unschedule(this.drinkingTimer);
        }
    }
    
    /***
     * 
     * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>chk end <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
     * $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
     */
    
    public void onCameraButtonClicked() {
        takeScreenShot();
        this.camera.stopAllActions();
        this.camera.runAction(this.actionHandler.getMoveAction(0.8f,
                    this.camera.getPositionX(), this.camera.getPositionY(), this.wySize.width + 250.0f, 
                    this.camera.getPositionY()));
    }
    
    /**
     *
     */
    public void onEatButtonClicked() {
        SharedData.getInstance().soundsHandler.playButtonClickSound();
        this.isEating = true;
        this.eat.stopAllActions();
        this.eat.runAction(this.actionHandler.getMoveAction(0.8f, 
                    this.eat.getPositionX(), this.eat.getPositionY(), this.eat.getPositionX() + this.wySize.width + 250.0f, 
                    this.eat.getPositionY()));
    }
    
    /**
     *
     */
    public void onDrinkButtonClicked() {
        if (this.particleSystem != null) {
            this.particleSystem.stopSystem();
            removeChild(this.particleSystem, true);
        }
        SharedData.getInstance().soundsHandler.playButtonClickSound();
        this.isDrinking = true;
        this.drink.stopAllActions();
        this.drink.runAction(this.actionHandler.getMoveAction(0.8f, 
                    this.drink.getPositionX(), this.drink.getPositionY(), this.wySize.width + 250.0f, 
                    this.drink.getPositionY()));
    }
    
    /**
     *
     */
    public void onHomeButtonClicked() {
        SharedData.getInstance().soundsHandler.playButtonClickSound();
        Director.getInstance().popSceneWithTransition((TransitionScene) 
                PageTurn3DTransition.make(0.5f, 
                new MainMenuScene()).autoRelease());
        VunglePub.displayIncentivizedAdvert(false);
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
        
    }
    
    /**
     *
     * @param event
     * @return
     */
    @Override
    public boolean wyTouchesBegan(MotionEvent event) {
        Director.getInstance().convertToGL(event.getX(), event.getY());
        WYPoint point = this.rt.convertTouchToNodeSpace(event);
        if (this.isEating) {
            SharedData.getInstance().soundsHandler.playEatSound();
            Sprite sprite = Sprite.make(Texture2D.make("art/eating_mask.png"));
            sprite.setPosition(point);
            sprite.setBlendFunc(new WYBlendFunc(0, 771));
            this.rt.beginRender();
            sprite.visit();
            this.rt.endRender();
            try {
                 for (int i = 0; i <= this.spriteItemList.size(); i++) {
                    if (((Sprite) this.spriteItemList.get(i)).getBoundingBoxRelativeToWorld().
                       containsPoint(point)) {
                            ((Sprite) this.spriteItemList.get(i)).setVisible(false);
                            this.spriteItemList.remove(i);
                            }
                        }
                 } catch (java.lang.IndexOutOfBoundsException e) {
            Log.d("arr_err", "java.lang.IndexOutOfBoundsException", e);
            }
            //this.eat.setEnabled(false);
            this.eat.setVisible(false);
            //removeChild(this.eat, true);
        }
        if ((this.isDrinking) && (this.drinkIngrediant.getBoundingBoxRelativeToWorld().
                containsPoint(point)) && (this.clipDrinkingOffset > 0)) {
            Scheduler.getInstance().schedule(drinkingTimer);
            //this.drink.setEnabled(false);
            this.drink.setVisible(false);
            //removeChild(this.drink, true);
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
        Scheduler.getInstance().unschedule(drinkingTimer);
        return true;
    }
    
}//end class
