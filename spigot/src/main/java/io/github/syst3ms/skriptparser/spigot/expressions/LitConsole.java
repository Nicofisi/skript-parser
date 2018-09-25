package io.github.syst3ms.skriptparser.spigot.expressions;

import io.github.syst3ms.skriptparser.event.TriggerContext;
import io.github.syst3ms.skriptparser.lang.Expression;
import io.github.syst3ms.skriptparser.lang.Literal;
import io.github.syst3ms.skriptparser.parsing.ParseResult;
import io.github.syst3ms.skriptparser.spigot.SpigotSkriptAddon;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LitConsole implements Literal<ConsoleCommandSender> {

    static {
        SpigotSkriptAddon.getRegistration().addExpression(
                LitConsole.class,
                ConsoleCommandSender.class,
                true,
                "[the] console"
        );
    }

    private final static ConsoleCommandSender sender = Bukkit.getConsoleSender();

    @Override
    public ConsoleCommandSender[] getValues() {
        return new ConsoleCommandSender[]{sender};
    }

    @Override
    public boolean init(@NotNull Expression<?>[] expressions, int matchedPattern, @NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    public String toString(@Nullable TriggerContext e, boolean debug) {
        return "the console";
    }
}
