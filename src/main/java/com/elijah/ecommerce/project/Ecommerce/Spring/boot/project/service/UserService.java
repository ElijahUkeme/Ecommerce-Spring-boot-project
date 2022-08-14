package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.service;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.SignInDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.SignUpDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.UserDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.email.EmailClientService;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataAlreadyExistException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataNotFoundException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.AuthenticationTokens;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.User;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.repository.UserRepository;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.response.ApiResponse;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.response.SignInResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EmailClientService emailClientService;

    @Autowired
    private AuthenticationTokenService authenticationTokenService;

    @Transactional
    public ResponseEntity<ApiResponse> signUp(SignUpDto signUpDto) throws DataAlreadyExistException, DataNotFoundException {

        //first check if the email address has already been taken by another user
        //if true, returns an error message to choose another email else go ahead and signup the user
        //hash the password so that it can't be seen
        //create a token for each user that signup

        if (Objects.nonNull(userRepository.findByEmail(signUpDto.getEmail()))){
            throw new DataAlreadyExistException("Email Address Already Taken");
        }
        String encryptedPassword = signUpDto.getPassword();
        try {
            encryptedPassword = hashPassword(signUpDto.getPassword());
        }catch (Exception e){
            e.printStackTrace();
        }

        User user = new User();
        user.setEmail(signUpDto.getEmail());
        user.setPassword(encryptedPassword);
        user.setFirstName(signUpDto.getFirstName());
        user.setLastName(signUpDto.getLastName());
        user.setProfileImage(signUpDto.getProfileImage());
        userRepository.save(user);
        final AuthenticationTokens authenticationTokens = new AuthenticationTokens(user);
        authenticationTokenService.saveConfirmationToken(authenticationTokens);
        emailClientService.sendSimpleEmail(user.getEmail(),
                "Confirmation Email from the E-commerce App",
                "Dear "+user.getFirstName()+" "+user.getLastName()+"\nThis is to inform you that your registration" +
                        "to the E-commerce application was successful\nHere is your authentication token "+authenticationTokens+
                "\nPlease keep it private and save because you will need it in several occasion in the app\nThanks for your patronage" +
                        "\n\n\n\nFor More Enquiries,\nCall 08167988220\nElijah Ukeme\nThe Technical Officer");
        //ResponseDto responseDto = new ResponseDto("success","User Created Successfully");
        //return responseDto;
        return new ResponseEntity<>(new ApiResponse(true,"User Created Successfully"),HttpStatus.OK);
    }
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        byte[] digest = messageDigest.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toLowerCase().substring(0,12);
        return hash;

    }
    public SignInResponseDto signIn(SignInDto signInDto) throws DataNotFoundException, NoSuchAlgorithmException {
        //first of all find the user that want to login with email validation
        User user = userRepository.findByEmail(signInDto.getEmail());
        if (Objects.isNull(user)){
            throw new DataNotFoundException("Incorrect Email or Password");
        }
        //hash the user password
            if (!(user.getPassword().equals(hashPassword(signInDto.getPassword())))){
                throw new DataNotFoundException("Incorrect Email or Password");
            }
        AuthenticationTokens tokens =authenticationTokenService.getToken(user);
        if (Objects.isNull(tokens)){
            throw new DataNotFoundException("Token Not Found");
        }
        return new SignInResponseDto("Success",tokens.getToken());

    }
    public UserDto getUserInfo(SignInDto signInDto) throws DataNotFoundException, NoSuchAlgorithmException {
        User user = userRepository.findByEmail(signInDto.getEmail());
            if (Objects.isNull(user)){
                throw new DataNotFoundException("Incorrect Email Or Password");
            }
            if (!(user.getPassword().equals(hashPassword(signInDto.getPassword())))){
                throw new DataNotFoundException("Incorrect Email or Password");
            }
            AuthenticationTokens tokens = authenticationTokenService.getToken(user);
            if (Objects.isNull(tokens)){
                throw new DataNotFoundException("Token Not Available");
        }
            UserDto userDto = new UserDto();
            userDto.setEmail(user.getEmail());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setProfileImage(user.getProfileImage());
            userDto.setToken(tokens.getToken());
            return userDto;
    }
}
