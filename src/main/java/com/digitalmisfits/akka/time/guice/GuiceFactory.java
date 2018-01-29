package com.digitalmisfits.akka.time.guice;

import com.digitalmisfits.akka.time.AkkaModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceFactory {

    public static final Injector injector = Guice.createInjector(new AkkaModule());
}
