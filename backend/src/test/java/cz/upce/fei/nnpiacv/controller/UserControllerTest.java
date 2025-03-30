package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.NnpiacvApplication;
import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.dto.UserRequestDto;
import cz.upce.fei.nnpiacv.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = NnpiacvApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User("test@test.com", "test"));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
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
    void givenValidUser_whenCreateUser_thenStatus201() throws Exception {
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
    void givenExistingEmail_whenCreateUser_thenStatus409() throws Exception {
        String userJson = """
                {
                    "email": "test@test.com",
                    "password": "password123"
                }
                """;

        mvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isConflict());
    }

    @Test
    void givenInactiveUser_whenActivateUser_thenStatus204() throws Exception {
        user.setActive(false);
        userRepository.save(user);

        mvc.perform(post("/api/v1/users/" + user.getId() + "/activate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenActiveUser_whenActivateUser_thenStatus409() throws Exception {
        user.setActive(true);
        userRepository.save(user);

        mvc.perform(post("/api/v1/users/" + user.getId() + "/activate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void givenNonExistentUser_whenActivateUser_thenStatus404() throws Exception {
        mvc.perform(post("/api/v1/users/0/activate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenInactiveUser_whenDeactivateUser_thenStatus204() throws Exception {
        user.setActive(true);
        userRepository.save(user);

        mvc.perform(post("/api/v1/users/" + user.getId() + "/deactivate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenActiveUser_whenDeactivateUser_thenStatus409() throws Exception {
        user.setActive(false);
        userRepository.save(user);

        mvc.perform(post("/api/v1/users/" + user.getId() + "/deactivate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void givenNonExistentUser_whenDeactivateUser_thenStatus404() throws Exception {
        mvc.perform(post("/api/v1/users/0/deactivate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}