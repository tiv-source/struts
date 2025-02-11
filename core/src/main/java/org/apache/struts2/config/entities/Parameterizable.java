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
package org.apache.struts2.config.entities;

import java.util.Map;

/**
 * <!-- START SNIPPET: javadoc -->
 * <p>
 * Actions implementing Parameterizable will receive a map of the static parameters defined in the action
 * configuration.
 * </p>
 *
 * <p>
 * The {@link org.apache.struts2.interceptor.StaticParametersInterceptor} must be in the action's interceptor
 * queue for this to work.
 * </p>
 * <!-- END SNIPPET: javadoc -->
 *
 * @author Jason Carreira
 */
public interface Parameterizable {

    void addParam(String name, String value);

    void setParams(Map<String, String> params);

    Map<String, String> getParams();
}
