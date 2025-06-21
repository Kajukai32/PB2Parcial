public class CuentaCorriente extends Cuenta {
    private Double limDesubierto;

    public CuentaCorriente(String cbu, String dni, double saldo) {
        super(cbu, dni, saldo);
        this.limDesubierto = -5000.0;
    }

    public Double getLimDesubierto() {
        return limDesubierto;
    }
}
