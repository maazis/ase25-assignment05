package de.unibayreuth.se.taskboard;

import de.unibayreuth.se.taskboard.business.domain.Task;
import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.business.ports.TaskService;
import de.unibayreuth.se.taskboard.business.ports.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Load initial data into the list via the list service from the business layer.
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Profile("dev")
class LoadInitialData implements InitializingBean {

    private final TaskService taskService;

    /**
     * UserService may not exist yet while TODOs are still being resolved.
     * Using ObjectProvider prevents the dev profile from failing to start
     * when the user business implementation is not present.
     */
    private final ObjectProvider<UserService> userServiceProvider;

    @Override
    public void afterPropertiesSet() {
        log.info("Deleting existing data...");

        UserService userService = userServiceProvider.getIfAvailable();
        if (userService != null) {
            userService.clear();
        }

        taskService.clear();

        log.info("Loading initial data...");

        List<User> users = Collections.emptyList();
        if (userService != null) {
            try {
                users = TestFixtures.createUsers(userService);
            } catch (Exception e) {
                log.warn("Could not create initial users (User fixtures/service not ready yet). Continuing without users.", e);
            }
        }

        List<Task> tasks = TestFixtures.createTasks(taskService);

        if (!tasks.isEmpty()) {
            Task task1 = tasks.getFirst();
            if (!users.isEmpty()) {
                task1.setAssigneeId(users.getFirst().getId());
            }
            taskService.upsert(task1);

            if (tasks.size() > 1) {
                Task task2 = tasks.getLast();
                if (users.size() > 1) {
                    task2.setAssigneeId(users.getLast().getId());
                }
                taskService.upsert(task2);
            }
        }
    }
}
