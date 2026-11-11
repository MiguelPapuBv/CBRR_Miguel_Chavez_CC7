package utez.edu.mx.CBRR_Miguel_Chavez_CC7.service;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import utez.edu.mx.CBRR_Miguel_Chavez_CC7.model.Estado;
import utez.edu.mx.CBRR_Miguel_Chavez_CC7.model.Pedido;
import utez.edu.mx.CBRR_Miguel_Chavez_CC7.model.Producto;
import utez.edu.mx.CBRR_Miguel_Chavez_CC7.repository.PedidoRepository;
import utez.edu.mx.CBRR_Miguel_Chavez_CC7.repository.ProductoRepository;

@Service
public class MyService {

    private final ProductoRepository productoRepository;
    private final PedidoRepository pedidoRepository;

    public MyService(PedidoRepository pedidoRepository, ProductoRepository productoRepository){

        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }
@Transactional
public Pedido crearPedido(Long id, String nombre, Integer cantidad){

        Producto producto = productoRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Error el producto no existe"));


        Pedido pedido = new Pedido();
        if(!producto.isActivo()){
            new RuntimeException("El usuario no esta activo");
        }

        if(nombre.isBlank()){
            new RuntimeException("El nombre no puede ir en blanco");
        }

        if(pedido.getCantidad() <0){
            new RuntimeException("Error la cantidad debe ser mayor a cero");
        }

        if(pedido.getCantidad() > producto.getStock()){
            new RuntimeException("Error el stock fue superado por la cantidad");
        }

        Double precioActual = producto.getPrecio();

        Double totalActual = precioActual * cantidad;

        cantidad = cantidad - producto.getStock();
        pedido.setEstado(Estado.PENDIENTE);
        pedido.setNombreCliente(nombre);
        pedido.setPrecioUnitario(producto.getPrecio());
        pedido.setTotal(totalActual);
        pedido.setProducto(producto);

        return pedidoRepository.save(pedido);


    }
    @Transactional
    public Pedido cancelarPedido(Long id){

        Pedido pedido =  pedidoRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Error el producto no existe"));

        if(pedido.getEstado() != Estado.PENDIENTE){
            new RuntimeException("Error el estado pedido debe ser PENDIENTE");
        }

        pedido.setEstado(Estado.CANCELADO);
        Producto producto = new Producto();


        producto.setStock(producto.getStock() + pedido.getCantidad());
        return pedidoRepository.save(pedido);

    }
    @Transactional
    public Pedido procesarPedido(Long id){

        Pedido pedido = pedidoRepository.findById(id).
                orElseThrow(() -> new RuntimeException("No existe El pedido"));

        if(pedido.getEstado() != Estado.PENDIENTE){
            new RuntimeException("Error no se puede procesar un pedido si no esta en estado pendiente");
        }

        pedido.setEstado(Estado.PROCESADO);

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Producto desactivarProducto(Long id){

        Producto producto = productoRepository.findById(id).
                orElseThrow(() -> new RuntimeException("El producto no existe"));

        Pedido pedido = new Pedido();

        if(pedido.getEstado() == Estado.PENDIENTE){

            new RuntimeException("Error no se pueden desactivar productos con pedidos en estado pendiente");
        }

        producto.setActivo(false);

        return productoRepository.save(producto);

    }

}
