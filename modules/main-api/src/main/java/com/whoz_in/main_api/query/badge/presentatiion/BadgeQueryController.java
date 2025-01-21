package com.whoz_in.main_api.query.badge.presentatiion;

import com.whoz_in.main_api.query.badge.application.BadgeId;
import com.whoz_in.main_api.query.badge.application.BadgeIdsResponse;
import com.whoz_in.main_api.query.badge.application.BadgeInfoResponse;
import com.whoz_in.main_api.query.badge.application.MemberId;
import com.whoz_in.main_api.query.shared.application.EmptyQuery;
import com.whoz_in.main_api.query.shared.application.QueryBus;
import com.whoz_in.main_api.query.shared.presentation.QueryController;
import com.whoz_in.main_api.shared.presentation.CrudResponseCode;
import com.whoz_in.main_api.shared.presentation.ResponseEntityGenerator;
import com.whoz_in.main_api.shared.presentation.SuccessBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BadgeQueryController extends QueryController {
    public BadgeQueryController(QueryBus queryBus) {
        super(queryBus);
    }

    @GetMapping("/api/v1/badges")
    public ResponseEntity<SuccessBody<BadgeInfoResponse>> viewBadgeInfo(@RequestBody BadgeId request) {
        BadgeInfoResponse r = ask(request);
        return ResponseEntityGenerator.success(r, CrudResponseCode.READ);
    }

    @GetMapping("/api/v1/badges/whozin")
    public ResponseEntity<SuccessBody<BadgeIdsResponse>> viewBadgeIdsOfWhozIn() {
        BadgeIdsResponse r = ask(new EmptyQuery());
        return ResponseEntityGenerator.success(r,CrudResponseCode.READ);
    }

    @GetMapping("/api/v1/badges/members")
    public ResponseEntity<SuccessBody<BadgeIdsResponse>> viewBadgesOfMember(@RequestBody MemberId request) {
        BadgeIdsResponse r = ask(request);
        return ResponseEntityGenerator.success(r,CrudResponseCode.READ);
    }
}
