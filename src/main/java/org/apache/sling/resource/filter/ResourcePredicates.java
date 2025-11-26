/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sling.resource.filter;

import java.util.Map;
import java.util.function.Predicate;

import org.apache.sling.api.resource.Resource;
import org.osgi.annotation.versioning.ProviderType;

/**
 * Service which provides the ability to convert a String into a
 * Predicate&lt;Resource&gt; to match against Resource Objects
 *
 */
@ProviderType
public interface ResourcePredicates {

    /**
     * Creates a Predicate&lt;Resource&gt; based on the provided script
     *
     * @param filter
     * @return
     */
    Predicate<Resource> parse(String filter);

    /**
     * Creates a Predicate&lt;Resource&gt; based on the provided script
     *
     * @param filter
     * @param charEncoding
     * @return
     */
    Predicate<Resource> parse(String filter, String charEncoding);

    /**
     * Add a series of key - value pairs that can then be evaluated as part of the
     * Predicate&lt;Resource&gt; creation
     *
     * @param params
     *            Map of Key - Value pairs
     * @return ResourcePredicateBuilder
     */
    ResourcePredicateBuilder withParameters(Map<String, Object> params);

    /**
     * Adds a key - value pair that can then be evaluated as part of the
     * Predicate&lt;Resource&gt; creation
     *
     * @param key
     * @param value
     * @return ResourcePredicateBuilder
     */
    ResourcePredicateBuilder withParameter(String key, Object value);

    /**
     * Replaces the existing parameter map with the supplied Map&lt;String,Object&gt;
     * object, all prior provided parameters will be replaced
     *
     * @return ResourcePredicateBuilder
     */
    ResourcePredicateBuilder usingParameterMap(Map<String, Object> params);

    /**
     * Provides a transitional state where multiple parameters can be applied before
     * creating the Predicate
     *
     */
    public static interface ResourcePredicateBuilder extends ResourcePredicates {}
}
