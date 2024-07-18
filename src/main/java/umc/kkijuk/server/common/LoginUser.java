package umc.kkijuk.server.common;

public class LoginUser {
    private static final LoginUser LOGIN_USER = new LoginUser(0L);

    public LoginUser(Long id) {
    }

    public static LoginUser get() {
        return LOGIN_USER;
    }
}
