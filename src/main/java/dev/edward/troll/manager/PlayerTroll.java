package dev.edward.troll.manager;

import java.util.UUID;

public class PlayerTroll {

    private final UUID troller;
    private final UUID target;

    private Troll troll;

    public PlayerTroll(UUID troller, UUID target) {
        this.troller = troller;
        this.target = target;
    }

    public PlayerTroll(UUID troller, UUID target, Troll troll) {
        this.troller = troller;
        this.target = target;
        this.troll = troll;
    }

    public UUID getTroller() {
        return troller;
    }

    public UUID getTarget() {
        return target;
    }

    public Troll getTroll() {
        return troll;
    }

    public void setTroll(Troll troll) {
        this.troll = troll;
    }
}
