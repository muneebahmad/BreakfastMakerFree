package com.algorithmi.maker.breakfast.data;

import com.algorithmi.maker.breakfast.model.AddOn;
import com.algorithmi.maker.breakfast.model.Cup;
import com.algorithmi.maker.breakfast.model.Drink;
import java.util.ArrayList;

import com.algorithmi.maker.breakfast.model.Plate;
import com.wiyun.engine.types.WYColor3B;

/*
 * @author muneebahmad
 * @config SharedData
 */
public class CoreData {

    /**
     *
     * @return
     */
    public static ArrayList<Plate> getPlatesData() {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(new Plate("plate", "plate", "plate", false,
                0.99000000953674316406F, "all_plates_package"));
        localArrayList.add(new Plate("plate2", "plate2", "plate2", false,
                0.99000000953674316406F, "all_plates_package"));
        localArrayList.add(new Plate("plate3", "plate3", "plate3", false,
                0.99000000953674316406F, "all_plates_package"));
        localArrayList.add(new Plate("plate4", "Plate", "plate4", true,
                0.99000000953674316406F, "all_plates_package"));
        localArrayList.add(new Plate("plate5", "Plate", "plate5", true,
                0.99000000953674316406F, "all_plates_package"));
        localArrayList.add(new Plate("plate6", "Plate", "plate6", true,
                0.99000000953674316406F, "all_plates_package"));
        localArrayList.add(new Plate("plate7", "Plate", "plate7", true,
                0.99000000953674316406F, "all_plates_package"));
        localArrayList.add(new Plate("plate8", "Plate", "plate8", true,
                0.99000000953674316406F, "all_plates_package"));
        localArrayList.add(new Plate("plate9", "Plate", "plate9", true,
                0.99000000953674316406F, "all_plates_package"));
        localArrayList.add(new Plate("plate10", "Plate", "plate10", true,
                0.99000000953674316406F, "all_plates_package"));
        localArrayList.add(new Plate("plate11", "Plate", "plate11", true,
                0.99000000953674316406F, "all_plates_package"));
        localArrayList.add(new Plate("plate12", "Plate", "plate12", true,
                0.99000000953674316406F, "all_plates_package"));
        return localArrayList;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Drink> getDrinksData() {
        ArrayList drinkList = new ArrayList();
        drinkList.add(new Drink("milk", "Drink", "milk", false,
                0.99000000953674316406F, "all_drinks_package",
                WYColor3B.make(256, 256, 256)));
        drinkList.add(new Drink("black_tea", "Drink", "black_tea", false,
                0.99000000953674316406F, "all_drinks_package",
                WYColor3B.make(240, 173, 80)));
        drinkList.add(new Drink("green_tea", "Drink", "green_tea", true,
                0.99000000953674316406F, "all_drinks_package",
                WYColor3B.make(225, 111, 111)));
        drinkList.add(new Drink("coffee", "Drink", "coffee", true,
                0.99000000953674316406F, "all_drinks_package",
                WYColor3B.make(70, 40, 0)));
        drinkList.add(new Drink("chocolate_drink", "Drink", "chocolate_drink", true,
                0.99000000953674316406F, "all_drinks_package",
                WYColor3B.make(40, 20, 0)));
        return drinkList;
    }
    
    /**
     *
     * @return ArrayList
     */
    public static ArrayList<Cup> getCupsData() {
        ArrayList cupList = new ArrayList();
        cupList.add(new Cup("cup_new1", "Cup", "cup_new1", false, 
                0.99000000953674316406F, "all_cups_package"));
        cupList.add(new Cup("cup_new2", "Cup", "cup_new2", false, 
                0.99000000953674316406F, "all_cups_package"));
        cupList.add(new Cup("cup_new3", "Cup", "cup_new3", false, 
                0.99000000953674316406F, "all_cups_package"));
        cupList.add(new Cup("cup_new4", "Cup", "cup_new4", true, 
                0.99000000953674316406F, "all_cups_package"));
        cupList.add(new Cup("cup_new5", "Cup", "cup_new5", true, 
                0.99000000953674316406F, "all_cups_package"));
        cupList.add(new Cup("cup_new6", "Cup", "cup_new6", true, 
                0.99000000953674316406F, "all_cups_package"));
        cupList.add(new Cup("cup_new7", "Cup", "cup_new7", true, 
                0.99000000953674316406F, "all_cups_package"));
        cupList.add(new Cup("cup_new8", "Cup", "cup_new8", true, 
                0.99000000953674316406F, "all_cups_package"));
        cupList.add(new Cup("cup_new9", "Cup", "cup_new9", true, 
                0.99000000953674316406F, "all_cups_package"));
        cupList.add(new Cup("cup_new10", "Cup", "cup_new10", true, 
                0.99000000953674316406F, "all_cups_package"));
        cupList.add(new Cup("cup_new11", "Cup", "cup_new11", true, 
                0.99000000953674316406F, "all_cups_package"));
        cupList.add(new Cup("cup_new12", "Cup", "cup_new12", true, 
                0.99000000953674316406F, "all_cups_package"));
        return cupList;
    }
    
    /**
     *
     * @return
     */
    public static ArrayList<AddOn> getAddOnData() {
        ArrayList addOnList = new ArrayList();
        addOnList.add(new AddOn("addon_apple", "AddOn", "addon_apple", false, 
                0.99000000953674316406F, "all_addon_package"));
        addOnList.add(new AddOn("addon_berry", "AddOn", "addon_berry", false, 
                0.99000000953674316406F, "all_addon_package"));
        addOnList.add(new AddOn("addon_bnana", "AddOn", "addon_bnana", false, 
                0.99000000953674316406F, "all_addon_package"));
        addOnList.add(new AddOn("addon_butter", "AddOn", "addon_butter", true, 
                0.99000000953674316406F, "all_addon_package"));
        addOnList.add(new AddOn("addon_egg", "AddOn", "addon_egg", true, 
                0.99000000953674316406F, "all_addon_package"));
        addOnList.add(new AddOn("addon_grapes", "AddOn", "addon_grapes", true, 
                0.99000000953674316406F, "all_addon_package"));
        addOnList.add(new AddOn("addon_lemon", "AddOn", "addon_lemon", true, 
                0.99000000953674316406F, "all_addon_package"));
        addOnList.add(new AddOn("addon_mango", "AddOn", "addon_mango", true, 
                0.99000000953674316406F, "all_addon_package"));
        addOnList.add(new AddOn("addon_orrange", "AddOn", "addon_orrange", true, 
                0.99000000953674316406F, "all_addon_package"));
        addOnList.add(new AddOn("addon_orrange_peace", "AddOn", "addon_orrange_peace", true, 
                0.99000000953674316406F, "all_addon_package"));
        addOnList.add(new AddOn("addon_pineapple", "AddOn", "addon_pineapple", true, 
                0.99000000953674316406F, "all_addon_package"));
        addOnList.add(new AddOn("addon_strawberry", "AddOn", "addon_strawberry", true, 
                0.99000000953674316406F, "all_addon_package"));
        addOnList.add(new AddOn("addon_cake", "AddOn", "addon_cake", false, 
                0.99000000953674316406F, "all_addon_package"));
        addOnList.add(new AddOn("addon_chips", "AddOn", "addon_chips", true, 
                0.99000000953674316406F, "all_addon_package"));
        addOnList.add(new AddOn("addon_pizza", "AddOn", "addon_pizza", true, 
                0.99000000953674316406F, "all_addon_package"));
        return addOnList;
    }
}/* end class */
