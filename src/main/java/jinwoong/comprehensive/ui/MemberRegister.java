package jinwoong.comprehensive.ui;

import jinwoong.comprehensive.domain.Member;
import jinwoong.comprehensive.domain.Role;
import jinwoong.comprehensive.domain.Status;
import jinwoong.comprehensive.service.MemberService;

public class MemberRegister {
    private final InputManager inputManager = new InputManager();
    private final MemberService memberService;

    public MemberRegister(MemberService memberService) {
        this.memberService = memberService;
    }

    void registerMember() {
        try {
            String inputMessage = "이름 입력: ";
            String name = inputManager.getInputByString(inputMessage);

            if (memberService.isDuplicateMemberName(name)) {
                inputMessage = "이미 등록된 이름입니다. 추가하시겠습니까? (예: 1, 아니오: 2) : ";
                int choice = inputManager.getInputByInt(inputMessage);
                switch (choice) {
                    case 1 -> { }
                    case 2 -> { return; }
                    default -> { throw new IllegalArgumentException("유효하지 않은 입력입니다."); }
                }
            }

            String message = "역할 선택" + System.lineSeparator() + inputManager.showElements(Role.values());
            inputMessage = "번호 입력: ";

            int roleId = inputManager.getInputByInt(message, inputMessage);

            int nextMemberNo = memberService.findAllMembers().size() + 1;
            Member newMember = new Member(nextMemberNo, name, Role.fromInt(roleId), Status.IS_ACTIVE);

            memberService.registerMember(newMember);
            System.out.println("회원 등록 성공: " + newMember);

        } catch (IllegalArgumentException e) {
            System.out.println("회원 등록 실패: " + e.getMessage());
        }
    }



}
