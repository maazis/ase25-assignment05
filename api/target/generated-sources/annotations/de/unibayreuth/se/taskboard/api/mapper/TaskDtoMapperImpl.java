package de.unibayreuth.se.taskboard.api.mapper;

import de.unibayreuth.se.taskboard.api.dtos.TaskDto;
import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.business.domain.Task;
import de.unibayreuth.se.taskboard.business.domain.TaskStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-16T23:41:31+0100",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.45.0.v20260101-2150, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class TaskDtoMapperImpl extends TaskDtoMapper {

    @Override
    public TaskDto fromBusiness(Task source) {
        if ( source == null ) {
            return null;
        }

        UUID id = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;
        String title = null;
        String description = null;
        TaskStatus status = null;

        id = source.getId();
        createdAt = mapTimestamp( source.getCreatedAt() );
        updatedAt = mapTimestamp( source.getUpdatedAt() );
        title = source.getTitle();
        description = source.getDescription();
        status = source.getStatus();

        UserDto assignee = getUserById(source.getAssigneeId());

        TaskDto taskDto = new TaskDto( id, createdAt, updatedAt, title, description, status, assignee );

        return taskDto;
    }

    @Override
    public Task toBusiness(TaskDto source) {
        if ( source == null ) {
            return null;
        }

        String title = null;
        String description = null;

        title = source.getTitle();
        description = source.getDescription();

        Task task = new Task( title, description );

        if ( source.getStatus() != null ) {
            task.setStatus( source.getStatus() );
        }
        else {
            task.setStatus( TaskStatus.TODO );
        }
        task.setId( source.getId() );

        task.setAssigneeId( mapAssigneeId(source.getAssignee()) );
        task.setCreatedAt( mapTimestamp(source.getCreatedAt()) );
        task.setUpdatedAt( mapTimestamp(source.getUpdatedAt()) );

        return task;
    }
}
