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

import com.mageflow.beanstalkd.interfaces.BeanstalkdConnection;
import com.mageflow.beanstalkd.interfaces.BeanstalkdConnectionFactory;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.AuthenticationMechanism;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ConfigProperty;
import javax.resource.spi.ConnectionDefinition;
import javax.resource.spi.Connector;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.TransactionSupport;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;
import org.apache.log4j.Logger;

/**
 *
 * @author sven
 */
@Connector(
        reauthenticationSupport = false,
        transactionSupport = TransactionSupport.TransactionSupportLevel.NoTransaction,
        displayName = "BeanstalkdConnector",
        vendorName = "MageFlow",
        version = "1.0",
        authMechanisms = @AuthenticationMechanism(
                authMechanism = "BasicPassword",
                credentialInterface = AuthenticationMechanism.CredentialInterface.PasswordCredential)
)
@ConnectionDefinition(
        connection = BeanstalkdConnection.class,
        connectionFactory = BeanstalkdConnectionFactory.class,
        connectionImpl = BeanstalkdConnectionImpl.class,
        connectionFactoryImpl = BeanstalkdConnectionFactoryImpl.class
)
public class BeanstalkdConnector implements ResourceAdapter {

    /**
     * The logger
     */
    private static Logger LOG = Logger.getLogger(BeanstalkdConnector.class);

    /**
     * Name property
     */
    @ConfigProperty(defaultValue = "BeanstalkdConnector", supportsDynamicUpdates = true)
    private String name;

    /**
     *
     * Default constructor
     *
     */
    public BeanstalkdConnector() {

    }

    /**
     *
     * Set name
     *
     * @param name The value
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * *
     * Get name
     *
     * @return The value
     *
     */
    public String getName() {

        return name;

    }

    /**
     *
     * This is called during the activation of a message endpoint.
     *
     *
     *
     * @param endpointFactory A message endpoint factory instance.
     *
     * @param spec An activation spec JavaBean instance.
     *
     * @throws ResourceException generic exception *
     */
    public void endpointActivation(MessageEndpointFactory endpointFactory,
            ActivationSpec spec) throws ResourceException {

    }

    /**
     *
     * This is called when a message endpoint is deactivated. *
     *
     *
     * @param endpointFactory A message endpoint factory instance.
     *
     * @param spec An activation spec JavaBean instance.
     *
     */
    public void endpointDeactivation(MessageEndpointFactory endpointFactory,
            ActivationSpec spec) {

    }

    /**
     *
     * This is called when a resource adapter instance is bootstrapped.
     *
     *
     *
     * @param ctx A bootstrap context containing references
     *
     * @throws ResourceAdapterInternalException indicates bootstrap failure.
     *
     */
    public void start(BootstrapContext ctx)
            throws ResourceAdapterInternalException {
        LOG.info("Resource Adapter bootstrap!");
    }

    /**
     *
     * This is called when a resource adapter instance is undeployed or
     *
     * during application server shutdown. *
     */
    public void stop() {
        LOG.info("Resource adapter shutdown!");
    }

    /**
     *
     * This method is called by the application server during crash recovery.
     *
     *
     *
     * @param specs an array of ActivationSpec JavaBeans
     *
     * @throws ResourceException generic exception
     *
     * @return an array of XAResource objects
     *
     */
    public XAResource[] getXAResources(ActivationSpec[] specs)
            throws ResourceException {

        return null;

    }

    /**
     * *
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     *
     */
    @Override

    public int hashCode() {

        int result = 17;

        if (name != null) {
            result += 31 * result + 7 * name.hashCode();
        } else {
            result += 31 * result + 7;
        }

        return result;

    }

    @Override
    public boolean equals(Object o) {

        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (!(o instanceof BeanstalkdConnector)) {
            return false;
        }

        BeanstalkdConnector obj = (BeanstalkdConnector) o;

        boolean result = true;

        if (result) {

            if (name == null) {
                result = obj.getName() == null;
            } else {
                result = name.equals(obj.getName());
            }
        }
        return result;
    }
}
