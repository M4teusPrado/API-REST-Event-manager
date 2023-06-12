package eventoapp.domain.entities;

import java.io.Serializable;

import javax.persistence.*;

import eventoapp.domain.entities.objectsValue.Email;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

@Inheritance( strategy = InheritanceType.JOINED )
public class BaseUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY) 
    private Long id;
    private String name;

    @Embedded
    private Email email;

    public BaseUser() { }

    public BaseUser(Long id, String name, Email email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

}
