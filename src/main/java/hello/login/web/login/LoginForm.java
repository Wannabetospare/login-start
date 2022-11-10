package hello.login.web.login;


import lombok.Data;

import javax.validation.constraints.NotEmpty;


// 로그인을 위한 로그인 전용 폼
@Data
public class LoginForm {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;


}
