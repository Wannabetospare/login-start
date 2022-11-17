package hello.login.web.argumentresolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



// @Login 어노테이션이 잘 작동하게 해주는 클래스
@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 실행 로그
        log.info("supportsParameter 실행");

        // 파라미터에 로그인 클래스가 있는지 확인
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);

        // 멤버 클래스가 파라미터의 클래스 타입과 같은지 확인
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        // 위 두가지를 다 만족하면 true 로 실행
        return hasLoginAnnotation && hasMemberType;
    }



    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        log.info("resolveArgument 실행");

        // 요청 정보를 다운캐스팅해서 받고, getNativeRequest 로 request 를 받는다.
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        // 세션을 받는다.
        HttpSession session = request.getSession(false);

        // 세션이 null 이면 null 을 넣는다.
        if (session == null) {
            return null;
        }

        // 세션이 null 이 아니라면 세션에서 로그인 멤버를 뽑고 반환한다.
        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
