package io.github.syst3ms.skriptparser.parsing;

import io.github.syst3ms.skriptparser.BaseSkriptAddon;
import io.github.syst3ms.skriptparser.registration.DefaultRegistration;
import io.github.syst3ms.skriptparser.util.FileUtils;
import java.io.IOException;
import java.net.URISyntaxException;

public class TestRegistration {

    public static void register() {
        DefaultRegistration.register();
        try {
            FileUtils.loadClasses("io.github.syst3ms.skriptparser", "effects", "expressions", "lang");
        } catch (IOException | URISyntaxException e) {
            System.err.println("Something is fugged :");
            e.printStackTrace();
        }
        BaseSkriptAddon.getRegistration().register();
    }
}
