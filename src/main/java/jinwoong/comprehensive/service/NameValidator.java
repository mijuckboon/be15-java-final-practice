package jinwoong.comprehensive.service;

import jinwoong.comprehensive.domain.Member;

import java.util.regex.Pattern;

public class NameValidator {
    final static Pattern REGEX_NAME = Pattern.compile("[가-힣a-zA-Z_0-9]+");

    static boolean isValid(String name) {
        return name.matches(REGEX_NAME.pattern());
    }

    static void checkName(Member member) {
        if (!isValid(member.getName())) {
            throw new IllegalArgumentException("유효하지 않은 이름입니다. (규칙: 한글, 영어, 숫자, 특수 문자: _)");
        }
    }
}
