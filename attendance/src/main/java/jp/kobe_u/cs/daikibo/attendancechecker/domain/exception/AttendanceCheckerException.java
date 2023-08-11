package jp.kobe_u.cs.daikibo.attendancechecker.domain.exception;

/**
 * 予約アプリのビジネス例外
 */
public class AttendanceCheckerException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    /* ユーザ系エラーコード */
    public static final int USER_NOT_FOUND = 100;
    public static final int INVALID_USER_ROLE = 101;
    public static final int USER_ALREADY_EXISTS = 102;
    public static final int INVALID_USER_UPDATE = 103;
    public static final int INVALID_USER_STATUS = 104;
    /* 出退勤系エラーコード */
    public static final int ATTENDANCE_NOT_FOUND = 200;
    public static final int INVALID_ATTENDANCE_FORM = 201;
    public static final int ATTENDANCE_ALREADY_EXISTS = 202;
    public static final int INVALID_ATTENDANCE_UPDATE = 203;
    /* お気に入り系エラーコード */
    public static final int FAVORITE_NOT_FOUND = 300;
    public static final int INVALID_FAVORITE_FORM = 301;
<<<<<<< HEAD:attendance/src/main/java/jp/kobe_u/cs/daikibo/attendancechecker/domain/exception/AttendanceCheckerException.java
    public static final int FAVORITE_ALREADY_EXISTS = 302;
    public static final int INVALID_FAVORITE_STATUS = 303;
=======
    public static final int FAVORITE_ALREADY_EXIST = 302;
    public static final int RESERVATION_NOT_PERMITTED = 303;
    public static final int INVALID_FAVORITE_STATUS = 304;
>>>>>>> 5a16226b5a0a8d4430cdf56d540e62c392bcdd3f:demo/attendance/src/main/java/jp/kobe_u/cs/daikibo/attendancechecker/domain/exception/AttendanceCheckerException.java
    /* とにかくエラー */
    public static final int ERROR = 999;
    public static final int FAVORITE_ALREADY_EXISTS = 0;

    private int code; //この例外のエラーコード
    //コンストラクタ
    public AttendanceCheckerException(int code, String message) {
        super(message);
        this.code = code;
    }
    //例外をラップするコンストラクタ
    public AttendanceCheckerException(int code, String message, Exception cause) {
        super(message, cause);
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
