package com.whoz_in.api_query_jpa.badge;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Entity
@Immutable
@Subselect("SELECT "
        + "b.id AS id, "
        + "b.name AS name, "
        + "b.color_code AS color_code, "
        + "b.badge_type AS badge_type, "
        + "b.created_at AS created_at "
        + "FROM badge_entity b")
@Synchronize({"badge_entity"})
public class Badge {
    @Id
    @UuidGenerator
    private UUID id;
    private String name;
    private String color_code;
    private String badge_type;
    private LocalDateTime created_at;
}
