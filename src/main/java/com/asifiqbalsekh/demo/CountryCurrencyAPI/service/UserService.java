package com.asifiqbalsekh.demo.CountryCurrencyAPI.service;

import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.*;
import com.asifiqbalsekh.demo.CountryCurrencyAPI.model.User;
import com.asifiqbalsekh.demo.CountryCurrencyAPI.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService  {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;


    public UserResponse addUserDetails(UserRequest givenUser) {

        if (userRepository.existsById(givenUser.email())){
            throw new RuntimeException("User Already Exist, Try To Login!");
        }

        User user = User.builder()
                .email(givenUser.email())
                .password(passwordEncoder.encode(givenUser.password()))
                .roles(Set.of(Roles.USER))
                .fullName(givenUser.fullName())
                .enabled(true)
                .build();

        var new_user=userRepository.save(user);
        return new UserResponse("User Created Successfully",new_user.getEmail(),new_user.getFullName(),
        new_user.getRoles());
    }


    public UserResponse updateRoles(@Valid RolesRequest data) {

        var givenUser=userRepository.findById(data.email()).orElseThrow(() -> new UsernameNotFoundException(data.email()+" does not Exist"));
       if(data.req_roles().contains(777)){
           givenUser.getRoles().add(Roles.ADMIN);
           givenUser.getRoles().add(Roles.MANAGER);
           givenUser.getRoles().add(Roles.EMPLOYEE);
       }
       else{
            data.req_roles().forEach(item -> {

                switch (item){
                    case 666:
                        givenUser.getRoles().add(Roles.MANAGER);
                        break;
                    case 888:
                        givenUser.getRoles().add(Roles.EMPLOYEE);
                        break;
                }

            });
        }
        var new_user=userRepository.save(givenUser);
        return new UserResponse("UserRoles Updated Successfully",new_user.getEmail(),new_user.getFullName(),
                new_user.getRoles());

    }

    public List<AllUserResponse> getAllRegisteredUser() {
        var users=userRepository.findAll();
        return users.stream()
                .map(item-> new AllUserResponse(item.getFullName(),item.getEmail(),item.isEnabled(),item.getRoles()))
                .collect(Collectors.toList());
    }

    public UserResponse deactivateUser(String email) {
        var givenUser=userRepository.findById(email).orElseThrow(() -> new UsernameNotFoundException(email+" does not Exist"));
        givenUser.setEnabled(false);
        var new_user=userRepository.save(givenUser);
        return new UserResponse("User Deactivated Successfully",new_user.getFullName(),new_user.getEmail(),new_user.getRoles());
    }

    public UserResponse deleteUser(String email) {
        var new_user=userRepository.findById(email).orElseThrow(() -> new UsernameNotFoundException(email+" does not Exist"));
        userRepository.deleteById(email);
        return new UserResponse("User Deleted Successfully",new_user.getFullName(),new_user.getEmail(),new_user.getRoles());
    }

    public LoginResponse verify(LoginRequest data) {
        var authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(data.username(), data.password()));
        User loggedUser =(User) authentication.getPrincipal();
        return jwtService.generateToken(loggedUser);
    }
}
