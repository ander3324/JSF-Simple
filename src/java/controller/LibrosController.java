/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.LibrosDAO;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import orm.Libros;

/**
 *
 * @author ander
 */
@ManagedBean
@RequestScoped
public class LibrosController {

    //Objetos necesarios:
    private Libros libro;
    private List<Libros> listaLibros;

    @EJB
    LibrosDAO libDao;

    /**
     * Creates a new instance of LibrosController
     */
    public LibrosController() {
        libro = new Libros();
    }

    @PostConstruct
    public void inicializar() {
        cargarLibros();
    }

    void cargarLibros() {
        setListaLibros(libDao.selectLibros());
    }

    public Libros getLibro() {
        return libro;
    }

    public void setLibro(Libros libro) {
        this.libro = libro;
    }

    public List<Libros> getListaLibros() {
        if(listaLibros == null)
            listaLibros = libDao.selectLibros();
        return listaLibros;
    }

    public void setListaLibros(List<Libros> listaLibros) {
        this.listaLibros = listaLibros;
    }

    /*
     * METODOS QUE REDIRECCIONAN ACCIONES DE LA UI
     */
    public String doPrepararNuevoLibro() {
        libro = new Libros();
        return "nuevo";
    }

    public String doGuardarLibro() {

        libDao.insertLibro(libro);

        cargarLibros();
        return "index";
    }

    public String doPrepararModificacion(int isbn) {
        libro = libDao.selectLibroPorISBN(isbn);
        return "editar";
    }

    public String doEditarLibro() {
        libDao.updateLibro(libro);
        cargarLibros();
        return "index";
    }

    public void doBorrarLibro(int isbn) {
        libro = libDao.selectLibroPorISBN(isbn);
        libDao.deleteLibro(libro);
        cargarLibros();
    }

}
