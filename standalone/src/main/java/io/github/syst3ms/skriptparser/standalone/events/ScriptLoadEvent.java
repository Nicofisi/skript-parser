package io.github.syst3ms.skriptparser.standalone.events;

import io.github.syst3ms.skriptparser.event.TriggerContext;
import io.github.syst3ms.skriptparser.lang.Expression;
import io.github.syst3ms.skriptparser.lang.SkriptEvent;
import io.github.syst3ms.skriptparser.parsing.ParseResult;
import io.github.syst3ms.skriptparser.standalone.StandaloneSkriptAddon;
import org.jetbrains.annotations.Nullable;

public class ScriptLoadEvent extends SkriptEvent {

    static {
        StandaloneSkriptAddon.getRegistration()
            .newEvent(ScriptLoadEvent.class, "script load[ing]")
            .setHandledContexts(ScriptLoadContext.class)
            .register();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(TriggerContext context) {
        return context instanceof ScriptLoadContext;
    }

    @Override
    public String toString(@Nullable TriggerContext e, boolean debug) {
        return "script loading";
    }
}
