package com.example.rqs.core.space;

public enum SpaceRole {
    ADMIN(999),
    MEMBER(998),
    GUEST(0);

    private final int seq;

    SpaceRole(int seq) {
        this.seq = seq;
    }

    public boolean gt(SpaceRole role) {
        return this.seq > role.seq;
    }

    public boolean goe(SpaceRole role) {
        return this.seq >= role.seq;
    }
}
