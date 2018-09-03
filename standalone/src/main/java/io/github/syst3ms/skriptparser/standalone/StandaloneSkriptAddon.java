package io.github.syst3ms.skriptparser.standalone;

import io.github.syst3ms.skriptparser.lang.Effect;
import io.github.syst3ms.skriptparser.lang.Trigger;
import io.github.syst3ms.skriptparser.registration.SkriptAddon;
import io.github.syst3ms.skriptparser.registration.SkriptRegistration;
import io.github.syst3ms.skriptparser.standalone.events.ScriptLoadContext;
import java.util.ArrayList;
import java.util.List;

public class StandaloneSkriptAddon extends SkriptAddon {

    private static final StandaloneSkriptAddon instance;
    private static SkriptRegistration registration;

    private List<Trigger> mainTriggers = new ArrayList<>();


    static {
        instance = new StandaloneSkriptAddon();
        registration = new SkriptRegistration(instance);
    }

    public static StandaloneSkriptAddon getInstance() {
        return instance;
    }

    public static SkriptRegistration getRegistration() {
        return registration;
    }

    @Override
    public void handleTrigger(Trigger trigger) {
        mainTriggers.add(trigger);
    }

    @Override
    public void finishedLoading() {
        for (Trigger trigger : mainTriggers) {
            Effect.runAll(trigger, new ScriptLoadContext(Main.getArgs()));
        }
    }
}
