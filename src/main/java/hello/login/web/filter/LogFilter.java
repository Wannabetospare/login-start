package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;


// 필터인터페이스를 상속받은 로그인필터 클래스
@Slf4j // 로그어노테이션
public class LogFilter implements Filter {


    // 필터시작 메서드
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }



    // 가장 중요한 메서드 - 필터를 실행시키는 로직
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 요청값 다운캐스팅
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // URL 주소
        String requestURI = httpRequest.getRequestURI();
        // 랜덤 UUID 값 생성
        String uuid = UUID.randomUUID().toString();

        try {

            log.info("REQUEST  [{}][{}]", uuid, requestURI);
            // chain - 다음 필터가 있으면 필터를 호출하고, 필터가 없으면 서블릿을 호출한다. 만약 이 로직을 호출하지 않으면 다음 단계로 진행되지 않는다.
            chain.doFilter(request, response);

        } catch (Exception e) {
            throw e;

        } finally {
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }
    }


    // 필터 종료 메서드
    @Override
    public void destroy() {
        log.info("log filter destroy");
    }

}
