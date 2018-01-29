package com.digitalmisfits.akka.time;

import com.digitalmisfits.akka.time.actor.TimeActorFactory;
import com.digitalmisfits.akka.time.actor.TimeActor;
import com.digitalmisfits.akka.time.time.Clock;
import com.digitalmisfits.akka.time.time.RealClock;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class AkkaModule extends AbstractModule {

    @Override
    protected void configure() {

        binder().requireExplicitBindings();

        install(new FactoryModuleBuilder()
                .implement(TimeActor.class, TimeActor.class)
                .build(TimeActorFactory.class));

        bind(Clock.class).to(RealClock.class);
    }
}