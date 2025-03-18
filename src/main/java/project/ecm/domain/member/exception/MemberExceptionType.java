package project.ecm.domain.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import project.ecm.global.exception.ExceptionType;

@Getter
@AllArgsConstructor
public enum MemberExceptionType implements ExceptionType {

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "member-001", "회원을 찾을 수 없습니다."),
    NOT_PERMISSION(HttpStatus.FORBIDDEN, "member-002", "권한이 없습니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "member-003", "아이디 또는 패스워드가 잘못되었습니다."),
    EMPTY_USER_INFO(HttpStatus.BAD_REQUEST, "member-004", "아이디 또는 패스워드에 잘못된 값이 있습니다."),
    DUPLICATED_USERNAME(HttpStatus.BAD_REQUEST, "member-005", "중복된 아이디입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}