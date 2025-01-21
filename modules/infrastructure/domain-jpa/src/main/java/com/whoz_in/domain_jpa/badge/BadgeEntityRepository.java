package com.whoz_in.domain_jpa.badge;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeEntityRepository extends JpaRepository<BadgeEntity, Long> {
    Optional<BadgeEntity> findByName(String name);
    Optional<BadgeEntity> findById(UUID id);
}
