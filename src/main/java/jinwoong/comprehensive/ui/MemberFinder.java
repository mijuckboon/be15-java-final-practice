package jinwoong.comprehensive.ui;

import jinwoong.comprehensive.domain.Member;
import jinwoong.comprehensive.domain.Role;
import jinwoong.comprehensive.domain.Status;
import jinwoong.comprehensive.service.MemberService;

import java.util.List;

public class MemberFinder { // 전략 패턴 적용할 수 있을 듯?
    private final InputManager inputManager = new InputManager();
    private final MemberService memberService;

    public MemberFinder(MemberService memberService) {
        this.memberService = memberService;
    }

    void showAllMembers() {
        List<Member> members = memberService.findAllMembers();
        if (members.isEmpty()) {
            System.out.println("등록된 회원이 없습니다.");
        } else {
            members.forEach(System.out::println);
        }
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

    void showResult(MemberFindMethod method, Member member) {
        if (member != null) {
            System.out.println(member);
        } else {
            System.out.printf("해당 %s의 회원을 찾을 수 없습니다.%n", method.getDescription());
        }
    }

    void showResult(MemberFindMethod method, List<Member> members) {
        if (!members.isEmpty()) {
            System.out.printf("=== 결과 ===%n%s%n", inputManager.showElements(members.toArray()));
        } else {
            System.out.printf("해당 %s의 회원을 찾을 수 없습니다.%n", method.getDescription());
        }
    }

    void findMemberByNo() {
        String inputMessage = "찾을 회원 번호 입력: ";
        int no = inputManager.getInputByInt(inputMessage);

        Member member = memberService.findMemberByNo(no);
        showResult(MemberFindMethod.BY_NUMBER, member);
    }

    void findMembersByName() {
        String inputMessage = "찾을 회원 이름 입력: ";
        String name = inputManager.getInputByString(inputMessage);

        List<Member> members = memberService.findMembersByName(name);
        showResult(MemberFindMethod.BY_NAME, members);
    }

    void findMembersByRole() {
        String message = "===== 회원 역할 선택 =====%n%s".formatted(inputManager.showElements(Role.values()));
        String inputMessage = "번호 입력: ";
        int no = inputManager.getInputByInt(message, inputMessage);

        List<Member> members = memberService.findMembersByRole(Role.fromInt(no));
        showResult(MemberFindMethod.BY_ROLE, members);
    }

    void findMembersByStatus() {
        String message = "===== 회원 상태 선택 =====%n%s".formatted(inputManager.showElements(Status.values()));
        String inputMessage = "번호 입력: ";
        int no = inputManager.getInputByInt(message, inputMessage);

        List<Member> members = memberService.findMembersByStatus(Status.fromInt(no));
        showResult(MemberFindMethod.BY_STATUS, members);
    }

}
