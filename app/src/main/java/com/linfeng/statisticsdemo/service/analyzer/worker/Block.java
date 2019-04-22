package com.linfeng.statisticsdemo.service.analyzer.worker;

import io.realm.Realm;

public interface Block extends Runnable {

    @Override
    default void run() {
        final Realm defaultInstance = Realm.getDefaultInstance();
        execute(defaultInstance);
        defaultInstance.close();
    }

    void execute(Realm defaultInstance);
}