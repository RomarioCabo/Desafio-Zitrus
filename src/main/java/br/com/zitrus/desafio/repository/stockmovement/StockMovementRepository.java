package br.com.zitrus.desafio.repository.stockmovement;

import br.com.zitrus.desafio.domain.stockmovement.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {}
