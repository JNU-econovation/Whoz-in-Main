package com.whoz_in.log_writer.system_validator;

import com.whoz_in.log_writer.common.validation.ValidationResult;
import com.whoz_in.log_writer.common.validation.Validator;
import com.whoz_in.log_writer.common.process.TransientProcess;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandInstalledValidator implements Validator<String> {

    @Override
    public ValidationResult getValidationResult(String command){
        ValidationResult validationResult = new ValidationResult();

        List<String> results = new TransientProcess("which " + command).resultList();
        if (results.isEmpty() || !results.get(0).contains("/")) {
            validationResult.addError(command + "가 설치되지 않았습니다.");
        }
        return validationResult;
    }
}
