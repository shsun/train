package base.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * Created by shsun on 7/13/17.
 */

public class XApplicationEventListener implements ApplicationListener {


    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof ContextClosedEvent) {
            System.out.println(event.getClass().getSimpleName() + " 事件已发生！");



            System.out.println("Oh, what the fuck is going on. the ContextClosedEvent be fired........................");


        } else if (event instanceof ContextRefreshedEvent) {

            System.out.println(event.getClass().getSimpleName() + " 事件已发生！");

        } else if (event instanceof ContextStartedEvent) {

            System.out.println(event.getClass().getSimpleName() + " 事件已发生！");

        } else if (event instanceof ContextStoppedEvent) {

            System.out.println(event.getClass().getSimpleName() + " 事件已发生！");

        } else {

            System.out.println("有其它事件发生:" + event.getClass().getName());
        }
    }

}