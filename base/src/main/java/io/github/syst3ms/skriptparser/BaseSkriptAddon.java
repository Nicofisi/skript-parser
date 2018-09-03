package io.github.syst3ms.skriptparser;

import io.github.syst3ms.skriptparser.lang.Trigger;
import io.github.syst3ms.skriptparser.registration.SkriptAddon;
import io.github.syst3ms.skriptparser.registration.SkriptRegistration;

public class BaseSkriptAddon extends SkriptAddon {

    private static final BaseSkriptAddon instance;
    private static SkriptRegistration registration;

    static {
        instance = new BaseSkriptAddon();
        registration = new SkriptRegistration(instance);
    }

    public static BaseSkriptAddon getInstance() {
        return instance;
    }

    public static SkriptRegistration getRegistration() {
        return registration;
    }

    @Override
    public void handleTrigger(Trigger trigger) {

    }
}
