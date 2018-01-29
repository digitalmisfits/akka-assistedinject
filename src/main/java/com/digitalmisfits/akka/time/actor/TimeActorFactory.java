package com.digitalmisfits.akka.time.actor;

/**
 * Guice AssistedInject factory for the {@see TimeActorImpl}
 */
public interface TimeActorFactory {

    @SuppressWarnings("unused")
    TimeActor create(TimeActorConfig config);
}
