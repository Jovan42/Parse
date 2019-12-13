package app.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SuppressWarnings("rawtypes")
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
  String baseUrl() default "";

  Class type() default Object.class;

  Class arrayType() default Object.class;
}
