[![Apache Sling](https://sling.apache.org/res/logos/sling.png)](https://sling.apache.org)

&#32;[![Build Status](https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-resource-filter/job/master/badge/icon)](https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-resource-filter/job/master/)&#32;[![Test Status](https://img.shields.io/jenkins/tests.svg?jobUrl=https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-resource-filter/job/master/)](https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-resource-filter/job/master/test/?width=800&height=600)&#32;[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=apache_sling-org-apache-sling-resource-filter&metric=coverage)](https://sonarcloud.io/dashboard?id=apache_sling-org-apache-sling-resource-filter)&#32;[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=apache_sling-org-apache-sling-resource-filter&metric=alert_status)](https://sonarcloud.io/dashboard?id=apache_sling-org-apache-sling-resource-filter)&#32;[![JavaDoc](https://www.javadoc.io/badge/org.apache.sling/org.apache.sling.resource.filter.svg)](https://www.javadoc.io/doc/org.apache.sling/org.apache.sling.resource.filter)&#32;[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.apache.sling/org.apache.sling.resource.filter/badge.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.apache.sling%22%20a%3A%22org.apache.sling.resource.filter%22) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

# Resource Predicate Service
`ResourcePredicate` is a service that allows you to convert a string that defines a simple matching requirements into a `Predicate<Resource>` for use with the Collections and the Streams Java API. In addition it also allows you to add parameters to the underlying context that the script will use.

```java
@Reference
ResourcePredicates rp;

Predicate<Resource> predicate = rp.parse("[jcr:content/created] < 2013-08-08T16:32");
resourceCollection.stream().filter(predicate).forEach(
    resource -> System.out.println(resource.getPath())
);

```

## Resource Stream
`ResourceStream` is a general utility to provide a `Stream<Resource>` which traverses a resource and it's subtree. The implementation takes a `Predicate<Resource>` object as part of the stream creation to define a branch selector that controls which children of a resource are followed.

In addition there is a `getChildren(Predicate)` method which returns a filtered list of children of the given resource.


## Resource Filter Stream
`ResourceFilterStream` combines the `ResourceStream` functionality with the `ResourcePredicates` service to provide an ability to define a `Stream<Resource>` that follows specific child pages and looks for specific Resources as defined by the resources filter script. The ResourceStreamFilter is access by adaption.

```java
     ResourceFilterStream rfs = resource.adaptTo(ResourceFilterStream.class);
     
     rfs
        .setBranchSelector("[jcr:primaryType] == 'cq:Page'")
        .setChildSelector("[jcr:content/sling:resourceType] != 'apps/components/page/folder'")
        .stream()
        .collect(Collectors.toList());
```



## ResourceFilter Scripting

### Operators

| Name        | Comparison Type | Description                                |
| ---------        | --------------- | --------------------------------           |
| and              | NA              | Logical AND                                |
| &&               | NA              | Logical AND                                |
| or               | NA              | Logical OR                                 |
|&#124;&#124;      | NA              | Logical OR                                 |
| ==               | String          | Equal operator for Strings                 |
| <                | Number          | Less than operator for Numbers             |
| <=               | Number          | Less than or equal operator for Numbers    |
| >                | Number          | Greater than operator for Numbers          |
| >=               | Number          | Greater than or equal operator for Numbers |
| !=               | String          | Is not equal to for Strings                |
| ~=               | String - Regex  | Regex match against String                 |
| less than        | Number          | less than operator for Numbers             |
| greater than     | Number          | greater than operator for Numbers          |
| is               | String          | Equal operator for Strings                 |
| is not           | String          | Is not equal operator for Strings          |
| like             | String - Regex  | Regex match against String                 |
| is like          | String - Regex  | Regex match against String                 |
| not like         | String - Regex  | Regex does not match String                |
| contains         | String[]        | String[] contains all of items             |
| contains not     | String[]        | String[] does not contain all of the items |
| contains any     | String[]        | String[] contains at least one of items    |
| contains not any | String[]        | String[] does not contain any of the items |
### Logical Operators
The 'and' and 'or' operators are logical operators that string together conditions. 'And' operators take precedence. 'Or' operators evaluate from left to right


### Values

Values for comparison are obtained through multiple methods

| Method       | Description                               |
| ----------   | ----------------------------------------  |
| Literal      | Single(') or double (") quoted text in the query will be interpreted as a String. Boolean values of *true* and *false* will be translated to a String. |
| Property     | A String between square brackets '[',']'s will be interpreted as a property value and will be retrieved from the Resource using the get method |
| Function     | A string followed by parens containing an optional comma separated list of values. |

### Types
All types are converted to either a String or a Number. For direct equivalence the comparison is done as a String. For relational comparisons the object will be adapted to a number.

### Dates/Instants
Dates are special, there are multiple ways to enter a date.

In line, as part of the query, a date can be identified as a string that conforms to a standard ISO-8601 date time.

> '2013-08-08T16:32:59.000'
>
> '2013-08-08T16:32:59'
>
> '2013-08-08T16:32'

Are all valid date representations that are defaulting to the UTC timezone.

For a ISO8601 date with timezone offset use the date function.

> date('2013-08-08T16:32:59.000+02:00')

If you need a different date format then the date function can accommodate that

> date('2013-08-08','yyyy-MM-dd')

Or you can add your own custom Function 

Dates are transitionally represented as a java.util.Instant which is then converted to a String in ISO-8601 format or as a Long number based on the type of comparison. The number representing the time in milliseconds since the EPOCH UTC region

### Functions

Functions provide the ability to add additional functionality to the Filter language. A Function is written in the format

> string '(' comma, separated, list() ')'

All functions MUST return either a String, a Number, or an Instant. Strings are assumed to be using the default UTF encoding.

OOTB Functions are:

| Name  | Arguments | Returns | Description                                                    |
| ----  | --------- | ------- | -----------------------------------                            |
| name  | none      | String  | Provides the name of the resource                              |
| date  | 0 - 2     | Instant | First argument is string representation of the date, second argument is a standard Java DateFormat representation of the value. No argument returns the current time. |
| path  | none		| String  | path of the tested resource        |

### Parameters
The ResourceFilter and ResourceFilteStream can have key value pairs added so that the values may be used as part of the script resolution. Parameters are accessed by using the dollar sign '$'

```java
rfs.setBranchSelector("[jcr:content/sling:resourceType] != $type").addParam("type","apps/components/page/folder");
```


## Optimizing Traversals
Similar to indexing in a query there are strategies that you can do within a tree traversal so that traversals can be done in an efficient manner across a large number of resources. The following strategies will assist in traversal optimization.

### Limit traversal paths
In a naive implementation of a tree traversal the traversal occurs across all nodes in the tree regardless of the ability of the tree structure to support the nodes that are being looked for. An example of this is a tree of Page resources that have have a child node of jcr:content which contains a subtree of data to define the page structure. If the jcr:content node is not capable of having a child resource of type Page and the goal of the traversal is to identify Page resources that match a specific criteria then the traversal of the jcr:content node can not lead to additional matches. Using this knowledge of the resource structure, you can improve performance by adding a branch selector that prevents the traversal from proceeding down a non productive path
  
### Limit memory consumption
The instantiation of a Resource object from the underlying ResourceResolver is a non trivial consumption of memory. When the focus of a tree traversal is obtaining information from thousands of Resources, an effective method is to extract the information as part of the stream processing or utilizing the forEach method of the ResourceStream object which allows the resource to be garbage collected in an efficient manner. 
