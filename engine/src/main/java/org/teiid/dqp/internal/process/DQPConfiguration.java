/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package org.teiid.dqp.internal.process;

import org.jboss.managed.api.annotation.ManagementProperty;
import org.teiid.cache.CacheConfiguration;
import org.teiid.client.RequestMessage;
import org.teiid.core.util.ApplicationInfo;


public class DQPConfiguration{
	
    //Constants
    static final int DEFAULT_FETCH_SIZE = RequestMessage.DEFAULT_FETCH_SIZE * 10;
    static final int DEFAULT_PROCESSOR_TIMESLICE = 2000;
    static final int DEFAULT_MAX_RESULTSET_CACHE_ENTRIES = 1024;
    static final int DEFAULT_QUERY_THRESHOLD = 600;
    static final String PROCESS_PLAN_QUEUE_NAME = "QueryProcessorQueue"; //$NON-NLS-1$
    public static final int DEFAULT_MAX_PROCESS_WORKERS = 64;
	public static final int DEFAULT_MAX_SOURCE_ROWS = -1;
	public static final int DEFAULT_MAX_ACTIVE_PLANS = 20;
    
	private int maxThreads = DEFAULT_MAX_PROCESS_WORKERS;
	private int timeSliceInMilli = DEFAULT_PROCESSOR_TIMESLICE;
	private int maxRowsFetchSize = DEFAULT_FETCH_SIZE;
	private int lobChunkSizeInKB = 100;
	private int preparedPlanCacheMaxCount = SessionAwareCache.DEFAULT_MAX_SIZE_TOTAL;
	private boolean useDataRoles = false;
	private int queryThresholdInSecs = DEFAULT_QUERY_THRESHOLD;
	private boolean exceptionOnMaxSourceRows = true;
	private int maxSourceRows = -1;
	private int maxActivePlans = DEFAULT_MAX_ACTIVE_PLANS;
	private CacheConfiguration resultsetCacheConfig;

	@ManagementProperty(description="Max active plans (default 20).  Increase this value, and max threads, on highly concurrent systems - but ensure that the underlying pools can handle the increased load without timeouts.")
	public int getMaxActivePlans() {
		return maxActivePlans;
	}
	
	public void setMaxActivePlans(int maxActivePlans) {
		this.maxActivePlans = maxActivePlans;
	}
	
	@ManagementProperty(description="Process pool maximum thread count. (default 64)")
	public int getMaxThreads() {
		return maxThreads;
	}

	public void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}

	@ManagementProperty(description="Query processor time slice, in milliseconds. (default 2000)")
	public int getTimeSliceInMilli() {
		return timeSliceInMilli;
	}

	public void setTimeSliceInMilli(int timeSliceInMilli) {
		this.timeSliceInMilli = timeSliceInMilli;
	}
	
	@ManagementProperty(description="Maximum allowed fetch size, set via JDBC. User requested value ignored above this value. (default 20480)")
	public int getMaxRowsFetchSize() {
		return maxRowsFetchSize;
	}

	public void setMaxRowsFetchSize(int maxRowsFetchSize) {
		this.maxRowsFetchSize = maxRowsFetchSize;
	}

	@ManagementProperty(description="The max lob chunk size in KB transferred to the client for xml, blobs, clobs (default 100KB)")
	public int getLobChunkSizeInKB() {
		return this.lobChunkSizeInKB;
	}

	public void setLobChunkSizeInKB(int lobChunkSizeInKB) {
		this.lobChunkSizeInKB = lobChunkSizeInKB;
	}

	@ManagementProperty(description="The maximum number of query plans that are cached. Note: this is a memory based cache. (default 512)")
	public int getPreparedPlanCacheMaxCount() {
		return this.preparedPlanCacheMaxCount;
	}

	public void setPreparedPlanCacheMaxCount(int preparedPlanCacheMaxCount) {
		this.preparedPlanCacheMaxCount = preparedPlanCacheMaxCount;
	}
	
	public CacheConfiguration getResultsetCacheConfig() {
		return this.resultsetCacheConfig;
	}	
	
	public void setResultsetCacheConfig(CacheConfiguration config) {
		this.resultsetCacheConfig = config;
	}
	
	@ManagementProperty(description="Denotes whether or not result set caching is enabled. (default true)")
	public boolean isResultSetCacheEnabled() {
		return this.resultsetCacheConfig != null && this.resultsetCacheConfig.isEnabled();
	}
	
    /**
     * Determine whether role checking is enabled on the server.
     * @return <code>true</code> if server-side role checking is enabled.
     */
    @ManagementProperty(description="Turn on role checking on resources based on the roles defined in VDB")
    public boolean getUseDataRoles() {
        return useDataRoles;
    }

	public void setUseDataRoles(boolean useEntitlements) {
		this.useDataRoles = useEntitlements;
	}

	@ManagementProperty(description="Long running query threshold, after which a alert can be generated by tooling if configured")
	public int getQueryThresholdInSecs() {
		return queryThresholdInSecs;
	}

	public void setQueryThresholdInSecs(int queryThresholdInSecs) {
		this.queryThresholdInSecs = queryThresholdInSecs;
	}
	
	@ManagementProperty(description="Teiid runtime version", readOnly=true)
	public String getRuntimeVersion() {
		return ApplicationInfo.getInstance().getBuildNumber();
	}
	
	/**
	 * Throw exception if there are more rows in the result set than specified in the MaxSourceRows setting.
	 * @return
	 */
	@ManagementProperty(description="Indicates if an exception should be thrown if the specified value for Maximum Source Rows is exceeded; only up to the maximum rows will be consumed.")
	public boolean isExceptionOnMaxSourceRows() {
		return exceptionOnMaxSourceRows;
	}
	
	public void setExceptionOnMaxSourceRows(boolean exceptionOnMaxSourceRows) {
		this.exceptionOnMaxSourceRows = exceptionOnMaxSourceRows;
	}

	/**
	 * Maximum source set rows to fetch
	 * @return
	 */
	@ManagementProperty(description="Maximum rows allowed from a source query. -1 indicates no limit. (default -1)")
	public int getMaxSourceRows() {
		return maxSourceRows;
	}

	public void setMaxSourceRows(int maxSourceRows) {
		this.maxSourceRows = maxSourceRows;
	}
}
