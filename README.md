# OWL-API-Play

[Ammonite](https://ammonite.io/) scripts to explore ontologies like the [Financial Industry Business Ontology (FIBO)](https://spec.edmcouncil.org/fibo/) with the [OWL-API](http://owlcs.github.io/owlapi/).

The OWL-API is an interface for reasoners and Ontology development Apps such as [Protégé](https://protege.stanford.edu/). Being able to write scripts when downloading and testing ontologies makes it much easier to explore the OWL-API programmatically as well as the downloaded ontologies. 

The OWL-API is a bit clunky, using the traditional Java style of interfaces and the Visitor pattern. I am using this now as I want
to make sure I have accessible the 20 years of work on reasoning that went into it. More practical libraries to work with RDF for applications are [banana-rdf](https://github.com/banana-rdf/banana-rdf/wiki/Scripting-with-Ammonite), but that does not yet integrate OWL reasoning. In any case the Ammonite Shell makes it much easier to work with data, explore APIs, write scripts, whatever your task.

Note, the scripts themselves don't need the full sbt structure published here. Those are useful when using Integrated Development Environments like [IntelliJ](https://www.jetbrains.com/idea/), so that one can explore the very large codebased of the OWL-API. 

The scripts are in the amm directory.
