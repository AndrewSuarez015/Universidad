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

        //Menu
        while (!salir) {
            System.out.println("\n========== Menú Principal ==========");
            System.out.println("1. Registrar Vehículo");
            System.out.println("2. Consultar Vehículos");
            System.out.println("3. Modificar Vehículo");
            System.out.println("4. Borrar Vehículo");
            System.out.println("5. Salir");
            System.out.println("====================================");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    registrarVehiculo();
                    break;
                case 2:
                    consultarVehiculos();
                    break;
                case 3:
                    modificarVehiculo();
                    break;
                case 4:
                    borrarVehiculo();
                    break;
                case 5:
                    salir = true;
                    System.out.println("Saliendo...");
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

        // Añade una columna para la Fecha de Compra en el encabezado
        String header = "| %-10s | %-10s | %-15s | %-15s | %-20s | %-20s | %-20s | %-20s |\n";
        System.out.format(header, "ID", "Placa", "Tipo", "Municipio", "Fecha Compra", "Precio Compra", "Valor Actual", "Impuesto");
        System.out.println(new String(new char[155]).replace("\0", "-")); // longitud de la línea divisoria

        DecimalFormat formatter = new DecimalFormat("###,###,###");

        for (Vehiculo vehiculo : vehiculos) {
            // Mapear datos de la tabla
            String row = "| %-10d | %-10s | %-15s | %-15s | %-20s | %-20s | %-20s | %-20s |\n";
            System.out.format(row,
                    vehiculo.getId(),
                    vehiculo.getPlaca(),
                    vehiculo.getTipo(),
                    vehiculo.getMunicipioRegistro(),
                    vehiculo.getFechaCompra(),
                    formatter.format(vehiculo.getPrecioCompra()),
                    formatter.format(vehiculo.getValorActual()),
                    formatter.format(vehiculo.calcularImpuesto()));
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
}
