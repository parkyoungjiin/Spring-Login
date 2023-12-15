package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    //저장
    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    };


    //찾기
    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public Optional<Member> findByLoginId(String loginId) {
        /*List<Member> all = findAll();
        for(Member m : all){
            if(m.getLoginId().equals(loginId)){
                return Optional.of(m);
            }
        }
        //Optional을 빈 통으로 생각하고, 회원객체가 존재할 수도 없을 수도 있다. (NPE를 방지)
        return Optional.empty();*/

        return findAll().stream() // stream이라는 byte단위로 변경.
                .filter(m -> m.getLoginId().equals(loginId)) // 조건에 일치하는 값들만 다음 단계로.(filter(조건))
                .findFirst(); //처음에 나오는 값을 반환.
    }

    public void clearStore(){
        store.clear();
    }
}
