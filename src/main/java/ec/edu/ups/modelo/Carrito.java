package ec.edu.ups.modelo;


import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

import java.util.List;

public class Carrito {

    private int codigo;
    private static int contador =1;
    private GregorianCalendar fecha;
    private final List<ItemCarrito> items;
    private double subtotal=0;
    private double total;
    private double IVA;
    private Usuario usuarioLogueado;

    @Override
    public String toString() {
        return "Carrito{" +
                "codigo=" + codigo +
                ", fecha=" + fecha +
                ", items=" + items +
                ", subtotal=" + subtotal +
                ", total=" + total +
                ", IVA=" + IVA +
                '}';
    }

    public Carrito(GregorianCalendar fecha, int codigo, double subtotal, double total, double IVA) {

        this.items = new ArrayList<>();
        this.fecha = fecha;
        this.codigo = contador++;
        this.subtotal = 0;
        this.total = total;
        this.IVA = 0.12;
    }

    public Carrito() {
        items = new ArrayList<>();
        codigo = contador++;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public GregorianCalendar getFecha() {
        return fecha;
    }

    public void setFecha(GregorianCalendar fecha) {
        this.fecha = fecha;
    }

    public void agregarProducto(Producto producto, int cantidad){
        ItemCarrito item = new ItemCarrito(producto, cantidad);
        items.add(item);

    }

    public double calcularSubtotal(){
        for(ItemCarrito item : items){
            subtotal += item.getSubtotal();
        }
        return subtotal;

    }
    public double calcularIVA(){
        IVA = subtotal * 0.12;
        return IVA;
    }
    public double calcularTotal() {
        total = subtotal + IVA;
        return total;
    }

    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    public void setUsuario(Usuario usuarioLogueado) {
        this.usuarioLogueado=usuarioLogueado;
    }
}
