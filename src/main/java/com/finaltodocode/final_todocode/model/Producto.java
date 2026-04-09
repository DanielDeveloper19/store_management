package com.finaltodocode.final_todocode.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigoProducto;
    private String nombre;
    private String marca;
    private Double precio ;
    private Double cantidadDisponible;

    //Este atributo siempre irá en '0' dentro de la entidad, ya que sera usado en el json
    // de la Venta para definir que cantidad de este producto deseamos incluir dentro de la Venta
    private int cantidadAComprar;


    public Producto(Long codigoProducto, String nombre, String marca, double precio, Double cantidadDisponible) {
        this.codigoProducto = codigoProducto;
        this.nombre= nombre;
        this.marca = marca;
        this.precio = precio;
        this.cantidadDisponible= cantidadDisponible;

    }
}
