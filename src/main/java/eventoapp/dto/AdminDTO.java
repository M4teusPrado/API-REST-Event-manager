package eventoapp.dto;

import eventoapp.models.Admin;
import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
public class AdminDTO {
    
    private String name;
    private String email;
    private String phoneNumber;

    public AdminDTO(Admin admin) {
        this.name = admin.getName();
        this.email = admin.getEmail();
        this.phoneNumber = admin.getPhoneNumber();
    }
}