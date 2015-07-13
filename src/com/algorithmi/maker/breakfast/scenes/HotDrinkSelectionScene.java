/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algorithmi.maker.breakfast.scenes;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.algorithmi.maker.breakfast.data.SharedData;
import com.algorithmi.maker.breakfast.free.main.R;
import com.algorithmi.maker.breakfast.model.Cup;
import com.algorithmi.maker.breakfast.model.Drink;
import com.heyzap.sdk.ads.HeyzapAds;
import com.heyzap.sdk.ads.InterstitialAd;
import com.muneebahmad.microsun.hndl.ActionHandler;
import com.muneebahmad.microsun.interfaces.HudLayerInterface;
import com.muneebahmad.microsun.layers.GridInterface;
import com.muneebahmad.microsun.layers.GridLayer;
import com.muneebahmad.microsun.layers.HudLayer;
import com.muneebahmad.microsun.util.Core;
import com.muneebahmad.microsun.util.ParticlePouring;
import com.wiyun.engine.actions.CallFunc;
import com.wiyun.engine.actions.DelayTime;
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
import com.wiyun.engine.types.WYRect;
import com.wiyun.engine.types.WYSize;
import com.wiyun.engine.utils.TargetSelector;

/**
 *
 * @author muneebahmad
 */
public class HotDrinkSelectionScene extends Scene implements 
        HudLayerInterface, GridInterface {

    private final int _MAX_CLIP_HEIGHT_DIVISOR = 100;
    private int clipHeightOffset;
    private Sprite bg;
    private WYSize wySize = Director.getInstance().getWindowSize();
    private ActionHandler actionHandler;
    private HudLayer hudLayer;
    private GridLayer gridLayer;
    private Sprite cupSprite;
    private Sprite drinkIngrediant;
    private Sprite thermos;
    private Timer pouringTimer;
    private TargetSelector pouringSelector;
    private ParticleSystem pourParticles;
    private Button drinksButton;
    private Button cupsButton;
    private Sprite thermosIngrediantSprite;
    
    /**
     *
     */
    public HotDrinkSelectionScene() {
        autoRelease(true);
        this.actionHandler = new ActionHandler();
        setTouchEnabled(true);
        setContents();
        
        /**
		 * Heyzap
		 */
		HeyzapAds.start((Activity) Director.getInstance().getContext());
		InterstitialAd.display((Activity) Director.getInstance().getContext());
    }
    
    private void setContents() {
        initTimer();
        addBg();
        addButtons();
        addCup();
        addCupIngrediant();
        addThermos();
        addThermosIngrediantSprite();
        addHudLayer();
    }
    
    private void addBg() {
        this.bg = Sprite.make(Texture2D.make("bgs/bg3.png"));
        this.bg.setPosition(this.wySize.width / 2.0f, this.wySize.height / 2.0f);
        this.bg.setContentSize(this.wySize.width, this.wySize.height);
        this.bg.setAutoFit(true);
        addChild(this.bg);
        this.bg.autoRelease(true);
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
    
    private void addCup() {
        SharedData.getInstance().
                player.usedCup = ((Cup) SharedData.getInstance().cupList.get(0));
        this.cupSprite = Sprite.make(Texture2D.make(SharedData.getInstance().
                player.usedCup.getImageResourceName()));
        this.cupSprite.setPosition(this.wySize.width / 2.0f, this.wySize.height / 2.5f);
        addChild(this.cupSprite, 1);
        this.cupSprite.setScale(0.60f);
        bringToFront(this.cupSprite);
        this.cupSprite.autoRelease(true);
    }
    
    private void addCupIngrediant() {
        this.drinkIngrediant = Sprite.make(Texture2D.make("art/cup_ingrediant.png"));
        this.drinkIngrediant.setPosition(this.cupSprite.getWidth() / 2.3f, 
                this.cupSprite.getHeight() / 1.8f);
        this.cupSprite.addChild(this.drinkIngrediant);
        this.drinkIngrediant.setZOrder(-5);
        this.drinkIngrediant.autoRelease(true);
        this.drinkIngrediant.setVisible(false);
    }
    
    private void addThermos() {
        this.thermos = Sprite.make(Texture2D.make("art/thermos2.png"));
        this.thermos.setPosition(this.wySize.width + 300.0f, 
                this.wySize.height / 1.3f);
        addChild(this.thermos);
        this.thermos.autoRelease(true);
        this.thermos.setVisible(false);
    }
    
     private void addThermosIngrediantSprite() {
        this.thermosIngrediantSprite = Sprite.make(Texture2D.make("art/water_white.png"));
        this.thermosIngrediantSprite.setPosition(this.thermos.getWidth() / 2.0f - 5.0f, 
                this.thermos.getHeight() / 3.0f);
        this.thermos.addChild(this.thermosIngrediantSprite);
        this.thermosIngrediantSprite.autoRelease(true);
        this.thermosIngrediantSprite.setZOrder(-5);
        
    }
    
    private void addButtons() {
        drinksButton = Button.make(Sprite.make(Texture2D.make("art/drinks_button.png")), 
                Sprite.make(Texture2D.make("art/drinks_button.png")), this, null, null, "onDrinksButtonClicked");
        drinksButton.setPosition(this.wySize.width / 3.0f, 
                this.wySize.height - drinksButton.getHeight() / 2.0f - 90.0f);
        addChild(drinksButton);
        drinksButton.autoRelease(true);
        this.drinksButton.setEnabled(false);
        cupsButton = Button.make(Sprite.make(Texture2D.make("art/cup_button.png")), 
                Sprite.make(Texture2D.make("art/cup_button.png")), this, null, null, "onCupsButtonClicked");
        cupsButton.setPosition(this.wySize.width / 1.3f, drinksButton.getPositionY());
        addChild(cupsButton);
        cupsButton.autoRelease(true);
        drinksButton.runAction(this.actionHandler.getRepeatingJumpAction(0.5f, 
                drinksButton.getPositionX(), 
                drinksButton.getPositionY(), 10));
        //drinksButton.runAction(this.actionHandler.getRepeatingRotateAction(0.5f, 15));
        drinksButton.runAction(this.actionHandler.getRepeatingScaleAction(0.5f, 
                0.69999999f, 0.80000001192092895508f));
        cupsButton.runAction(this.actionHandler.getRepeatingJumpAction(0.5f, 
                cupsButton.getPositionX(), 
                cupsButton.getPositionY(), 10));
        cupsButton.runAction(this.actionHandler.getRepeatingScaleAction(0.5f, 
                1.0f, 0.80000001192092895508f));
    }
    
    private void initTimer() {
        Object[] arrayOfObj = new Object[1];
        arrayOfObj[0] = Float.valueOf(0.0f);
        this.pouringSelector = new TargetSelector(this, "onPouringTick(float)", 
                arrayOfObj);
        if (this.pouringTimer == null) {
            this.pouringTimer = new Timer(this.pouringSelector, 
                    0.10000000149011611938f);
        }else {
            Scheduler.getInstance().unschedule(pouringTimer);
        }
    }
    
    private void setPourColor(float r, float g, float b) {
        this.pourParticles.setStartColorVariance(r / 255.0f, 
                g / 255.0f, b / 255.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    /**
     *
     */
    public void executeDrinkPouring() {
        this.pourParticles = ParticlePouring.make();
        this.pourParticles.scale(2.5f);
        this.pourParticles.setPosition(this.cupSprite.getPositionX() - 5.0f,
                this.thermos.getPositionY() - this.thermos.getHeight() / 4.0f - 5.0f);
        WYColor3B color = SharedData.getInstance().player.usedDrink.getColor();
        setPourColor(color.r, color.g, color.b);
        addChild(this.pourParticles);
        this.pourParticles.setZOrder(1);
        this.pourParticles.autoRelease(true);
    }
    
    private void makeGridView(int paramInt) {
        this.hudLayer.updateHudObjectVisibility(false, false, false);
        if (this.gridLayer != null) {
            this.gridLayer.removeGridViewWithAction();
        }
        this.gridLayer = new GridLayer(this);
        this.gridLayer.setPosition(0.0f, this.wySize.height - 120.0f);
        addChild(this.gridLayer, 25);
        this.gridLayer.autoRelease(true);
        this.gridLayer.setZOrder(50);
        bringToFront(this.gridLayer);
        this.gridLayer.populateGrid();
        MoveTo localMoveTo = MoveTo.make(0.5f, 0.0f, -1000.0f, 0.0f, 0.0f);
        this.gridLayer.runAction(localMoveTo);
    }
    
    /**
     *
     */
    public void onDrinksButtonClicked() {
         Core.gridModeCurrent = 2;
        makeGridView(Core.gridModeCurrent);
    }
    
    /**
     *
     * @param paramFloat
     */
    public void onPouringTick(float paramFloat) {
        
        this.clipHeightOffset = (1 + this.clipHeightOffset);
        this.drinkIngrediant.setClipRect(WYRect.make(0.0f, 0.0f, 
                this.drinkIngrediant.getWidth(), 
                this.drinkIngrediant.getHeight() / 100.0f * this
                .clipHeightOffset), true);
        if (this.clipHeightOffset >= 60) {
            Scheduler.getInstance().unschedule(pouringTimer);
            this.pourParticles.stopSystem();
            this.hudLayer.updateHudObjectVisibility(true, true, false);
            this.hudLayer.setNextButtonPosition(this.wySize.width - 80.0f,
				80.0f);
            HudLayer localHudLayer = this.hudLayer;
		this.hudLayer.getClass();
		localHudLayer.startNextButtonAction(4);
            RotateBy rotate = (RotateBy) RotateBy.make(0.69999998807907104492f, 45.0f).
                    autoRelease();
            this.thermos.runAction(rotate);
            //this.pouringWhite.setVisible(false);
            MoveTo move = (MoveTo) MoveTo.make(0.5f, 
                    this.thermos.getPositionX(), this.thermos.getPositionY(), 
                    + this.wySize.width + 300.0f, this.thermos.getPositionY()).
                    autoRelease();
            this.thermos.runAction(move);
        }
    }
    
    /**
     *
     */
    public void onCupsButtonClicked() {
        Core.gridModeCurrent = 3;
        makeGridView(Core.gridModeCurrent);
    }
    
    /**
     *
     * @return
     */
    @Override
    public boolean onBackButton() {
        SharedData.getInstance().soundsHandler.playButtonClickSound();
        Director.getInstance().replaceScene((TransitionScene) LeftPushInTransition
                .make(0.5f, new DrinkSelectionScene()).autoRelease());
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
        SharedData.getInstance().soundsHandler.playButtonClickSound();
        Director.getInstance().replaceScene((TransitionScene) RightPushInTransition
                .make(0.5f, new DecorationScene()).autoRelease());
    }

    /**
     *
     */
    @Override
    public void onCrossButtonClicked() {
        this.hudLayer.updateHudObjectVisibility(true, false, false);
        this.hudLayer.setEnabled(true);
        this.gridLayer.removeGridViewWithAction();
        Core.gridModeCurrent = 0;
    }

    /**
     *
     * @param paramString
     */
    @Override
    public void onGridItemClicked(String paramString) {
        this.gridLayer.removeGridViewWithAction();
        switch (Core.gridModeCurrent) {
            case 2:
                try {
                for (int i = 0; i <= SharedData.getInstance().drinkList.size(); i++) {
                    if ((((Drink) SharedData.getInstance().drinkList.get(i)).
                            getId().equalsIgnoreCase(paramString))) {
                        replaceDrinks(i);
                        Core.gridModeCurrent = 0;
                    } else if ((((Drink) SharedData.getInstance().
                            drinkList.get(i)).isLocked())) {
                        //TODO~
                    }
                }//end loop
                }catch (java.lang.IndexOutOfBoundsException jlE) {
                    Log.d("Muneeb", "Exception count 1", jlE);
                }
          //      this.cupsButton.setEnabled(false);
            //    this.cupsButton.stopAllActions();
                
              //  this.drinksButton.setEnabled(false);
               // this.drinksButton.stopAllActions();
                
                
                this.hudLayer.updateHudObjectVisibility(true, false, false);
                break;
            
            case 3:
        try {
        for (int i = 0; i <= SharedData.getInstance().cupList.size(); i++) {
        if ((((Cup)SharedData
                .getInstance().cupList.get(i)).getId().equalsIgnoreCase(paramString))) {
            replaceCup(i);
            this.drinksButton.setEnabled(true);
            Core.gridModeCurrent = 0;
            }else if ((((Cup)SharedData
                    .getInstance().cupList.get(i)).isLocked())) {
                //TODO~
            }
        }//end loop
        } catch (java.lang.IndexOutOfBoundsException e) {
            Log.d("MuneebAhmad", "Exception: java.lang.IndexOutOfBoundsException", e);
        }
        //this.cupsButton.setEnabled(false);
        //this.cupsButton.stopAllActions();
        //this.cupsButton.setVisible(false);
        this.hudLayer.updateHudObjectVisibility(true, false, false);
                break;
}//end switch
    }
    
    /**
     *
     * @param cupId
     */
    public void replaceCup(int cupId) {
        if ((((Cup)SharedData
                    .getInstance().cupList.get(cupId)).isLocked())) {
            makeToastDrink();
        } else {
        SharedData.getInstance().player
                .usedCup = ((Cup) SharedData.getInstance().cupList.get(cupId));
        this.cupSprite.setDisplayFrame(Sprite
                .make(Texture2D.make(SharedData.getInstance().player.usedCup
                .getImageResourceName())).makeFrame());
        this.cupsButton.stopAllActions();
        this.cupsButton.runAction(this.actionHandler.getMoveAction(0.5f, 
                this.cupsButton.getPositionX(), this.cupsButton.getPositionY(), this.wySize.width + 200.0f,
                this.cupsButton.getPositionY()));
        this.cupsButton.setEnabled(false);
        //this.cupsButton.setVisible(false);
        }
    }
    
    private void replaceDrinks(int drinkId) {
        //this.drinkIngrediant.setVisible(true);
        if ((((Drink) SharedData.getInstance().
                            drinkList.get(drinkId)).isLocked())) {
            makeToastDrink();
        } else {
        SharedData.getInstance().player
                .usedDrink = ((Drink) SharedData.getInstance().drinkList.get(drinkId));
        this.drinkIngrediant.setColor(SharedData.getInstance().player.usedDrink.getColor());
        DelayTime delay = DelayTime.make(1.0f);
        FiniteTimeAction[] arrayOfFiniteTimeAction = new FiniteTimeAction[2];
        arrayOfFiniteTimeAction[0] = CallFunc.make(this, "startPouringDrink");
        arrayOfFiniteTimeAction[1] = CallFunc.make(this, "displayDrinks");
        this.drinkIngrediant.runAction(Sequence.make(delay, arrayOfFiniteTimeAction));
        this.thermosIngrediantSprite.
                setColor(SharedData.getInstance().player.usedDrink.getColor());
        this.drinksButton.stopAllActions();
        this.drinksButton.runAction(this.actionHandler.getMoveAction(0.5f, this.drinksButton.getPositionX(), 
                this.drinksButton.getPositionY(), -200.0f, this.drinksButton.getPositionY()));
        this.drinksButton.setEnabled(false);
        //this.drinksButton.setVisible(false);
        }
    }
    
    private void makeToastDrink() {
        ((Activity) Director.getInstance().getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(Director.getInstance().getContext(), "This item is locked!"
                        + " Please upgrade to pro version to unlock", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();            }
        });
    }
    
    /**
     *
     */
    public void displayDrinks() {
       // this.drinkIngrediant.setVisible(true);
    }
    
    /**
     *
     */
    public void startPouringDrink() {
        this.thermos.setVisible(true);
        this.thermos.runAction(this.actionHandler.
                getMoveAction(0.2000000298023223877f, 
                this.thermos.getPositionX(), this.thermos.getPositionY(), 
                this.thermos.getWidth() + 100.0f, this.thermos.getPositionY() - 20.0f));
        IntervalAction action1 = (IntervalAction) RotateBy.make(1.29999995231628417969f, 
                -45.0f).autoRelease();
        CallFunc func = (CallFunc) CallFunc.make(this, "rotateThermosActionEnded").
                autoRelease();
        FiniteTimeAction[] arrayOfFiniteTimeAction = new FiniteTimeAction[1];
        arrayOfFiniteTimeAction[0] = func;
        IntervalAction action2 = (IntervalAction) Sequence.make(action1, 
                arrayOfFiniteTimeAction).autoRelease();
        this.thermos.runAction(action2);
    }
    
    /**
     *
     */
    public void rotateThermosActionEnded() {
        Scheduler.getInstance().schedule(pouringTimer);
        this.drinkIngrediant.setVisible(true);
        executeDrinkPouring();
    }
    
}/* end class */
