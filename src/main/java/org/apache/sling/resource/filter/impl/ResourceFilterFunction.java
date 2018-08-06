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
package org.apache.sling.resource.filter.impl;

import java.util.function.BiFunction;

import org.apache.sling.api.resource.Resource;

/**
 * A ResourceFilterFunction implementation is used to translate a command in a
 * script to a value object that will be used as part of a comparison
 * 
 */
public interface ResourceFilterFunction extends BiFunction<Object[], Resource, Object> {

    /**
     * This method returns a {@code Object} to be used as part of a comparison.
     * 
     * @param arguments
     *            A list of {@code Function}'s which provides the arguments defined
     *            in the script, to obtain the arguments, each argument must be
     *            called
     * @return A {@code Object} which should be a String, Instant, or Number to be
     *         used as part of a comparison or Function
     */
    Object apply(Object[] arguments, Resource resource);


}
