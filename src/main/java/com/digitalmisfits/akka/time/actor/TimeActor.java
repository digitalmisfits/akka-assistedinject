package com.digitalmisfits.akka.time.actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.digitalmisfits.akka.time.time.Clock;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;
import java.time.ZoneId;

public class TimeActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private final Clock clock;
    private final TimeActorConfig config;

    @Inject
    public TimeActor(Clock clock, @Assisted TimeActorConfig config) {
        this.clock = clock;
        this.config = config;
    }

    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> s.equals("Tell the time"), s -> log.info("The time is {}", clock.now().atZone(ZoneId.of(config.zoneId))))
                .matchAny(this::unhandled)
                .build();
    }
}
