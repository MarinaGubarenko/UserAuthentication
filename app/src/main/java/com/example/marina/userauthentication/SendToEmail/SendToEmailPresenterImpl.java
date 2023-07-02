package com.example.marina.userauthentication.SendToEmail;



import android.provider.ContactsContract;

import com.example.marina.userauthentication.DataModel.DataModel;
import com.example.marina.userauthentication.DataModel.DataModelImpl;

import java.util.ArrayList;


public class SendToEmailPresenterImpl implements SendToEmailPresenter {

    SendToEmailView sendView;
    DataModel dataModel;


    public SendToEmailPresenterImpl(SendToEmailView sendView) {
        this.sendView = sendView;
        this.dataModel = DataModelImpl.getInstance();
    }


    @Override
    public ArrayList<Integer> getTimeTouch() {
        return dataModel.getTimeTouch();
    }

    @Override
    public ArrayList<Integer> getTimePress() {
        return dataModel.getTimePress();
    }


}
