/*
 * The MIT License
 *
 * Copyright 2014 Sven Varkel <sven@mageflow.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.mageflow.beanstalkd;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import javax.resource.ResourceException;
import javax.resource.spi.ConfigProperty;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;
import javax.security.auth.Subject;
import org.apache.log4j.Logger;

/**
 *
 * @author Sven Varkel <sven@mageflow.com>
 */
public class BeanstalkdManagedConnectionFactory implements ManagedConnectionFactory, ResourceAdapterAssociation {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(BeanstalkdManagedConnectionFactory.class);

    private ResourceAdapter resourceAdapter;

    private PrintWriter logWriter;

    private String hostname;

    private Integer port;

    private Boolean useBlockingIO = false;

    private Boolean useUniqueConnectionPerThread = false;
    private String userName;
    private String password;
    private String user;

    /**
     *
     */
    public BeanstalkdManagedConnectionFactory() {
        this.resourceAdapter = null;
        this.logWriter = null;
    }

    /**
     *
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @param connectionManager
     * @return
     * @throws ResourceException
     */
    @Override
    public Object createConnectionFactory(ConnectionManager connectionManager) throws ResourceException {
        return new BeanstalkdConnectionFactoryImpl(this, connectionManager);
    }

    /**
     *
     * @return
     * @throws ResourceException
     */
    @Override
    public Object createConnectionFactory() throws ResourceException {
        throw new ResourceException("This resource adapter doesn't support non-managed environments");
    }

    /**
     *
     * @param subject
     * @param cxRequestInfo
     * @return
     * @throws ResourceException
     */
    @Override
    public ManagedConnection createManagedConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        return new BeanstalkdManagedConnection(this);
    }

    /**
     *
     * @param connectionSet
     * @param subject
     * @param cxRequestInfo
     * @return
     * @throws ResourceException
     */
    @Override
    public ManagedConnection matchManagedConnections(Set connectionSet, Subject subject,
            ConnectionRequestInfo cxRequestInfo) throws ResourceException {

        ManagedConnection managedConnection = null;

        Iterator it = connectionSet.iterator();
        while (managedConnection == null && it.hasNext()) {
            ManagedConnection mc = (ManagedConnection) it.next();
            if (mc instanceof BeanstalkdManagedConnection) {
                BeanstalkdManagedConnection hwmc = (BeanstalkdManagedConnection) mc;
                managedConnection = hwmc;
            }
        }
        return managedConnection;
    }

    /**
     *
     * @param out
     * @throws ResourceException
     */
    @Override
    public void setLogWriter(PrintWriter out) throws ResourceException {
        this.logWriter = out;
    }

    /**
     *
     * @return
     * @throws ResourceException
     */
    @Override
    public PrintWriter getLogWriter() throws ResourceException {
        return this.logWriter;
    }

    /**
     *
     * @return
     */
    @Override
    public ResourceAdapter getResourceAdapter() {
        return this.resourceAdapter;
    }

    /**
     *
     * @param resourceAdapter
     * @throws ResourceException
     */
    @Override
    public void setResourceAdapter(ResourceAdapter resourceAdapter) throws ResourceException {
        this.resourceAdapter = resourceAdapter;
    }

    @Override
    public int hashCode() {
        int result = 17;
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof BeanstalkdManagedConnectionFactory)) {
            return false;
        }
        BeanstalkdManagedConnectionFactory obj = (BeanstalkdManagedConnectionFactory) other;
        boolean result = true;
        return result;
    }

    /**
     *
     * @return
     */
    public String getHostname() {
        return hostname;
    }

    /**
     *
     * @param hostname
     */
    @ConfigProperty(defaultValue = "127.0.0.1")
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     *
     * @return
     */
    public Integer getPort() {
        return port;
    }

    /**
     *
     * @param port
     */
    @ConfigProperty(defaultValue = "11300")
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     *
     * @return
     */
    public Boolean isUseBlockingIO() {
        return useBlockingIO;
    }

    /**
     *
     * @param useBlockingIO
     */
    @ConfigProperty(defaultValue = "false")
    public void setUseBlockingIO(Boolean useBlockingIO) {
        this.useBlockingIO = useBlockingIO;
    }

    /**
     *
     * @return
     */
    public Boolean isUseUniqueConnectionPerThread() {
        return useUniqueConnectionPerThread;
    }

    /**
     *
     * @param useUniqueConnectionPerThread
     */
    @ConfigProperty(defaultValue = "false")
    public void setUseUniqueConnectionPerThread(Boolean useUniqueConnectionPerThread) {
        this.useUniqueConnectionPerThread = useUniqueConnectionPerThread;
    }

}
