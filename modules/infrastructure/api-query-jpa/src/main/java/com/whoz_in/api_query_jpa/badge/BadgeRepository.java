package com.whoz_in.api_query_jpa.badge;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, UUID> {
    Optional<Badge> findById(UUID id);
    @Query("SELECT b.id FROM Badge b")
    Set<UUID> findAllIds();
}