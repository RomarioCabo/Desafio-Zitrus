package br.com.zitrus.desafio.service.interfaces;

import br.com.zitrus.desafio.service.enums.Direction;
import org.springframework.data.domain.Page;

public interface CrudInterface<DTO, FORM, FILTER> {

  DTO save(FORM form, Long id);

  void delete(Long id);

  Page<DTO> find(
      FILTER filter, Integer page, Integer linesPerPage, String sortBy, Direction direction);
}
