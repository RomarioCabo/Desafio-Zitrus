package br.com.zitrus.desafio.controller.helper;

import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class ApiHelper<DTO> {

  public HttpHeaders responseHeaders(Page<DTO> listPages) {
    if (listPages.isEmpty()) {
      return null;
    }

    int totalPages = listPages.getTotalPages();

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("totalPages", Integer.toString(totalPages));
    responseHeaders.set("access-control-expose-headers", "totalPages");
    responseHeaders.set("totalElements", Long.toString(listPages.getTotalElements()));
    responseHeaders.set("access-control-expose-headers", "totalElements");

    return responseHeaders;
  }

  public URI getUri(String fromHttpUrl, String queryParam, Long id) {
    return ServletUriComponentsBuilder.fromHttpUrl(fromHttpUrl)
        .queryParam(queryParam)
        .buildAndExpand(id)
        .toUri();
  }
}
