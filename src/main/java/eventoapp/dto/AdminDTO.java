package eventoapp.dto;

import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
public class AdminDTO {
    
    private String name;
    private String emai;
    private String phoneNumber;

    public AdminDTO() {
    }
    public AdminDTO(String name, String emai, String phoneNumber) {
        this.name = name;
        this.emai = emai;
        this.phoneNumber = phoneNumber;
    }
}
