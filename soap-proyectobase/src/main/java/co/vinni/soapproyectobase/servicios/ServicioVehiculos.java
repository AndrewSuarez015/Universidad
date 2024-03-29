package co.vinni.soapproyectobase.servicios;
import java.util.Optional;
import co.vinni.soapproyectobase.entidades.Vehiculo;
import co.vinni.soapproyectobase.repositorios.RepositorioVehiculo;
import co.vinni.soapproyectobase.utilidades.UtilidadArchivos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServicioVehiculos implements RepositorioVehiculo, Serializable {
    private List<Vehiculo> listaVehiculos;
    private static final String ARCHIVO_VEHICULOS = "vehiculos.dat";

    public ServicioVehiculos() {
        listaVehiculos = new ArrayList<>();
        cargarVehiculos();
    }

    private void cargarVehiculos() {
        Object data = UtilidadArchivos.obtener(ARCHIVO_VEHICULOS);
        if (data != null && data instanceof List) {
            listaVehiculos = (List<Vehiculo>) data;
        }
    }

    @Override
    public void registrarVehiculo(Vehiculo vehiculo) {
        listaVehiculos.add(vehiculo);
        UtilidadArchivos.guardar(ARCHIVO_VEHICULOS, listaVehiculos);
    }

    @Override
    public List<Vehiculo> consultarVehiculos() {
        return listaVehiculos;
    }

    // Método para modificar vehículos Por placa
    public boolean modificarVehiculoPorPlaca(String placa, Vehiculo vehiculoModificado) {
        Optional<Vehiculo> vehiculoExistente = listaVehiculos.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst();

        if (vehiculoExistente.isPresent()) {
            int index = listaVehiculos.indexOf(vehiculoExistente.get());
            vehiculoModificado.setPlaca(vehiculoExistente.get().getPlaca());
            listaVehiculos.set(index, vehiculoModificado);
            UtilidadArchivos.guardar(ARCHIVO_VEHICULOS, listaVehiculos);
            return true;
        }
        return false;
    }
    // Método para borrar vehículos Por placa
    public boolean borrarVehiculoPorPlaca(String placa) {
        boolean resultado = listaVehiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(placa));
        if (resultado) {
            UtilidadArchivos.guardar(ARCHIVO_VEHICULOS, listaVehiculos);
        }
        return resultado;
    }
}
