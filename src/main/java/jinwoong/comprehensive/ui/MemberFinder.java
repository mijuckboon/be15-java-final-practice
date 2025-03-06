package jinwoong.comprehensive.ui;

import jinwoong.comprehensive.domain.Member;
import jinwoong.comprehensive.domain.Role;
import jinwoong.comprehensive.domain.Status;
import jinwoong.comprehensive.persistence.FileMemberStorage;
import jinwoong.comprehensive.persistence.MemberRepository;
import jinwoong.comprehensive.service.MemberService;

import java.util.List;

public class MemberFinder { // 전략 패턴 적용할 수 있을 듯?
    InputManager inputManager = new InputManager();
    MemberRepository memberRepository = new MemberRepository(new FileMemberStorage());
    MemberService memberService;

    public MemberFinder(MemberService memberService) {
        this.memberService = memberService;
    }

    void findMember() {
        String message = "===== %s =====".formatted(Feature.FIND_MEMBER.toString()) + System.lineSeparator() + inputManager.showElements(MemberFindMethod.values());
        String inputMessage = "검색 조건 선택 (번호 입력): ";
        int choice = inputManager.getInputByInt(message, inputMessage);

        MemberFindMethod method = MemberFindMethod.fromInt(choice);

        switch (method) {
            case BY_NUMBER -> findMemberByNo();
            case BY_NAME -> findMembersByName();
            case BY_ROLE -> findMembersByRole();
            case BY_STATUS -> findMembersByStatus();
            default -> System.out.println("회원 찾기에 실패했습니다. (번호 없음)");
        }
    }

    void findMemberByNo() {
        String inputMessage = "찾을 회원 번호 입력: ";
        int no = inputManager.getInputByInt(inputMessage);

        Member member = memberService.findMemberByNo(no);
        if (member != null) {
            System.out.println(member);
        } else {
            System.out.println("해당 번호의 회원을 찾을 수 없습니다.");
        }
    }

    void findMembersByName() {
        String inputMessage = "찾을 회원 이름 입력: ";
        String name = inputManager.getInputByString(inputMessage);

        List<Member> members = memberService.findMembersByName(name);
        if (!members.isEmpty()) {
            System.out.println("=== 결과 ===" + System.lineSeparator() +
                               inputManager.showElements(members.toArray()));
        } else {
            System.out.println("해당 이름의 회원을 찾을 수 없습니다.");
        }
    }

    void findMembersByRole() {
        String message = "===== 회원 역할 선택 =====" + System.lineSeparator() + inputManager.showElements(Role.values());
        String inputMessage = "번호 입력: ";
        int no = inputManager.getInputByInt(message, inputMessage);

        List<Member> members = memberService.findMembersByRole(Role.fromInt(no));
        if (!members.isEmpty()) {
            System.out.println("=== 결과 ===" + System.lineSeparator() +
                               inputManager.showElements(members.toArray()));
        } else {
            System.out.println("해당 역할의 회원을 찾을 수 없습니다.");
        }
    }

    void findMembersByStatus() {
        String message = "===== 회원 상태 선택 =====" + System.lineSeparator() + inputManager.showElements(Status.values());
        String inputMessage = "번호 입력: ";
        int no = inputManager.getInputByInt(message, inputMessage);

        List<Member> members = memberService.findMembersByStatus(Status.fromInt(no));
        if (!members.isEmpty()) {
            System.out.println("=== 결과 ===" + System.lineSeparator() +
                               inputManager.showElements(members.toArray()));
        } else {
            System.out.println("해당 상태의 회원을 찾을 수 없습니다.");
        }
    }

}
