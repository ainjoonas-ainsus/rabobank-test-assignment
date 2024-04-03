## Rabobank demo task: Record validation

Validate a CSV file and a XML file on 2 counts using a simplified version of the SWIFT statement:

1. References should be unique
2. end balance has to be the sum of start value and mutation

In the task, the data is given in the 2 files. This project treats these as static inputs and simulates their appearance as files in a certain part of the server as files in its project resources folder.
To run the program, either run the prepackaged jar or build the gradle project.

## Building the Gradle project:
### Option 1 (Intellij):
If you run intellij, it's a matter of opening the Gradle toolset on the right side toolbar and running :bootRun under Tasks > application. It will build project and run the code.

### Option 2 (Manual):
If you are not using a tool like Intellij, navigate to project root using your favorite terminal application and run ./gradlew bootRun. Ensure you are running JDK21.