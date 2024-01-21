package spring.natanel.fightwaybackend.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.natanel.fightwaybackend.dto.auth.SignUpRequestDto;
import spring.natanel.fightwaybackend.dto.auth.CustomerResponseDto;
import spring.natanel.fightwaybackend.entity.Customer;
import spring.natanel.fightwaybackend.entity.Role;
import spring.natanel.fightwaybackend.error.BadRequestException;
import spring.natanel.fightwaybackend.error.StoreException;
import spring.natanel.fightwaybackend.repository.RoleRepository;
import spring.natanel.fightwaybackend.repository.CustomerRepository;


import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerDetailServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;


    private  final PasswordEncoder passwordEncoder;
    @Transactional
    public CustomerResponseDto signUp(SignUpRequestDto dto){
        val userRole = roleRepository.findByNameIgnoreCase("ROLE_USER")
                .orElseThrow(() -> new StoreException("Please Contact Admin"));

        return signUpUser(dto, userRole);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //fetch our user entity from our database
        var user = customerRepository.findCustomerByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //map our roles to Springs SimpleGrantedAuthority
        var roles = user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList();

        //User(Spring) implements UserDetail
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),roles);
    }

    @Transactional
    public void signAdminUp(SignUpRequestDto dto){
        val userRole = roleRepository.findByNameIgnoreCase("ROLE_ADMIN")
                .orElseThrow(() -> new StoreException("Please Contact Admin"));

        signUpUser(dto, userRole);
    }

    private CustomerResponseDto signUpUser(SignUpRequestDto dto, Role userRole) {
        val byUser = customerRepository.findCustomerByUsernameIgnoreCase(dto.getUsername().trim());
        val byEmail = customerRepository.findCustomerByEmailIgnoreCase(dto.getEmail().trim());

        if (byEmail.isPresent()){
            throw new BadRequestException("email", "Email or username already exists");
        } else if (byUser.isPresent()) {
            throw new BadRequestException("username","Username already exists");
        }

        //3) val user = new User(... encoded-password)
        var user = new Customer(
                null,
                dto.getUsername(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword().trim()),
                Set.of(userRole),
                List.of()
        );

        //save
        var savedUser = customerRepository.save(user);

        //response dto
        return modelMapper.map(savedUser, CustomerResponseDto.class);
    }
}
