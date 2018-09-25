package io.github.syst3ms.skriptparser.spigot;

import io.github.syst3ms.skriptparser.lang.Effect;
import io.github.syst3ms.skriptparser.lang.Trigger;
import io.github.syst3ms.skriptparser.registration.SkriptAddon;
import io.github.syst3ms.skriptparser.registration.SkriptRegistration;
import io.github.syst3ms.skriptparser.spigot.events.SpigotScriptLoadContext;
import java.util.ArrayList;
import java.util.List;

public class SpigotSkriptAddon extends SkriptAddon {

    private static final SpigotSkriptAddon instance;
    private static SkriptRegistration registration;

    private List<Trigger> scriptLoadTriggers = new ArrayList<>();

    static {
        instance = new SpigotSkriptAddon();
        registration = new SkriptRegistration(instance);
    }

    public static SpigotSkriptAddon getInstance() {
        return instance;
    }

    public static SkriptRegistration getRegistration() {
        return registration;
    }

    @Override
    public void handleTrigger(Trigger trigger) {
        scriptLoadTriggers.add(trigger);
    }

    @Override
    public void finishedLoading() {
        for (Trigger trigger : scriptLoadTriggers) {
            Effect.runAll(trigger, new SpigotScriptLoadContext());
        }
    }
}
