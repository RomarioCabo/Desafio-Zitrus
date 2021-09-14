package br.com.zitrus.desafio.service.pojo.dto.stockmovement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReduceDto implements Serializable {
    private static final long serialVersionUID = -930339813143696591L;

    private Long id;
    private String name;
}
