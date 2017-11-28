package org.orh.javabeans;

import org.orh.javabeans.property.editor.DatePropertyEditor;
import org.orh.javabeans.property.editor.IdPropertyEditor;

import java.beans.*;
import java.lang.reflect.Method;
import java.util.Arrays;

public class JavaBeansInfo {
    public static void main(String[] args) throws IntrospectionException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        // Object.class Stop Class

        Class<?> beanClass = Class.forName("org.orh.javabeans.bean.User");
        BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
//        BeanInfo beanInfo = Introspector.getBeanInfo(User.class);
//        BeanInfo beanInfo = Introspector.getBeanInfo(User.class, Object.class);
        System.out.println(beanInfo.toString());

        // Bean描述符
        BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
        System.out.println(beanDescriptor);

        // 方法描述信息
        MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
        Arrays.stream(methodDescriptors).forEach(System.out::println);

        // 属性描述符
        // readMethod、writeMethod 读方法、写方法， 不一定需要成对出现
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Arrays.stream(propertyDescriptors).forEach(System.out::println);

        // 创建 User bean
//        User user = new User();
        Object bean = beanClass.newInstance();

        Arrays.stream(propertyDescriptors).forEach(propertyDescriptor -> {
            String propertyName = propertyDescriptor.getName();

            if ("id".equals(propertyName)) { // 属性名称等于 “id”
                propertyDescriptor.setPropertyEditorClass(IdPropertyEditor.class);

                PropertyEditor propertyEditor = propertyDescriptor.createPropertyEditor(bean); //对某一个bean 做相应调整
                propertyEditor.addPropertyChangeListener(new SetPropertyChangeListener(bean, propertyDescriptor.getWriteMethod()));
                propertyEditor.setAsText("12");
            } else if ("date".equals(propertyName)) {
                propertyDescriptor.setPropertyEditorClass(DatePropertyEditor.class);
                PropertyEditor propertyEditor = propertyDescriptor.createPropertyEditor(bean); //对某一个bean 做相应调整
                propertyEditor.addPropertyChangeListener(new SetPropertyChangeListener(bean, propertyDescriptor.getWriteMethod()));
                propertyEditor.setAsText("2017-11-28");
            }
        });

        System.out.println(bean);

    }

    private static class SetPropertyChangeListener implements PropertyChangeListener {
        private final Method setterMethod;
        private final Object bean;

        private SetPropertyChangeListener(Object instance, Method setterMethod) {
            this.setterMethod = setterMethod;
            this.bean = instance;
        }

        @Override
        public void propertyChange(PropertyChangeEvent event) {
            PropertyEditor source = (PropertyEditor) event.getSource();

            Object newValue = event.getNewValue();
            try {
                setterMethod.invoke(bean, source.getValue());
            } catch (Exception e) {
            }
        }
    }
}
