package com.venkatyarlagadda.SpringDataSplitAccess.routing;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.venkatyarlagadda.SpringDataSplitAccess.enums.Route;

/**
 * 
 * @author Venkat Yarlagadda
 * @version V1
 *
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

	private static final ThreadLocal<Route> routeContext = new ThreadLocal<>();

	@Override
	protected Object determineCurrentLookupKey() {
		return routeContext.get();
	}

	public static void clearReplicaRoute() {
		routeContext.remove();
	}
	
	public static void setReplicaRoute() {
		routeContext.set(Route.REPLICA);
	}
}
