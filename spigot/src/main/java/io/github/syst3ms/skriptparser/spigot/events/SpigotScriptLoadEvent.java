package io.github.syst3ms.skriptparser.spigot.events;

import io.github.syst3ms.skriptparser.event.TriggerContext;
import io.github.syst3ms.skriptparser.lang.Expression;
import io.github.syst3ms.skriptparser.lang.SkriptEvent;
import io.github.syst3ms.skriptparser.parsing.ParseResult;
import io.github.syst3ms.skriptparser.spigot.SpigotSkriptAddon;
import org.jetbrains.annotations.Nullable;

public class SpigotScriptLoadEvent extends SkriptEvent {

    static {
        SpigotSkriptAddon.getRegistration()
            .newEvent(SpigotScriptLoadEvent.class, "script load[ing]")
            .setHandledContexts(SpigotScriptLoadContext.class)
            .register();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(TriggerContext context) {
        return context instanceof SpigotScriptLoadContext;
    }

    @Override
    public String toString(@Nullable TriggerContext e, boolean debug) {
        return "script loading";
    }
}
