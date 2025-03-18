package project.ecm.global.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import project.ecm.global.exception.ExceptionType;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder({"isSuccess", "code", "message", "timestamp"})
public class ExceptionResponse {

    @JsonProperty("isSuccess")
    private final boolean isSuccess;

    private final String code;
    private final String message;
    private final LocalDateTime timestamp;

    public ExceptionResponse(String code, String message) {
        this.isSuccess = false;
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ExceptionResponse(ExceptionType exceptionType) {
        this(exceptionType.getErrorCode(), exceptionType.getMessage());
    }

    public ExceptionResponse(ExceptionType exceptionType, String message) {
        this(exceptionType.getErrorCode(), message);
    }

    @JsonProperty("isSuccess") // "success"가 추가되지 않도록 명시적으로 설정
    public boolean isSuccess() {
        return isSuccess;
    }
}