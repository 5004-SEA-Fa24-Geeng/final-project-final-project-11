````mermaid
classDiagram
    %% Main Application
    class PetForUApp {
        +main(args: String[])
    }

    %% Model Classes
    class User {
        -gender: String
        -preferredPetGender: String
        -mbti: String
        -energyLevel: int
        -space: double
        -budget: double
        -allergic: boolean
        -hasYard: boolean
        -timePerDay: double
        +User(gender, preferredPetGender, mbti, energyLevel, space, budget, allergic, hasYard, timePerDay)
        +getGender(): String
        +getPreferredPetGender(): String
        +getMbti(): String
        +getEnergyLevel(): int
        +getSpace(): double
        +getBudget(): double
        +isAllergic(): boolean
        +hasYard(): boolean
        +getTimePerDay(): double
    }

    class Pet {
        -name: String
        -type: String
        -breed: String
        -gender: String
        -mbti: String
        -energyLevel: int
        -requiredSpace: double
        -monthlyCost: double
        -allergenic: boolean
        -requiresYard: boolean
        -timeNeededPerDay: double
        -imagePath: String
        +Pet(name, type, breed, gender, mbti, energyLevel, requiredSpace, monthlyCost, allergenic, requiresYard, timeNeededPerDay, imagePath)
        +getName(): String
        +getType(): String
        +getBreed(): String
        +getGender(): String
        +getMbti(): String
        +getEnergyLevel(): int
        +getRequiredSpace(): double
        +getMonthlyCost(): double
        +isAllergenic(): boolean
        +requiresYard(): boolean
        +getTimeNeededPerDay(): double
        +getImagePath(): String
    }

    class PetWithScore {
        -pet: Pet
        -score: double
        +PetWithScore(pet, score)
        +getPet(): Pet
        +getScore(): double
    }

    %% Interface Classes
    class ICompatibilityCalculator {
        <<interface>>
        +calculate(user: User, pet: Pet): double
    }

    class IPetSorter {
        <<interface>>
        +sortAndExportToCSV(pets: List~Pet~, outputCsvPath: String): void
    }

    class IPetMatcher {
        <<interface>>
        +sortAndEvaluateBestMatch(user: User, csvFilePath: String): Pet
    }

    class IPetFilter {
        <<interface>>
        +filterPetsByCompatibility(pets: List~Pet~): List~Pet~
    }

    class IPetSearcher {
        <<interface>>
        +searchAndDisplay(petType: String, petBreed: String, csvPath: String): void
    }

    %% Implementation Classes
    class CompatibilityCalculator {
        +calculate(user: User, pet: Pet): double
        -getSpaceScore(userSpace: double, petSpace: double): double
        -getTimeScore(userTime: double, petTime: double): double
        -getBudgetScore(userBudget: double, petCost: double): double
        -getYardScore(userHasYard: boolean, petNeedsYard: boolean): double
        -getEnergyLevelScore(userEnergy: int, petEnergy: int): double
        -getMBTIScore(userMBTI: String, petMBTI: String): double
        -getGenderScore(preferredGender: String, petGender: String): double
    }

    class PetSorter {
        -user: User
        -calculator: ICompatibilityCalculator
        +PetSorter(user, calculator)
        +sortAndExportToCSV(pets: List~Pet~, outputCsvPath: String): void
        -formatPercentage(score: double): String
        -genderMatchPriority(p: PetWithScore): int
        -mbtiMatchCount(p: PetWithScore): int
        -spaceRatio(p: PetWithScore): double
        -energyDiff(p: PetWithScore): double
        -timeRatio(p: PetWithScore): double
        -budgetRatio(p: PetWithScore): double
        -yardMatch(p: PetWithScore): int
    }

    class PetMatcher {
        -petDatabase: List~Pet~
        +PetMatcher(petDatabase)
        +sortAndEvaluateBestMatch(user: User, csvFilePath: String): Pet
    }

    class PetFilter {
        -csvPath: String
        +PetFilter(csvPath)
        +filterPetsByCompatibility(pets: List~Pet~): List~Pet~
    }

    class PetSearcher {
        -consoleController: ConsoleController
        -petDatabase: List~Pet~
        +PetSearcher(consoleController)
        +PetSearcher(consoleController, petDatabase)
        +searchAndDisplay(petType: String, petBreed: String, csvPath: String): void
    }

    %% Controller Classes
    class ConsoleController {
        -user: User
        -calculator: ICompatibilityCalculator
        +ConsoleController(user, calculator)
        +displayBestMatch(csvFilePath: String): void
        +displayRecommendedPets(csvFilePath: String): void
        +displayAllPetsWithScores(csvFilePath: String): void
        +displaySearchResult(List~PetWithScore~): void
        -displayBestMatchDetails(petWithScore: PetWithScore): void
        -displayLowCompatibility(petWithScore: PetWithScore): void
        -displayNoCompatiblePets(): void
        -displayFilteredPets(filteredPets: List~PetWithScore~): void
        -openImage(imagePath: String): void
        -displayPetDetails(pet: Pet): void
        -formatPercentage(score: double): String
        -getScoreFromCSV(petName: String, petBreed: String, csvFilePath: String): double
    }

    class ArgsController {
        -helpRequested: boolean
        +ArgsController(args)
        +isHelp(): boolean
        +getHelp(): String
    }

    class PetManager {
        -petManager: PetManager
        -consoleController: ConsoleController
        -petSorter: PetSorter
        -petMatcher: PetMatcher
        -petFilter: PetFilter
        -petSearcher: PetSearcher
        -csvFilePath: String
        +getInstance(): PetManager
        +initialize(user: User): void
        +findBestMatch(): Pet
        +findRecommendedPets(): List~Pet~
        +displayAllPetsWithScores(): void
        +searchPets(petType: String, petBreed: String): void
    }

    %% Database Class
    class PetDatabase {
        -petList: List~Pet~
        +getAllPets(): List~Pet~
    }

    %% Relationships
    PetForUApp --> User: creates
    PetForUApp --> ArgsController: uses
    PetForUApp --> PetDatabase: gets pets from
    PetForUApp --> ConsoleController: uses
    PetForUApp --> PetManager: uses

    PetSorter ..|> IPetSorter: implements
    PetMatcher ..|> IPetMatcher: implements
    PetFilter ..|> IPetFilter: implements
    PetSearcher ..|> IPetSearcher: implements
    CompatibilityCalculator ..|> ICompatibilityCalculator: implements

    PetWithScore o-- Pet: contains

    PetSorter --> User: uses
    PetSorter --> ICompatibilityCalculator: uses
    PetSorter --> PetWithScore: creates

    PetMatcher --> Pet: returns

    PetFilter --> Pet: filters

    PetSearcher --> ConsoleController: uses

    PetManager --> ConsoleController: uses
    PetManager --> PetSorter: uses
    PetManager --> PetMatcher: uses
    PetManager --> PetFilter: uses
    PetManager --> PetSearcher: uses

    ConsoleController --> User: references
    ConsoleController --> ICompatibilityCalculator: uses
    ConsoleController --> PetWithScore: uses

    PetDatabase --> Pet: contains
```
````
