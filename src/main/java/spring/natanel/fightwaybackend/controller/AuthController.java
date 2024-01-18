package spring.natanel.fightwaybackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.natanel.fightwaybackend.dto.auth.SignInRequestDto;
import spring.natanel.fightwaybackend.dto.auth.SignInResponseDto;
import spring.natanel.fightwaybackend.dto.auth.SignUpRequestDto;
import spring.natanel.fightwaybackend.dto.auth.CustomerResponseDto;
import spring.natanel.fightwaybackend.entity.Role;
import spring.natanel.fightwaybackend.repository.CustomerRepository;
import spring.natanel.fightwaybackend.security.JWTProvider;
import spring.natanel.fightwaybackend.service.auth.CustomerDetailServiceImpl;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final CustomerRepository customerRepository;

    private final CustomerDetailServiceImpl authService;

    private final JWTProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<CustomerResponseDto> signUp(@RequestBody @Valid SignUpRequestDto dto){
        val response = authService.signUp(dto);

        var customerRoles = customerRepository.findCustomerByUsernameIgnoreCase(dto.getUsername()).orElseThrow().getRoles();

        // Convert the Set<Role> to a List<String>
        var rolesList = customerRoles.stream()
                .map(Role::getName)  // assuming Role class has a getName method
                .toList();

        // Generate JWT after successful registration
        String token = jwtProvider.generateToken(dto.getUsername(), rolesList);

        response.setJwt(token);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto dto){
        var user = authService.loadUserByUsername(dto.getUsername());
        var customerRoles = customerRepository.findCustomerByUsernameIgnoreCase(dto.getUsername()).orElseThrow().getRoles();

        var rolesList = customerRoles.stream()
                .map(Role::getName)
                .toList();

        var savedPassword = user.getPassword();
        var givenPassword = dto.getPassword();

        //generate a new JWT if successful login
        if (passwordEncoder.matches(givenPassword,savedPassword)){

            var token = jwtProvider.generateToken(user.getUsername(), rolesList);
            return ResponseEntity.ok(new SignInResponseDto(token));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
}
