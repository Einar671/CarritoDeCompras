package ec.edu.ups.modelo;

/**
 * Define los roles que puede tener un {@link Usuario} dentro del sistema.
 * <p>
 * Los roles se utilizan para controlar los permisos y el acceso a diferentes
 * funcionalidades de la aplicación.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public enum Rol {
    /**
     * Rol con los máximos privilegios. Puede gestionar usuarios, productos
     * y otras configuraciones del sistema.
     */
    ADMINISTRADOR,

    /**
     * Rol estándar para clientes. Pueden navegar por los productos,
     * gestionar su carrito de compras y realizar pedidos.
     */
    USUARIO
}