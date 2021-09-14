package br.com.zitrus.desafio.repository.stockmovement;

import br.com.zitrus.desafio.domain.stockmovement.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

  @Transactional(readOnly = true)
  @Query("SELECT sm FROM StockMovement sm WHERE sm.idStockMovement = :id")
  StockMovement findByIdStockMovement(Long id);

  @Transactional(readOnly = true)
  @Query("SELECT sm FROM StockMovement sm WHERE sm.movementType = :movementType")
  List<StockMovement> findByMovementType(String movementType);
}
