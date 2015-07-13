package com.algorithmi.maker.breakfast.model;

/**
 * @author muneebahmad
 * */

import android.content.res.Resources;

import com.algorithmi.maker.breakfast.free.main.R;
import com.wiyun.engine.nodes.Director;
import com.wiyun.engine.types.WYPoint;

/**
 *
 * @author muneebahmad
 */
public class Item {

	/**
     *
     */
    protected String id;
	/**
     *
     */
    protected String imageName;
	/**
     *
     */
    protected boolean isLocked;
	/**
     *
     */
    protected String name;
	/**
     *
     */
    protected WYPoint point;

	/**
     *
     * @param id
     * @param name
     * @param imageName
     * @param isLocked
     */
    public Item(String id, String name, String imageName, boolean isLocked) {
		this.id = id;
		this.name = name;
		this.imageName = imageName;
		this.isLocked = isLocked;
	}

	/**
     *
     * @return
     */
    public String getId() {
		return this.id;
	}

	/**
     *
     * @return
     */
    public int getImageResourceId() {
		int i;
		int j;

		try {
			j = Director
					.getInstance()
					.getContext()
					.getResources()
					.getIdentifier(
							"drawable/" +
							this.imageName,
							"drawable",
							Director.getInstance().getContext()
									.getPackageName());
			i = j;
		} catch (Resources.NotFoundException rnfe) {
			i = R.drawable.ic_launcher;
		}
		return i;
	}
	
	/**
     *
     * @return
     */
    public String getImageResourceName() {
		String str1;
		String str2;
		try {
			str2 = "art/" + this.imageName + ".png";
			str1 = str2;
		}catch (Resources.NotFoundException rnfe) {
			str1 = "ic_launcher.png";
		}
		return str1;
	}
	
	/**
     *
     * @return
     */
    public WYPoint getItemPosition() {
		return this.point;
	}
	
	/**
     *
     * @return
     */
    public String getName() {
		return this.name;
	}
	
	/**
     *
     * @return
     */
    public float getPackagePrice() {
		return 0.0F;
	}
	
	/**
     *
     * @return
     */
    public boolean isLocked() {
		return this.isLocked;
	}
	
	/**
     *
     * @param imageName
     */
    public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	/**
     *
     * @param x
     * @param y
     */
    public void setItemPosition(float x, float y) {
		this.point = WYPoint.make(x, y);
	}
	
	/**
     *
     * @param isLocked
     */
    public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	/**
     *
     * @param name
     */
    public void setName(String name) {
		this.name = name;
	}
	
}//end class
