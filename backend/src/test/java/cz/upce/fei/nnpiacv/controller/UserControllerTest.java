package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.dto.UserRequestDto;
import cz.upce.fei.nnpiacv.service.UserService;
import cz.upce.fei.nnpiacv.service.exception.UserAlreadyExistsException;
import cz.upce.fei.nnpiacv.service.exception.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private final UserService userService = null;

    private final User user = new User(1L, "test@test.com", "test", Collections.emptyList());

    @BeforeEach
    void setUp() throws UserNotFoundException {
        Mockito.when(userService.findUser(user.getId())).thenReturn(user);
        Mockito.when(userService.findUser(0L)).thenThrow(new UserNotFoundException(0L));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void givenStatus_200() throws Exception {
        mvc.perform(get("/api/v1/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.password").value("test"));
    }

    @Test
    void givenStatus_404() throws Exception {
        mvc.perform(get("/api/v1/users/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidUser_whenCreateUser_thenStatus201() throws Exception, UserAlreadyExistsException {
        User createdUser = new User(2L, "newuser@test.com", "password123", Collections.emptyList());
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(createdUser);

        UserRequestDto userRequestDto = new UserRequestDto("newuser@test.com", "password123");
        mvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + userRequestDto.getEmail() + "\", \"password\": \"" + userRequestDto.getPassword() + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value(userRequestDto.getEmail()))
                .andExpect(jsonPath("$.password").value(userRequestDto.getPassword()));
    }

    @Test
    void givenExistingEmail_whenCreateUser_thenStatus409() throws Exception, UserAlreadyExistsException {
        UserRequestDto userRequestDto = new UserRequestDto("existinguser@test.com", "password123");
        Mockito.when(userService.createUser(Mockito.any(User.class)))
                .thenThrow(new UserAlreadyExistsException(userRequestDto));

        String userJson = """
                {
                    "email": "existinguser@test.com",
                    "password": "password123"
                }
                """;

        mvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isConflict());
    }
}