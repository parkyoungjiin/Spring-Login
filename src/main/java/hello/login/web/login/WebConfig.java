package hello.login.web.login;

import hello.login.web.filter.LogFIlter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
//    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LogFIlter());
        filterFilterRegistrationBean.setOrder(1); // 순서
        filterFilterRegistrationBean.addUrlPatterns("/*"); // 모든 URL에 필터 적용

        return filterFilterRegistrationBean;

    }

    @Bean
    public FilterRegistrationBean logCheckFilter(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LoginCheckFilter());
        filterFilterRegistrationBean.setOrder(2); // 순서
        filterFilterRegistrationBean.addUrlPatterns("/*"); // 모든 URL에 필터 적용

        return filterFilterRegistrationBean;

    }
    //인터셉터 추가 설정
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error"); // 해당 경로는 interCepter를 하지 않음.
    }
}
