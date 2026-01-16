package de.unibayreuth.se.taskboard.data.mapper;

import de.unibayreuth.se.taskboard.business.domain.Task;
import de.unibayreuth.se.taskboard.data.persistence.TaskEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-16T23:03:16+0100",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.45.0.v20260101-2150, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class TaskEntityMapperImpl implements TaskEntityMapper {

    @Override
    public TaskEntity toEntity(Task source) {
        if ( source == null ) {
            return null;
        }

        TaskEntity taskEntity = new TaskEntity();

        taskEntity.setAssigneeId( source.getAssigneeId() );
        taskEntity.setCreatedAt( source.getCreatedAt() );
        taskEntity.setDescription( source.getDescription() );
        taskEntity.setId( source.getId() );
        taskEntity.setStatus( source.getStatus() );
        taskEntity.setTitle( source.getTitle() );
        taskEntity.setUpdatedAt( source.getUpdatedAt() );

        return taskEntity;
    }

    @Override
    public Task fromEntity(TaskEntity source) {
        if ( source == null ) {
            return null;
        }

        String title = null;
        String description = null;

        title = source.getTitle();
        description = source.getDescription();

        Task task = new Task( title, description );

        task.setId( source.getId() );
        task.setCreatedAt( source.getCreatedAt() );
        task.setUpdatedAt( source.getUpdatedAt() );
        task.setStatus( source.getStatus() );
        task.setAssigneeId( source.getAssigneeId() );

        return task;
    }
}
