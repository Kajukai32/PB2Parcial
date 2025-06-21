import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

public class BancoTest {

    private Cliente cliente1;
    private Cliente cliente2;
    private Cliente cliente3;
    private Cliente cliente4;
    private Cliente cliente5;
    private Cliente cliente6;
    private Cliente cliente7;
    private CuentaSueldo cuentaSueldo2;
    private CuentaSueldo cuentaSueldo3;
    private CuentaSueldo cuentaSueldo4;
    private CuentaSueldo cuentaSueldo5;
    private CuentaSueldo cuentaSueldo6;
    private CajaDeAhorros cajaDeAhorros2;
    private CajaDeAhorros cajaDeAhorros3;
    private CajaDeAhorros cajaDeAhorros4;
    private CajaDeAhorros cajaDeAhorros5;
    private CajaDeAhorros cajaDeAhorros6;
    private CuentaCorriente cuentaCorriente2;
    private CuentaCorriente cuentaCorriente3;
    private CuentaCorriente cuentaCorriente4;
    private CuentaCorriente cuentaCorriente5;
    private CuentaCorriente cuentaCorriente6;
    private Cuenta cuentaSueldo;
    private Cuenta cajaDeAhorros;
    private Cuenta cuentaCorriente;
    private Banco banco;


    @Before
    public void setUp() throws ClienteDuplicadoException, CuentaNoExisteException, ClienteInexistenteException, CBUDuplicadoExeption {
        cliente1 = new Cliente("12345678", "Juan Perez");
        cliente2 = new Cliente("87654321", "Maria Lopez");
        cliente3 = new Cliente("22334455", "Carlos Gómez");
        cliente4 = new Cliente("33445566", "Laura Fernández");
        cliente5 = new Cliente("44556677", "Roberto Díaz");
        cliente6 = new Cliente("55667788", "Sofía Ruíz");
        cliente7 = new Cliente("55667788", "Sofía Ruíz");


        cuentaSueldo = new CuentaSueldo("CBU001", cliente1.getDni(), 2000.0);
        cajaDeAhorros = new CajaDeAhorros("CBU002", cliente1.getDni(), 10000.0);
        cuentaCorriente = new CuentaCorriente("CBU003", cliente2.getDni(), 500.0);
        cuentaSueldo2 = new CuentaSueldo("CBU001", cliente3.getDni(), 3000.0);  // CBU duplicado
        cuentaSueldo2 = new CuentaSueldo("CBU004", cliente3.getDni(), 3000.0);
        cuentaSueldo3 = new CuentaSueldo("CBU005", cliente4.getDni(), 2500.0);
        cuentaSueldo4 = new CuentaSueldo("CBU006", cliente5.getDni(), 0.0);
        cuentaSueldo5 = new CuentaSueldo("CBU007", cliente1.getDni(), 2800.0);
        cuentaSueldo6 = new CuentaSueldo("CBU008", cliente6.getDni(), 3200.0); // Nueva cuenta sueldo para el nuevo cliente

        cajaDeAhorros3 = new CajaDeAhorros("CBU010", cliente4.getDni(), 7000.0);
        cajaDeAhorros4 = new CajaDeAhorros("CBU010", cliente5.getDni(), 7000.0); // CBU duplicado
        cajaDeAhorros6 = new CajaDeAhorros("CBU013", cliente6.getDni(), 9000.0); // Nueva caja de ahorros para el nuevo cliente

        cuentaCorriente2 = new CuentaCorriente("CBU014", cliente3.getDni(), 1000.0);
        cuentaCorriente5 = new CuentaCorriente("CBU006", cliente2.getDni(), 800.0);
        cuentaCorriente6 = new CuentaCorriente("CBU018", cliente6.getDni(), 2000.0);

        banco = new Banco();

        banco.agregarCliente(cliente1);
        banco.agregarCliente(cliente2);
        banco.agregarCliente(cliente3);
        banco.agregarCliente(cliente4);
        banco.agregarCliente(cliente5);
        banco.agregarCliente(cliente6);
        try {
            banco.agregarCliente(cliente7);//Cliente duplicado
        } catch (ClienteDuplicadoException e) {
            System.out.println(e.getMessage());
        }

        banco.agregarCuenta(cuentaSueldo);
        banco.agregarCuenta(cajaDeAhorros);
        banco.agregarCuenta(cuentaCorriente);
        try {
            banco.agregarCuenta(cuentaSueldo2); // CBU duplicado
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        banco.agregarCuenta(cuentaSueldo3);
        banco.agregarCuenta(cuentaSueldo4);
        banco.agregarCuenta(cuentaSueldo5);
        banco.agregarCuenta(cuentaSueldo6);

        banco.agregarCuenta(cajaDeAhorros3);
        try {
            banco.agregarCuenta(cajaDeAhorros4); // CBU duplicado
        } catch (CBUDuplicadoExeption e) {
            System.out.println(e.getMessage());
        }

//        banco.agregarCuenta(cajaDeAhorros5);
        banco.agregarCuenta(cajaDeAhorros6);

        banco.agregarCuenta(cuentaCorriente2);
//        banco.agregarCuenta(cuentaCorriente3);
//        banco.agregarCuenta(cuentaCorriente4);
        try {
            banco.agregarCuenta(cuentaCorriente5);
        } catch (CBUDuplicadoExeption e) {
            System.out.println(e.getMessage());
            ;
        }
        banco.agregarCuenta(cuentaCorriente6);
        Assert.assertEquals(6, banco.getCantidadClientes());
        Assert.assertEquals(12, banco.getCantidadCuentas());

    }


    @Test
    public void queSePuedaExtraer1000PesosDeUnaCuentaSueldoConSaldoIgualA2000Pesos() throws Exception {

        banco.retirar(cuentaSueldo2.getCbu(), 1000);
        Double saldo = banco.checkSaldo(cuentaSueldo2.getCbu());
        Assert.assertEquals(Double.valueOf(2000.0), saldo);
    }


    @Test(expected = SaldoInsuficienteException.class)
    public void queNoSePuedaExtraer2500PesosDeUnaCuentaSueldoConSaldoIgualA2000Pesos() throws Exception {

        banco.retirar(cuentaSueldo.getCbu(), 2500);
        Double saldo = banco.checkSaldo(cuentaSueldo.getCbu());

    }

    @Test
    public void queAlRealizar6ExtraccionesDe1000EnUnaCajaDeAhorroConSaldoInicialDe10000SuSaldoFinalSea3900() throws Exception {

        banco.retirar(cajaDeAhorros.getCbu(), 1000);
        banco.retirar(cajaDeAhorros.getCbu(), 1000);
        banco.retirar(cajaDeAhorros.getCbu(), 1000);
        banco.retirar(cajaDeAhorros.getCbu(), 1000);
        banco.retirar(cajaDeAhorros.getCbu(), 1000);
        banco.retirar(cajaDeAhorros.getCbu(), 1000);
        Double saldo = banco.checkSaldo(cajaDeAhorros.getCbu());
        Assert.assertEquals(Double.valueOf(3900), saldo);
    }

    @Test(expected = SaldoInsuficienteException.class)
    public void queAlRealizar7ExtraccionesDe1000EnUnaCajaDeAhorroConSaldoInicialDe7000SoloLaUltimaLanceExcepcionSaldoInsuficiente() throws Exception {

        banco.retirar(cajaDeAhorros3.getCbu(), 1000);
        banco.retirar(cajaDeAhorros3.getCbu(), 1000);
        banco.retirar(cajaDeAhorros3.getCbu(), 1000);
        banco.retirar(cajaDeAhorros3.getCbu(), 1000);
        banco.retirar(cajaDeAhorros3.getCbu(), 1000);
        banco.retirar(cajaDeAhorros3.getCbu(), 1000);
        banco.retirar(cajaDeAhorros3.getCbu(), 1000);

        Double saldo = banco.checkSaldo(cajaDeAhorros3.getCbu());

    }


    @Test
    public void queSeCobreRecargoAlRealizarUnaExtraccionMayorAlSaldoEnUnaCuentaCorriente() throws Exception {

        banco.retirar(cuentaCorriente2.getCbu(), 2000);
        //esa cuenta tiene saldo de 1000, con prestamo de 2000 deberia haber un saldo de -1000, pero con recargo es de -1050
        Double saldo = banco.checkSaldo(cuentaCorriente2.getCbu());
        Assert.assertEquals(Double.valueOf(-1050.0), saldo);
    }

    @Test(expected = ClienteInexistenteException.class)
    public void queAlIntentarDarDeAltaUnaCuentaAUnClienteInexistenteLanceExcepcion() throws Exception {
        Cliente cliente10 = new Cliente("33445541", "Laura Fernández");
        Cuenta cuentaSueldo10 = new CuentaSueldo("CBU084", cliente10.getDni(), 3000.0);

        banco.agregarCuenta(cuentaSueldo10);
    }

    //este metodo estaba private
    @Test(expected = CuentaNoExisteException.class)
    public void queAlBuscarUnaCuentaPorCBUErroneoLanceExcepcion() throws CuentaNoExisteException {
        try {
            banco.existeCuenta("1212dd");
        } catch (CuentaNoExisteException e) {
            System.out.println(e.getMessage());
            throw new CuentaNoExisteException("Cuenta no existe");
        }
    }

    @Test
    public void queSeObtengaElListadoDeClientesOrdenadosPorDni() {
        banco.getListaClientes();
        Assert.assertEquals("{12345678=Cliente{dni='12345678', nombre='Juan Perez'}, 22334455=Cliente{dni='22334455', nombre='Carlos Gómez'}, 33445566=Cliente{dni='33445566', nombre='Laura Fernández'}, 44556677=Cliente{dni='44556677', nombre='Roberto Díaz'}, 55667788=Cliente{dni='55667788', nombre='Sofía Ruíz'}, 87654321=Cliente{dni='87654321', nombre='Maria Lopez'}}", banco.getListaClientes());
    }

    @Test
    public void queSeObtengaUnListadoDeTodasLasCuentasOrdenadoPorSaldo() {
        Comparator<Cuenta> comparador = new ComparoCuentaPorSaldo();
        System.out.println(banco.ordenarCuentasCustom(comparador));
        Assert.assertEquals("[Cuenta{saldo=0.0, dni='44556677', cbu='CBU006'}, Cuenta{saldo=500.0, dni='87654321', cbu='CBU003'}, Cuenta{saldo=1000.0, dni='22334455', cbu='CBU014'}, Cuenta{saldo=2000.0, dni='12345678', cbu='CBU001'}, Cuenta{saldo=2500.0, dni='33445566', cbu='CBU005'}, Cuenta{saldo=2800.0, dni='12345678', cbu='CBU007'}, Cuenta{saldo=3000.0, dni='22334455', cbu='CBU004'}, Cuenta{saldo=3200.0, dni='55667788', cbu='CBU008'}, Cuenta{saldo=7000.0, dni='33445566', cbu='CBU010'}, Cuenta{saldo=9000.0, dni='55667788', cbu='CBU013'}, Cuenta{saldo=10000.0, dni='12345678', cbu='CBU002'}]", banco.ordenarCuentasCustom(comparador));

    }

    @Test
    public void queSeObtengaUnListadoDeCuentasCorrientesOrdenadoPorSaldo() {
        Comparator<Cuenta> comparador = new ComparoCuentaPorSaldo();
        Assert.assertEquals("[Cuenta{saldo=500.0, dni='87654321', cbu='CBU003'}, Cuenta{saldo=1000.0, dni='22334455', cbu='CBU014'}, Cuenta{saldo=2000.0, dni='55667788', cbu='CBU018'}]", banco.ordenarCuentasCorrienteSaldoDeudor(comparador));
    }

    @Test
    public void queSeObtengaUnListadoDeCuentasCorrientesDeudorasOrdenadoPorSaldoDeudor() throws Exception {
        Cliente cliente11 = new Cliente("44557677", "Roberto Díaz");
        Cuenta cuentaCorriente12 = new CuentaCorriente("CBU915", cliente11.getDni(), 100.0);
        banco.agregarCliente(cliente11);
        banco.agregarCuenta(cuentaCorriente12);

        Comparator<Cuenta> comparador = new ComparoCuentaPorSaldo();

        banco.retirar(cuentaCorriente2.getCbu(), 2000);
        banco.retirar(cuentaCorriente2.getCbu(), 2000);
        banco.retirar(cuentaCorriente6.getCbu(), 3000);
        banco.retirar(cuentaCorriente12.getCbu(), 3000);
        banco.retirar(cuentaCorriente12.getCbu(), 3000);

        banco.getCuentasCorrientesOrdenadasPorDeudor(comparador);
        Assert.assertEquals("[Cuenta{saldo=-3150.0, dni='22334455', cbu='CBU014'}, Cuenta{saldo=-3045.0, dni='44557677', cbu='CBU915'}, Cuenta{saldo=-1050.0, dni='55667788', cbu='CBU018'}]", banco.getCuentasCorrientesOrdenadasPorDeudor(comparador));
    }

}

//5%
//
//10%
//
//10%

//10%

//10%

//10%

//10%

//10%
//
//10%
//