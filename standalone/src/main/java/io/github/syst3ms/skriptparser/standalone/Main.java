package io.github.syst3ms.skriptparser.standalone;

import io.github.syst3ms.skriptparser.BaseSkriptAddon;
import io.github.syst3ms.skriptparser.parsing.ScriptLoader;
import io.github.syst3ms.skriptparser.registration.DefaultRegistration;
import io.github.syst3ms.skriptparser.standalone.events.ScriptLoadContext;
import io.github.syst3ms.skriptparser.standalone.events.ScriptLoadEvent;
import io.github.syst3ms.skriptparser.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    private static String[] args;

    public static void main(String[] args) {
        Main.args = args;

        DefaultRegistration.register();
        try {
            FileUtils.loadClasses("io.github.syst3ms.skriptparser", "expressions", "effects", "event");
            FileUtils.loadClasses("io.github.syst3ms.skriptparser.standalone", "expressions", "effects", "events");
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error while loading classes:");
            e.printStackTrace();
        }
        BaseSkriptAddon.getRegistration().register();
        StandaloneSkriptAddon.getRegistration().register();
        if (args.length == 0) {
            System.err.println("You need to provide a script name!");
            System.exit(1);
        }
        File script = new File(args[0]);
        ScriptLoader.loadScript(script);
    }

    public static String[] getArgs() {
        return args;
    }
}
