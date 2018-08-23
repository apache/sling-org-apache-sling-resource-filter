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

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.resource.filter.ResourcePredicates;
import org.apache.sling.resource.filter.impl.node.Node;
import org.apache.sling.resource.filter.impl.script.FilterParser;
import org.apache.sling.resource.filter.impl.script.ParseException;
import org.osgi.service.component.annotations.Component;

@Component
public class ResourcePredicateImpl implements ResourcePredicates {

    @Override
    public Predicate<Resource> parse(String filter) {
        return new ResourcePredicateBuilderImpl().parse(filter);
    }

    @Override
    public Predicate<Resource> parse(String filter, String charEncoding) {
        return new ResourcePredicateBuilderImpl().parse(filter, charEncoding);
    }

    @Override
    public ResourcePredicateBuilder withParameters(Map<String, Object> params) {
        return new ResourcePredicateBuilderImpl().withParameters(params);
    }

    @Override
    public ResourcePredicateBuilder withParameter(String key, Object value) {
        return new ResourcePredicateBuilderImpl().withParameter(key, value);
    }
    
    @Override
    public ResourcePredicateBuilder usingParameterMap(Map<String, Object> params) {
        return new ResourcePredicateBuilderImpl().usingParameterMap(params);
    }

    public static class ResourcePredicateBuilderImpl implements ResourcePredicateBuilder {

        private Context context;

        private ResourcePredicateBuilderImpl() {
            context = new DefaultContext();
            new LogicVisitor(context);
            new ComparisonVisitor(context);
        }

        @Override
        public Predicate<Resource> parse(String filter) {
            Node rootNode;
            try {
                rootNode = new FilterParser(new ByteArrayInputStream(filter.getBytes())).parse();
                return rootNode.accept(context.getLogicVisitor());
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public Predicate<Resource> parse(String filter, String charEncoding) {
            Node rootNode;
            try {
                rootNode = new FilterParser(new ByteArrayInputStream(filter.getBytes()), charEncoding).parse();
                return rootNode.accept(context.getLogicVisitor());
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public ResourcePredicateBuilder withParameters(Map<String, Object> params) {
            context.addParameters(params);
            return this;
        }

        @Override
        public ResourcePredicateBuilder withParameter(String key, Object value) {
            context.addArgument(key, value);
            return this;
        }

        @Override
        public ResourcePredicateBuilder usingParameterMap(Map<String, Object> params) {
            context.replaceParameterMap(params);
            return this;
        }

    }

}
