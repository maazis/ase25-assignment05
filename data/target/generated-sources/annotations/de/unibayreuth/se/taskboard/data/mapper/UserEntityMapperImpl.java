package de.unibayreuth.se.taskboard.data.mapper;

import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.data.persistence.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-16T23:41:31+0100",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.45.0.v20260101-2150, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class UserEntityMapperImpl implements UserEntityMapper {

    @Override
    public UserEntity toEntity(User source) {
        if ( source == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( source.getId() );
        userEntity.setCreatedAt( source.getCreatedAt() );
        userEntity.setName( source.getName() );

        return userEntity;
    }

    @Override
    public User fromEntity(UserEntity source) {
        if ( source == null ) {
            return null;
        }

        User user = new User();

        user.setId( source.getId() );
        user.setCreatedAt( source.getCreatedAt() );
        user.setName( source.getName() );

        return user;
    }
}
