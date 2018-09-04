package io.github.syst3ms.skriptparser;

import io.github.syst3ms.skriptparser.registration.SkriptAddon;

public class Skript extends SkriptAddon {
    private String[] mainArgs;

    public Skript(String[] mainArgs) {
        this.mainArgs = mainArgs;
    }
}
