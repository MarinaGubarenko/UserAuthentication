package com.example.marina.userauthentication.DataModel;

import java.util.ArrayList;

public class DataPresenterImpl implements DataPresenter {

    private DataView dataView;
    private ArrayList<Integer> timePress;
    private ArrayList<Integer> timeTouch;
    private DataModelImpl dataModel;


    public DataPresenterImpl(DataView dataView) {
        this.dataView = dataView;
        timePress = new ArrayList<>();
        timeTouch = new ArrayList<>();
        this.dataModel = DataModelImpl.getInstance();
    }

    public DataModelImpl getDataModel(){
        return dataModel;
    }

    @Override
    public void addTimePress() {
        dataModel.getTimePress().add(dataView.getTimePress());

    }

    @Override
    public void addTimeTouch() {
        dataModel.getTimeTouch().add(dataView.getTimeTouch());
    }

    @Override
    public void saveData(){
        dataModel.saveData();
    }


}
