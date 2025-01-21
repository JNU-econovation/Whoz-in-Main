package com.whoz_in.domain_jpa.member;

import com.whoz_in.domain.member.model.Position;
import com.whoz_in.domain.member.model.SocialProvider;
import com.whoz_in.domain_jpa.badge.BadgeMemberEntity;
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
import java.util.Set;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {

  @Id
  @Column(columnDefinition = "BINARY(16)", nullable = false)
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private int generation;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Position position;

  @Column(nullable = false)
  private String statusMessage;

  @Column(unique = true)
  private String loginId;

  private String password;

  @Enumerated(EnumType.STRING)
  private SocialProvider socialProvider;

  @Column(unique = true)
  private String socialId;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "member_id")
  private Set<BadgeMemberEntity> badgeMembers;

  public MemberEntity(UUID id, String name, int generation, Position position, String statusMessage,
                      String loginId, String password,
                      SocialProvider socialProvider, String socialId, Set<BadgeMemberEntity> badgeMembers) {
    this.id = id;
    this.name = name;
    this.generation = generation;
    this.position = position;
    this.statusMessage = statusMessage;
    this.loginId = loginId;
    this.password = password;
    this.socialProvider = socialProvider;
    this.socialId = socialId;
    this.badgeMembers = badgeMembers;
  }
}
