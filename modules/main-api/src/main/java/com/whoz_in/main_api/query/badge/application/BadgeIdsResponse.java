package com.whoz_in.main_api.query.badge.application;

import com.whoz_in.main_api.query.shared.application.Response;
import java.util.Set;

public record BadgeIdsResponse(Set<BadgeId> badgeIds) implements Response {
}
