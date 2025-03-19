package org.example.quizbox.common;

public enum ExceptionConstants {

    // SE (Server Error)
    SE1("SE1", "서버 에러", "SERVER"),

    // CM (Common Error)
    CM1("CM1", "요청 인자 확인 필요", "BUSINESS"),

    // QP (Quiz Pack)
    QP1("QP1", "존재하지 않는 퀴즈팩", "BUSINESS"),
    QP2("QP2", "퀴즈 생성에 실패", "SERVER"),
    QP3("QP3", "동일한 팩 내에서 퀴즈 이름 중복", "BUSINESS"),
    QP4("QP4", "퀴즈팩에 존재하지 않는 멤버", "AUTHORIZATION"),
    QP5("QP5", "퀴즈 생성 권한 미보유", "AUTHORIZATION"),
    QP6("QP6", "존재하지 않는 퀴즈", "BUSINESS"),
    QP7("QP7", "유효하지 않은 초대장", "BUSINESS"),
    QP_MIN_QUIZ("QP_MIN_QUIZ", "퀴즈팩의 퀴즈는 최소 1개 이상", "BUSINESS"),

    // QA (Quiz Option)
    QA1("QA1", "퀴즈 생성 시, 보기는 최소 2개 이상", "BUSINESS"),
    QA2("QA2", "퀴즈 생성 시, 정답은 최소 1개 이상", "BUSINESS"),

    // QG(Quiz Game)
    QG1("QG1", "퀴즈게임 시작 시, 퀴즈팩은 필수", "BUSINESS"),
    QG2("QG2", "현재 퀴즈 답변 후 퀴즈 뽑기 가능", "BUSINESS"),
    QG3("QG3", "퀴즈 뽑기 후 답변 가능", "BUSINESS"),
    QG4("QG4", "퀴즈의 보기가 아닌 값은 답으로 제출 불가", "BUSINESS"),
    QG5("QG5", "동일 퀴즈에 대하여 답 중복 제출 불가", "BUSINESS"),
    QG6("QG6", "답변은 최소 1개 이상 필수", "BUSINESS"),
    QG7("QG7", "존재하지 않는 퀴즈게임", "BUSINESS"),
    QG8("QG8", "QuizGame ID 형식 확인 필요", "BUSINESS"),
    QG9("QG9", "참가하지 않은 멤버", "AUTHORIZATION"),
    QG10("QG10", "지원하지 않은 뽑기 방식", "BUSINESS"),
    QG11("QG11", "더 이상 뽑을 퀴즈가 없음", "BUSINESS"),

    // TG(Keyword)
    TG1("TG1", "존재하지 않는 태그", "BUSINESS");

    private final String code;

    private final String detail;

    private final String type;

    ExceptionConstants(String code, String detail, String type) {
        this.code = code;
        this.detail = detail;
        this.type = type;
    }

    public String code() {
        return code;
    }

    public String detail() {
        return detail;
    }

    public String type() {
        return type;
    }
}
