package br.com.jcamelo.appfotos;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.
                Builder().
                name("appfotos.realm").
                build();

        //Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);
    }
}
