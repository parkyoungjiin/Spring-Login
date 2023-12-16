package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model){
        //쿠키로 조회하는 것이 아니라 세션 관리자에 의해 저장된 회원 정보 조회
        Member member = (Member)sessionManager.getSesssion(request);

        if (member == null){
            return "home";
        }
        model.addAttribute("member", member);
        return "loginHome";

    }
}