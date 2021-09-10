package br.com.zitrus.desafio.domain;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable> implements Serializable {
  private static final long serialVersionUID = -2039660288659171355L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private ID id;
}
