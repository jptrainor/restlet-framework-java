package org.restlet.ext.swagger.internal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class BasicAuthorizationDeclaration extends AuthorizationDeclaration {

    public BasicAuthorizationDeclaration() {
        this.setType(AuthorizationType.BASIC_AUTH);
    }
}
