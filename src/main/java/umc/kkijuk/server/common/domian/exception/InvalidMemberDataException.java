package umc.kkijuk.server.common.domian.exception;

public class InvalidMemberDataException extends RuntimeException{
    public InvalidMemberDataException() {super("MemberData cannot be null.");
    }
}
