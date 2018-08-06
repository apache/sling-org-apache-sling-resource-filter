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
import org.apache.sling.resource.filter.impl.script.ParseException;

/**
 * Creates a {@link Predicate} of type {@link Resource} to identify matching
 * Resource objects
 *
 */
public class ResourceFilterStream extends ResourceStream {

    private ResourceFilter resourceFilter;

    public ResourceFilterStream(Resource resource, ResourceFilter filter) {
        super(resource);
        this.resourceFilter = filter;
    }

    /**
     * Creates a Stream<Resource> using the branchSelector to create the traversal
     * predicate to select the appropriate child resources
     * 
     * @param branchSelector
     *            resourceFilter script for traversal control
     * @return ResourceStream
     * @throws ParseException
     */
    public Stream<Resource> stream(String branchSelector) throws ResourceFilterException {
        return stream(resourceFilter.parse(branchSelector));
    }

    /**
     * Creates a Stream<Resource> using the branchSelector to create the traversal
     * predicate to select the appropriate child resources
     * 
     * @param branchSelector
     *            resourceFilter script for traversal control
     * @param charEncoding
     *            char encoding of the branch selector String
     * @return ResourceStream
     * @throws ParseException
     */
    public Stream<Resource> stream(String branchSelector, String charEncoding) throws ResourceFilterException {
        return stream(resourceFilter.parse(branchSelector, charEncoding));
    }

    /**
     * Provides a stream of the child resources filtered by the child selector
     * 
     * @param childSelector
     * @return
     * @throws ResourceFilterException
     * @throws ParseException
     */
    public Stream<Resource> listChildren(String childSelector) throws ResourceFilterException {
        return listChildren(resourceFilter.parse(childSelector));
    }

    /**
     * Provides a stream of the child resources filtered by the child selector
     * 
     * @param childSelector
     *            text based definition of the Predicate to use
     * @param charEncoding
     *            char encoding of the branch selector String
     * @return
     * @throws ResourceFilterException
     * @throws ParseException
     */
    public Stream<Resource> listChildren(String childSelector, String charEncoding) throws ResourceFilterException {
        return listChildren(resourceFilter.parse(childSelector, charEncoding));
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
}
