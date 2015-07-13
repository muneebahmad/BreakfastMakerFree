package com.algorithmi.maker.breakfast.model;

/**
 *
 * @author muneebahmad
 */
public class Plate extends Item {

	private String packageId;

	/**
     *
     * @param id
     * @param name
     * @param imageName
     * @param isLocked
     * @param paramFloat
     * @param packageId
     */
    public Plate(String id, String name, String imageName, boolean isLocked,
			float paramFloat, String packageId) {
		super(id, name, imageName, isLocked);
		this.packageId = packageId;
	}

	/**
     *
     * @return
     */
    public String getPackageId() {
		return this.packageId;
	}

	/**
     *
     * @param packageId
     */
    public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

}// end class
