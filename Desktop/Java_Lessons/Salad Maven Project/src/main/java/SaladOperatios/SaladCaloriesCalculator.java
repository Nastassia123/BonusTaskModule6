package SaladOperatios;

import Entities.Vegetable;

import java.util.List;

public class SaladCaloriesCalculator {

        public double calculateCalories(List<Vegetable> saladlist){

            double calories = 0;
            for (Vegetable vegetableFromSalad : saladlist){
                calories += vegetableFromSalad.getCalories()*vegetableFromSalad.getWeight()/100;
            }
            return calories;
        }
    }


