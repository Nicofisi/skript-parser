package io.github.syst3ms.skriptparser.effects;

import io.github.syst3ms.skriptparser.BaseSkriptAddon;
import io.github.syst3ms.skriptparser.event.TriggerContext;
import io.github.syst3ms.skriptparser.lang.Effect;
import io.github.syst3ms.skriptparser.lang.Expression;
import io.github.syst3ms.skriptparser.parsing.ParseResult;
import org.jetbrains.annotations.Nullable;

public class EffPrintln extends Effect {
    private Expression<String> string;

    static {
        BaseSkriptAddon.getRegistration().addEffect(EffPrintln.class,
            "println %string%"
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, ParseResult parseResult) {
        string = (Expression<String>) expressions[0];
        return true;
    }

    @Override
    public void execute(TriggerContext e) {
        String str = string.getSingle(e);
        if (str == null)
            return;
        System.out.println(str);
    }

    @Override
    public String toString(@Nullable TriggerContext e, boolean debug) {
        return "println " + string.toString(e, debug);
    }
}
