/*******************************************************************************
 * Copyright (c) 2010 JVM Monitor project. All rights reserved. 
 * 
 * This code is distributed under the terms of the Eclipse Public License v1.0
 * which is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.jboss.tools.jmx.jvmmonitor.core;

/**
 * JVM attach handler. A client plug-in that contributes to the extension point
 * <tt>org.jboss.tools.jmx.jvmmonitor.core.jvmAttachHandler</tt> will implement this interface.
 */
public interface IJvmAttachHandler {

    /**
     * Sets the local host.
     * 
     * @param localhost
     *            The local host
     */
    void setHost(IHost localhost);

    /**
     * Gets the state indicating if valid JDK is available on local host in a
     * sense that JDK has tools.jar at expected location.
     * 
     * @return <tt>true</tt> if valid JDK is available
     */
    boolean hasValidJdk();
    
    
    /**
     * Alerts the attach handler to begin polling for VMs
     */
    void beginPolling();
    
    
    /**
     * Alerts the attach handler to suspend polling for VMs
     */
    void suspendPolling();
    
    
    /**
     * Get the current polling state
     * @return true if currently polling, false otherwise
     */
    boolean isPolling();
    
    /**
     * Update the list of active JVMs. 
     * @throws JvmCoreException
     */
    void refreshJVMs() throws JvmCoreException;
    
    /**
     * This method will invoke an attachment of the agent to the jvm 
     * in order to discover the vm's jmx url. 
     * 
     * @param monitoredVm
     * @param pid
     * @return
     * @throws JvmCoreException
     */
    public String getLocalConnectorAddress(Object monitoredVm, int pid) throws JvmCoreException;

}
