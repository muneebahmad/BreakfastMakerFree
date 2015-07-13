/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algorithmi.maker.breakfast.scenes;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.algorithmi.maker.breakfast.data.SharedData;
import com.algorithmi.maker.breakfast.free.main.R;
import com.algorithmi.maker.breakfast.model.AddOn;
import com.muneebahmad.microsun.hndl.ActionHandler;
import com.muneebahmad.microsun.interfaces.HudLayerInterface;
import com.muneebahmad.microsun.layers.GridInterface;
import com.muneebahmad.microsun.layers.GridLayer;
import com.muneebahmad.microsun.layers.HudLayer;
import com.muneebahmad.microsun.util.Core;
import com.muneebahmad.microsun.util.ParticleSmoke;
import com.vungle.sdk.VunglePub;
import com.wiyun.engine.actions.MoveTo;
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
import com.wiyun.engine.types.WYPoint;
import com.wiyun.engine.types.WYSize;

/**
 *
 * @author muneebahmad
 */
public class DecorationScene extends Scene implements HudLayerInterface, 
        GridInterface {

    private ActionHandler actionHandler;
    private Sprite bg;
    private WYSize wySize;
    private HudLayer hudLayer;
    private Sprite plateSprite;
    private Sprite bread;
    private Sprite bread2;
    private Sprite cupSprite;
    private Sprite drinkIngrediant;
    private Button addOnButton;
    private ParticleSystem particleSystem;
    private GridLayer gridLayer;
    private Sprite addOnSprite;
    private final int _MODE_DRAG_ADDON = 1;
    private int mode;
    
    /**
     *
     */
    public DecorationScene() {
        autoRelease(true);
        setTouchEnabled(true);
        this.actionHandler = new ActionHandler();
        this.wySize = Director.getInstance().getWindowSize();
        setContents();
    }
    
     private void setContents() {
        addBackground();
        addAddOnButton();
        addPlate();
        addHudLayer();
        showPreparedEggBread();
        addCup();
        addCupIngrediant();
        smokeParticleBegin();
        addAddOnSprite();
    }
     
     private void addBackground() {
         this.bg = Sprite.make(Texture2D.make("bgs/bg_plate.png"));
         this.bg.setPosition(this.wySize.width / 2.0f, this.wySize.height / 2.0f);
         this.bg.setContentSize(this.wySize.width, this.wySize.height);
         this.bg.setAutoFit(true);
         addChild(this.bg);
         this.bg.autoRelease(true);
     }
     
     private void addAddOnButton() {
         this.addOnButton = Button.make(Sprite.make(Texture2D.make("ui/addon_button.png")), 
                 Sprite.make(Texture2D.make("ui/addon_button.png")), null, null, this, "onAddOnButtonClicked");
         this.addOnButton.setPosition(160.0f, 
                this.wySize.height - addOnButton.getHeight() / 2.0f - 90.0f);
         addChild(this.addOnButton);
         this.addOnButton.autoRelease(true);
         
         this.addOnButton.runAction(this.actionHandler.getRepeatingJumpAction(0.5f, 
                addOnButton.getPositionX(), 
                addOnButton.getPositionY(), 10));
         this.addOnButton.runAction(this.actionHandler.getRepeatingRotateAction(0.5f, 15));
         this.addOnButton.runAction(this.actionHandler.getRepeatingScaleAction(0.5f, 
                0.80000001192092895508f, 1.0f));
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
     
     private void addPlate() {
        this.plateSprite = Sprite.make(Texture2D.make(SharedData.getInstance().player.usedPlate
                .getImageResourceName()));
        this.plateSprite.setPosition(this.wySize.width / 2.0f, this.wySize.height / 2.5f);
        addChild(this.plateSprite);
        this.plateSprite.setScale(0.75f);
        this.plateSprite.autoRelease(true);
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
     
     private void showPreparedEggBread() {
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
        addChild(this.bread);
        this.bread.autoRelease(true);
        this.bread2.setPosition(this.bread.getPositionX(),
                this.bread.getPositionY() - 140.0f);
        addChild(this.bread2);
        this.bread2.autoRelease(true);
        this.bread2.setRotation(-25.0f);
        Sprite egg = Sprite.make(Texture2D.make("art/fryegg3.png"));
        egg.setPosition(this.bread.getPositionX() + 150.0f, this.bread.getPositionY() - 40.0f);
        addChild(egg);
        egg.setRotation(90.0f);
        egg.autoRelease(true); 
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
    
    private void addAddOnSprite() {
        SharedData.getInstance()
                .player.usedAddOn = ((AddOn) SharedData.getInstance().addOnList.get(0));
        this.addOnSprite = Sprite.make(Texture2D.make(SharedData.getInstance().player.usedAddOn
                .getImageResourceName()));
        this.addOnSprite.setPosition(this.wySize.width / 1.5f, this.wySize.height / 3.8f);
        addChild(this.addOnSprite);
        this.addOnSprite.setScale(0.60f);
        this.addOnSprite.setVisible(false);
        this.addOnSprite.autoRelease(true);
    }
     
    /**
     *
     */
    public void onAddOnButtonClicked() {
        Core.gridModeCurrent = 5;
        makeGridView(Core.gridModeCurrent);
    }
    
    /**
     *
     * @return boolean
     */
    @Override
    public boolean onBackButton() {
        SharedData.getInstance().soundsHandler.playButtonClickSound();
        Director.getInstance().replaceScene((TransitionScene) LeftPushInTransition
                .make(0.5f, new HotDrinkSelectionScene()).autoRelease());
        this.particleSystem.stopAllActions();
        this.particleSystem.setVisible(false);
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
                .make(0.5f, new EndGameScene()).autoRelease());
        this.particleSystem.stopAllActions();
        this.particleSystem.setVisible(false);
        VunglePub.displayAdvert();
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
        this.hudLayer.updateHudObjectVisibility(true, true, false);
        this.hudLayer.setNextButtonPosition(this.wySize.width - 80.0f,
				80.0f);
        HudLayer localHudLayer = this.hudLayer;
		this.hudLayer.getClass();
		localHudLayer.startNextButtonAction(4);
        this.hudLayer.setEnabled(true);
        Core.gridModeCurrent = 0;
        try {
        for (int i = 0; i <= SharedData.getInstance().addOnList.size(); i++) {
        if ((((AddOn)SharedData
                .getInstance().addOnList.get(i)).getId().equalsIgnoreCase(paramString))) {
            replaceAddOn(i);
            }else if ((((AddOn)SharedData
                    .getInstance().addOnList.get(i)).isLocked())) {
                //TODO
            }
        }//end loop
        } catch (java.lang.IndexOutOfBoundsException e) {
            Log.d("Muneeb", "Exception: java.lang.IndexOutOfBoundsException", e);
        }
        this.mode = _MODE_DRAG_ADDON;
    }
    
    /**
     *
     * @param plateId
     */
    public void replaceAddOn(int plateId) {
        if ((((AddOn)SharedData
                    .getInstance().addOnList.get(plateId)).isLocked())) {
            makeToastDrink();
        }else {
        SharedData.getInstance().player
                .usedAddOn = ((AddOn) SharedData.getInstance().addOnList.get(plateId));
        this.addOnSprite.setDisplayFrame(Sprite
                .make(Texture2D.make(SharedData.getInstance().player.usedAddOn
                .getImageResourceName())).makeFrame());
        this.addOnSprite.setVisible(true);
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
     * @param event
     * @return boolean
     */
    @Override
    public boolean wyTouchesMoved(MotionEvent event) {
        WYPoint point = Director.getInstance().convertToGL(event.getX(),
				event.getY());
        switch(this.mode) {
            case _MODE_DRAG_ADDON:
                if (this.addOnSprite.getBoundingBoxRelativeToWorld()
                        .containsPoint(point)) {
                    this.addOnSprite.setPosition(point);
                }else if(this.cupSprite.getBoundingBoxRelativeToWorld()
                        .containsPoint(point)) {
                    this.cupSprite.setPosition(point);
                }
                break;
            default:
                break;
        }//end switch
        return true;
    }
}/* end class */
