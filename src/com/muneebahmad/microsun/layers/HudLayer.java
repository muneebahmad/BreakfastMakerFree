package com.muneebahmad.microsun.layers;

import com.muneebahmad.microsun.hndl.ActionHandler;
import com.muneebahmad.microsun.interfaces.HudLayerInterface;
import com.wiyun.engine.nodes.Button;
import com.wiyun.engine.nodes.Director;
import com.wiyun.engine.nodes.Label;
import com.wiyun.engine.nodes.Layer;
import com.wiyun.engine.nodes.Sprite;
import com.wiyun.engine.opengl.Texture2D;
import com.wiyun.engine.types.WYColor3B;
import com.wiyun.engine.types.WYSize;

/**
 *
 * @author muneebahmad
 */
public class HudLayer extends Layer {

	/**
     *
     */
    public final int ACTION_BLINK = 1;
	/**
     *
     */
    public final int ACTION_JUMP = 3;
	/**
     *
     */
    public final int ACTION_POINTING_NEXT = 4;
	/**
     *
     */
    public final int ACTION_SCALE = 2;

	/**
     *
     */
    protected Button backButton;
	/**
     *
     */
    protected Button nextButton;
	/**
     *
     */
    protected Label helpMessageLabel;
	private HudLayerInterface hudLayerInterface;
	/**
     *
     */
    protected WYSize wySize;

	/**
     *
     * @param paramHudLayerInterface
     */
    public HudLayer(HudLayerInterface paramHudLayerInterface) {
		this.hudLayerInterface = paramHudLayerInterface;
		this.wySize = Director.getInstance().getWindowSize();
	}

	/**
     *
     */
    public void onSoftBackButtonClicked() {
		this.hudLayerInterface.onSoftBackButtonClicked();
	}

	/**
     *
     */
    public void onSoftNextButtonClicked() {
		this.hudLayerInterface.onSoftNextButtonClicked();
	}

	/**
     *
     * @param x
     * @param y
     */
    public void setBackButtonPosition(float x, float y) {
		this.backButton.setPosition(x, y);
	}

	/**
     *
     * @param backButtonPos
     * @param nextButtonPos
     */
    public void setButtonsPositionY(float backButtonPos, float nextButtonPos) {
		this.backButton.setPosition(this.backButton.getPositionX(),
				backButtonPos + this.backButton.getHeight() / 2.0f);
		this.nextButton.setPosition(this.nextButton.getPositionX(),
				nextButtonPos + this.nextButton.getHeight() / 2.0f);
	}

	/**
	 * {@docRoot} 
	 * public void setContents(int paramInt1, int paramInt2, int paramInt3, int
	 * paramInt4, String fontPath, WYColor3B color, float fontSize, float width)
	 * 
	 * {@code}
	 * { this.helpMessageLabel = Label.make("Help Message", fontSize, fontPath,
	 * false, width); this.helpMessageLabel.setColor(color);
	 * this.helpMessageLabel.setAlignment(1); addChild(this.helpMessageLabel);
	 * this.helpMessageLabel.autoRelease(true);
	 * this.helpMessageLabel.setVisible(false);
	 * 
	 * }
         * 
         * @param backButtonNormal 
         * @param width
         * @param backButtonSelected 
         * @param nextButtonSelected 
         * @param fontPath 
         * @param color
         * @param nextButtonNormal
         * @param fontSize  
         */

	public void setContents(String backButtonNormal, String backButtonSelected,
			String nextButtonNormal, String nextButtonSelected, String fontPath,
			WYColor3B color, float fontSize, float width) {
		this.helpMessageLabel = Label.make("Help Message", fontSize, fontPath,
				false, width);
		this.helpMessageLabel.setColor(color);
		this.helpMessageLabel.setAlignment(1);
		addChild(this.helpMessageLabel);
		this.helpMessageLabel.autoRelease(true);
		this.helpMessageLabel.setVisible(false);
		this.backButton = Button.make(Sprite.make(Texture2D.make(backButtonNormal)), 
                        Sprite.make(Texture2D.make(backButtonSelected)),
				this, null, null, "onSoftBackButtonClicked");
		this.backButton.setPosition(10.0F + this.backButton.getWidth() / 2F,
				85.0F + this.backButton.getHeight() / 2);
		addChild(this.backButton);
		this.backButton.autoRelease(true);
		this.nextButton = Button.make(Sprite.make(Texture2D.make(nextButtonNormal)), 
                        Sprite.make(Texture2D.make(nextButtonSelected)),
				this, null, null, "onSoftNextButtonClicked");
		this.nextButton.setPosition(
				this.wySize.width - this.nextButton.getWidth() / 2F - 10.0F,
				85.0F + this.nextButton.getHeight() / 2F);
		addChild(this.nextButton);
		this.nextButton.autoRelease(true);
		this.nextButton.setVisible(false);
	}

	/**
     *
     * @param x
     * @param y
     */
    public void setNextButtonPosition(float x, float y) {
		this.nextButton.setPosition(x, y);
	}

	/**
     *
     * @param action
     */
    public void startNextButtonAction(int action) {
		ActionHandler localActionHandler = new ActionHandler();
		switch (action) {
		case 1:
			this.nextButton.runAction(localActionHandler
					.getRepeatingBlinkAction(1F, 2));
			break;
		case 2:
			this.nextButton.runAction(localActionHandler
					.getRepeatingScaleAction(0.5F, 1F, 1.5F));
			break;
		case 3:
			this.nextButton.runAction(localActionHandler
					.getRepeatingJumpAction(1F, this.nextButton.getPositionX(),
							this.nextButton.getPositionY(), 20));
			break;
		case 4:
			this.nextButton.runAction(localActionHandler
					.getRepeatingPointingAction(0.5F,
							this.nextButton.getPositionX() - 30.0F,
							this.nextButton.getPositionY(),
							this.nextButton.getPositionX(),
							this.nextButton.getPositionY()));
			break;
		default:
			return;
		}
	}

	/**
     *
     * @param x
     * @param y
     * @param msg
     */
    public void updateHelpMessage(float x, float y, String msg) {
		this.helpMessageLabel.setPosition(x, y);
		this.helpMessageLabel.setText(msg);
	}

	/**
     *
     * @param msg
     */
    public void updateHelpMessage(String msg) {
		this.helpMessageLabel.setText(msg);
	}

	/**
     *
     * @param backButtVisible
     * @param nextButtVisible
     * @param helpMsg
     */
    public void updateHudObjectVisibility(boolean backButtVisible,
			boolean nextButtVisible, boolean helpMsg) {
		this.backButton.setVisible(backButtVisible);
		this.nextButton.setVisible(nextButtVisible);
		this.helpMessageLabel.setVisible(helpMsg);
	}

	/**
     *
     * @param backButtonVisible
     * @param nextButtonVisible
     * @param helpMessageLabelVisible
     * @param x
     * @param y
     * @param msg
     */
    public void updateObjectsVisibility(boolean backButtonVisible,
			boolean nextButtonVisible, boolean helpMessageLabelVisible,
			float x, float y, String msg) {
		this.backButton.setVisible(backButtonVisible);
		this.nextButton.setVisible(nextButtonVisible);
		this.helpMessageLabel.setVisible(helpMessageLabelVisible);
		updateHelpMessage(x, y, msg);
	}

}// end class
