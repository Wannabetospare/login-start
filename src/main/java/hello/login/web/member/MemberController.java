package hello.login.web.member;


import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberRepository memberRepository;


    // 매핑 종류 - GET
    // 매핑 주소 - /members/add
    // 이름 - adForm
    // 매개변수 - 모델, member
    // 동작 - x
    // 반환 - 주소
    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member) {
        return "members/addMemberForm";
    }


    // 매핑 종류 - POST
    // 매핑 주소 - /members/add
    // 이름 - save (멤버를 저장하는 컨트롤러)
    // 매개변수 - 검증기, 모델, member, BindingResult
    // 동작 - BindingResult 에러가 있다면, members/addMemberForm 로 반환하고 아니라면 멤버를 저장하고 홈으로 redirect 한다.
    // 반환 - 주소
    @PostMapping("/add")
    public String save(@Valid @ModelAttribute Member member, BindingResult result) {

        if (result.hasErrors()) {
            return "members/addMemberForm";
        }

        memberRepository.save(member);
        return "redirect:/";
    }


}