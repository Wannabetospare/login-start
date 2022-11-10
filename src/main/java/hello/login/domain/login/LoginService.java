package hello.login.domain.login;


import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service // 서비스계층
@RequiredArgsConstructor // 생성자
public class LoginService {


    private final MemberRepository memberRepository;


    // 로그인이 성공하면 멤버를 반환하지만, 반환값이 null 이라면 로그인 실패
    public Member login(String loginId, String password){

        // loginId 를 찾고
        return memberRepository.findByLoginId(loginId)
                // password 를 비교해서 같으면 통과
                .filter(m -> m.getPassword().equals(password))
                // password 가 다르면 null 반환
                .orElse(null);

    }
}
