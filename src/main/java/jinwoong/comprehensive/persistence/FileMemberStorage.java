package jinwoong.comprehensive.persistence;

import jinwoong.comprehensive.domain.Member;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileMemberStorage implements MemberStorage {
    private static final String FILE_PATH = "src/main/java/jinwoong/comprehensive/db/memberDB.dat";

    @Override
    public void saveMembers(List<Member> members) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(members);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류 발생", e);
        }
    }

    @Override
    public List<Member> loadMembers() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file)) ) {
                return (List<Member>) ois.readObject();
        } catch (EOFException e) {
            System.out.println("회원 정보를 모두 로딩하였습니다.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("파일 로딩 중 오류 발생", e);
        }
    }
}
