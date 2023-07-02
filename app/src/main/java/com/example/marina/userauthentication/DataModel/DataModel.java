package com.example.marina.userauthentication.DataModel;

import java.util.ArrayList;

public interface DataModel {
    void sendToData(ArrayList timePress, ArrayList timeTouch);

    ArrayList<Integer> getTimePress();

    ArrayList<Integer> getTimeTouch();
    void saveData();
    int[] getEtalonePress();
    int[] getEtaloneTouch();

}
