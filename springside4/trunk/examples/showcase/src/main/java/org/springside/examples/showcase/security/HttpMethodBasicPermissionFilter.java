package org.springside.examples.showcase.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpMethodBasicPermissionFilter extends PermissionsAuthorizationFilter {
    /**
     * This class's private logger.
     */
    private static final Logger       log               = LoggerFactory
                                                                .getLogger(HttpMethodBasicPermissionFilter.class);

    /**
     * Map that contains a mapping between http methods to permission actions (verbs)
     */
    private final Map<String, String> httpMethodActions = new HashMap<String, String>();

    //Actions representing HTTP Method values (GET -> view, POST -> edit)
    private static final String       VIEW_ACTION       = "view";

    private static final String       EDIT_ACTION       = "edit";

    /**
     * Enum of constants for well-defined mapping values.  Used in the Filter's constructor to perform the map instance
     * used at runtime.
     */
    private static enum HttpMethodAction {

        GET(VIEW_ACTION), POST(EDIT_ACTION);

        private final String action;

        private HttpMethodAction(String action) {
            this.action = action;
        }

        public String getAction() {
            return this.action;
        }
    }

    /**
     * Creates the filter instance with default method-to-action values in the instance's
     * {@link #getHttpMethodActions() http method actions map}.
     */
    public HttpMethodBasicPermissionFilter() {
        for (HttpMethodAction methodAction : HttpMethodAction.values()) {
            httpMethodActions.put(methodAction.name().toLowerCase(), methodAction.getAction());
        }
    }

    /**
     * Returns the HTTP Method name (key) to action verb (value) mapping used to resolve actions based on an
     * incoming {@code HttpServletRequest}.  All keys and values are lower-case.  The
     * default key/value pairs are defined in the top class-level JavaDoc.
     *
     * @return the HTTP Method lower-case name (key) to lower-case action verb (value) mapping
     */
    protected Map<String, String> getHttpMethodActions() {
        return this.httpMethodActions;
    }

    /**
     * Determines the action (verb) attempting to be performed on the filtered resource by the current request.
     * <p/>
     * This implementation expects the incoming request to be an {@link HttpServletRequest} and returns a mapped
     * action based on the HTTP request {@link javax.servlet.http.HttpServletRequest#getMethod() method}.
     *
     * @param request to pull the method from.
     * @return The string equivalent verb of the http method.
     */
    protected String getHttpMethodAction(ServletRequest request) {
        String method = ((HttpServletRequest) request).getMethod();
        return getHttpMethodAction(method);
    }

    /**
     * Determines the corresponding application action that will be performed on the filtered resource based on the
     * specified HTTP method (GET, POST, etc).
     *
     * @param method to be translated into the verb.
     * @return The string equivalent verb of the method.
     */
    protected String getHttpMethodAction(String method) {
        String lc = method.toLowerCase();
        String resolved = getHttpMethodActions().get(lc);
        return resolved != null ? resolved : method;
    }

    /**
     * Returns a collection of String permissions with which to perform a permission check to determine if the filter
     * will allow the request to continue.
     * <p/>
     * This implementation merely delegates to {@link #buildPermissions(String[], String)} and ignores the inbound
     * HTTP servlet request, but it can be overridden by subclasses for more complex request-specific building logic
     * if necessary.
     *
     * @param request         the inbound HTTP request - ignored in this implementation, but available to
     *                        subclasses for more complex construction building logic if necessary
     * @param configuredPerms any url-specific permissions mapped to this filter in the URL rules mappings.
     * @param action          the application-friendly action (verb) resolved based on the HTTP Method name.
     * @return a collection of String permissions with which to perform a permission check to determine if the filter
     *         will allow the request to continue.
     */
    protected String[] buildPermissions(HttpServletRequest request, String[] configuredPerms, String action) {
        return buildPermissions(configuredPerms, action);
    }

    /**
     * Builds a new array of permission strings based on the original argument, appending the specified action verb
     * to each one per {@link org.apache.shiro.authz.permission.WildcardPermission WildcardPermission} conventions.  The
     * built permission strings will be the ones used at runtime during the permission check that determines if filter
     * access should be allowed to continue or not.
     * <p/>
     * For example, if the {@code configuredPerms} argument contains the following 3 permission strings:
     * <p/>
     * <ol>
     * <li>permission:one</li>
     * <li>permission:two</li>
     * <li>permission:three</li>
     * </ol>
     * And the action is {@code read}, then the return value will be:
     * <ol>
     * <li>permission:one:read</li>
     * <li>permission:two:read</li>
     * <li>permission:three:read</li>
     * </ol>
     * per {@link org.apache.shiro.authz.permission.WildcardPermission WildcardPermission} conventions.  Subclasses
     * are of course free to override this method or the
     * {@link #buildPermissions(javax.servlet.http.HttpServletRequest, String[], String) buildPermissions} request
     * variant for custom building logic or with different permission formats.
     *
     * @param configuredPerms list of configuredPerms to be converted.
     * @param action          the resolved action based on the request method to be appended to permission strings.
     * @return an array of permission strings with each element appended with the action.
     */
    protected String[] buildPermissions(String[] configuredPerms, String action) {
        if (configuredPerms == null || configuredPerms.length <= 0 || !StringUtils.hasText(action)) {
            return configuredPerms;
        }

        String[] mappedPerms = new String[configuredPerms.length];

        // loop and append :action
        for (int i = 0; i < configuredPerms.length; i++) {
            mappedPerms[i] = configuredPerms[i] + ":" + action;
        }

        if (log.isTraceEnabled()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mappedPerms.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(mappedPerms[i]);
            }
            log.trace("MAPPED '{}' action to permission(s) '{}'", action, sb);
        }

        return mappedPerms;
    }

    /**
     * Resolves an 'application friendly' action verb based on the {@code HttpServletRequest}'s method, appends that
     * action to each configured permission (the {@code mappedValue} argument is a {@code String[]} array), and
     * delegates the permission check for the newly constructed permission(s) to the superclass
     * {@link PermissionsAuthorizationFilter#isAccessAllowed(javax.servlet.ServletRequest, javax.servlet.ServletResponse, Object) isAccessAllowed}
     * implementation to perform the actual permission check.
     *
     * @param request     the inbound {@code ServletRequest}
     * @param response    the outbound {@code ServletResponse}
     * @param mappedValue the filter-specific config value mapped to this filter in the URL rules mappings.
     * @return {@code true} if the request should proceed through the filter normally, {@code false} if the
     *         request should be processed by this filter's
     *         {@link #onAccessDenied(ServletRequest,ServletResponse,Object)} method instead.
     * @throws IOException
     */
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws IOException {
        String[] perms = (String[]) mappedValue;
        // append the http action to the end of the permissions and then back to super
        String action = getHttpMethodAction(request);
        String[] resolvedPerms = buildPermissions(perms, action);
        return super.isAccessAllowed(request, response, resolvedPerms);
    }
}
