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

    /**
     *
     * Indicates whether some other object is equal to this one.
     *
     * @param other The reference object with which to compare.
     *
     * @return true If this object is the same as the obj argument, false otherwise.
     *
     */
    @Override

    public boolean equals(Object other) {

        if (other == null) {
            return false;
        }

        if (other == this) {
            return true;
        }

        if (!(other instanceof BeanstalkdConnector)) {
            return false;
        }

        BeanstalkdConnector obj = (BeanstalkdConnector) other;

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
