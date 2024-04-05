package project01.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project01.board.member.Member;
import project01.board.member.MemberNotFoundException;
import project01.board.member.MemberService;

import java.util.List;


@RequestMapping("/members")
@Controller
@Slf4j
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/{memberId}")
    public String showMember(@PathVariable Long memberId, Model model){
        Member member = memberService.findMember(memberId);
        model.addAttribute("member", member);
        return "members/member";
    }

    @GetMapping("/join")
    public String joinForm() {
        return "/members/joinForm";
    }

    @PostMapping("/join")
    public String joinMember(@ModelAttribute Member form) {
        Member member = new Member();
        member.setName(form.getName());
        member.setAge(form.getAge());
        memberService.join(member);
        //return "redirect:/members/find/name?name="+member.getName();
        return "redirect:/members/member/"+member.getMemberId();
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