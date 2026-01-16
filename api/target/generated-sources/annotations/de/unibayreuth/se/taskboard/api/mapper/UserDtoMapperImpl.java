package de.unibayreuth.se.taskboard.api.mapper;

import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.business.domain.User;
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
public class UserDtoMapperImpl extends UserDtoMapper {

    @Override
    public UserDto fromBusiness(User source) {
        if ( source == null ) {
            return null;
        }

        UUID id = null;
        LocalDateTime createdAt = null;
        String name = null;

        id = source.getId();
        createdAt = mapTimestamp( source.getCreatedAt() );
        name = source.getName();

        UserDto userDto = new UserDto( id, createdAt, name );

        return userDto;
    }

    @Override
    public User toBusiness(UserDto source) {
        if ( source == null ) {
            return null;
        }

        User user = new User();

        user.setName( source.name() );

        user.setCreatedAt( mapTimestamp(source.createdAt()) );

        return user;
    }
}
