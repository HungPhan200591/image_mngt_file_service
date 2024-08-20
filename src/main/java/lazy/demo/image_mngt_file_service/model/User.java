package lazy.demo.image_mngt_file_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    private Long userId;

    private String username;
    private String email;

    @JsonIgnore
    private String password;

    private boolean isAdmin;

    private String fullName;
    private LocalDate dateOfBirth;
    private String address;
    private String picture;

}
