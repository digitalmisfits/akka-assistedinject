package com.digitalmisfits.akka.time.actor;

/**
 * Guice AssistedInject factory for the {@see TimeActorImpl}
 */
@SuppressWarnings("unused")
public interface ActorFactory {

    TimeActor createTimeActor(TimeActorConfig config);

    DateActor createDateActor(DateActorConfig config);
}
