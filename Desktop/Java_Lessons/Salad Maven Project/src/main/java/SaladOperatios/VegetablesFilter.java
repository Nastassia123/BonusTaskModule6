package SaladOperatios;


import Entities.Vegetable;

import java.util.List;

public class VegetablesFilter {

    public String findVegetableByParameters(List<Vegetable> salad, int filterForWeight, int filterForCalories){
        for (Vegetable vegetable:
                salad ) {
            if (vegetable.getWeight() == filterForWeight && vegetable.getCalories() == filterForCalories) {
               return  vegetable.toString();
            }

        }
        return null;
    }
}


