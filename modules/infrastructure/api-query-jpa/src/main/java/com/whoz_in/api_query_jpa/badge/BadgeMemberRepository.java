package com.whoz_in.api_query_jpa.badge;

import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeMemberRepository extends JpaRepository<BadgeMember, Long> {
    @Query("SELECT bm.badge_id FROM BadgeMember bm WHERE bm.is_badge_shown = 'Y' AND bm.member_id = :memberId")
    Set<UUID> findByMemberId(@Param("memberId") UUID memberId);
}
