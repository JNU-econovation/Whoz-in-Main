package api.shared;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ApiResponseBody {
    @Getter
    @AllArgsConstructor
    public static class FailureBody implements Serializable {
        private String status;
        private String code;
        private String message;
    }

    @Getter
    @AllArgsConstructor
    public static class SuccessBody<D> implements Serializable {
        private D data;
        private String message;
        private String code;
    }
}
