package br.com.zitrus.desafio.service.pojo.form.category;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryForm implements Serializable {

  private static final long serialVersionUID = -233957872658267840L;

  private String name;
}
