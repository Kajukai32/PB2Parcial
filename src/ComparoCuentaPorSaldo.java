import java.util.Comparator;

public class ComparoCuentaPorSaldo implements Comparator<Cuenta> {
    @Override
    public int compare(Cuenta o1, Cuenta o2) {
        return o1.getSaldo().compareTo(o2.getSaldo());
    }

    @Override
    public Comparator<Cuenta> reversed() {
        return Comparator.super.reversed();
    }
}
