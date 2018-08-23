/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.sling.resource.filter;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.resource.filter.ResourceFilterStream;

/**
 * Creates a {@link Predicate} of type {@link Resource} to identify matching
 * Resource objects
 *
 */
public class ResourceFilterStream {

    private ResourceStream resources;

    private ResourceFilter resourceFilter;

    private Predicate<Resource> branchSelector = resource -> true;

    private Predicate<Resource> childSelector = resource -> true;

    public ResourceFilterStream(Resource resource, ResourceFilter filter) {
        resources = new ResourceStream(resource);
        this.resourceFilter = filter;
    }

    /**
     * Adds a branchSelector to define which child resource are acceptable to travel
     * down as part of the Resource traversal
     * 
     * @param branchSelector
     *            resourceFilter script for traversal control
     * @return ResourceStreamFilter
     * @throws ResourceFilterException
     */
    public ResourceFilterStream setBranchSelector(String branchSelector) {
        this.branchSelector = resourceFilter.parse(branchSelector);
        return this;
    }

    /**
     * Adds a childSelector to define which child resources should be part of the
     * stream
     * 
     * @param childSelector
     *            resourceFilter script to identify child resources to return
     * @return ResourceStreamFilter
     * @throws ResourceFilterException
     */
    public ResourceFilterStream setChildSelector(String childSelector) {
        this.childSelector = resourceFilter.parse(childSelector);
        return this;
    }

    /**
     * Add a key - value pair that can then be evaluated as part of the Script
     * 
     * @param params
     * @return
     */
    public ResourceFilterStream addParam(String key, Object value) {
        resourceFilter.addParam(key, value);
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
        resourceFilter.addParams(params);
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
