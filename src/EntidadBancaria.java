public interface EntidadBancaria {
    void depositar(String cbu, double monto);

    void retirar(String cbu, double monto) throws SaldoInsuficienteException, CuentaNoExisteException;

    void transferir(String cbuOrigen, String cbuDestino, double monto) throws CuentaNoExisteException, FondosInsuficientesException;

    void agregarCuenta(Cuenta unaCuenta) throws ClienteInexistenteException, CBUDuplicadoExeption, CuentaNoExisteException;

    void agregarCliente(Cliente unCliente) throws ClienteDuplicadoException;
}
