package de.unibayreuth.se.taskboard;

import de.unibayreuth.se.taskboard.api.dtos.TaskDto;
import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.api.mapper.TaskDtoMapper;
import de.unibayreuth.se.taskboard.api.mapper.UserDtoMapper;
import de.unibayreuth.se.taskboard.business.domain.Task;
import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.business.ports.UserService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

public class TaskBoardSystemTests extends AbstractSystemTest {

    @Autowired
    private TaskDtoMapper taskDtoMapper;

    @Autowired
    private UserDtoMapper userDtoMapper;

    @Autowired
    private UserService userService;

    @Test
    void getAllCreatedTasks() {
        List<Task> createdTasks = TestFixtures.createTasks(taskService);

        List<Task> retrievedTasks = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/tasks")
                .then()
                .statusCode(200)
                .body(".", hasSize(createdTasks.size()))
                .and()
                .extract().jsonPath().getList("$", TaskDto.class)
                .stream()
                .map(taskDtoMapper::toBusiness)
                .toList();

        assertThat(retrievedTasks)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("createdAt", "updatedAt")
                .containsExactlyInAnyOrderElementsOf(createdTasks);
    }

    @Test
    void createAndDeleteTask() {
        Task createdTask = taskService.create(
                TestFixtures.getTasks().getFirst()
        );

        when()
                .get("/api/tasks/{id}", createdTask.getId())
                .then()
                .statusCode(200);

        when()
                .delete("/api/tasks/{id}", createdTask.getId())
                .then()
                .statusCode(200);

        when()
                .get("/api/tasks/{id}", createdTask.getId())
                .then()
                .statusCode(400);
    }

    /* =========================
       Users system tests
       ========================= */

    @Test
    void getAllCreatedUsers() {
        List<User> createdUsers = TestFixtures.createUsers(userService);

        List<User> retrievedUsers = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .body(".", hasSize(createdUsers.size()))
                .and()
                .extract().jsonPath().getList("$", UserDto.class)
                .stream()
                .map(userDtoMapper::toBusiness)
                .toList();

        assertThat(retrievedUsers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("createdAt")
                .containsExactlyInAnyOrderElementsOf(createdUsers);
    }

    @Test
    void getUserById() {
        User created = userService.create(
                TestFixtures.getUsers().getFirst()
        );

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/users/{id}", created.getId())
                .then()
                .statusCode(200)
                .body("id", equalTo(created.getId().toString()))
                .body("name", equalTo(created.getName()))
                .body("createdAt", notNullValue());
    }

    @Test
    void createUserViaApi() {
        UserDto newUser = new UserDto(null, null, "Dora");

        String createdId = given()
                .contentType(ContentType.JSON)
                .body(newUser)
                .when()
                .post("/api/users")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo("Dora"))
                .extract()
                .jsonPath()
                .getString("id");

        when()
                .get("/api/users/{id}", createdId)
                .then()
                .statusCode(200)
                .body("id", equalTo(createdId))
                .body("name", equalTo("Dora"));
    }
}
