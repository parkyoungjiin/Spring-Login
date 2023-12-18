package hello.login.web.login;

import hello.login.web.filter.LogFIlter;
import hello.login.web.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {
    @Bean
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
}
