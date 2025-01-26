package com.whoz_in.main_api.query.badge.application;

import com.whoz_in.main_api.query.shared.application.Query;
import java.util.UUID;

public record MemberId(
        UUID memberId
) implements Query {}
