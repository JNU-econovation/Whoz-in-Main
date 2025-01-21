package com.whoz_in.domain_jpa.badge;

import com.whoz_in.domain.member.model.IsBadgeShown;
import com.whoz_in.domain_jpa.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(BadgeMemberId.class)
public class BadgeMemberEntity extends BaseEntity {
    @Id
    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    @Id
    @Column(name = "badge_id", nullable = false)
    private UUID badgeId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IsBadgeShown isBadgeShown = IsBadgeShown.Y;

    public BadgeMemberEntity(UUID memberId, UUID badgeId, IsBadgeShown isBadgeShown) {
        this.memberId = memberId;
        this.badgeId = badgeId;
        this.isBadgeShown = isBadgeShown;
    }
}
