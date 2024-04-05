package project01.board.controller;

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
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
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
        return "redirect:/members/find/name?name="+member.getName();
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
                                @ModelAttribute Member updateMember,
                                RedirectAttributes redirectAttributes) {
        Member existMember = memberService.findByName(name);
        existMember = memberService.Update(existMember.getMemberId(), updateMember);
        redirectAttributes.addAttribute("name", name);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/members/find/name?name={name}";
    }

    @GetMapping("/delete/member")
    public String deleteForm() {
        return "members/deleteForm";
    }

    @PostMapping("/delete/member")
    public String deleteMember(Model model, @RequestParam String name) {
        try {
            Member deleteMember = memberService.findByName(name);
            boolean result = memberService.Delete(deleteMember.getMemberId());
            model.addAttribute("member", deleteMember);
            return "members/member";
        } catch (MemberNotFoundException e) {
            model.addAttribute("message", "삭제할 회원을 찾을 수 없습니다.");
            return "members/deleteForm";
        }
        //return "member/saveMember";
/*        if(result)
            return "member/saveMember";
        else{
            model.addAttribute("message", "일치하는 회원을 찾을 수 없습니다.");
            return "member/removeMember";
        }*/
    }
}