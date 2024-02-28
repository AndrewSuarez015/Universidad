package co.vinni.soapproyectobase.controladores;

import co.vinni.soapproyectobase.entidades.Vehiculo;
import co.vinni.soapproyectobase.servicios.ServicioVehiculos;

import java.util.List;

public class ControladorVehiculos {
    private ServicioVehiculos servicioVehiculos;

    public ControladorVehiculos() {
        this.servicioVehiculos = new ServicioVehiculos();
    }

    public void registrarVehiculo(Vehiculo vehiculo) {
        servicioVehiculos.registrarVehiculo(vehiculo);
    }

    public List<Vehiculo> consultarVehiculos() {
        return servicioVehiculos.consultarVehiculos();
    }

    // Actualizado para modificar vehículos por placa
    public boolean modificarVehiculoPorPlaca(String placa, Vehiculo vehiculoModificado) {
        return servicioVehiculos.modificarVehiculoPorPlaca(placa, vehiculoModificado);
    }

    // Actualizado para borrar vehículos por placa
    public boolean borrarVehiculoPorPlaca(String placa) {
        return servicioVehiculos.borrarVehiculoPorPlaca(placa);
    }
    public boolean existeVehiculoConPlaca(String placa) {
        List<Vehiculo> vehiculos = consultarVehiculos();
        return vehiculos.stream().anyMatch(vehiculo -> vehiculo.getPlaca().equalsIgnoreCase(placa));
    }
}
