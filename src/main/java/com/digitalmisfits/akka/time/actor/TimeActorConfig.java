package com.digitalmisfits.akka.time.actor;

import com.digitalmisfits.akka.time.context.ActorConfig;

import java.io.Serializable;

public class TimeActorConfig implements ActorConfig, Serializable {

    public final String zoneId;

    public TimeActorConfig(String zoneId) {
        this.zoneId = zoneId;
    }
}
