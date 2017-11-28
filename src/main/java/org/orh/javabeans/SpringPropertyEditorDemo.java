package org.orh.javabeans;

import org.orh.javabeans.bean.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringPropertyEditorDemo {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        context.setConfigLocation("context.xml");
        context.refresh();

        User user = context.getBean(User.class);
        System.out.println(user);
    }
}
