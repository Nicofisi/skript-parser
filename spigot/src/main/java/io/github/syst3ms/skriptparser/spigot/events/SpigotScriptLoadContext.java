package io.github.syst3ms.skriptparser.spigot.events;

import io.github.syst3ms.skriptparser.event.TriggerContext;

public class SpigotScriptLoadContext implements TriggerContext {

    @Override
    public String getName() {
        return "script load";
    }
}
