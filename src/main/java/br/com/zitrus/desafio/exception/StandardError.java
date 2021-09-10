package br.com.zitrus.desafio.exception;

import java.io.Serializable;
import lombok.Data;

@Data
public class StandardError implements Serializable {
  private static final long serialVersionUID = 5139057664613866299L;

  private Integer status;
  private String message;
  private Long timeStamp;

  public StandardError(Integer status, String message, Long timeStamp) {
    this.status = status;
    this.message = message;
    this.timeStamp = timeStamp;
  }
}
