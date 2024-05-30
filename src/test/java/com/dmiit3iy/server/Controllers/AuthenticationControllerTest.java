package com.dmiit3iy.server.Controllers;

import com.dmiit3iy.server.models.User;
import com.dmiit3iy.server.security.jwt.JwtTokenProvider;
import com.dmiit3iy.server.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("testuser");
    }

    @Test
    void loginWithValidCredentials() throws Exception {
        when(userService.findByLogin("testuser")).thenReturn(user);
        when(jwtTokenProvider.createToken("testuser", user.getClass().getSimpleName())).thenReturn("token");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                        .param("userName", "testuser")
                        .param("password", "password")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("token"));

        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken("testuser", "password"));
        verify(userService).findByLogin("testuser");
        verify(jwtTokenProvider).createToken("testuser", user.getClass().getSimpleName());
    }

    @Test
    void loginWithInvalidCredentials() throws Exception {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Неправильное имя пользователя или пароль"));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                        .param("userName", "invaliduser")
                        .param("password", "invalidpassword")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken("invaliduser", "invalidpassword"));
        verify(userService, never()).findByLogin(anyString());
        verify(jwtTokenProvider, never()).createToken(anyString(), anyString());
    }
}