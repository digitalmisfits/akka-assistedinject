package com.digitalmisfits.akka.time.time;

import java.time.Instant;

public class RealClock implements Clock {

    @Override
    public Instant now() {
        return Instant.now();
    }
}
