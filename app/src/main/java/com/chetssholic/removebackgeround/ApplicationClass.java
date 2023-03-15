package com.chetssholic.removebackgeround;

import android.app.Application;

public class ApplicationClass extends Application {
    ApplicationClass  intancs;

    @Override
    public void onCreate() {
        super.onCreate();

        intancs= this;
    }
}
