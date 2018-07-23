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
package org.apache.sling.resource.filter.api;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.sling.api.resource.Resource;

public interface Context {

    /**
     * Adds a Function to the script to allow for customization. 
     * 
     * 
     * @param name
     *            of the function as it appears in the script
     * @param functionImpl
     *            defines the function in terms of passed in arguments, the resource
     *            that is being acted on and the return object
     * @return this Context
     */
    Context addFunction(String name, BiFunction<Object[], Resource, Object> functionImpl);

    /**
     * Remove the function with the given name from the context.
     * 
     * @param name
     * @return
     */
    Context removeFunction(String name);

    /**
     * Allows an object to be represented in the script for evaluation.
     * 
     * @param name of the argument
     * @param object value that is represented
     * @return this Context
     */
    Context addArgument(String name, Object object);

    /**
     * Retrieve the currently defined Logic Visitor 
     * 
     * @return Visitor
     */
    Visitor<Predicate<Resource>> getLogicVisitor();

    /**
     * Retrieve the currently defined Comparison Visitor 
     * 
     * @return Visitor
     */
    Visitor<Function<Resource, Object>> getComparisonVisitor();

    /**
     * Replaces the existing Logic Visitor, if present, with the provided Visitor
     * 
     * @param Visitor
     */
    void setLogicVisitor(Visitor<Predicate<Resource>> logicVisitor);

    /**
     * Replaces the existing Comparison Visitor, if present, with the provided Visitor
     * 
     * @param comparisonVisitor
     */
    void setComparionVisitor(Visitor<Function<Resource, Object>> comparisonVisitor);

    /**
     * Used to retrieve the function during script processing
     * 
     * @param name of the function in the context
     * @return Optional BiFunction object representing the name
     */
    Optional<BiFunction<Object[], Resource, Object>> getFunction(String name);

    /**
     * Retrieves the value object associated with the name
     * 
     * @param name of the argument
     * @return Optional object represented by the name
     */
    Optional<Object> getArgument(String name);

}
