package eventoapp.models.enums;

public enum TicketType {
    PAGO(1L, "Pago"),
    GRATUITO(2L, "Gratuito");

    private final long id;
    private final String descricao;

    TicketType(long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static TicketType getTicketTypeById(long id) {
        for (TicketType ticketType : TicketType.values()) {
            if (ticketType.id == id) {
                return ticketType;
            }
        }
        throw new IllegalArgumentException("Invalid TicketType ID: " + id);
    }
}