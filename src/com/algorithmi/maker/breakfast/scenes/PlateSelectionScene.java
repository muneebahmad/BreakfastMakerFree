/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 */
package com.algorithmi.maker.breakfast.scenes;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.algorithmi.maker.breakfast.data.SharedData;
import com.algorithmi.maker.breakfast.free.main.R;
import com.algorithmi.maker.breakfast.model.Plate;
import com.muneebahmad.microsun.hndl.ActionHandler;
import com.muneebahmad.microsun.interfaces.HudLayerInterface;
import com.muneebahmad.microsun.layers.GridInterface;
import com.muneebahmad.microsun.layers.GridLayer;
import com.muneebahmad.microsun.layers.HudLayer;
import com.muneebahmad.microsun.util.Core;
import com.wiyun.engine.actions.MoveTo;
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
public class PlateSelectionScene extends Scene implements HudLayerInterface,
        GridInterface {

    private ActionHandler actionHandler = new ActionHandler();
    private Sprite bg;
    private Sprite plateSprite;
    private WYSize wySize = Director.getInstance().getWindowSize();
    private HudLayer hudLayer;
    private GridLayer gridLayer;
    private Sprite bread;
    private Sprite bread2;

    //Default Constructor
    /**
     *
     */
    public PlateSelectionScene() {
        autoRelease(true);
        setTouchEnabled(true);
        setContents();
    }

    private void setContents() {
        addBackground();
        addHudLayer();
        addPlate();
        addPlateButton();
        showPreparedEggBread();
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
        SharedData.getInstance().player
                .usedPlate = ((Plate) SharedData.getInstance().plateList.get(0));
        this.plateSprite = Sprite.make(Texture2D.make(SharedData.getInstance().player.usedPlate
                .getImageResourceName()));
        this.plateSprite.setPosition(this.wySize.width / 2.0f, this.wySize.height / 2.0f);
        addChild(this.plateSprite);
        this.plateSprite.setScale(0.75f);
        this.plateSprite.autoRelease(true);
    }
    
    private void addPlateButton() {
        Button plateButt = Button.make(Sprite.make(Texture2D.make("ui/plate_button.png")), 
                Sprite.make(Texture2D.make("ui/plate_button.png")), this, null, null, "onPlateButtonClicked");
        plateButt.setPosition(this.wySize.width / 2.0f, 
                this.wySize.height - plateButt.getHeight() + 20.0f);
        addChild(plateButt);
        plateButt.autoRelease(true);
        plateButt.runAction(this.actionHandler.getRepeatingJumpAction(0.5f, plateButt.getPositionX(), 
                plateButt.getPositionY(), 10));
        plateButt.runAction(this.actionHandler.getRepeatingRotateAction(0.5f, 35));
        plateButt.runAction(this.actionHandler.getRepeatingScaleAction(0.5f, 
                1.0f, 1.2f));
    }

    private void addHudLayer() {
        this.hudLayer = new HudLayer(this);
        this.hudLayer.setContents("ui/arrow_left1.png",
                "ui/arrow_left2.png", "ui/arrow_right1.png",
                "ui/arrow_right2.png", "cheri.ttf",
                WYColor3B.make(255, 255, 255), 36.0f, 0.0f);
        this.hudLayer.updateObjectsVisibility(true, true, false,
                this.wySize.width / 2.0f, this.wySize.height / 8.0f, "bck");
        this.hudLayer.setBackButtonPosition(90.0f, 80.0f);
        this.hudLayer.setNextButtonPosition(this.wySize.width - 80.0f,
				80.0f);
        HudLayer localHudLayer = this.hudLayer;
		this.hudLayer.getClass();
		localHudLayer.startNextButtonAction(4);
        addChild(this.hudLayer);
        this.hudLayer.autoRelease(true);
        this.hudLayer.setZOrder(15);
        bringToFront(this.hudLayer);
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
    public void onPlateButtonClicked() {
        Core.gridModeCurrent = 1;
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
                .make(0.5f, new ToasterScene()).autoRelease());
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
                .make(0.5f, new DrinkSelectionScene()).autoRelease());
    }

    /**
     *
     */
    @Override
    public void onCrossButtonClicked() {
        this.hudLayer.updateHudObjectVisibility(true, true, false);
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
        for (int i = 0; i <= SharedData.getInstance().plateList.size(); i++) {
        if ((((Plate)SharedData
                .getInstance().plateList.get(i)).getId().equalsIgnoreCase(paramString))) {
            replacePlate(i);
            }else if (((Plate)SharedData
                    .getInstance().plateList.get(i)).isLocked()) {
                
            }
        }//end loop
        } catch (java.lang.IndexOutOfBoundsException e) {
            Log.d("Muneeb", "Exception: java.lang.IndexOutOfBoundsException", e);
        }
    }
    
    private void makeLockToast() {
        ((Activity) Director.getInstance().getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(Director.getInstance().getContext(), "This item is "
                        + "Locked. Please upgrade to paid version!", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
            }
        });
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
    
    /**
     *
     * @param plateId
     */
    public void replacePlate(int plateId) {
        if (((Plate) SharedData.getInstance().plateList.get(plateId)).isLocked()) {
            makeLockToast();
        }else {
        SharedData.getInstance().player
                .usedPlate = ((Plate) SharedData.getInstance().plateList.get(plateId));
        this.plateSprite.setDisplayFrame(Sprite
                .make(Texture2D.make(SharedData.getInstance().player.usedPlate
                .getImageResourceName())).makeFrame());
        }//end else
    }
    
}//end class
