package co.vinni.soapproyectobase;

import java.time.LocalDate;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.List;
import java.util.Optional;
import java.text.DecimalFormat;

import co.vinni.soapproyectobase.controladores.ControladorVehiculos;
import co.vinni.soapproyectobase.entidades.Vehiculo;

/**
 * Clase principal que ejecuta la aplicación.
 */
public class SoapProyectobaseApplication {

    private static final ControladorVehiculos controladorVehiculos = new ControladorVehiculos();
    private static final Scanner scanner = new Scanner(System.in);
    private static final NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n========== Menú Principal ==========");
            System.out.println("1. Gestión de Vehículos");
            System.out.println("2. Consulta de Clientes");
            System.out.println("3. Salir");
            System.out.println("====================================");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    mostrarMenuGestionVehiculos();
                    break;
                case 2:
                    mostrarMenuConsultaClientes();
                    break;
                case 3:
                    salir = true;
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("\nOpción no válida\n");
            }
        }
    }

    private static void mostrarMenuGestionVehiculos() {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("\n========== Menú de Gestión de Vehículos ==========");
            System.out.println("1. Registrar Vehículo");
            System.out.println("2. Modificar Vehículo");
            System.out.println("3. Borrar Vehículo");
            System.out.println("4. Consultar Vehículos");
            System.out.println("5. Regresar al Menú Principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    registrarVehiculo();
                    break;
                case 2:
                    modificarVehiculo();
                    break;
                case 3:
                    borrarVehiculo();
                    break;
                case 4:
                    mostrarMenuConsultaVehiculos();
                    break;
                case 5:
                    regresar = true;
                    break;
                default:
                    System.out.println("\nOpción no válida\n");
            }
        }
    }

    private static void mostrarMenuConsultaVehiculos() {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("\n========== Menú de Consulta de Vehículos ==========");
            System.out.println("1. Consultar Todos los Vehículos");
            System.out.println("2. Consultar Vehículo por Placa");
            System.out.println("3. Regresar");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    consultarVehiculos();
                    break;
                case 2:
                    consultarVehiculoPorPlaca();
                    break;
                case 3:
                    regresar = true;
                    break;
                default:
                    System.out.println("\nOpción no válida\n");
            }
        }
    }

    private static void mostrarMenuConsultaClientes() {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("\n========== Menú de Consulta de Clientes ==========");
            System.out.println("1. Consultar Vehículo por Placa");
            System.out.println("2. Pagar Impuestos");
            System.out.println("3. Regresar al Menú Principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    consultarVehiculoPorPlacaCliente(); // Este método mostrará solo los datos relevantes para el cliente
                    break;
                case 2:
                    realizarPagoCliente(); // Este método gestionará el pago
                    break;
                case 3:
                    regresar = true;
                    break;
                default:
                    System.out.println("\nOpción no válida\n");
            }
        }
    }

    private static void registrarVehiculo() {
        String placa = solicitarDatoString("Digite la placa del vehículo: ");

        // Verificar si ya existe un vehículo con la misma placa
        if (controladorVehiculos.existeVehiculoConPlaca(placa)) {
            System.out.println("Ya existe un vehículo registrado con esa placa.");
            return;
        }

        String tipo = solicitarDatoString("Digite el tipo de vehículo (Carro, Moto, etc.): ");
        String municipio = solicitarDatoString("Digite el municipio de registro: ");
        System.out.print("Digite el precio de compra: ");
        double precioCompra = scanner.nextDouble();
        scanner.nextLine(); // Limpiar el buffer del scanner

        System.out.print("Digite la fecha de compra (AAAA-MM-DD): ");
        String fechaCompraStr = scanner.nextLine();
        LocalDate fechaCompra = LocalDate.parse(fechaCompraStr);

        Vehiculo vehiculo = new Vehiculo(placa, tipo, municipio, precioCompra, fechaCompra);
        controladorVehiculos.registrarVehiculo(vehiculo);

        System.out.println("Vehículo registrado exitosamente.");
    }

    private static void consultarVehiculos() {
        List<Vehiculo> vehiculos = controladorVehiculos.consultarVehiculos();
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
            return;
        }

        System.out.println("| ID    | Placa  | Tipo      | Municipio      | Precio Compra | Valor Actual | Impuesto    | Estado |");
        System.out.println(new String(new char[95]).replace("\0", "-"));

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        for (Vehiculo vehiculo : vehiculos) {
            System.out.format("| %-6d | %-6s | %-9s | %-14s | %-13s | %-12s | %-11s | %-6s |\n",
                    vehiculo.getId(),
                    vehiculo.getPlaca(),
                    vehiculo.getTipo(),
                    vehiculo.getMunicipioRegistro(),
                    formatter.format(vehiculo.getPrecioCompra()),
                    formatter.format(vehiculo.getValorActual()),
                    formatter.format(vehiculo.calcularImpuesto()),
                    vehiculo.getEstado());
        }
    }

    private static void modificarVehiculo() {
        String placa = solicitarDatoString("Digite la placa del vehículo a modificar: ");
        Optional<Vehiculo> vehiculoOpt = controladorVehiculos.consultarVehiculos().stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst();

        if (!vehiculoOpt.isPresent()) {
            System.out.println("No hay ningún vehículo con esa placa.");
            return;
        }

        Vehiculo vehiculo = vehiculoOpt.get();
        vehiculo.setTipo(solicitarDatoString("Digite el nuevo tipo de vehículo (Carro, Moto, etc.): "));
        vehiculo.setMunicipioRegistro(solicitarDatoString("Digite el nuevo municipio de registro: "));
        System.out.print("Digite el nuevo precio de compra: ");
        vehiculo.setPrecioCompra(scanner.nextDouble());
        scanner.nextLine(); // Limpiar el buffer del scanner

        System.out.println("Vehículo modificado con éxito.");
    }

    private static void borrarVehiculo() {
        String placa = solicitarDatoString("Digite la placa del vehículo a borrar: ");
        boolean eliminado = controladorVehiculos.borrarVehiculoPorPlaca(placa);

        if (eliminado) {
            System.out.println("Vehículo borrado con éxito.");
        } else {
            System.out.println("No hay ningún vehículo con esa placa.");
        }
    }

    private static String solicitarDatoString(String mensaje) {
        System.out.print(mensaje);
        String input = scanner.nextLine().trim();
        while (input.isEmpty()) {
            System.out.print("Por favor, no deje este campo vacío. " + mensaje);
            input = scanner.nextLine().trim();
        }
        return input;
    }

    private static void consultarVehiculoPorPlaca() {
        System.out.print("Digite la placa del vehículo a consultar: ");
        String placa = scanner.nextLine();
        Optional<Vehiculo> vehiculo = controladorVehiculos.consultarVehiculoPorPlaca(placa);

        if (vehiculo.isPresent()) {
            Vehiculo v = vehiculo.get();
            // Encabezado de la tabla
            System.out.println("| ID       | Placa     | Tipo           | Municipio          | Fecha Compra    | Precio Compra   | Valor Actual    | Impuesto        |");
            System.out.println("|----------|-----------|----------------|--------------------|-----------------|-----------------|-----------------|-----------------|");

            // Formato de los datos en la fila
            String rowFormat = "| %-8d | %-9s | %-14s | %-18s | %-15s | %-15s | %-15s | %-15s |\n";
            System.out.format(rowFormat,
                    v.getId(),
                    v.getPlaca(),
                    v.getTipo(),
                    v.getMunicipioRegistro(),
                    v.getFechaCompra().toString(),
                    formatter.format(v.getPrecioCompra()),
                    formatter.format(v.getValorActual()),
                    formatter.format(v.calcularImpuesto()));
        } else {
            System.out.println("No se encontró un vehículo con la placa ingresada.");
        }
    }

    private static void consultarVehiculoPorPlacaCliente() {
        System.out.print("Digite la placa del vehículo a consultar: ");
        String placa = scanner.nextLine();
        Optional<Vehiculo> vehiculoOpt = controladorVehiculos.consultarVehiculoPorPlaca(placa);

        if (vehiculoOpt.isPresent()) {
            Vehiculo v = vehiculoOpt.get();
            System.out.println("| Placa | Tipo | Impuesto | Estado |");
            System.out.println(new String(new char[40]).replace("\0", "-"));
            String rowFormat = "| %-5s | %-4s | %-8s | %-6s |\n";
            System.out.format(rowFormat, v.getPlaca(), v.getTipo(), formatter.format(v.calcularImpuesto()), v.getEstado());
        } else {
            System.out.println("No se encontró un vehículo con la placa ingresada.");
        }
    }

    private static void realizarPagoCliente() {
        System.out.print("Digite la placa del vehículo para realizar el pago: ");
        String placa = scanner.nextLine();
        if (controladorVehiculos.realizarPago(placa)) {
            System.out.println("Pago realizado con éxito. El estado ha sido actualizado a 'Pagado'.");
        } else {
            System.out.println("No se encontró un vehículo con esa placa o el pago no se pudo realizar.");
        }
    }
}
