package de.unibayreuth.se.taskboard.business.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

/**
 * Domain class that represents a user.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User implements Serializable {

    @Nullable
    private UUID id; // null when user is not persisted yet

    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("UTC")); // set on user creation

    @NonNull
    private String name;
}
