package com.whoz_in.main_api.command.badge.presentation;

import com.whoz_in.main_api.command.badge.application.BadgeRegister;
import com.whoz_in.main_api.command.shared.application.CommandBus;
import com.whoz_in.main_api.command.shared.presentation.CommandController;
import com.whoz_in.main_api.shared.presentation.ResponseEntityGenerator;
import com.whoz_in.main_api.shared.presentation.SuccessBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BadgeCommandController extends CommandController {
    public BadgeCommandController(CommandBus commandBus) {super(commandBus);}

    @PostMapping("/api/v1/badges")
    public ResponseEntity<SuccessBody<Void>> create(@RequestBody BadgeRegister request) {
        dispatch(request);
        return ResponseEntityGenerator.success( "뱃지 생성 완료", HttpStatus.CREATED);
    }
}
