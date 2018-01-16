package Modèles;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MelangeAleatoire {

    private int[] arrayList;

    MelangeAleatoire(int[] arrayList){
        setArrayList(arrayList);
    }

    public void melange(){
        Random rnd = ThreadLocalRandom.current();
        for (int i = getArrayList().length - 1; i>0; i--){
            int index = rnd.nextInt(i+1);

            int a = getArrayList()[index];
            getArrayList()[index] = getArrayList()[i];
            getArrayList()[i] = getArrayList()[a];

        }
    }

    public int[] getArrayList() {
        return arrayList;
    }

    public void setArrayList(int[] arrayList) {
        this.arrayList = arrayList;
    }
}
