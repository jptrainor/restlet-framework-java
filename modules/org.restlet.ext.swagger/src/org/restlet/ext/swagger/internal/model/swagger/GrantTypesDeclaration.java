package org.restlet.ext.swagger.internal.model.swagger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class GrantTypesDeclaration {

    private ImplicitDeclaration implicit;

    private AuthorizationCodeDeclaration authorization_code;

    public ImplicitDeclaration getImplicit() {
        return implicit;
    }

    public void setImplicit(ImplicitDeclaration implicit) {
        this.implicit = implicit;
    }

    public AuthorizationCodeDeclaration getAuthorization_code() {
        return authorization_code;
    }

    public void setAuthorization_code(
            AuthorizationCodeDeclaration authorization_code) {
        this.authorization_code = authorization_code;
    }
}
