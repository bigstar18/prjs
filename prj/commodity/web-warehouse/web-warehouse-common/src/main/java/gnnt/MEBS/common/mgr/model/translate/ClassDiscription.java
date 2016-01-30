package gnnt.MEBS.common.mgr.model.translate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主要是对方法及属性的描述
 * @author Administrator
 *
 */
@Target(ElementType.FIELD)            // 域声明 : 表示注解用在什么地方
@Retention(RetentionPolicy.RUNTIME)   // 在 JVM 运行期也保留注解 : 表示注解的级别
@Documented                           // 将此注解包含在 javadoc 中
@Inherited							// 允许子类继承父类中的注解 
public @interface ClassDiscription {
	//名称
	String name();
	//描述
	String description() default "";
}
