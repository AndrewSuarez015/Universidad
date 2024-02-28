package co.vinni.soapproyectobase.utilidades;

import co.vinni.soapproyectobase.entidades.Vehiculo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UtilidadArchivosTest {


    @Test
    @DisplayName("Test registrar objeto en archivo")
    void registrarObjeto() {
        Vehiculo vehiculo = new Vehiculo(1,"millos","Equipo de la capital Bogotá");
        boolean rta = UtilidadArchivos.guardar("prueba.txt", vehiculo);
        Assertions.assertTrue(rta);
    }
    @Test
    @DisplayName("Test registrar objeto en archivo")
    void leerObjeto() {
        Vehiculo vehiculo = new Vehiculo(1,"millos","Equipo de la capital Bogotá");
        UtilidadArchivos.guardar("prueba.txt", vehiculo);
        Vehiculo elequipo = (Vehiculo)UtilidadArchivos.obtener ("prueba.txt");
        System.out.println(elequipo);
        Assertions.assertNotNull(elequipo);
    }

}
