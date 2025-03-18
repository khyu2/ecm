package project.ecm.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MemberSignUpDto(
        @NotNull @Email String email,
        @NotNull @Size(min = 4, max = 15) String password,
        @NotNull @Size(min = 4, max = 15) String nickname,
        @NotNull @Size(min = 4, max = 15) String tel,
        @NotNull LocalDate birth,
        String profileImage
) {
}
