package jinwoong.comprehensive.ui;

import jinwoong.comprehensive.domain.Role;
import jinwoong.comprehensive.domain.Status;
import jinwoong.comprehensive.domain.Member;
import jinwoong.comprehensive.persistence.FileMemberStorage;
import jinwoong.comprehensive.persistence.MemberRepository;
import jinwoong.comprehensive.service.MemberService;

import java.util.List;

public class Application {
    private final MemberService memberService;
    private final InputManager inputManager = new InputManager();
    private final MemberFinder memberFinder;
    private final StatusModifier statusModifier = new StatusModifier();

    public Application() {
        MemberRepository memberRepository = new MemberRepository(new FileMemberStorage());
        this.memberService = new MemberService(memberRepository);
        this.memberFinder = new MemberFinder(memberService);
    }

    public void run() {
        while (true) {
            String message = """
                    %n===== 회원 관리 프로그램 =====
                    %s""".formatted(inputManager.showElements(Feature.values()));
            String inputMessage = "메뉴 선택 (번호 입력): ";

            int choice = inputManager.getInputByInt(message, inputMessage);

            try {
                switch (Feature.fromInt(choice)) {
                    case SHOW_ALL_MEMBERS -> showAllMembers();
                    case FIND_MEMBER -> memberFinder.findMember();
                    case REGISTER_MEMBER -> registerMember();
                    case MODIFY_MEMBER_INFO -> modifyMemberInfo();
                    case MODIFY_MEMBER_STATUS -> statusModifier.modifyMemberStatus();
                    case EXIT_PROGRAM -> {
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("오류: " + e.getMessage());
            }
        }
    }

    private void showAllMembers() {
        List<Member> members =  memberService.findAllMembers();
        if (members.isEmpty()) {
            System.out.println("등록된 회원이 없습니다.");
        } else {
            members.forEach(System.out::println);
        }
    }

    private void registerMember() {
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

    private void modifyMemberInfo() {
        try {
            String inputMessage = "수정할 회원 번호 입력: ";
            int no = inputManager.getInputByInt(inputMessage);

            Member existingMember = memberService.findMemberByNo(no);
            if (existingMember == null || memberService.isDeleted(existingMember)) {
                System.out.println("해당 번호의 회원을 찾을 수 없습니다.");
                return;
            }

            System.out.println("수정할 정보를 입력하세요 (변경하지 않으려면 Enter 입력)");

            String message = "기존 이름: " + existingMember.getName();
            inputMessage = "변경할 이름: ";
            String name = inputManager.getInputByString(message, inputMessage);
            if (name.isEmpty()) name = existingMember.getName();

            message = """
                    기존 역할: %s
                    변경할 역할 선택
                    %s""".formatted(existingMember.getRole().getDescription(),
                    inputManager.showElements(Role.values())
            );
            inputMessage = "번호 입력: ";
            String roleId = inputManager.getInputByString(message, inputMessage);
            Role role = roleId.isEmpty() ?
                    existingMember.getRole() : Role.fromInt(Integer.parseInt(roleId));

            Status status = existingMember.getStatus();
            Member updatedMember = new Member(no, name, role, status);
            memberService.modifyMemberInfo(updatedMember);
            System.out.println("회원 정보 수정 완료: " + no);
        } catch (IllegalArgumentException e) {
            System.out.println("회원 정보 수정 실패: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Application().run();
    }
}
