package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.api.control;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.api.apiservice.UserAccessService;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.api.domain.Role;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.api.domain.UsersAccess;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataNotFoundException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.response.ApiResponse;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.utility.TokenUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserAccessController {

    private final UserAccessService userAccessService;

    @GetMapping("/users")
    public ResponseEntity<List<UsersAccess>> getAllUsers(){
        return new ResponseEntity<>(userAccessService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/user/save")
    public ResponseEntity<ApiResponse> saveUser(@RequestBody UsersAccess usersAccess){
        userAccessService.saveUser(usersAccess);
        return new ResponseEntity<>(new ApiResponse(true,"User's Information saved Successfully"),HttpStatus.CREATED);
    }
    @PostMapping("/role/save")
    public ResponseEntity<ApiResponse> saveRole(@RequestBody Role role){
        userAccessService.saveRole(role);
        return new ResponseEntity<>(new ApiResponse(true,"Role saved Successfully"),HttpStatus.CREATED);
    }

    @PostMapping("/user/addRole")
    public ResponseEntity<ApiResponse> addRoleToUser(@RequestBody RoleToUserForm form) throws DataNotFoundException {
        userAccessService.addRoleToUser(form.getUsername(),form.getRoleName());
        return new ResponseEntity<>(new ApiResponse(true,"Role added to the User Successfully"),HttpStatus.CREATED);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws DataNotFoundException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader !=null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(TokenUtility.ALGORITHM_SECRET.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                UsersAccess usersAccess = userAccessService.getUser(username);

                String access_token = JWT.create()
                        .withSubject(usersAccess.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+10 *60 *1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",usersAccess.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String,String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);
            }catch (Exception exception){

                log.error("Error Occurred {}",exception.getMessage());
                response.setHeader("error",exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message",exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }
        }else {
          throw   new DataNotFoundException("Refresh Token is Missing");
        }
    }

    @GetMapping("/get/one/{username}")
    public UsersAccess getUserByName(@PathVariable("username") String username) throws DataNotFoundException {
        return userAccessService.getUser(username);
    }
}

@Data
class RoleToUserForm{
    private String username;
    private String roleName;
}
