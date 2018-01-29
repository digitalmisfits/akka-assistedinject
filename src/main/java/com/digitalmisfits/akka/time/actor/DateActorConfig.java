package com.digitalmisfits.akka.time.actor;

import com.digitalmisfits.akka.time.context.ActorConfig;

import java.io.Serializable;

public class DateActorConfig implements ActorConfig, Serializable {

    public final String zoneId;

    public DateActorConfig(String zoneId) {
        this.zoneId = zoneId;
    }
}
