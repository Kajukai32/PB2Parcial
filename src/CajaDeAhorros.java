public class CajaDeAhorros extends Cuenta {
    private Integer contadorExtracciones = 0;

    public CajaDeAhorros(String cbu, String dni, double saldo) {
        super(cbu, dni, saldo);
    }

}
