package hello.login.domain.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class Member {

    private Long id;

    // 로그인 아이디 - 비어있으면 오류
    @NotEmpty
    private String loginId;

    // 사용자 이름 - 비어있으면 오류
    @NotEmpty
    private String name;

    // 비밀번호 - 비어있으면 오류
    @NotEmpty
    private String password;


}
