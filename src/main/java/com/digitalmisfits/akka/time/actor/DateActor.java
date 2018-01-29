package com.digitalmisfits.akka.time.actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.digitalmisfits.akka.time.time.Clock;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;
import java.time.ZoneId;

public class DateActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private final Clock clock;
    private final DateActorConfig config;

    @Inject
    public DateActor(Clock clock, @Assisted DateActorConfig config) {
        this.clock = clock;
        this.config = config;
    }

    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> s.equals("Tell the date"), s -> log.info("The date is {}", clock.now().atZone(ZoneId.of(config.zoneId)).toLocalDate()))
                .matchAny(this::unhandled)
                .build();
    }
}
