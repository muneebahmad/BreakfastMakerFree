package com.algorithmi.maker.breakfast.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.wiyun.engine.nodes.Director;

/**
 *
 * @author muneebahmad
 */
public class Player {

	/**
     *
     */
    public ArrayList<String> drinksIds = new ArrayList();
	/**
     *
     */
    public ArrayList<String> fruitsIds = new ArrayList();
	/**
     *
     */
    public ArrayList<String> cupIds = new ArrayList();
	/**
     *
     */
    public String name = "";
	/**
     *
     */
    public ArrayList<String> packageDealIds = new ArrayList();
	/**
     *
     */
    public ArrayList<String> plateIds = new ArrayList();
	/**
     *
     */
    public ArrayList<String> sideordersIds = new ArrayList();
	/**
     *
     */
    public ArrayList<String> strawsIds = new ArrayList();
        /**
     *
     */
    public AddOn usedAddOn;
	/**
     *
     */
    public Drink usedDrink;
	/**
     *
     */
    public ArrayList<String> addOnIds = new ArrayList();
	/**
     *
     */
    public Cup usedCup;
	/**
     *
     */
    public Plate usedPlate;
	//public ArrayList<SideOrder> usedSideOrderList = new ArrayList();
	//public Straw usedStraw;

	/**
     *
     * @return
     */
    public boolean isEmpty() {
		int i = 0;
		if ((this.drinksIds.size()) == 0 || (this.addOnIds.size() == 0)
				|| (this.cupIds.size() == 0) || (this.plateIds.size() == 0)
				|| (this.sideordersIds.size() == 0)
				|| (this.strawsIds.size() == 0)
				|| (this.packageDealIds.size() == 0)) {
			i = 1;
		}

		return true;
	}
	
	/**
     *
     */
    public void saveUserState() {
		//String str = new Gson().toJson(this);
		FileOutputStream fos;
		try {
			fos = Director.getInstance().getContext().openFileOutput("playerStateAlgorithmiBreakfastMaker", 0);
			//fos.write(str.getBytes());
			fos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
}//end class
