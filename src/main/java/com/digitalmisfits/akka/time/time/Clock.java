package com.digitalmisfits.akka.time.time;

import java.time.Instant;

public interface Clock {

    Instant now();
}
