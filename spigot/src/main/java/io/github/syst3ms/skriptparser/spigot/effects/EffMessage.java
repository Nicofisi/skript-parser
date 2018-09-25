package io.github.syst3ms.skriptparser.spigot.effects;

import io.github.syst3ms.skriptparser.event.TriggerContext;
import io.github.syst3ms.skriptparser.lang.Effect;
import io.github.syst3ms.skriptparser.lang.Expression;
import io.github.syst3ms.skriptparser.parsing.ParseResult;
import io.github.syst3ms.skriptparser.spigot.SpigotSkriptAddon;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EffMessage extends Effect {

    static {
        SpigotSkriptAddon.getRegistration().addEffect(EffMessage.class,
                "message %strings% to %commandsenders%"
        );
    }

    private Expression<String> messages;
    private Expression<CommandSender> recipients;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(@NotNull Expression<?>[] expressions, int matchedPattern, @NotNull ParseResult parseResult) {
        messages = (Expression<String>) expressions[0];
        recipients = (Expression<CommandSender>) expressions[1];
        return true;
    }

    @Override
    public void execute(@NotNull TriggerContext e) {
        String[] messages = this.messages.getArray(e);

        for (CommandSender recipient : recipients.getArray(e)) {
            for (String message : messages) {
                recipient.sendMessage(message);
            }
        }
    }

    @Override
    public String toString(@Nullable TriggerContext e, boolean debug) {
        return "message " + messages.toString(e, debug) + " to " + recipients.toString(e, debug);
    }
}
