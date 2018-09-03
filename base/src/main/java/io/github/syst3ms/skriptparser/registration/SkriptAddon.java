package io.github.syst3ms.skriptparser.registration;

import io.github.syst3ms.skriptparser.lang.Trigger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SkriptAddon {
    private static Set<SkriptAddon> addons = new HashSet<>();

    {
        addons.add(this);
    }

    public static Set<SkriptAddon> getAddons() {
        return addons;
    }

    private String name;

    public void handleTrigger(Trigger trigger) {}

    public void finishedLoading() {}
}
