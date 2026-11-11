package utez.edu.mx.CBRR_Miguel_Chavez_CC7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.CBRR_Miguel_Chavez_CC7.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
