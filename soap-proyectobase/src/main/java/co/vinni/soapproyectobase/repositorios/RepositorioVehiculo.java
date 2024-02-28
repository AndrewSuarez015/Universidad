package co.vinni.soapproyectobase.repositorios;

import co.vinni.soapproyectobase.entidades.Vehiculo;
import java.util.List;

public interface RepositorioVehiculo {
    void registrarVehiculo(Vehiculo vehiculo);
    List<Vehiculo> consultarVehiculos();
}
