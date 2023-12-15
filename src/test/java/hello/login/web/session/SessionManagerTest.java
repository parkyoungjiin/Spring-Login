package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.*;

public class SessionManagerTest {
    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest(){

        //HttpServletResponse는 인터페이스임.
        // 스프링의 MockHttpServletResponse를 사용하여 가짜 객체를 적용.
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSesssion(member, response);

        //요청에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        //세션 조회(쿠키 : 클라 -> 세션 저장소 )
        Object result = sessionManager.getSesssion(request);
        assertThat(result).isEqualTo(member);

        //세션 만료
        sessionManager.expire(request);

        //만료 되었는 지 확인
        Object expired = sessionManager.getSesssion(request);
        assertThat(expired).isNull();

    }
}
