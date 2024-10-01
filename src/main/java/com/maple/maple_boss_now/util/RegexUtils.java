package com.maple.maple_boss_now.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    private static final Logger logger = LoggerFactory.getLogger(RegexUtils.class);
    public final static String REGEX_SUB_DOMAIN = "^[a-z0-9]+[a-z0-9-]*[a-z0-9]+$";
    public final static String REGEX_EMAIL = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*(?:\\w+\\.)[a-zA-Z]{2,3}$";
    public final static String REGEX_MOBILE = "^[0-9-+()]*$";
    public final static String REGEX_KO = ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*";
    public final static String REGEX_NUMBER = "^[0-9]+$";
    public final static String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&#^()_+`{}|:<>,./])[A-Za-z\\d$@$!%*?&#^()_+`{}|:<>,./]{9,}";

    public final static Pattern PATTERN_CAMEL = Pattern.compile("([a-z])([A-Z]+)");

    public static boolean isEmail(String email) {
        try {
            Pattern pattern = Pattern.compile(REGEX_EMAIL);
            Matcher matcher = pattern.matcher(email);
            return matcher.find();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isMobile(String mobile) {
        try {
            Pattern pattern = Pattern.compile(REGEX_MOBILE);
            Matcher matcher = pattern.matcher(mobile);
            return matcher.find();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isLangKo(String str) {
        try {
            Pattern pattern = Pattern.compile(REGEX_KO);
            Matcher matcher = pattern.matcher(str);
            return matcher.find();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Camel 문자열을 DB 컬럼 문자열로 변경
     *      - 검색 파라미터를 변환 할때 사용
     *          ?s=userId:asc,name:desc
     * @param camelString
     * @return
     */
    public static String camelToUnderscore(String camelString) {
//        return camelString.replaceAll("([a-z])([A-Z]+)", "$1_$2") .toLowerCase();
        return PATTERN_CAMEL.matcher(camelString).replaceAll("$1_$2").toLowerCase();
    }

    public static boolean validPassword(String password) {
        try {
            Pattern pattern = Pattern.compile(REGEX_PASSWORD);
            Matcher matcher = pattern.matcher(password);
            return matcher.find();
        } catch (Exception e) {
            return false;
        }
    }

}
