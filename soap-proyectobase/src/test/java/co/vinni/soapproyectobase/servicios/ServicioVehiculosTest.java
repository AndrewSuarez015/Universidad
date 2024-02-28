package co.vinni.soapproyectobase.servicios;

import co.vinni.soapproyectobase.entidades.Vehiculo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ServicioVehiculosTest {
    ServicioVehiculos servicio = new ServicioVehiculos();

    ServicioVehiculosTest(){
        servicio.registrarEquipo(new Vehiculo(1,"Millos",  "Ëquipo de Bogota"));
        servicio.registrarEquipo(new Vehiculo(2,"Fortaleza",  "Ëquipo de Bogota"));
        servicio.registrarEquipo(new Vehiculo(3,"Equidad",  "Ëquipo de Bogota"));

    }

    @Test
    void registrarEquipo() {


        Vehiculo e = new Vehiculo();

        servicio.registrarEquipo(e);
        servicio.registrarEquipo(new Vehiculo(1,"Millos",  "Ëquipo de Bogota"));
        List<Vehiculo> lista = servicio.consultarEquipos();
        Assertions.assertTrue(lista.size()>3);

    }

    @Test
    void consultarEquipos() {
        List<Vehiculo> lista = servicio.consultarEquipos();
        Assertions.assertEquals(3, lista.size());
    }
}
