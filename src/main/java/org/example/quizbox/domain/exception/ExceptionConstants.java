package org.example.quizbox.domain.exception;

public enum ExceptionConstants {
    GM1("GM1", "최소 1개 이상의 퀴즈 필수"),
    GM2("GM2", "이미 답변한 퀴즈입니다."),
    GM3("GM3", "현재 퀴즈가 아닌 경우 답변할 수 없습니다."),
    GM4("GM4", "퀴즈가 아직 진행중입니다."),
    GM5("GM5", "게임 정보가 존재하지 않습니다."),
    GM6("GM6", "게임 참여자가 아닙니다."),
    GM7("GM7", "플레이어 정보를 확인하세요.");

    private final String code;

    private final String detail;

    ExceptionConstants(String code, String detail) {
        this.code = code;
        this.detail = detail;
    }

    public String code() {
        return code;
    }

    public String detail() {
        return detail;
    }
}
