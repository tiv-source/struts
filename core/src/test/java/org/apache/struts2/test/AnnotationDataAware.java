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
package org.apache.struts2.test;

import org.apache.struts2.conversion.annotations.Conversion;
import org.apache.struts2.conversion.annotations.TypeConversion;
import org.apache.struts2.conversion.impl.FooBarConverter;
import org.apache.struts2.util.Bar;
import org.apache.struts2.validator.annotations.RequiredFieldValidator;
import org.apache.struts2.validator.annotations.RequiredStringValidator;


/**
 * Implemented by SimpleAction3 and AnnotationTestBean2 to test class hierarchy traversal.
 *
 * @author Mark Woon
 * @author Rainer Hermanns
 */
@Conversion()
public interface AnnotationDataAware {

    void setBarObj(Bar b);

    @TypeConversion(converterClass = FooBarConverter.class)
    Bar getBarObj();

    @RequiredFieldValidator(message = "You must enter a value for data.")
    @RequiredStringValidator(message = "You must enter a value for data.")
    void setData(String data);

    String getData();
}
