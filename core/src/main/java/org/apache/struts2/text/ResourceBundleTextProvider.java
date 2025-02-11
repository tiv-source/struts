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
package org.apache.struts2.text;

import org.apache.struts2.locale.LocaleProvider;

import java.util.ResourceBundle;

/**
 * Extension Interface for TextProvider to help supporting ResourceBundles.
 *
 * @author Rene Gielen
 */
public interface ResourceBundleTextProvider extends TextProvider {

    /**
     * Set the resource bundle to use.
     *
     * @param bundle the bundle to use.
     */
    void setBundle(ResourceBundle bundle);

    /**
     * Set the class to use for reading the resource bundle.
     *
     * @param clazz the class to use for loading.
     */
    void setClazz(Class<?> clazz);

    /**
     * Set the LocaleProvider to use.
     *
     * @param localeProvider the LocaleProvider to use.
     */
    void setLocaleProvider(LocaleProvider localeProvider);

}
