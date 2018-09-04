package io.github.syst3ms.skriptparser.expressions;

import io.github.syst3ms.skriptparser.BaseSkriptAddon;
import io.github.syst3ms.skriptparser.event.TriggerContext;
import io.github.syst3ms.skriptparser.lang.Expression;
import io.github.syst3ms.skriptparser.parsing.ParseResult;
import org.jetbrains.annotations.Nullable;

public class ExprBooleanOperators implements Expression<Boolean> {
    private int pattern;
    private Expression<Boolean> first;
    @Nullable
    private Expression<Boolean> second;

    static {
        BaseSkriptAddon.getRegistration().addExpression(
                ExprBooleanOperators.class,
                Boolean.class,
                true,
                1,
                "not %=boolean%",
                "%=boolean% or %=boolean%",
                "%=boolean% and %=boolean%"
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, ParseResult parseResult) {
        pattern = matchedPattern;
        first = (Expression<Boolean>) expressions[0];
        if (expressions.length > 1) {
            second = (Expression<Boolean>) expressions[1];
        }
        return true;
    }

    @Override
    public Boolean[] getValues(TriggerContext e) {
        assert pattern > 0 || second == null;
        Boolean f = first.getSingle(e);
        if (f == null)
            return new Boolean[0];
        if (pattern == 0) {
            return new Boolean[]{!f};
        } else {
            Boolean s = second.getSingle(e);
            if (s == null)
                return new Boolean[0];
            if (pattern == 1) {
                return new Boolean[]{f || s};
            } else {
                return new Boolean[]{f && s};
            }
        }
    }

    @Override
    public String toString(@Nullable TriggerContext e, boolean debug) {
        if (pattern == 0) {
            return "not " + first.toString(e, debug);
        } else {
            assert second != null;
            if (pattern == 1) {
                return first.toString(e, debug) + " or " + second.toString(e, debug);
            } else {
                return first.toString(e, debug) + " and " + second.toString(e, debug);
            }
        }
    }
}
