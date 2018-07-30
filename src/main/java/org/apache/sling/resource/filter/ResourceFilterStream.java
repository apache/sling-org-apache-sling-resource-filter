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

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.resource.filter.impl.ParseException;

public class ResourceFilterStream extends ResourceStream {

    public ResourceFilterStream(Resource resource) {
        super(resource);
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
    public Stream<Resource> stream(String branchSelector) throws ParseException {
        return stream(new ResourceFilter(branchSelector));
    }
    
    /**
     * Provides a stream of the child resources of the base resource. The predicate
     * is a filter to determine which of the children are returned
     * 
     * @param childSelector
     * @return
     * @throws ParseException 
     */
    public Stream<Resource> listChildren(String childSelector) throws ParseException {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(resource.listChildren(),
                Spliterator.ORDERED | Spliterator.IMMUTABLE), false).filter(new ResourceFilter(childSelector));
    }

}