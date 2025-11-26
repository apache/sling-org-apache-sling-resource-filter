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
package org.apache.sling.resource.filter.impl;

import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.resource.filter.ResourceFilterStream;
import org.apache.sling.resource.filter.ResourcePredicates;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        property = {
            "adaptables=org.apache.sling.api.resource.Resource",
            "adapters=org.apache.sling.resource.filter.ResourceFilterStream"
        })
public class ResourceFilterAdapter implements AdapterFactory {

    @Reference
    private volatile ResourcePredicates filter;

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAdapter(Object adaptable, Class<T> type) {
        if (adaptable instanceof Resource) {
            return (T) new ResourceFilterStream((Resource) adaptable, filter);
        }
        return null;
    }
}
