package ar.edu.unahur.obj2.observer.observables;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.observer.excepciones.OfertaSubastadorException;
import ar.edu.unahur.obj2.observer.observadores.Subastador;

class ProductoSubatadoTest {
    Subastador sub1 = new Subastador("gonzager");
    Subastador sub2 = new Subastador("diazdan");
    Subastador sub3 = new Subastador("martomau");

    ProductoSubatado productoSubatado = new ProductoSubatado();

    @BeforeEach
    void setUp() {

        productoSubatado.reset();
        sub1.reiniciarSubastador();
        sub2.reiniciarSubastador();
        sub3.reiniciarSubastador();

        productoSubatado.agregarSubastador(sub1);
        productoSubatado.agregarSubastador(sub3);

    }

    void escenario1() {
        productoSubatado.agregarOferta(sub3.crearOferta());
        productoSubatado.agregarOferta(sub1.crearOferta());
        productoSubatado.agregarOferta(sub3.crearOferta());
    }

    @Test
    void dadoElEscenario1_seVerifiqueLaCorrectaNofificacionDeLaUltimaOfertaALosSubastadores() {
        escenario1();
        assertEquals(sub3, sub1.getUltimaOferta().getSubastador());
        assertEquals(sub3, sub3.getUltimaOferta().getSubastador());
    }

    @Test
    void dadoElEscenario1_seVverificarQueLaUltimaOfertaRegistradaPerteneceAlSubastadorCorrecto() {
        escenario1();
        assertTrue(productoSubatado.getUltimaOferta().equals(sub3.getUltimaOferta()));
    }

    @Test
    void dadoElEscenario1_seVerificaQueElValorDeLaUltimaOfertaSeaLaCorrecta() {
        escenario1();
        assertEquals(30, productoSubatado.getUltimaOferta().getValor());
    }

    @Test
    void dadoElEscenario1_seVerificaQueLaCantidadDeOfertasRecibidasSeaLaCorrecta() {
        escenario1();
        assertEquals(3, productoSubatado.getOfertas().size());
    }

    @Test
    void dadoElEscenario1_alAgregarUnOfertaDeUnUsuarioQueNoParticipaEnLaSubsta_seProduceUnaExcepcion() {
        escenario1();
        assertThrowsExactly(OfertaSubastadorException.class,
                () -> {
                    productoSubatado.agregarOferta(sub2.crearOferta());
                }, "El subasador diazdan no participa en la subasta");
    }
}
