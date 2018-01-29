package com.digitalmisfits.akka.time;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.digitalmisfits.akka.time.actor.DateActor;
import com.digitalmisfits.akka.time.actor.DateActorConfig;
import com.digitalmisfits.akka.time.actor.TimeActor;
import com.digitalmisfits.akka.time.actor.TimeActorConfig;
import com.digitalmisfits.akka.time.guice.GuiceActorProducer;
import com.digitalmisfits.akka.time.guice.GuiceExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Main {


    private static final long ACTOR_SYSTEM_SHUTDOWN_DURATION = 300;
    private static final String ACTOR_SYSTEM_NAME = "MainActorSystem";

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ActorSystem system = ActorSystem.create(ACTOR_SYSTEM_NAME);

    public static void main(String[] args) {
        (new Main()).run();
    }

    private void run() {

        system.registerExtension(GuiceExtension.provider);

        final ActorRef timeActorRef = system.actorOf(Props.create(GuiceActorProducer.class, TimeActor.class, new TimeActorConfig("Africa/Windhoek")));
        final ActorRef dateActorRef = system.actorOf(Props.create(GuiceActorProducer.class, DateActor.class, new DateActorConfig("Africa/Windhoek")));

        timeActorRef.tell("Tell the time", ActorRef.noSender());
        dateActorRef.tell("Tell the date", ActorRef.noSender());

        try {
            int read = System.in.read();
        } catch (IOException ignored) {
        } finally {
            system.terminate();
            try {
                Await.result(system.whenTerminated(), Duration.create(ACTOR_SYSTEM_SHUTDOWN_DURATION, TimeUnit.SECONDS));
            } catch (Exception e) {
                LoggerFactory.getLogger(getClass()).info("ActorSystem {} failed to terminate within {} seconds", system.name(), ACTOR_SYSTEM_SHUTDOWN_DURATION);
            }
            logger.info("ActorSystem terminated");
        }
    }
}
