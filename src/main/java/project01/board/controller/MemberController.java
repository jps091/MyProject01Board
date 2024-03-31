package project01.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project01.board.member.Member;
import project01.board.member.MemberNotFoundException;
import project01.board.member.MemberService;

import java.util.List;


@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String moveCreateMember() {
        return "member/createMemberForm";
    }

    @PostMapping
    public String createMember(MemberForm form, Model model) {
        Member member = new Member();
        member.setName(form.getName());
        member.setAge(form.getAge());

        memberService.join(member);
        model.addAttribute("member", member);
        return "member/saveMember";
    }

    @GetMapping("/members")
    public String membersList(Model model) {
        List<Member> members = memberService.findAllMember();
        model.addAttribute("members", members);
        return "member/membersList";
    }

    @GetMapping("/name")
    public String moveFindMember() {
        return "member/findMember";
    }

    @GetMapping("/findName")
    public String findMember(Model model, @ModelAttribute MemberForm member) {
        try {
            Member findMember = memberService.findByName(member.getName());
            model.addAttribute("member", findMember);
            return "member/saveMember";
        } catch (MemberNotFoundException e) {
            model.addAttribute("message", "일치하는 회원을 찾을 수 없습니다.");
            return "member/findMember";
        }
    }
    @GetMapping("/update")
    public String moveUpdateMember() {
        return "member/updateMember";
    }

    @PostMapping("/update")
    public String updateMember(Model model, @RequestParam("name") String name, @RequestParam(required = false, defaultValue = "100") int age) {
        Member existMember = memberService.findByName(name);
        Member updateMember = new Member();
        updateMember.setMemberId(existMember.getMemberId());
        updateMember.setName(name);
        updateMember.setAge(age);
        updateMember = memberService.Update(existMember.getMemberId(), updateMember);
        model.addAttribute("member",updateMember);
        return "member/saveMember";
    }

    @GetMapping("/remove")
    public String moveRemoveMember() {
        return "member/removeMember";
    }

    @PostMapping("/removeName")
    public String deleteMember(Model model, @RequestParam String name) {
        try {
            Member deleteMember = memberService.findByName(name);
            boolean result = memberService.Delete(deleteMember.getMemberId());
            model.addAttribute("member", deleteMember);
            return "member/saveMember";
        } catch (MemberNotFoundException e) {
            model.addAttribute("message", "삭제할 회원을 찾을 수 없습니다.");
            return "member/removeMember";
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
