package jinwoong.comprehensive.ui;

import jinwoong.comprehensive.domain.Member;
import jinwoong.comprehensive.domain.Role;
import jinwoong.comprehensive.domain.Status;
import jinwoong.comprehensive.service.MemberService;

public class MemberInfoModifier {
    private final InputManager inputManager = new InputManager();
    private final MemberService memberService;

    public MemberInfoModifier(MemberService memberService) {
        this.memberService = memberService;
    }

    void modifyMemberInfo() {
        try {
            Member existingMember = getMemberToModify();
            if (existingMember == null || memberService.isDeleted(existingMember)) {
                System.out.println("해당 번호의 회원을 찾을 수 없습니다.");
                return;
            }
            System.out.println("수정할 정보를 입력하세요 (변경하지 않으려면 Enter 입력)");
            String name = getNameToChange(existingMember);
            Role role = getRoleToChange(existingMember);
            int no = existingMember.getNo();
            Status status = existingMember.getStatus();

            Member updatedMember = new Member(no, name, role, status);
            memberService.modifyMemberInfo(updatedMember);
            System.out.println("회원 정보 수정 완료: " + no);
        } catch (IllegalArgumentException e) {
            System.out.println("회원 정보 수정 실패: " + e.getMessage());
        }
    }

    Member getMemberToModify() {
        String inputMessage = "수정할 회원 번호 입력: ";
        int no = inputManager.getInputByInt(inputMessage);

        return memberService.findMemberByNo(no);
    }

    String getNameToChange(Member existingMember) {
        String message = "기존 이름: " + existingMember.getName();
        String inputMessage = "변경할 이름: ";
        String name = inputManager.getInputByString(message, inputMessage);
        if (name.isEmpty()) name = existingMember.getName();
        return name;
    }

    Role getRoleToChange(Member existingMember) {
        String message = """
                    기존 역할: %s
                    변경할 역할 선택
                    %s""".formatted(
                existingMember.getRole().getDescription(),
                inputManager.showElements(Role.values())
        );
        String inputMessage = "번호 입력: ";
        String roleId = inputManager.getInputByString(message, inputMessage);
        return roleId.isEmpty() ?
                existingMember.getRole() : Role.fromInt(Integer.parseInt(roleId));
    }
}
