package com.example.rqs.core.common.exception;

public class RQSError {
    private RQSError() {}
    public static String DUPLICATE_EMAIL = "이미 가입되어있는 이메일입니다.";
    public static String INVALID_EMAIL_OR_PW = "아이디 혹은 비밀번호를 확인하세요.";

    public static String SPACE_IS_NOT_EXIST = "스페이스가 존재하지 않습니다.";
    public static String SPACE_IS_EMPTY = "스페이스가 비어있습니다.";
    public static String SPACE_MEMBER_NOT_FOUND = "해당 멤버가 스페이스에 존재하지 않습니다.";

    public static String ITEM_IS_NOT_EXIST_IN_SPACE = "스페이스에 해당 아이템이 존재하지 않습니다.";
}
