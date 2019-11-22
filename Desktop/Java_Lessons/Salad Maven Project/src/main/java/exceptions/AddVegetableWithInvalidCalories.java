package exceptions;

public class AddVegetableWithInvalidCalories  extends  Exception{
        public AddVegetableWithInvalidCalories(){
        }

    public AddVegetableWithInvalidCalories(String message){
            super(message);
    }

    public AddVegetableWithInvalidCalories(String message, Throwable exception){
         super(message, exception);
    }

    public AddVegetableWithInvalidCalories(Throwable exception){
            super(exception);
    }
    }




