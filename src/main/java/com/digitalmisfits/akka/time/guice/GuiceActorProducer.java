package com.digitalmisfits.akka.time.guice;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import akka.japi.Pair;
import com.digitalmisfits.akka.time.context.ActorConfig;
import com.google.common.collect.Lists;
import com.google.inject.Binding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import static com.digitalmisfits.akka.time.guice.GuiceFactory.injector;

public class GuiceActorProducer implements IndirectActorProducer {

    private static final String FACTORY_CLASS_NAME = "ActorFactory";

    private final Class<? extends Actor> actorClass;
    private final ActorConfig config;

    public GuiceActorProducer(Class<? extends Actor> actorClass, ActorConfig config) {
        this.actorClass = actorClass;
        this.config = config;
    }

    @Override
    public Actor produce() {

        final List<Pair<Binding<?>, Method>> candidates = Lists.newArrayList();

        // find matching binding for class with name *FACTORY_CLASS_NAME and
        // method signature [public <ActorClass> name(ActorConfigImplClass param)]
        for (Binding<?> binding : injector.getAllBindings().values()) {
            final Class<?> rawType = binding.getKey().getTypeLiteral().getRawType();
            if (rawType.isInterface()
                    && rawType.getSimpleName().equals(FACTORY_CLASS_NAME)) {
                for (Method m : rawType.getMethods()) {
                    if (Modifier.isPublic(m.getModifiers())
                            && m.getReturnType().equals(actorClass)
                            && m.getParameterTypes().length == 1
                            && m.getParameterTypes()[0].isAssignableFrom(config.getClass())) {
                        candidates.add(Pair.create(binding, m));
                    }
                }
            }
        }

        if (candidates.size() == 0) {
            throw new RuntimeException(String.format("No ActorFactory candidates with appropriate signature found for %s", actorClass));
        }

        try {
            return (Actor) candidates.get(0).second().invoke(injector.getInstance(candidates.get(0).first().getKey()), config);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(String.format("Failed to invoke factory method %s", candidates.get(0)), e);
        }
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return actorClass;
    }
}