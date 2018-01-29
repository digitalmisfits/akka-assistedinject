package com.digitalmisfits.akka.time.guice;


import akka.actor.Extension;
import com.google.inject.Injector;

public class GuiceExtensionImpl implements Extension {

    public Injector getInjector() {
        return GuiceFactory.injector;
    }
}