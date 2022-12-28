package com.sid.gl.manageemployee.controllers;

import com.sid.gl.manageemployee.dto.AuthRequest;
import com.sid.gl.manageemployee.dto.TokenResponse;
import com.sid.gl.manageemployee.exceptions.AuthenticationException;
import com.sid.gl.manageemployee.models.Token;
import com.sid.gl.manageemployee.security.UserPrincipal;
import com.sid.gl.manageemployee.service.impl.EmployeeImpl;
import com.sid.gl.manageemployee.service.impl.RoleImpl;
import com.sid.gl.manageemployee.tools.JwtTokenUtil;
import com.sid.gl.manageemployee.tools.response.BasicResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/public")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private EmployeeImpl userService;

    @Autowired
    private RoleImpl roleService;

    @SneakyThrows
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @Operation(summary = "Login to kumba", tags = {"Authentication"}, responses = {
            @ApiResponse(description = "Login", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthRequest.class))),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "1000: incorrect login or password."),
            @ApiResponse(responseCode = "500", description = "Internal error")})
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest authRequest) {

        try {

            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            UserPrincipal userPrincipal = (UserPrincipal) authenticate.getPrincipal();

            userPrincipal.setPermissions(userService.getAllPermissionsAsGrantedAuthorities(userPrincipal.getId()));

            Map<String, Object> claims = UserPrincipal.toClaims(userPrincipal);

            Token token = jwtTokenUtil.generateToken(claims, userPrincipal.getUsername());

            BasicResponse basicResponse = new BasicResponse("Successfully authenticated !!!");
            basicResponse.setData(new TokenResponse(token));

            return ResponseEntity.ok(basicResponse);

        } catch (BadCredentialsException ex) {
            throw new AuthenticationException("Bad credentials");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AuthenticationException("Error authentication");
        }
    }

}
