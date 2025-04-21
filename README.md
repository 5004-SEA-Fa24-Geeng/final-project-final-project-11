[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/IE0ITl4j)
# Final Project for CS 5004 - (APPLICATION NAME/Update)

# Final Project for CS 5004 – PetForU

## Team Members
- **Hao Chen** – https://github.com/5004-SEA-Fa24-Geeng/hello-world-chenhbb8
- **Wenxuan Yang** – 
- **Shiqi Yang** – [5004-SEA-Fa24-Geeng/5004-sea-sp25-geeng-classroom-assignment-1-module-01-02-hello-world-hello_world](https://github.com/5004-SEA-Fa24-Geeng/5004-sea-sp25-geeng-classroom-assignment-1-module-01-02-hello-world-hello_world)

- **Yifan Tian** – 

## Application Name
**PetForU** – A personalized pet matching system that recommends the most compatible pets based on user lifestyle factors and MBTI personality compatibility.

## Features
- Be able to load in lists of items / previously saved lists, and modify them.
- Be able to search for items in the collection
- Be able to sort items in the collection
- Be able to filter items in the collection
- Include images for items

## Description
PetForU is a Java console application that collects user preferences via an interactive questionnaire (MBTI type, energy level, space, budget, allergies, time availability, etc.), computes a compatibility score for each pet in a curated database, and provides:
- A single “best match” recommendation
- A list of all pets with ≥ 80% compatibility
- A search-by-type-and-breed feature
- Exportable results to CSV

## Design Documents and Manuals
Project Proposal & Timeline: 
    https://github.com/5004-SEA-Fa24-Geeng/final-project-final-project-11/blob/main/DesignDocuments/Group%2011FinalProject%20Proposal.md

## Project Structure
```plaintext
final-project-final-project-11/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── controller/
│   │       │   ├── ArgsController.java
│   │       │   ├── ConsoleController.java
│   │       │   └── PetManager.java
│   │       ├── database/
│   │       │   └── PetDatabase.java
│   │       ├── model/
│   │       │   ├── CompatibilityCalculator.java
│   │       │   ├── ICompatibilityCalculator.java
│   │       │   ├── IPetFilter.java
│   │       │   ├── IPetMatcher.java
│   │       │   ├── IPetSearcher.java
│   │       │   ├── IPetSorter.java
│   │       │   ├── Pet.java
│   │       │   ├── PetFilter.java
│   │       │   ├── PetMatcher.java
│   │       │   ├── PetSearcher.java
│   │       │   ├── PetSorter.java
│   │       │   ├── PetWithScore.java
│   │       │   └── User.java
│   │       └── PetForUApp.java
│   └── test/
│       └── java/
│           ├── controller/
│           │   ├── ConsoleControllerTest.java
│           │   └── PetManagerTest.java
│           ├── model/
│           │   ├── CompatibilityCalculatorTest.java
│           │   ├── PetFilterTest.java
│           │   ├── PetMatcherTest.java
│           │   ├── PetSearcherTest.java
│           │   └── PetSorterTest.java
│           └── PetForUAppTest.java
└── README.md
```
## How to Run the Application

### Prerequisites
- Java JDK 11 or higher
- (Optional) IntelliJ IDEA or another Java IDE
- JUnit 5 (if running tests via CLI)

# Clone the repository
git clone https://github.com/5004-SEA-Fa24-Geeng/final-project-final-project-11.git

# Run the application
```
java -cp out PetForUApp
```
For details, please check the `manual` folder.

## Resources and References

### Data Sources
- Pet data used in this application was generated with assistance from ChatGPT, including pet names, breeds, personality traits, and compatibility factors.
- MBTI personality type descriptions and compatibility insights were adapted from general psychology resources with assistance from ChatGPT.

### Technical References
- Oracle Java Documentation: [Java SE Documentation](https://docs.oracle.com/en/java/javase/17/)
- Java CSV handling approach adapted from: [Baeldung CSV Processing in Java](https://www.baeldung.com/java-csv)
- UML Diagrams created using Mermaid syntax: [Mermaid Documentation](https://mermaid-js.github.io/mermaid/#/)