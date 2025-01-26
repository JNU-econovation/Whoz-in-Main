package com.whoz_in.domain.badge.model;

import java.util.UUID;

public record BadgeId(UUID id) {
    public BadgeId() {this(UUID.randomUUID());};

    @Override
    public String toString() {return id.toString();}
}
