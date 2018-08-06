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

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.resource.filter.impl.ResourceFilterImpl;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ResourceFilterTest {

    @Rule
    public final SlingContext context = new SlingContext();

    private static String START_PATH = "/content/sample/en";


    private Resource resource;
    
    ResourceFilter resourceFilter = new ResourceFilterImpl();

    @Before
    public void setUp() throws ResourceFilterException {
        context.load().json("/data.json", "/content/sample/en");
        resource = context.resourceResolver().getResource(START_PATH);
    }

    @Test
    public void testPropertyEquals() throws ResourceFilterException {
        String query = "[jcr:content/jcr:title] == 'English'";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(4, found.size());
    }

    @Test
    public void testPropertyIs() throws ResourceFilterException {
        String query = "[jcr:content/jcr:title] is 'English'";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(4, found.size());
    }

    @Test
    public void testDateBeforeValue() throws ResourceFilterException {
        String query = "[jcr:content/created] < date('2013-08-08T16:32:59.000+02:00')";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(3, found.size());
    }

    @Test
    public void testDateBeforeValue2() throws ResourceFilterException {
        String query = "[jcr:content/created] less than date('2013-08-08T16:32:59.000+02:00')";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(3, found.size());
    }

    @Test
    public void testDateBeforeValue3() throws ResourceFilterException {
        String query = "[jcr:content/created] < date('2013-08-08','yyyy-MM-dd')";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(3, found.size());
    }

    @Test
    public void testDateAndProperty() throws ResourceFilterException {
        String query = "[jcr:content/created] < date('2013-08-08T16:32:59.000+02:00') and [jcr:content/jcr:title] == 'English'";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(3, found.size());
    }

    @Test
    public void testDateAndPropertyTwice() throws ResourceFilterException {
        String query = "([jcr:content/created] < date('2013-08-08T16:32:59.000+02:00') and [jcr:content/jcr:title] == 'English') or [jcr:content/jcr:title] == 'Mongolian'";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(4, found.size());
    }

    @Test
    public void testDateOrProperty() throws ResourceFilterException {
        String query = "[jcr:content/created] < date('2013-08-08T16:32:59.000+02:00') or [jcr:content/jcr:title] == 'Mongolian'";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(4, found.size());
    }

    @Test
    public void testDateAsString() throws ResourceFilterException {
        String query = "[jcr:content/created] < '2013-08-08T16:32'";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(3, found.size());
    }

    @Test
    public void testNullPropertyAndLimit() throws ResourceFilterException {
        String query = "[jcr:content/foo] == null ";
        List<Resource> found = new ResourceStream(resource).stream(r -> true).filter(resourceFilter.parse(query)).limit(3)
                .collect(Collectors.toList());
        assertEquals(3, found.size());
    }

    @Test
    public void testNullProperty() throws ResourceFilterException {
        String query = "[jcr:content/foo] == null ";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(20, found.size());
    }

    @Test
    public void testNumberLiteral() throws ResourceFilterException {
        String query = "[count] < 2 ";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(1, found.size());
    }

    @Test
    public void testNumberLiteral2() throws ResourceFilterException {
        String query = "[count] < 2 or [count] > 1";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(1, found.size());
    }

    @Test
    public void testNumberLiteral3() throws ResourceFilterException {
        String query = "[views] < 7 ";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(1, found.size());
    }

    @Test
    public void testNotNullProperty() throws ResourceFilterException {
        String query = "[layout] != null ";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(5, found.size());
    }

    @Test
    public void testNotProperty() throws ResourceFilterException {
        String query = "[layout] != 'foo' ";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(4, found.size());
    }

    @Test
    public void testNameFunctionIs() throws ResourceFilterException {
        String query = "name() == 'testpage1'";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(1, found.size());
    }

    @Test
    public void testNameFunctionAgainstRegex() throws ResourceFilterException {
        String query = "name() like 'testpage.*'";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(4, found.size());
    }

    @Test
    public void testNameFunctionAgainstRegex2() throws ResourceFilterException {
        String query = "name() like 'testpage[1-2]'";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(2, found.size());
    }

    @Test
    public void testChildExistence() throws ResourceFilterException {
        String query = "name() == 'testpage3' ";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(1, found.size());
    }

    @Test
    public void testBoolean() throws ResourceFilterException {
        String query = "[published] == true";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(1, found.size());
    }

    @Test
    public void testContains() throws ResourceFilterException {
        String query = "[jcr:content/monkey] contains 'fish'";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(1, found.size());
    }

    @Test
    public void testContainsNot() throws ResourceFilterException {
        String query = "[jcr:content/monkey] contains not 'fish'";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(19, found.size());
    }

    @Test
    public void testIn() throws ResourceFilterException {
        String query = "'fish' in [jcr:content/monkey]";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(1, found.size());
    }

    @Test
    public void testPathLike() throws ResourceFilterException {
        String query = "path() like '/content/sample/en/testpage1.*'";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(4, found.size());
    }

    @Test
    public void testPathLike2() throws ResourceFilterException {
        String query = "path() like '/content/sample/en/testpage1'";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(1, found.size());
    }

    @Test
    public void testPathLike3() throws ResourceFilterException {
        String query = "path() is '/content/sample/en/testpage1'";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(1, found.size());
    }

    @Test
    public void testNotIn() throws ResourceFilterException {
        String query = "'fish' not in [jcr:content/monkey]";
        List<Resource> found = handle(START_PATH, query);
        assertEquals(19, found.size());
    }

    @Test
    public void testInNotException() throws ResourceFilterException {
        ResourceFilterException error = null;
        try {
            String query = "'fish' in not [jcr:content/monkey]";
            handle(START_PATH, query);
        } catch (ResourceFilterException e) {
            error = e;
        }
        assert (error.getMessage()
                .startsWith("org.apache.sling.resource.filter.impl.script.ParseException: Encountered \" <PROPERTY> \"jcr:content/monkey \"\" at line 1, column 15."));
    }

    private List<Resource> handle(String path, String filter) throws ResourceFilterException {
        return new ResourceStream(resource).stream(r -> true).filter(resourceFilter.parse(filter))
                .collect(Collectors.toList());
    }
}
