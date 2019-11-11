package com.example.rxjavatest;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.pqpo.smartcropperlib.SmartCropper;

public class BaseApplication  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);//Realm数据库初始化

        Stetho.initialize(Stetho.newInitializerBuilder(this).enableDumpapp(Stetho.defaultDumperPluginsProvider(this)).enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build()).build());

        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().name("test_rxjava.realm").schemaVersion(1).deleteRealmIfMigrationNeeded().build());

        SmartCropper.buildImageDetector(this);
    }
}
