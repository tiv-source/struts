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
package org.apache.struts2.interceptor;

import org.apache.struts2.ActionContext;
import org.apache.struts2.ActionInvocation;
import org.apache.struts2.ActionSupport;
import junit.framework.TestCase;
import org.apache.struts2.dispatcher.HttpParameters;

import java.io.Serial;
import java.util.LinkedHashMap;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

public class ParameterRemoverInterceptorTest extends TestCase {

    private ActionContext context;
    private ActionInvocation actionInvocation;

    @Override
    protected void setUp() throws Exception {
        context = ActionContext.of();
        actionInvocation = createMock(ActionInvocation.class);
        expect(actionInvocation.getAction()).andStubReturn(new SampleAction());
        expect(actionInvocation.getInvocationContext()).andStubReturn(context);
        expect(actionInvocation.invoke()).andStubReturn("success");
    }

    public void testInterception1() throws Exception {
        context.withParameters(HttpParameters.create(new LinkedHashMap<String, Object>() {
            {
                put("param1", new String[]{"paramValue1"});
                put("param2", new String[]{"paramValue2"});
                put("param3", new String[]{"paramValue3"});
                put("param", new String[]{"paramValue"});
            }
        }).build());

        replay(actionInvocation);

        ParameterRemoverInterceptor interceptor = new ParameterRemoverInterceptor();
        interceptor.setParamNames("param1,param2");
        interceptor.setParamValues("paramValue1,paramValue2");
        interceptor.intercept(actionInvocation);

        HttpParameters params = context.getParameters();
        assertEquals(params.keySet().size(), 2);
        assertTrue(params.contains("param3"));
        assertTrue(params.contains("param"));
        assertEquals(params.get("param3").getValue(), "paramValue3");
        assertEquals(params.get("param").getValue(), "paramValue");

        verify(actionInvocation);
    }


    public void testInterception2() throws Exception {
        context.withParameters(HttpParameters.create(new LinkedHashMap<String, Object>() {
            {
                put("param1", new String[]{"paramValue2"});
                put("param2", new String[]{"paramValue1"});
            }
        }).build());

        replay(actionInvocation);

        ParameterRemoverInterceptor interceptor = new ParameterRemoverInterceptor();
        interceptor.setParamNames("param1,param2");
        interceptor.setParamValues("paramValue1,paramValue2");
        interceptor.intercept(actionInvocation);

        HttpParameters params = context.getParameters();
        assertEquals(params.keySet().size(), 0);

        verify(actionInvocation);
    }


    public void testInterception3() throws Exception {
        context.withParameters(HttpParameters.create(new LinkedHashMap<String, Object>() {
            {
                put("param1", new String[]{"paramValueOne"});
                put("param2", new String[]{"paramValueTwo"});
            }
        }).build());

        replay(actionInvocation);

        ParameterRemoverInterceptor interceptor = new ParameterRemoverInterceptor();
        interceptor.setParamNames("param1,param2");
        interceptor.setParamValues("paramValue1,paramValue2");
        interceptor.intercept(actionInvocation);

        HttpParameters params = context.getParameters();
        assertEquals(params.keySet().size(), 2);
        assertTrue(params.contains("param1"));
        assertTrue(params.contains("param2"));
        assertEquals(params.get("param1").getValue(), "paramValueOne");
        assertEquals(params.get("param2").getValue(), "paramValueTwo");

        verify(actionInvocation);
    }

    static class SampleAction extends ActionSupport {
        @Serial
        private static final long serialVersionUID = 7489487258845368260L;
    }
}
