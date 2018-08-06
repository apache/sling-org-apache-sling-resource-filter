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
import java.util.stream.Stream;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.resource.filter.impl.script.ParseException;

public abstract class ResourceFilterStream extends ResourceStream {

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
    public abstract Stream<Resource> stream(String branchSelector) throws ResourceFilterException;

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
    public abstract Stream<Resource> stream(String text, String charEncoding) throws ResourceFilterException;
    

    /**
     * Provides a stream of the child resources filtered by the child selector
     * 
     * @param childSelector
     * @return
     * @throws ResourceFilterException 
     * @throws ParseException
     */
    public abstract Stream<Resource> listChildren(String text) throws ResourceFilterException;

    /**
     * Provides a stream of the child resources filtered by the child selector
     * 
     * @param childSelector text based definition of the Predicate to use
     * @param charEncoding
     *            char encoding of the branch selector String
     * @return
     * @throws ResourceFilterException 
     * @throws ParseException
     */
    public abstract Stream<Resource> listChildren(String text, String charEncoding) throws ResourceFilterException;
    
    /**
     * Add a series of key - value pairs that can then be evaluated as part of the ScriptFilter
     * 
     * @param params
     * @return
     */
    public abstract ResourceFilterStream addParams(Map<String,Object> params);
    
    /**
     * Add a key - value pair that can then be evaluated as part of the Script
     * 
     * @param params
     * @return
     */
    public abstract ResourceFilterStream addParam(String key, Object value);
}