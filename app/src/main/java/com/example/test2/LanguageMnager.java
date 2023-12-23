package com.example.test2;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageMnager {
    private Context mContext;
    public LanguageMnager(Context context){
        mContext=context;
    }

    public void UpdateResource(String code){
        Locale locale=new Locale(code);
        Locale.setDefault(locale);
        Resources resources =mContext.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());

    }

}
