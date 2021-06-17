package eventoapp.dto;

import java.util.List;

import eventoapp.models.Admin;
import eventoapp.models.Event;
import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
public class AdminGetDTO {

    private Long id;
    private String          name;
    private String          email;
    private String          phoneNumber;
    private List<Event>     events;

    public AdminGetDTO() {}

    public AdminGetDTO(Admin admin) {
        this.id             = admin.getId();
        this.name           = admin.getName();
        this.email          = admin.getEmail();
        this.phoneNumber    = admin.getPhoneNumber();
        this.events         = admin.getEvents();
    }
}