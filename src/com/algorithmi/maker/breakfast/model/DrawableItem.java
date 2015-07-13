package com.algorithmi.maker.breakfast.model;

import com.wiyun.engine.actions.Action;
import com.wiyun.engine.actions.IntervalAction;
import com.wiyun.engine.actions.MoveTo;
import com.wiyun.engine.actions.Repeat;
import com.wiyun.engine.nodes.Sprite;
import com.wiyun.engine.opengl.Texture2D;

/**
 *
 * @author muneebahmad
 */
public class DrawableItem {

	/**
     *
     */
    public Item item;
	/**
     *
     */
    public Sprite itemSprite;

	/**
     *
     * @param paramItem
     */
    public DrawableItem(Item paramItem) {
		this.item = paramItem;
		this.itemSprite = Sprite.make(Texture2D.make(paramItem.getImageResourceName()));
	}

	/**
     *
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     */
    public void runMoveBounceAction(float startX, float startY, float endX,
			float endY) {
		Action localAction = (Action) Repeat.make(
				(IntervalAction) MoveTo.make(2.0F, startX, startY, endX, endY)
						.autoRelease(), 1).autoRelease();
		this.itemSprite.runAction(localAction);
	}
	
}//end class
