package hello.login;


import hello.login.web.argumentresolver.LoginMemberArgumentResolver;
import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import javax.servlet.Filter;
import java.util.List;


@Configuration
public class WebConfig implements WebMvcConfigurer {


    // LoginMemberArgumentResolver 를 등록
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }



    // 스프링 인터셉터를 등록하는 메서드
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LogInterceptor())
                .order(1)
                // 전체 경로
                .addPathPatterns("/**")
                // 인터셉터 적용 x - 제외되는 경로
                .excludePathPatterns("/css/**", "/*.ico", "/error");


        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                // 자세하게 제외할 주소를 편하게 설정할 수 있음
                .excludePathPatterns(
                        "/", "/members/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error");
    }


    // @Bean - 스프링 컨테이너에 빈 등록
    public FilterRegistrationBean logFilter() {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        // 필터를 등록할 클래스를 지정한다.
        filterRegistrationBean.setFilter(new LogFilter());
        // 필터는 체인으로 동작한다. 따라서 순서가 필요하다. 낮을 수록 먼저 동작한다.
        filterRegistrationBean.setOrder(1);
        // 필터를 적용할 URL 패턴을 지정 - * 은 모든 경로에 적용
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;

        
    }

    @Bean
    public FilterRegistrationBean loginCheckFilter() {

        // 필터리스트 생성
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        // 필터 클래스 지정
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        // 필터 순서 지정
        filterRegistrationBean.setOrder(2);
        // 필터 경로 지정
        filterRegistrationBean.addUrlPatterns("/*");
        // 필터 반환
        return filterRegistrationBean;
    }



}