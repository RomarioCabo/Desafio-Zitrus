package br.com.zitrus.desafio.repository.category.criteria.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryFilter implements Serializable {
  private static final long serialVersionUID = -7105747450437931552L;

  private Long id;
  private String name;
}
