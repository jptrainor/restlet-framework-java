/**
 * Copyright 2005-2014 Restlet
 * 
 * The contents of this file are subject to the terms of one of the following
 * open source licenses: Apache 2.0 or LGPL 3.0 or LGPL 2.1 or CDDL 1.0 or EPL
 * 1.0 (the "Licenses"). You can select the license that you prefer but you may
 * not use this file except in compliance with one of these Licenses.
 * 
 * You can obtain a copy of the Apache 2.0 license at
 * http://www.opensource.org/licenses/apache-2.0
 * 
 * You can obtain a copy of the LGPL 3.0 license at
 * http://www.opensource.org/licenses/lgpl-3.0
 * 
 * You can obtain a copy of the LGPL 2.1 license at
 * http://www.opensource.org/licenses/lgpl-2.1
 * 
 * You can obtain a copy of the CDDL 1.0 license at
 * http://www.opensource.org/licenses/cddl1
 * 
 * You can obtain a copy of the EPL 1.0 license at
 * http://www.opensource.org/licenses/eclipse-1.0
 * 
 * See the Licenses for the specific language governing permissions and
 * limitations under the Licenses.
 * 
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly at
 * http://restlet.com/products/restlet-framework
 * 
 * Restlet is a registered trademark of Restlet S.A.S.
 */

package org.restlet.ext.apispark.internal.introspection;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.engine.util.StringUtils;
import org.restlet.ext.apispark.internal.model.Contract;
import org.restlet.ext.apispark.internal.model.Definition;
import org.restlet.ext.apispark.internal.model.Endpoint;
import org.restlet.ext.apispark.internal.utils.IntrospectionUtils;
import org.restlet.routing.VirtualHost;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Publish the documentation of a Restlet-based Application to the APISpark
 * console.
 * 
 * @author Thierry Boileau
 */
public class ApplicationIntrospector extends IntrospectionUtils {

    /** Internal logger. */
    protected static Logger LOGGER = Logger.getLogger(ApplicationIntrospector.class
            .getName());

    /**
     * Returns an instance of what must be a subclass of {@link org.restlet.Application}.
     * Returns null in case of errors.
     *
     * @param className
     *            The name of the application class.
     * @return An instance of what must be a subclass of {@link org.restlet.Application}.
     */
    public static Application getApplication(String className) {
        if (className == null) {
            return null;
        }

        try {
            Class<?> clazz = Class.forName(className);
            if (Application.class.isAssignableFrom(clazz)) {
                return (Application) clazz.getConstructor().newInstance();
            } else {
                throw new RuntimeException(className
                        + " does not seem to be a valid subclass of "
                        + Application.class.getName() + " class.");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot locate the definition source.", e);
        } catch (Exception e) {
            throw new RuntimeException("Cannot instantiate the application class. " +
                    "Check that the application class has an empty constructor.", e);
        }
    }

    /**
     * Constructor.
     *
     * @param application
     *            An application to introspect.
     */
    public static Definition getDefinition(Application application) {
        return getDefinition(application, null, null);
    }

    /**
     * Constructor.
     *
     * @param application
     *            An application to introspect.
     */
    public static Definition getDefinition(
            Application application,
            Reference baseRef) {

        return getDefinition(application, baseRef, null);
    }

    /**
     * Returns a APISpark description of the current application. By default,
     * this method discovers all the resources attached to this application. It
     * can be overridden to add documentation, list of representations, etc.
     *
     * @param application
     *            An application to introspect.
     * @param component
     *            An component to introspect in order to get extra details such
     *            as the endpoint.
     *
     * @return An application description.
     */
    public static Definition getDefinition(
            Application application,
            Reference baseRef,
            Component component) {

        Definition definition = new Definition();

        //Contract
        Contract contract = new Contract();
        contract.setDescription(StringUtils.nullToEmpty(application.getDescription()));
        if (StringUtils.isNullOrEmpty(application.getName())) {
            LOGGER.log(Level.WARNING,
                    "Please provide a name to your application, used "
                            + contract.getName() + " by default.");
            contract.setName(application.getClass().getName());
        } else {
            contract.setName(application.getName());
        }
        definition.setContract(contract);

        //Go through restlet nodes to collect resources, representations and schemes
        CollectInfo collectInfo = new CollectInfo();
        RestletCollector.collect(
                collectInfo /* resources are added during collect*/,
                "" /* start path is empty */,
                application.getInboundRoot(),
                null /* there is no challenge scheme yet */);

        //add resources
        contract.setResources(collectInfo.getResources());
        //add representations
        contract.setRepresentations(collectInfo.getRepresentations());


        //todo add protocols ??
//        java.util.List<String> protocols = new ArrayList<String>();
//        for (ConnectorHelper<Server> helper : Engine.getInstance()
//                .getRegisteredServers()) {
//            for (Protocol protocol : helper.getProtocols()) {
//                if (!protocols.contains(protocol.getName())) {
//                    LOGGER.fine("Protocol " + protocol.getName()
//                            + " added.");
//                    protocols.add(protocol.getName());
//                }
//            }
//        }

        //Introspect component if any
        if (component != null) {
            LOGGER.fine("Look for the endpoint.");
            // Look for the endpoint to which this application is attached.
            Endpoint endpoint = ComponentIntrospector.getEndpoint(component.getDefaultHost(), application);
            if (endpoint != null) {
                definition.getEndpoints().add(endpoint);
            }
            for (VirtualHost virtualHost : component.getHosts()) {
                endpoint = ComponentIntrospector.getEndpoint(virtualHost, application);
                if (endpoint != null) {
                    definition.getEndpoints().add(endpoint);
                }
            }
        } else {
            String scheme =
                    collectInfo.getSchemes().isEmpty() ?
                            null :
                            collectInfo.getSchemes().get(0).getName();
            Endpoint endpoint = new Endpoint("example.com",
                    80, Protocol.HTTP.getSchemeName(), "/v1", scheme);
            definition.getEndpoints().add(endpoint);
        }

        //todo not add sections

        IntrospectionUtils.sortDefinition(definition);
        return definition;
    }
}
