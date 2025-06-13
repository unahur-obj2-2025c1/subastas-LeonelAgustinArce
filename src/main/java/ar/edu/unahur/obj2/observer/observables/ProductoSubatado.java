package ar.edu.unahur.obj2.observer.observables;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.edu.unahur.obj2.observer.Oferta;
import ar.edu.unahur.obj2.observer.excepciones.OfertaSubastadorException;
import ar.edu.unahur.obj2.observer.observadores.Observer;

public class ProductoSubatado implements Observable{
    private List<Oferta> ofertas = new ArrayList<>();
    private Set<Observer> subastadores = new HashSet<>();

     public void agregarOferta(Oferta oferta) {
        if (!subastadores.contains(oferta.getSubastador()))
            throw new OfertaSubastadorException(
                    "El subasador" + oferta.getSubastador().getUserName() + " no participa en la subasta");
        ofertas.add(oferta);
        notificar();
    }

    @Override
    public void agregarSubastador(Observer observador) {
        subastadores.add(observador);
    }

    @Override
    public void removerSubastador(Observer observador) {
        subastadores.remove(observador);
    }

    public List<Oferta> getOfertas() {
        return ofertas;
    }

    public Set<Observer> getSubastadores() {
        return subastadores;
    }

    @Override
    public void notificar() {
        subastadores.stream().forEach(observer -> observer.actualizar(getUltimaOferta()));
    }
    
    public Oferta getUltimaOferta() {
        return ofertas.get(ofertas.size() - 1);
    }

    public void reset(){
        ofertas.clear();
        subastadores.clear();
    }
}
