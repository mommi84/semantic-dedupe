# semantic-dedupe
Semantization of datasets for key discovery and deduplication using [Rocker](http://github.com/AKSW/rocker).

## Usage

Extend and customize some `MyParser extends Parser`, then run:

```java
RDFConverter.convert(MyParser.parse("input.json"), "http://namespace/", "output.nt");
```
