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
import org.apache.sling.resource.filter.ResourceFilter;
import org.apache.sling.resource.filter.impl.node.Node;
import org.apache.sling.resource.filter.impl.script.FilterParser;
import org.apache.sling.resource.filter.impl.script.ParseException;

public class ResourceFilterImpl implements ResourceFilter {

    
    private Context context;

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.sling.resource.filter.ResourceFilterStream#getContext()
     */
    public Context getContext() {
        if (context == null) {
            context = new DefaultContext();
            new LogicVisitor(context);
            new ComparisonVisitor(context);
        }
        return context;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.sling.resource.filter.ResourceFilterStream#setContext(org.apache.sling.
     * resource.filter.impl.Context)
     */

    public ResourceFilter setContext(Context context) {
        this.context = context;
        return this;
    }
    
    
    @Override
    public Predicate<Resource> parse(String filter) {
        Node rootNode;
        try {
            rootNode = new FilterParser(new ByteArrayInputStream(filter.getBytes())).parse();
            return rootNode.accept(getContext().getLogicVisitor());
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }



    @Override
    public Predicate<Resource> parse(String filter, String charEncoding) {
        Node rootNode;
        try {
            rootNode = new FilterParser(new ByteArrayInputStream(filter.getBytes()),charEncoding).parse();
            return rootNode.accept(getContext().getLogicVisitor());
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public ResourceFilter addParams(Map<String, Object> params) {
        getContext().addParams(params);
        return this;
    }

    @Override
    public ResourceFilter addParam(String key, Object value) {
        getContext().addArgument(key, value);
        return this;
    }

}
