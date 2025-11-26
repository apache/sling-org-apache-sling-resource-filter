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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.sling.api.resource.Resource;
import org.osgi.annotation.versioning.ProviderType;

/**
 * Creates a {@link Predicate} of type {@link Resource} to identify matching
 * Resource objects
 *
 */
@ProviderType
public class ResourceFilterStream {

    private ResourceStream resources;

    private ResourcePredicates resourcePredicate;

    private Predicate<Resource> branchSelector = resource -> true;

    private Predicate<Resource> childSelector = resource -> true;

    private Map<String, Object> parameters = new HashMap<>();

    public ResourceFilterStream(Resource resource, ResourcePredicates filter) {
        this.resources = new ResourceStream(resource);
        this.resourcePredicate = filter;
    }

    /**
     * Adds a branchSelector to define which child resource are acceptable to travel
     * down as part of the Resource traversal
     *
     * @param branchSelector
     *            resourcePredicate script for traversal control
     * @return ResourceStreamFilter
     * @throws ResourceFilterException
     */
    public ResourceFilterStream setBranchSelector(String branchSelector) {
        this.branchSelector = resourcePredicate.usingParameterMap(parameters).parse(branchSelector);
        return this;
    }

    /**
     * Adds a childSelector to define which child resources should be part of the
     * stream
     *
     * @param childSelector
     *            resourcePredicate script to identify child resources to return
     * @return ResourceStreamFilter
     * @throws ResourceFilterException
     */
    public ResourceFilterStream setChildSelector(String childSelector) {
        this.childSelector = resourcePredicate.usingParameterMap(parameters).parse(childSelector);
        return this;
    }

    /**
     * Add a key - value pair that can then be evaluated as part of the Script
     *
     * @param params
     * @return
     */
    public ResourceFilterStream addParam(String key, Object value) {
        parameters.put(key, value);
        return this;
    }

    /**
     * Add a series of key - value pairs that can then be evaluated as part of the
     * ScriptFilter
     *
     * @param params
     * @return
     */
    public ResourceFilterStream addParams(Map<String, Object> params) {
        parameters.putAll(params);
        return this;
    }

    /**
     * Stream<Resource> which uses the branchSelector as the basis of the traversal
     * and then filters the resources based on the childSelector that was provided
     *
     * @return pre filterd Stream<Resource>
     */
    public Stream<Resource> stream() {
        return resources.stream(branchSelector).filter(childSelector);
    }
}
