package base.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author shsun
 * 
 */
public class XSpringApplicationContextHolder implements ApplicationContextAware, DisposableBean {
	
	// static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(XSpringApplicationContextHolder.class);

	private static ApplicationContext theApplicationContext;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		XSpringApplicationContextHolder.theApplicationContext = context;
		// logger.info("setApplicationContext");
	}
	
	public static ApplicationContext getApplicationContext() {
		return theApplicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) theApplicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> cls) {
		return (T) theApplicationContext.getBean(cls);
	}

	@Override
	public void destroy() throws Exception {
		XSpringApplicationContextHolder.cleanApplicationContext();
	}

	
	public static void cleanApplicationContext() {
		theApplicationContext = null;
	}
}
