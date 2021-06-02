package eventoapp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import eventoapp.models.Admin;
import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
public class AdminDTO {
    

    @NotBlank(message = "{name.not.blank}")
    @Size(min = 3, max = 20)
    private String name;

    @NotBlank(message = "{email.not.blank}")
    @Email(message = "{email.not.valid}")
    private String email;

    @NotBlank(message = "{phoneNumber.not.blank}")
    private String phoneNumber;

    public AdminDTO(Admin admin) {
        this.name = admin.getName();
        this.email = admin.getEmail();
        this.phoneNumber = admin.getPhoneNumber();
    }

    public AdminDTO() {}
}