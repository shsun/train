package com.demo.bootstrap.aop;

import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

// import com.youdo.log4j.YouDoLevel;
import com.youdo.spring.beans.factory.config.CustomizedPropertyPlaceholderConfigurer;
//import com.yk.addata.adstat2.support.vo.ErrorDescriptor;
//import com.yk.addata.adstat2.vo.base.ApplicationSharedObject;
//import com.yk.addata.adstat2.vo.base.ISqlParameterSource;

/**
 * 
 * @author shsun
 * 
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }) })
public class OffsetLimitInterceptor extends com.youdo.mybatis.plugin.OffsetLimitInterceptor {

	private static final String RELEASE_MODE = "release";

	// static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(OffsetLimitInterceptor.class);

	@Autowired
	private CustomizedPropertyPlaceholderConfigurer propertyConfigurer;

	/**
	 * 
	 */
	@Override
	protected void onAfterProcessIntercept(Object parameter, String sql) {
		try {
			// logger.debug(sql);
			// logger.log(YouDoLevel.SQL, sql);
			// ((ISqlParameterSource) parameter).setSQL(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@SuppressWarnings("static-access")
	@Override
	protected void onProceedThrowInvocationTargetException(InvocationTargetException exception,
			String additionalMessage, Object parameter) {
		String hostInfo = "";
		try {
			hostInfo = String.valueOf(InetAddress.getLocalHost());
		} catch (Exception e) {
			hostInfo = "";
		}
		String message = hostInfo + "\n\n\n" + exception.getCause().getMessage() + "\n\n\n" + additionalMessage;

		// send warning with email under release mode
		if (RELEASE_MODE.equals(this.propertyConfigurer.getContextProperty("system.mode"))) {
			// ApplicationSharedObject.getInstance().sendSQLExceptionMail(message);
		}

		// error code
		message = "数据库查询缓慢，请稍候再尝试";
		// ((ISqlParameterSource) parameter).getHttpServletRequestParameter()
		// .setErrorDescriptor(new ErrorDescriptor("1", message));

	}
}
