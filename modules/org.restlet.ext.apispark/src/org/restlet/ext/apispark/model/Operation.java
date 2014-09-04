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

package org.restlet.ext.apispark.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Cyprien Quilici
 */
public class Operation {

    /** Mediatypes consumed by this operation */
    private List<String> consumes;

    /** Textual description of this operation. */
    private String description;

    /** Headers to use for this operation. */
    private List<Header> headers;

    /** Representation retrieved by this operation if any. */
    private Entity inRepresentation;

    /** HTTP method for this operation. */
    private String method;

    /**
     * Unique name for this operation<br>
     * Note: will be used for client SDK generation in the future.
     */
    private String name;

    /**
     * Representation to send in the body of your request for this operation if
     * any.
     */
    private Entity outRepresentation;

    /** Mediatypes produced by this operation */
    private List<String> produces;

    /** Query parameters available for this operation. */
    private List<QueryParameter> queryParameters;

    /** Possible response messages you could encounter. */
    private List<Response> responses;

    public List<String> getConsumes() {
        if (consumes == null) {
            consumes = new ArrayList<String>();
        }
        return consumes;
    }

    public String getDescription() {
        return description;
    }

    public List<Header> getHeaders() {
        if (headers == null) {
            headers = new ArrayList<Header>();
        }
        return headers;
    }

    public Entity getInRepresentation() {
        return inRepresentation;
    }

    public String getMethod() {
        return method;
    }

    public String getName() {
        return name;
    }

    public Entity getOutRepresentation() {
        return outRepresentation;
    }

    public List<String> getProduces() {
        if (produces == null) {
            produces = new ArrayList<String>();
        }
        return produces;
    }

    public QueryParameter getQueryParameter(String name) {
        for (QueryParameter result : getQueryParameters()) {
            if (name.equals(result.getName())) {
                return result;
            }
        }
        return null;
    }

    public List<QueryParameter> getQueryParameters() {
        if (queryParameters == null) {
            queryParameters = new ArrayList<QueryParameter>();
        }
        return queryParameters;
    }

    public Response getResponse(int code) {
        for (Response result : getResponses()) {
            if (code == result.getCode()) {
                return result;
            }
        }
        return null;
    }

    public List<Response> getResponses() {
        if (responses == null) {
            responses = new ArrayList<Response>();
        }
        return responses;
    }

    public void setConsumes(List<String> consumes) {
        this.consumes = consumes;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public void setInRepresentation(Entity inRepresentation) {
        this.inRepresentation = inRepresentation;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOutRepresentation(Entity outRepresentation) {
        this.outRepresentation = outRepresentation;
    }

    public void setProduces(List<String> produces) {
        this.produces = produces;
    }

    public void setQueryParameters(List<QueryParameter> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }
}
