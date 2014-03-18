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
import com.surftools.BeanstalkClientImpl.ClientImpl;
import java.net.Socket;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 *
 * @author Sven Varkel <sven@mageflow.com>
 */
public class BeanstalkdConnectionImpl extends ClientImpl implements BeanstalkdConnection {

    /**
     * The logger
     */
    private static final Logger LOG = Logger.getLogger(BeanstalkdConnectionImpl.class);

    /**
     * ManagedConnection
     */
    private BeanstalkdManagedConnection mc;

    /**
     * ManagedConnectionFactory
     */
    private BeanstalkdManagedConnectionFactory mcf;
    Socket socket = null;
    private Integer connectionId = 0;

    /**
     *
     * @param mc
     * @param mcf
     */
    public BeanstalkdConnectionImpl(BeanstalkdManagedConnection mc, BeanstalkdManagedConnectionFactory mcf) {
        super(mcf.getHostname(), mcf.getPort(), mcf.isUseBlockingIO());
        this.setUniqueConnectionPerThread(mcf.isUseUniqueConnectionPerThread());
        this.mc = mc;
        this.mcf = mcf;
        Random r = new Random();
        connectionId = r.nextInt((9999 - 1000) + 1) + 1000;
        LOG.debug(String.format("Created connection #%s", connectionId));
    }

    @Override
    public void close() {
        if (mc != null) {
            mc.closeHandle(this);
        }
        try {
            if (this.socket != null) {
                this.socket.close();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public Socket getSocket() {
        return this.socket;
    }

    @Override
    public void setSocket(Socket socket
    ) {
        this.socket = socket;
    }

    @Override
    public Integer getConnectionId() {
        return connectionId;
    }

    @Override
    public void setManagedConnection(Object c
    ) {
        this.mc = (BeanstalkdManagedConnection) c;
    }

}
