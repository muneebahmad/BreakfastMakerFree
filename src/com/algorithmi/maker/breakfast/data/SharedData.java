package com.algorithmi.maker.breakfast.data;

import com.algorithmi.maker.breakfast.model.AddOn;
import com.algorithmi.maker.breakfast.model.Cup;
import com.algorithmi.maker.breakfast.model.Drink;
import java.util.ArrayList;

import com.algorithmi.maker.breakfast.model.Plate;
import com.algorithmi.maker.breakfast.model.Player;
import com.algorithmi.maker.breakfast.scenes.EndGameScene;
import com.muneebahmad.microsun.hndl.SoundsHandler;

/**
 *
 * @author muneebahmad
 */
public class SharedData {

	/**
     *
     */
    public static SharedData instance;
	// public EndGameScene coatingBaseSceneReference;
	/**
     *
     */
    public ArrayList<Drink> drinkList;
	/**
     *
     */
    public ArrayList<AddOn> addOnList;
	/**
     *
     */
    public boolean isRedrawingRequired;
        /**
     *
     */
    public EndGameScene coatingBaseSceneReference;
	/**
     *
     */
    public ArrayList<Cup> cupList;
	// public ArrayList<PackageDeal> packageDealList;
	/**
     *
     */
    public ArrayList<Plate> plateList;
	/**
     *
     */
    public Player player = new Player();
	// public ArrayList<SideOrder> sideOrderList;
	/**
     *
     */
    public SoundsHandler soundsHandler = new SoundsHandler();

	// public ArrayList<Straw> strawsList;

	private SharedData() {
		getData();
	}

	private void getData() {
		this.drinkList = CoreData.getDrinksData();
		this.plateList = CoreData.getPlatesData();
		this.cupList = CoreData.getCupsData();
		this.addOnList = CoreData.getAddOnData();
		// this.sideOrderList = DataProvider.getSideOrderData();
		// this.strawsList = DataProvider.getStrawsData();
		// this.packageDealList = DataProvider.getPackageDealsData();
	}

	/**
     *
     * @return
     */
    public static SharedData getInstance() {
		if (instance == null) {
			instance = new SharedData();
		}
		return instance;
	}

	private void loadGameState() {
		int k = 0;
		int i1 = 0;

		if (i1 < this.plateList.size()) {
			int i2 = 0;
			++i2;
			if (i2 >= this.player.plateIds.size()) {
				++i1;
				if (!(((String) this.player.plateIds.get(i2))
						.equalsIgnoreCase(((Plate) this.plateList.get(i1))
								.getId())))
					((Plate) this.plateList.get(i1)).setLocked(false);
			}
		}
	}

}// end class
