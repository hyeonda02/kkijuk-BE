package umc.kkijuk.server.member.domain.exception;

public class InvalidMemberDataException extends RuntimeException{
    public InvalidMemberDataException() {super("MemberData cannot be null.");
    }
}
