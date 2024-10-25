/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.struts2.components;

import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * <!-- START SNIPPET: javadoc -->
 *
 * <p>This tag is used to create a URL.</p>
 *
 * <p>You can use the &lt;param&gt; tag inside the body to provide
 * additional request parameters. If the value of a param is an Array or
 * an Iterable all the values will be added to the URL.</p>
 *
 * <b>NOTE:</b>
 * <p>By default request parameters will be separated using escaped ampersands (i.e., &amp;amp;).
 * This is necessary for XHTML compliance, however, when using the URL generated by this tag
 * with the &lt;s:property&gt; tag, the <b>escapeAmp</b> attribute should be used to disable
 * ampersand escaping.</p>
 *
 * <b>NOTE:</b>
 * <p>When includeParams is 'all' or 'get', the parameter defined in a &lt;param&gt;
 * tag will take precedence over any params included due to the includeParams attribute. For
 * example, in Example 3 below, if there is a id parameter in the url where the page this
 * tag is included like http://&lt;host&gt;:&lt;port&gt;/&lt;context&gt;/editUser.action?id=3333&amp;name=John
 * the generated url will be http://&lt;host&gt;:&lt;port&gt;/&lt;context&gt;/editUser.action?id=22&amp;name=John
 * because the parameter defined in the param tag will take precedence.</p>
 *
 * <!-- END SNIPPET: javadoc -->
 *
 *
 * <!-- START SNIPPET: params -->
 *
 * <ul>
 *      <li>action (String) - (value or action choose either one, if both exist value takes precedence) action's name (alias) </li>
 *      <li>value (String) - (value or action choose either one, if both exist value takes precedence) the url itself</li>
 *      <li>scheme (String) - http scheme (http, https) defaults to the scheme this request is in</li>
 *      <li>namespace - action's namespace</li>
 *      <li>method (String) - action's method name, defaults to 'execute'</li>
 *      <li>encode (Boolean) - url encode the generated url. Defaults to 'true'.</li>
 *      <li>includeParams (String) - The includeParams attribute may have the value 'none', 'get' or 'all'. Defaults to 'none'.
 *                                   none - include no parameters in the URL (default)
 *                                   get  - include only GET parameters in the URL
 *                                   all  - include both GET and POST parameters in the URL
 *      </li>
 *      <li>includeContext (Boolean) - Specifies whether to include the web app context path. Defaults to 'true'.</li>
 *      <li>escapeAmp (Boolean) - Specifies whether to escape ampersand (&amp;) to (&amp;amp;) or not. Defaults to 'true'.</li>
 *      <li>portletMode (String) - The resulting portlet mode.</li>
 *      <li>windowState (String) - The resulting portlet window state.</li>
 *      <li>portletUrlType (String) - Specifies if this should be a portlet render or action URL.</li>
 *      <li>forceAddSchemeHostAndPort (Boolean) - Specifies whether to force the addition of scheme, host and port or not.</li>
 * </ul>
 *
 * <!-- END SNIPPET: params -->
 *
 * <p><b>Examples</b></p>
 * <pre>
 * <!-- START SNIPPET: example -->
 *
 * &lt;-- Example 1 --&gt;
 * &lt;s:url value="editGadget.action"&gt;
 *     &lt;s:param name="id" value="%{selected}" /&gt;
 * &lt;/s:url&gt;
 *
 * &lt;-- Example 2 --&gt;
 * &lt;s:url action="editGadget"&gt;
 *     &lt;s:param name="id" value="%{selected}" /&gt;
 * &lt;/s:url&gt;
 *
 * &lt;-- Example 3--&gt;
 * &lt;s:url includeParams="get"&gt;
 *     &lt;s:param name="id" value="%{'22'}" /&gt;
 * &lt;/s:url&gt;
 *
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @see Param
 *
 */
@StrutsTag(name="url", tldTagClass="org.apache.struts2.views.jsp.URLTag", description="This tag is used to create a URL")
public class URL extends ContextBean {

    private UrlProvider urlProvider;
    private UrlRenderer urlRenderer;

