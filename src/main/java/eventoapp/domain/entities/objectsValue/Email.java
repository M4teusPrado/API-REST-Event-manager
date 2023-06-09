package eventoapp.domain.entities.objectsValue;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
public class Email {

    @Column(name = "email")
    private String email;

    public Email(){}


    public Email(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }

        this.email = email;
    }

    private boolean isValidEmail(String email) {
        // Implemente a lógica de validação do e-mail aqui
        // Pode ser usado uma expressão regular ou outra abordagem de validação

        // Exemplo simples de validação com uma expressão regular
        String emailRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        return Pattern.matches(emailRegex, email);
    }

    public String getEmail() {
        return email;
    }
}
