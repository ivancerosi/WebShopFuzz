package hr.algebra.api.webshop2024api.ApiDTO;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_connections")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOUserConnection {
    @NotEmpty(message = "Username is required")
    private String username;
    private LocalDateTime lastConnection;
    private String ipAddress;

    public DTOUserConnection(LocalDateTime lastConnection, String ipAddress) {
        this.lastConnection = lastConnection;
        this.ipAddress = ipAddress;
    }

    public DTOUserConnection(String username, LocalDateTime lastConnection, String ipAddress) {
        this.username = username;
        this.lastConnection = lastConnection;
        this.ipAddress = ipAddress;
    }
}
