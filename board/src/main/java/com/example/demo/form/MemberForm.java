package com.example.demo.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@PasswordMatches
public class MemberForm {

    @NotBlank(message = "로그인 아이디를 입력해 주세요.")
    @Size(min = 4, max = 20, message = "로그인 아이디는 4자 이상 20자 이하여야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해 주세요.")
    private String passwordConfirmation;

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "올바른 이메일 형식으로 입력해 주세요.")
    private String email;
}
