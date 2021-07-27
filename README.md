# Recognizer Application
            
The recognizer application is a prototype implementation for reasearch on fruit detection.

![Build](https://github.com/marquies/recognizer2/actions/workflows/gradle.yml/badge.svg)


## Build 

Build the project with the command

> gradle build
                                  
The created jar with dependencies will be created at `build/libs`
                         

## Download the model files

...


## Standalone application

To execute the application as standalone, create a jar with all the dependencies.

> gradle fatJar

Executue with ``java -jar libs/recognizer2-VERSION.jar``

## Use in the GMAF Framework

To use the code as a plugin within the GMAF, create a library and publish it in the local maven repository.

> gradle publishToMavenLocal

After the execution, the lib can be included as a maven dependeny:

```
		<dependency>
			<groupId>de.patricksteinert</groupId>
			<artifactId>recognizer2</artifactId>
			<version>1.0</version>
		</dependency>
```

To use the plugin, you can add it in the ``plugin.config``:

```
de.patricksteinert.recognizer.GMAF_PluginAdapter
```
