package com.example.bbs.form;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    @NotBlank(message = "로그인 아이디를 입력해 주세요.")
    @Size(min = 4, max = 20, message = "로그인 아이디는 4자 이상 20자 이하로 입력해 주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(min = 8, message = "비밀번호는 8자 이상 입력해 주세요.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해 주세요.")
    private String passwordConfirm;

    @NotBlank(message = "이름을 입력해 주세요.")
    @Size(max = 255, message = "이름은 255자 이하로 입력해 주세요.")
    private String name;

    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "올바른 이메일 형식으로 입력해 주세요.")
    @Size(max = 254, message = "이메일은 254자 이하로 입력해 주세요.")
    private String email;

    @AssertTrue(message = "비밀번호 확인이 일치하지 않습니다.")
    public boolean isPasswordConfirmed() {
        return password == null || passwordConfirm == null || password.equals(passwordConfirm);
    }
}
