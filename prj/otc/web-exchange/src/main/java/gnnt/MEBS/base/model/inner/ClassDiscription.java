package gnnt.MEBS.base.model.inner;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({java.lang.annotation.ElementType.METHOD})
public @interface ClassDiscription
{
  String name();
  
  boolean key() default false;
  
  boolean keyWord() default false;
  
  boolean isStatus() default false;
  
  String discription() default "no discription";
}
