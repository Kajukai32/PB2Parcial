import java.util.*;

public class Banco implements EntidadBancaria {
    private final Map<String, Cliente> listaClientes = new TreeMap<>();
    private final Set<Cuenta> listaCuentas = new TreeSet<>();


    @Override
    public void depositar(String cbu, double monto) {

    }

    @Override
    public void retirar(String cbu, double monto) throws SaldoInsuficienteException, CuentaNoExisteException {
        if (monto > 0) {
            if (monto < existeCuenta(cbu).getSaldo() || existeCuenta(cbu) instanceof CuentaCorriente) {
                if (existeCuenta(cbu) instanceof CuentaSueldo) {
                    existeCuenta(cbu).setSaldo(existeCuenta(cbu).getSaldo() - monto);

                } else if (existeCuenta(cbu) instanceof CajaDeAhorros) {

                    if (existeCuenta(cbu).getContExtracciones() < 5) {
                        existeCuenta(cbu).setContExtracciones();
                        existeCuenta(cbu).setSaldo(existeCuenta(cbu).getSaldo() - monto);
                    } else {
                        existeCuenta(cbu).setContExtracciones();
                        existeCuenta(cbu).setSaldo(existeCuenta(cbu).getSaldo() - (monto + 100));
                    }
                } else if (existeCuenta(cbu) instanceof CuentaCorriente) {

                    Double limDes = ((CuentaCorriente) existeCuenta(cbu)).getLimDesubierto();
                    Double saldoInicial = existeCuenta(cbu).getSaldo();
                    if (existeCuenta(cbu).getSaldo() - monto > limDes) {
                        existeCuenta(cbu).setSaldo(saldoInicial - monto);
                        Double saldoFinal = existeCuenta(cbu).getSaldo();
                        if (saldoFinal < 0.0) {
                            if (saldoInicial <= 0) {
                                existeCuenta(cbu).setSaldo(saldoFinal - (monto * 0.05));
                            } else if (saldoInicial > 0) {
                                existeCuenta(cbu).setSaldo(saldoFinal - (-saldoFinal * 0.05));
                            }
                        }

                    }
                }
            } else throw new SaldoInsuficienteException("saldo insuficiente");
        }
    }

    public Double checkSaldo(String cbu) throws CuentaNoExisteException {
        return existeCuenta(cbu).getSaldo();
    }

    @Override
    public void transferir(String cbuOrigen, String cbuDestino, double monto) throws CuentaNoExisteException {

    }

    @Override
    public void agregarCuenta(Cuenta unaCuenta) throws ClienteInexistenteException, CBUDuplicadoExeption, CuentaNoExisteException {
        if (!listaCuentas.contains(unaCuenta)) {
            if (existeCliente(unaCuenta.getDni())) {
                listaCuentas.add(unaCuenta);
            } else throw new ClienteInexistenteException("Cliente inexistente");
        } else throw new CBUDuplicadoExeption("cbu Duplicado");
    }

    public void agregarCliente(Cliente cliente1) throws ClienteDuplicadoException {

        if (!listaClientes.containsKey(cliente1.getDni())) {
            listaClientes.put(cliente1.getDni(), cliente1);
        } else {
            throw new ClienteDuplicadoException("Cliente duplicado");
        }
    }

    private Boolean existeCliente(String c) {
        return listaClientes.containsKey(c);
    }

    //esto lo habia puesto privado para que lo use internamente el banco pero un test me lo pide publico
    public Cuenta existeCuenta(String cbu) throws CuentaNoExisteException {

        for (Cuenta c : listaCuentas) {
            if (cbu.equals(c.getCbu())) {
                return c;
            }
        }
        throw new CuentaNoExisteException("Cuenta no existe");
    }


    public int getCantidadClientes() {
        return this.listaClientes.size();
    }

    public int getCantidadCuentas() {
        return this.listaCuentas.size();
    }

    public String getListaClientes() {
        return listaClientes.toString();
    }

    public String ordenarCuentasCustom(Comparator<Cuenta> comparador) {
        Set<Cuenta> listaOrdenada = new TreeSet<>(comparador);
        listaOrdenada.addAll(listaCuentas);

        return listaOrdenada.toString();
    }

    public String ordenarCuentasCorrienteSaldoDeudor(Comparator<Cuenta> comparador) {

        Set<Cuenta> listaOrdenada = new TreeSet<>(comparador);

        for (Cuenta c : listaCuentas) {
            if (c instanceof CuentaCorriente) {
                listaOrdenada.add(c);
            }
        }
        return listaOrdenada.toString();
    }

    public String getCuentasCorrientesOrdenadasPorDeudor(Comparator<Cuenta> comparador) {

        Set<Cuenta> listaOrdenada = new TreeSet<>(comparador);

        for (Cuenta c : listaCuentas) {
            if (c instanceof CuentaCorriente && c.getSaldo() < 0.0) {
                listaOrdenada.add(c);
            }
        }
        return listaOrdenada.toString();
    }
}