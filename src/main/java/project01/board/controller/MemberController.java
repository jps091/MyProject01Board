package project01.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project01.board.Utiliry.GenderType;
import project01.board.member.Member;
import project01.board.member.MemberNotFoundException;
import project01.board.member.MemberService;
import project01.board.validation.MemberValidator;

import java.util.List;


@RequestMapping("/members")
@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberValidator memberValidator;

/*    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }*/

    @InitBinder
    public void init(WebDataBinder dataBinder){
        log.info("init binder {}", dataBinder);
        dataBinder.addValidators(memberValidator);
    }

    @ModelAttribute("genderTypes")
    public GenderType[] genderTypes(){
        return GenderType.values();
    }

    @GetMapping("/member/{memberId}")
    public String showMember(@PathVariable Long memberId, Model model){
        Member member = memberService.findMember(memberId);
        model.addAttribute("member", member);
        return "members/member";
    }

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("member", new Member());
        return "/members/joinForm";
    }

    //@PostMapping("/join")
    public String joinMember(@ModelAttribute Member form) {
        log.info("Member.open={}",form.getOpen());
        memberService.join(form);
        return "redirect:/members/member/"+form.getMemberId();
    }

    //@PostMapping("/join")
    public String joinMemberV1(@ModelAttribute Member form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // 유효성 검증 로직
        if(!StringUtils.hasText(form.getName())){
            bindingResult.rejectValue("name", "required");
        }
        if(form.getAge() == null || form.getAge() < 5 || form.getAge() > 20){
            bindingResult.rejectValue("age", "range", new Object[]{5, 20}, null);
        }

        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "/members/joinForm";
        }

        // 성공 로직
        log.info("Member.open={}",form.getOpen());
        memberService.join(form);
        redirectAttributes.addAttribute("memberId", form.getMemberId());
        return "redirect:/members/member/{memberId}";
    }

    //@PostMapping("/join")
    public String joinMemberV2(@ModelAttribute Member form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        memberValidator.validate(form, bindingResult);
        // 유효성 검증 로직
        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "/members/joinForm";
        }


        // 성공 로직
        log.info("Member.open={}",form.getOpen());
        memberService.join(form);
        redirectAttributes.addAttribute("memberId", form.getMemberId());
        return "redirect:/members/member/{memberId}";
    }

    @PostMapping("/join")
    public String joinMemberV3(@Validated @ModelAttribute Member form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // 유효성 검증 로직
        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "/members/joinForm";
        }


        // 성공 로직
        log.info("Member.open={}",form.getOpen());
        memberService.join(form);
        redirectAttributes.addAttribute("memberId", form.getMemberId());
        return "redirect:/members/member/{memberId}";
    }

    @GetMapping
    public String membersList(Model model) {
        List<Member> members = memberService.findAllMember();
        model.addAttribute("members", members);
        return "members/members";
    }

    @GetMapping("/find")
    public String findForm() {
        return "members/findForm";
    }

    @GetMapping("/find/name") //"@{/members/find/{name}(name=${member.name})}"
    public String findMember(Model model, @RequestParam String name) {
        try {
            Member findMember = memberService.findByName(name);
            model.addAttribute("member", findMember);
            return "members/member";
        } catch (MemberNotFoundException e) {
            model.addAttribute("message", "일치하는 회원을 찾을 수 없습니다.");
            return "members/findForm";
        }
    }
    @GetMapping("/update")
    public String nameForm(){
        return "members/nameForm";
    }

    @GetMapping("/update/name") //@RequestParam(required = false, defaultValue = "100") int age
    public String nameForm2(@RequestParam("name") String name, Model model) {
        Member findMember = memberService.findByName(name);
        model.addAttribute("member",findMember);
        return "members/ageForm";
    }


    @PostMapping("/update/{name}") //@RequestParam(required = false, defaultValue = "100") int age
    public String updateAgeForm(@PathVariable String name,
                                @RequestParam int age,
                                RedirectAttributes redirectAttributes) {
        Member updateMember = memberService.findByName(name);
        updateMember.setAge(age);
        Long updateId = memberService.Update(updateMember.getMemberId(), updateMember).getMemberId();
        redirectAttributes.addAttribute("updateId", updateId);
        redirectAttributes.addAttribute("status", true);
        log.info("existMember.getMemberId()={}",updateId);
        return "redirect:/members/member/{updateId}";
    }

    @GetMapping("/delete/member")
    public String deleteForm() {
        return "members/deleteForm";
    }

    @PostMapping("/delete/member")
    public String deleteMember(Model model, @RequestParam String name) {
        try {
            Member deleteMember = memberService.findByName(name);
            memberService.Delete(deleteMember.getMemberId());
            model.addAttribute("member", deleteMember);
            return "redirect:/";
        } catch (MemberNotFoundException e) {
            model.addAttribute("message", "삭제할 회원을 찾을 수 없습니다.");
            return "members/deleteForm";
        }
    }
}