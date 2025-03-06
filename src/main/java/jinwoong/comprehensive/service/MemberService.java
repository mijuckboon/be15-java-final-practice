package jinwoong.comprehensive.service;

import jinwoong.comprehensive.domain.Member;
import jinwoong.comprehensive.domain.Role;
import jinwoong.comprehensive.domain.Status;
import jinwoong.comprehensive.persistence.MemberRepository;

import java.util.List;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAllMembers() {
        return memberRepository.selectAllMembers();
    }

    public Member findMemberByNo(int no) {
        return memberRepository.selectMemberByNo(no);
    }

    public List<Member> findMembersByName(String name) {
        return memberRepository.selectMembersByName(name);
    }

    public List<Member> findMembersByRole(Role role) {
        return memberRepository.selectMembersByRole(role);
    }

    public List<Member> findMembersByStatus(Status status) {
        return memberRepository.selectMembersByStatus(status);
    }

    public void registerMember(Member member) {
        NameValidator.checkName(member);
        memberRepository.insertMember(member);
    }

    public void modifyMemberInfo(Member updatedMember) {
        Member existingMember = memberRepository.selectMemberByNo(updatedMember.getNo());
        if (existingMember == null || isDeleted(existingMember)) {
            throw new IllegalArgumentException("회원 정보 수정 실패: 해당 회원을 찾을 수 없습니다.");
        }
        NameValidator.checkName(updatedMember);
        memberRepository.updateMemberInfo(updatedMember);
    }

    public void activateMember(int no) {
        Member existingMember = memberRepository.selectMemberByNo(no);
        if (existingMember == null || isDeleted(existingMember)) {
            throw new IllegalArgumentException("회원 활성화 실패: 해당 회원을 찾을 수 없습니다.");
        } if (isActive(existingMember)) {
            throw new IllegalArgumentException("회원 활성화 실패: 이미 활성화 상태인 회원입니다.");
        }
        memberRepository.activateMember(no);
    }

    public void inactivateMember(int no) {
        Member existingMember = memberRepository.selectMemberByNo(no);
        if (existingMember == null || isDeleted(existingMember)) {
            throw new IllegalArgumentException("회원 비활성화 실패: 해당 회원을 찾을 수 없습니다.");
        } if (isInactive(existingMember)) {
            throw new IllegalArgumentException("회원 비활성화 실패: 이미 비활성화 상태인 회원입니다.");
        }
        memberRepository.inactivateMember(no);
    }

    public void removeMember(int no) {
        Member existingMember = memberRepository.selectMemberByNo(no);
        if (existingMember == null || isDeleted(existingMember)) {
            throw new IllegalArgumentException("회원 탈퇴 실패: 해당 회원을 찾을 수 없습니다.");
        }
        memberRepository.deleteMember(no);
    }

    public boolean isDuplicateMemberName(String memberName) {
        return memberRepository.selectAllMembers()
                .stream()
                .filter(member -> !member.getStatus().equals(Status.IS_DELETED)) // 탈퇴한 회원은 제외
                .anyMatch(member -> member.getName().equals(memberName));
    }

    // Status 클래스 변경 시 컴파일러가 경고 띄우도록 enum - switch로 작성
    public boolean isActive(Member member) {
        Status status = member.getStatus();
        return switch (status) {
            case IS_ACTIVE -> true;
            case IS_DELETED, IS_INACTIVE -> false;
        };
    }

    public boolean isInactive(Member member) {
        Status status = member.getStatus();
        return switch (status) {
            case IS_INACTIVE -> true;
            case IS_ACTIVE, IS_DELETED -> false;
        };
    }

    public boolean isDeleted(Member member) {
        Status status = member.getStatus();
        return switch (status) {
            case IS_DELETED -> true;
            case IS_ACTIVE, IS_INACTIVE -> false;
        };
    }
}
