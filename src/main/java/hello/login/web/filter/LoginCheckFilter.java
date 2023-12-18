package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {
    //인터페이스라도 default 메서드는 구현하지 않아도 된다.

    //whiteList는 인증을 거치지 않아도 되는 페이지를 의미한다.
    private static final String[] whiteList = {"/", "/members/add", "/login", "/logout", "/css/*"};
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try{
            log.info("인증 체크 필터 시작{}", requestURI);
            if(isLoginCheckPath(requestURI)){
                log.info("인증 체크 로직 실행 {}", requestURI);
                //인증을 해야 하기에 세션에서 찾아봄.
                HttpSession session = httpRequest.getSession(false);
                if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
                    log.info("미인증 사용자의 요청 {}", requestURI);
                    //로그인으로 Redirect 후 로그인 진행 후에 기존 페이지로
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    //그냥 return -> 다음 서블릿, 컨트롤러를 호출하지 않는다.
                    return;
                }
            }
            chain.doFilter(request, response);
        }catch (Exception e){
            throw e;
        }finally {
            log.info("인증 체크 필터 종료", requestURI);
        }
    }

    //whiteList의 경우 인증 체크를 하지 않는 메서드.
    private boolean isLoginCheckPath(String requestURI){
        // PatternMatchUtils.simpleMatch(@Nullable String[] patterns, String str)
        // -> patterns와 str를 비교하여 str이 없는 경우 false를 리턴하고, str이 있는 경우는 true를 리턴함.
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
