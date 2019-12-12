package app.exceptions.handlers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorMessage {
  private Integer statusCode;
  private String message;
  private String url;
  private Long timestamp;
  private String description;
}
