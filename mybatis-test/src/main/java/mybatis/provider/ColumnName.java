package mybatis.provider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * ��ColumnName.java��ʵ��������ӳ������ݿ��ֶ���������Ĭ���շ�ת�»���
 * ������QueryParam�ϣ�Ҳ������DOģ����
 * ���ջᷴ�����Զ�ƴװ����ɾ�Ĳ�sql�ϣ��Լ���ѯ���ӳ����
 * @author yilun.fyl 2017��1��20�� ����5:01:50
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ColumnName {
	String value() default "";
	/**
	 * ���valueֵ����IGNORE��ֵ����������field�������κ�sqlƴװ
	 */
	public static final String IGNORE="��(�R���Q)��-EMPTY-��(�R���Q)��";
}
