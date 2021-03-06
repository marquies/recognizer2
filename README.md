# Recognizer Application
            
The recognizer application is a prototype implementation for reasearch on fruit detection.

![Build](https://github.com/marquies/recognizer2/actions/workflows/gradle.yml/badge.svg)

          
## Prerequisites

The application is tested on:
* macOS 11.4
* Ubuntu 18.04.5 LTS

The following packages should be installed via the package managers (homebrew/apt).
* Python3 (tested Ubuntu 3.6.9 & macOS 3.9.5)
* OpenCV2 (tested Ubuntu 4.1.1 & macOS 4.5.2)
* Java JDK 11 or newer (tested Ubuntu 11.0.11 & macOS 16.0.1)
* Gradle 6 or 7 (tested Ubuntu 7.1.1 & macOS 7.1)
* TensorFlow 2.5, opencv, pandas, pillow, numpy



## Download the model files

There are two model packages necessary for the project.

* ssd_mobilenet_v2_coco_2018_03_29 (by TensorFlow)
* yolo_v3_fruits360_2021_07_23_4 (self-generated)

You can download and unzip the models with the following commands.
Download them in the project directory.

```
wget https://www.patricksteinert.de/stuff/ssd_mobilenet_v2_coco_2018_03_29.zip
unzip ssd_mobilenet_v2_coco_2018_03_29.zip

wget https://www.patricksteinert.de/stuff/yolo_v3_fruits360_2021_07_23_4.zip
unzip yolo_v3_fruits360_2021_07_23_4.zip
```
                

## Build 

Build the project with the command

```
gradle build
```
                                  
The created jar with dependencies will be created at `build/libs`
    

## Standalone application

To execute the application as standalone, create a jar with all the dependencies.

***Note for macOS users**: due to security settings, you have to allow camera access and restart the application*

```
gradle jar
```

To configure the selected recognition method you need to set the environment variable 'RECOGNIZER_METHOD' to
one of the following values:
* fake (recognizes class "unclassified" with 99.9 confidence)
* ssd_mobilenet (uses default tensorflow ssd_mobilenet)
* yolo_v3 (uses the transfer-learned YoloV3 net)


Execute with ```java -jar build/libs/recognizer2-VERSION.jar```

## Use in the GMAF Framework

To use the code as a plugin within the GMAF, create a library and publish it in the local maven repository. 

```
gradle publishToMavenLocal
```

After the execution, the lib can be included as a maven dependency:

```
		<dependency>
			<groupId>de.patricksteinert</groupId>
			<artifactId>recognizer2</artifactId>
			<version>1.0</version>
		</dependency>
```

Make sure that you have also copied the model files to the GMAF project directory. To use the plugin, you can add it in the ``plugin.config``:

```
de.patricksteinert.recognizer.GMAF_PluginAdapter
```
