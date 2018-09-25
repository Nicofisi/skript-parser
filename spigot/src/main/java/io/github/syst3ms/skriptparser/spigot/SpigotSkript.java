package io.github.syst3ms.skriptparser.spigot;

import io.github.syst3ms.skriptparser.parsing.ScriptLoader;
import io.github.syst3ms.skriptparser.registration.SkriptRegistration;
import io.github.syst3ms.skriptparser.util.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpigotSkript extends JavaPlugin {

    @Nullable
    private static SpigotSkript instance;

    public SpigotSkript getInstance() {
        SpigotSkript i = instance;
        if (i == null)
            throw new IllegalStateException();
        return i;
    }

    public SpigotSkript() throws IllegalStateException {
        if (instance != null)
            throw new IllegalStateException("Cannot create multiple instances of Skript!");
        instance = this;
    }

    @Override
    public void onEnable() {
        SkriptRegistration reg = SpigotSkriptAddon.getRegistration();
        reg.addType(CommandSender.class, "commandsender", "commandsender¦s");
        reg.addType(ConsoleCommandSender.class, "consolecommandsender", "consolecommandsender¦s");
        reg.register();

        try {
            FileUtils.loadClasses("io.github.syst3ms.skriptparser.spigot", "expressions", "effects", "events");
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error while loading classes:");
            e.printStackTrace();
        }
        reg.register();


        Bukkit.getScheduler().runTask(this, () -> {
            getLogger().info("Loading scripts...");

            File scriptDir = getScriptDirectory();
            if (!scriptDir.exists()) {
                scriptDir.mkdirs(); // REMIND error
            }
            File[] allScripts = scriptDir.listFiles();
            if (allScripts == null) {
                // REMIND error
                return;
            }

            long scriptLoadStart = System.currentTimeMillis();

            List<File> activeScripts = Stream.of(allScripts)
                    .filter(file -> !file.getName().startsWith("-"))
                    .collect(Collectors.toList());

            activeScripts.forEach(ScriptLoader::loadScript);

            double scriptLoadTime = ((double) ((System.currentTimeMillis() - scriptLoadStart) / 10)) / 100;

            getLogger().info("Loaded " + activeScripts.size() +
                    " scripts with a total of " + ScriptLoader.getTriggerMap().size() +
                    " triggers in " + scriptLoadTime + " seconds");
        });
    }

    public File getScriptDirectory() {
        return new File(getDataFolder(), "scripts");
    }
}
