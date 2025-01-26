package com.whoz_in.domain_jpa.member;

import com.whoz_in.domain.member.MemberRepository;
import com.whoz_in.domain.member.model.Member;
import com.whoz_in.domain.member.model.MemberId;
import com.whoz_in.domain_jpa.badge.BadgeConverter;
import com.whoz_in.domain_jpa.badge.BadgeEntityRepository;
import com.whoz_in.domain_jpa.badge.BadgeMemberEntityRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository implements MemberRepository {
  private final MemberConverter memberConverter;
  private final MemberEntityJpaRepository memberRepo;

  @Override
  public void save(Member member) {
    MemberEntity memberEntity = memberConverter.from(member);
    memberRepo.save(memberEntity);
  }

  @Override
  public Optional<Member> findByLoginId(String loginId) {
    return memberRepo.findByLoginId(loginId).map(memberConverter::to);
  }

  @Override
  public boolean existsBySocialId(String socialId) {
    return memberRepo.existsBySocialId(socialId);
  }

  @Override
  public boolean existsByMemberId(MemberId memberId) {
    return memberRepo.existsById(memberId.id());
  }

  @Override
  public List<Member> findByName(String name) {
    List<MemberEntity> entities = memberRepo.findByName(name);
    return entities.stream().map(memberConverter::to).toList();
  }

  @Override
  public Optional<Member> findByMemberId(MemberId id) {
    UUID memberId = id.id();
    Optional<MemberEntity> entity = memberRepo.findById(memberId);
    return entity.map(memberConverter::to);
  }

  @Override
  public Optional<Member> findBySocialId(String socialId) {
    return memberRepo.findBySocialId(socialId).map(memberConverter::to);
  }
}
