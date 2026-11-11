package utez.edu.mx.CBRR_Miguel_Chavez_CC7.controller;

import jakarta.validation.Valid;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForMap;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.CBRR_Miguel_Chavez_CC7.model.Pedido;
import utez.edu.mx.CBRR_Miguel_Chavez_CC7.model.Producto;
import utez.edu.mx.CBRR_Miguel_Chavez_CC7.service.MyService;

@RestController
@RequestMapping("/controller")
@CrossOrigin("*")
public class MyController {
    private final MyService service;


    public MyController(MyService service, NotEmptyValidatorForMap notEmptyValidatorForMap) {
        this.service = service;

    }

    @PostMapping("crearpedido")
    public ResponseEntity<Pedido> crearunPedido(@RequestBody @Valid Long id, String nombre, Integer cantidad){
        return ResponseEntity.status(201).body(service.crearPedido(id, nombre, cantidad));
    }

    @PostMapping("cancelarpedido/{id}")
    public ResponseEntity<Pedido> cancelarPedido(@RequestBody @Valid Long id){
        return ResponseEntity.status(201).body(service.cancelarPedido(id));
    }

    @RequestMapping("procesar/{id}")
    public ResponseEntity<Pedido> procesarPedido(@RequestParam @Valid Long id){
        return ResponseEntity.status(201).body(service.procesarPedido(id));
    }

    @RequestMapping("desactiar/{id}")
    public ResponseEntity<Producto> desactivarProducto(@RequestParam @Valid Long id){
        return ResponseEntity.status(201).body(service.desactivarProducto(id));
    }


}
