package hello.login.domain.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class Member {
    private Long id;
    @NotBlank
    private String loginId; //로그안 ID
    @NotEmpty
    private String name; //사용자 이름
    @NotEmpty
    private String password;
}
