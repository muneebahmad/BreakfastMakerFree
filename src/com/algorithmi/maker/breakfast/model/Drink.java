/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algorithmi.maker.breakfast.model;

import com.wiyun.engine.types.WYColor3B;

/**
 *
 * @author muneebahmad
 */
public class Drink extends Item {
    
    private WYColor3B color;
    private String packageId;
    
    /**
     *
     * @param id
     * @param name
     * @param imageName
     * @param isLocked
     * @param paramFloat
     * @param packageId
     * @param color
     */
    public Drink(String id, String name, String imageName, boolean isLocked,
			float paramFloat, String packageId, WYColor3B color) {
        super(id, name, imageName, isLocked);
        this.packageId = packageId;
        this.color = color;
    }
    
    /**
     *
     * @return
     */
    public WYColor3B getColor() {
        return this.color;
    }
    
    /*
            public int getThermosImageResourceId() {
         //TODO
    }
    * public String getThermosImageResourceName() {
    *   //TODO
    * }
    * 
    * */
    
    /**
     *
     * @return
     */
    public String getPackageId() {
        return this.packageId;
    }
    
    /**
     *
     * @param color
     */
    public void setColor(WYColor3B color) {
        this.color = color;
    }
    
    /**
     *
     * @param packageId
     */
    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
    
    }//end class
