package hello.login.domain.member;




// 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.*;

@Slf4j
@Repository // 저장소 계층 선언
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); // static 사용 - 하나로 공유됨
    private static long sequence = 0L; // static 사용 - 하나로 공유됨

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    }


    // id 를 매개변수로 사용해서 멤버를 찾는 메서드
    public Member findById(Long id) {
        return store.get(id);
    }


    // loginId 을 매개변수로 이용해서 멤버를 찾는 메서드
    public Optional<Member> findByLoginId(String loginId) {
        // 모든 loginId 를 찾고, 스트림(반복문비슷)을 돌려서 찾고,
        return findAll().stream()
                // 필터에 만족하는 것들만 다음단계로 넘어가고,
                .filter(m -> m.getLoginId().equals(loginId))
                // 먼저 받아진것을 반환한다.(그 뒤에는 무시된다.)
                .findFirst();
    }


    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }


    public void clearStore() {
        store.clear();
    }
}


