package br.com.zitrus.desafio.controller.stockmovement;

import br.com.zitrus.desafio.exception.StandardError;
import br.com.zitrus.desafio.service.impl.stockmovement.StockMovementService;
import br.com.zitrus.desafio.service.pojo.dto.stockmovement.StockMovementDto;
import br.com.zitrus.desafio.service.pojo.form.stockmovement.StockMovementForm;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/stock_movement")
@RequiredArgsConstructor
public class StockMovementController {

  private final StockMovementService stockMovementService;

  @ApiResponses(
      value = {
        @ApiResponse(code = 201, message = "Created", response = StockMovementDto.class),
        @ApiResponse(code = 400, message = "Bad Request", response = StandardError.class),
        @ApiResponse(code = 500, message = "Internal Error", response = StandardError.class)
      })
  @PostMapping(
      value = "/save",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<StockMovementDto> save(@Valid @RequestBody StockMovementForm request) {
    StockMovementDto stockMovementDto = stockMovementService.save(request);

    return ResponseEntity.created(null).body(stockMovementDto);
  }
}
