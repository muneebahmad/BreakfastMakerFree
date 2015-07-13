package com.muneebahmad.microsun.layers;

/**
 *
 * @author muneebahmad
 */
public abstract interface GridInterface {
	/**
     *
     */
    public abstract void onCrossButtonClicked();
	
	/**
     *
     * @param paramString
     */
    public abstract void onGridItemClicked(String paramString);
}//end interface
