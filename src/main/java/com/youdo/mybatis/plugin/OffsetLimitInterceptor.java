package com.youdo.mybatis.plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.youdo.mybatis.dialect.Dialect;

/**
 * <pre>
 * 		<plugins>
 * 			<plugin interceptor="com.youdo.mybatis.plugin.OffsetLimitInterceptor">
 * 				<property name="dialectClass" value="com.youdo.mybatis.dialect.OracleDialect"/>
 * 			</plugin>
 * 		</plugins>
 * </pre>
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class,
		ResultHandler.class }) })
public abstract class OffsetLimitInterceptor implements Interceptor {
	
	// private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(OffsetLimitInterceptor.class);
	
	private static int MAPPED_STATEMENT_INDEX = 0;
	private static int PARAMETER_INDEX = 1;
	private static int ROWBOUNDS_INDEX = 2;
	private Dialect dialect;
	
	/**
	 * 
	 */
	public Object intercept(Invocation invocation) throws Throwable {
		StringBuilder messageBuilder = new StringBuilder("");
		processIntercept(invocation.getArgs(), messageBuilder);
		Object o = null;
		try {
			o = invocation.proceed();
		} catch (InvocationTargetException ex1) {
			Object parameter = invocation.getArgs()[PARAMETER_INDEX];
			this.onProceedThrowInvocationTargetException(ex1, messageBuilder.toString(), parameter);
		} catch (IllegalAccessException ex2) {
			ex2.getMessage();
		}
		return o;
	}
	
	/**
	 * 
	 * @param exception
	 * @param additionalMessage
	 */
	protected abstract void onProceedThrowInvocationTargetException( InvocationTargetException exception, String additionalMessage, Object parameter );
	
	/**
	 * 
	 * @param queryArgs
	 * @param messageBuilder
	 */
	private void processIntercept(final Object[] queryArgs, StringBuilder messageBuilder) {
		// queryArgs = query(MappedStatement ms, Object parameter, RowBounds
		// rowBounds, ResultHandler resultHandler)
		MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
		Object parameter = queryArgs[PARAMETER_INDEX];
		final RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];
		int offset = rowBounds.getOffset();
		int limit = rowBounds.getLimit();
		// 
		BoundSql boundSql = ms.getBoundSql(parameter);
		String sql = boundSql.getSql().trim();
		if (dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
			if (dialect.supportsLimitOffset()) {
				sql = dialect.getLimitString(sql, offset, limit);
				offset = RowBounds.NO_ROW_OFFSET;
			} else {
				sql = dialect.getLimitString(sql, 0, limit);
			}
			limit = RowBounds.NO_ROW_LIMIT;
			queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
			BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
			MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
			queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
		}
		messageBuilder.append(sql);
		// 
		this.onAfterProcessIntercept(parameter, sql);
	}
	
	protected abstract void onAfterProcessIntercept( Object parameter, String sql );
	
	// see: MapperBuilderAssistant
	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		//builder.keyProperty(ms.getKeyProperty());
		if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
			builder.keyProperty(ms.getKeyProperties()[0]);
		}
		
		// setStatementTimeout()
		builder.timeout(ms.getTimeout());

		// setStatementResultMap()
		builder.parameterMap(ms.getParameterMap());

		// setStatementResultMap()
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());

		// setStatementCache()
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		String dialectClass = properties.getProperty("dialectClass");
		try {
			dialect = (Dialect) Class.forName(dialectClass).newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException("cannot create dialect instance by dialectClass:" + dialectClass, e);
		}
	}

	public static class BoundSqlSqlSource implements SqlSource {

		private BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}
}