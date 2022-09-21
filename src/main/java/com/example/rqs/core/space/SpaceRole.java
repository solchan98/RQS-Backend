package com.example.rqs.core.space;

public enum SpaceRole {
    ADMIN(999),
    MEMBER(998);

    private final int seq;

    SpaceRole(int seq) {
        this.seq = seq;
    }

    public boolean gt(SpaceRole role) {
        return this.seq > role.seq;
    }

    public boolean gte(SpaceRole role) {
        return this.seq >= role.seq;
    }
}
