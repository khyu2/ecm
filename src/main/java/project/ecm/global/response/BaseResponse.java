package project.ecm.global.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"isSuccess", "code", "result"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse<T> {

    @JsonProperty("isSuccess")
    private final boolean isSuccess;

    private final String code;

    @JsonInclude(JsonInclude.Include.NON_NULL) // NULL인 경우 제외
    private final T result;

    public BaseResponse(T result) {
        this.isSuccess = true;
        this.code = "200";
        this.result = result;
    }

    @JsonProperty("isSuccess") // "success"가 추가되지 않도록 명시적으로 설정
    public boolean isSuccess() {
        return isSuccess;
    }
}