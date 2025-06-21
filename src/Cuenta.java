import java.util.Objects;

public class Cuenta implements Comparable<Cuenta> {

    protected String cbu;
    protected String dni;
    protected Double saldo;
    protected Integer contExtracciones;

    public Cuenta(String cbu, String dni, Double saldo) {
        this.cbu = cbu;
        this.dni = dni;
        this.saldo = saldo;
        this.contExtracciones = 0;
    }

    public String getCbu() {
        return cbu;
    }

    public String getDni() {
        return dni;
    }

    public Double getSaldo() {
        return saldo;
    }

    public Integer getContExtracciones() {
        return this.contExtracciones;
    }

    public void setContExtracciones() {
        this.contExtracciones++;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "saldo=" + saldo +
                ", dni='" + dni + '\'' +
                ", cbu='" + cbu + '\'' +
                '}';
    }

    @Override
    public int compareTo(Cuenta cuenta) {
        return this.getCbu().compareToIgnoreCase(cuenta.getCbu());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(cbu, cuenta.cbu);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cbu);
    }
}
