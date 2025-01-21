package com.whoz_in.domain_jpa.badge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeMemberEntityRepository extends JpaRepository<BadgeMemberEntity, Long> {
}
