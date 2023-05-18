package eventoapp.models.objectsValue;

import java.util.regex.Pattern;

public class Email {
    private final String value;

    public Email(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }

        this.value = email;
    }

    private boolean isValidEmail(String email) {
        // Implemente a lógica de validação do e-mail aqui
        // Pode ser usado uma expressão regular ou outra abordagem de validação

        // Exemplo simples de validação com uma expressão regular
        String emailRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        return Pattern.matches(emailRegex, email);
    }

    public String getValue() {
        return value;
    }
}