    public URL(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        super(stack);
        urlProvider = new ComponentUrlProvider(this, this.attributes);
        urlProvider.setHttpServletRequest(req);
        urlProvider.setHttpServletResponse(res);
    }

    @Inject(StrutsConstants.STRUTS_URL_INCLUDEPARAMS)
    public void setUrlIncludeParams(String urlIncludeParams) {
       urlProvider.setUrlIncludeParams(urlIncludeParams);
    }

    @Inject
	public void setUrlRenderer(UrlRenderer urlRenderer) {
		urlProvider.setUrlRenderer(urlRenderer);
        this.urlRenderer = urlRenderer;
	}

    @Inject(required=false)
    public void setExtraParameterProvider(ExtraParameterProvider provider) {
        urlProvider.setExtraParameterProvider(provider);
    }

    public boolean start(Writer writer) {
        boolean result = super.start(writer);
        urlRenderer.beforeRenderUrl(urlProvider);
        return result;
    }

    public boolean end(Writer writer, String body) {
    	urlRenderer.renderUrl(writer, urlProvider);
        return super.end(writer, body);
    }

    public String findString(String expr) {
        return super.findString(expr);
    }

    public UrlProvider getUrlProvider() {
        return urlProvider;
    }

    @StrutsTagAttribute(description="The includeParams attribute may have the value 'none', 'get' or 'all'", defaultValue="none")
    public void setIncludeParams(String includeParams) {
        urlProvider.setIncludeParams(includeParams);
    }

    @StrutsTagAttribute(description="Set scheme attribute")
    public void setScheme(String scheme) {
        urlProvider.setScheme(scheme);
    }

    @StrutsTagAttribute(description="The target value to use, if not using action")
    public void setValue(String value) {
        urlProvider.setValue(value);
    }

    @StrutsTagAttribute(description="The action to generate the URL for, if not using value")
    public void setAction(String action) {
        urlProvider.setAction(action);
    }

    @StrutsTagAttribute(description="The namespace to use")
    public void setNamespace(String namespace) {
        urlProvider.setNamespace(namespace);
    }

    @StrutsTagAttribute(description="The method of action to use")
    public void setMethod(String method) {
        urlProvider.setMethod(method);
    }

    @StrutsTagAttribute(description="Whether to encode parameters", type="Boolean", defaultValue="true")
    public void setEncode(boolean encode) {
        urlProvider.setEncode(encode);
    }

    @StrutsTagAttribute(description="Whether actual context should be included in URL", type="Boolean", defaultValue="true")
    public void setIncludeContext(boolean includeContext) {
        urlProvider.setIncludeContext(includeContext);
    }

    @StrutsTagAttribute(description="The resulting portlet mode")
    public void setPortletMode(String portletMode) {
        urlProvider.setPortletMode(portletMode);
    }

    @StrutsTagAttribute(description="The resulting portlet window state")
    public void setWindowState(String windowState) {
        urlProvider.setWindowState(windowState);
    }

    @StrutsTagAttribute(description="Specifies if this should be a portlet render or action URL. Default is \"render\". To create an action URL, use \"action\".")
    public void setPortletUrlType(String portletUrlType) {
       urlProvider.setPortletUrlType(portletUrlType);
    }

    @StrutsTagAttribute(description="The anchor for this URL")
    public void setAnchor(String anchor) {
        urlProvider.setAnchor(anchor);
    }

    @StrutsTagAttribute(description="Specifies whether to escape ampersand (&amp;) to (&amp;amp;) or not", type="Boolean", defaultValue="true")
    public void setEscapeAmp(boolean escapeAmp) {
        urlProvider.setEscapeAmp(escapeAmp);
    }

    @StrutsTagAttribute(description="Specifies whether to force the addition of scheme, host and port or not", type="Boolean", defaultValue="false")
    public void setForceAddSchemeHostAndPort(boolean forceAddSchemeHostAndPort) {
        urlProvider.setForceAddSchemeHostAndPort(forceAddSchemeHostAndPort);
    }
}
