package spring.natanel.fightwaybackend.dto.auth;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.natanel.fightwaybackend.validator.UniqueEmail;
import spring.natanel.fightwaybackend.validator.UniqueUsername;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 20, message = "Username must contain at least 2 letters")
    @UniqueUsername
    private String username;

    @NotNull
    @Email
    @NotEmpty
    @UniqueEmail
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 20)
    @NotNull(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{6,20}$", message = "Password must contain at least 6 characters, one uppercase and digits")
    private String password;
}
