package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// 세션 관리
@Component
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "mySessionId";
    //여러 요청, 여러 스레드가 접근할 경우(동시성 문제) ConcurrentHashMap을 사용.
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /*
    세션 생성
    1. sessionId 생성(임의 랜덤 값)
    2. 세션 저장소에 sessionId와 value 저장
    3. sessionId로 응답 쿠키 생성하여 클라이언트에 전달.
    */
    public void createSesssion(Object value, HttpServletResponse response){
        //1번 : 세션 생성
        String sessionId = UUID.randomUUID().toString();

        //2번 : 세션 저장소에 저장
        sessionStore.put(sessionId, value);

        //3번 : 쿠키 생성 + 응답 쿠키 저장
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);

    }
    //세션 조회
    public Object getSesssion(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null){
            return null;
        }
        //쿠키를 조회했을 때 세션 저장소에 있는 경우
        //-> sessionStore에 sessionCookie의 getValue를 통해
        System.out.println("확인:"+sessionCookie.getValue());
        return sessionStore.get(sessionCookie.getValue());

    }

    //3. 세션 만료
    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }


    //쿠키 찾는 메서드(분리)
    public Cookie findCookie(HttpServletRequest request, String cookieName){
        if(request.getCookies() == null){
            return null;
        }
        //배열을 스트림으로 변경.
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                //findFirst는 제일 먼저, findAny는
                .findAny()
                .orElse(null);

    }


}
