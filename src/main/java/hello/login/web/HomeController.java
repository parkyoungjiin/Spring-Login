package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

//    @GetMapping("/")
    public String home() {
        return "home";
    }
//    @GetMapping("/")
    public String homeLogin(@CookieValue(name ="memberId", required = false) Long memberId, Model model){
        //쿠키가 없는 경우는 home(로그인이 필요한 페이지로)
        if (memberId == null){
            return "home";
        }
        //쿠키가 있는 경우 로그인(로그인이 완료된 페이지로)
        Member loginMember = memberRepository.findById(memberId);

        if (loginMember == null){
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "loginHome";

    }

//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model){
        //쿠키로 조회하는 것이 아니라 세션 관리자에 의해 저장된 회원 정보 조회
        Member member = (Member)sessionManager.getSesssion(request);

        if (member == null){
            return "home";
        }
        model.addAttribute("member", member);
        return "loginHome";

    }

//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model){
        //세션은 필요할 때만 생성. false -> 있을 때만 세션을 생성.
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
//        System.out.println(loginMember);
        if (loginMember == null){
            return "home";
        }
        //세션 유지되면 로그인으로 이동.
        model.addAttribute("member", loginMember);
        return "loginHome";

    }
    //V3Spring : @SessionAttribute를 사용하는 로직.
//    @GetMapping("/")
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model){
        if (loginMember == null){
            return "home";
        }
        //세션 유지되면 로그인으로 이동.
        model.addAttribute("member", loginMember);
        return "loginHome";

    }

    //homeLoginV3ArgumentResolver : @Login을 사용한 로그인 여부 확인 로직.
    @GetMapping("/")
    public String homeLoginV3ArgumentResolver(@Login Member loginMember, Model model){
        if (loginMember == null){
            return "home";
        }
        //세션 유지되면 로그인으로 이동.
        model.addAttribute("member", loginMember);
        return "loginHome";

    }
}