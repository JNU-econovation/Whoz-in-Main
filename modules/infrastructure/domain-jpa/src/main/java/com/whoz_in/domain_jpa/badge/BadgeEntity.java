package com.whoz_in.domain_jpa.badge;

import com.whoz_in.domain.badge.model.BadgeType;
import com.whoz_in.domain_jpa.shared.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BadgeEntity extends BaseEntity {
    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID id;

    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BadgeType badgeType;

    private String colorCode;

    @Column(columnDefinition = "BINARY(16)", nullable = true)
    private UUID creator;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "badge_id")
    private List<BadgeMemberEntity> badgeMembers;

    public BadgeEntity(UUID id, String name, BadgeType badgeType, String colorCode, UUID creator) {
        this.id = id;
        this.name = name;
        this.badgeType = badgeType;
        this.creator = creator;
        this.colorCode = colorCode;
    }
}
