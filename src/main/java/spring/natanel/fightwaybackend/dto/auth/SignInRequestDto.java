package spring.natanel.fightwaybackend.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequestDto {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 20, message = "Username must contain at least 2 letters")
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 20)
    @NotNull(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{6,20}$", message = "Password must contain at least 6 characters, one uppercase and digits")
    private String password;
}
