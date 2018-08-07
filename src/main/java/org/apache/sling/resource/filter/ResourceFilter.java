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

public interface ResourceFilter {

    /**
     * Creates a Predicate<Resource> based on the script
     * 
     * @param filter
     * @return
     * @throws ResourceFilterException
     */
    public Predicate<Resource> parse(String filter) throws ResourceFilterException;

    /**
     * Creates a Predicate<Resource> based on the script
     * 
     * @param filter
     * @param charEncoding
     * @return
     * @throws ResourceFilterException
     */
    public Predicate<Resource> parse(String filter, String charEncoding) throws ResourceFilterException;

    /**
     * Add a series of key - value pairs that can then be evaluated as part of the
     * filter creation
     * 
     * @param params Map of Key - Value pairs
     * @return this
     */
    public abstract ResourceFilter addParams(Map<String, Object> params);

    /**
     * Adds a key - value pair that can then be evaluated as part of the filter
     * creation
     * 
     * @param key
     * @param value
     * @return this
     */
    public abstract ResourceFilter addParam(String key, Object value);
}
