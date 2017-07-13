package base.listener;

import javax.servlet.ServletContextEvent;

/**
 * 
 */
public class XBeforeContextLoaderListener implements javax.servlet.ServletContextListener {

    /**
     * @param event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {

        Thread shutdownThread = new Thread() {
            public void run() {

                System.out.println("Oh, what the fuck is going on. the jvm is shutdown........................");
            }
        };

        Runtime.getRuntime().addShutdownHook(shutdownThread);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Oh, what the fuck is going on. the XBeforeContextLoaderListener.contextDestroyed be fired........................");
    }

}
