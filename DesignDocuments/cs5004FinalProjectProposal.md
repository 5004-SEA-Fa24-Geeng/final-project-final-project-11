# Pet: Animal Shelter Listing and Adoption Tracker

## Team Name: 11
## Product Proposal

---

## Overview

Pet is a web-based application designed to help animal shelters manage and present information about pets available for adoption. The system allows shelter staff to create and maintain pet profiles, while potential adopters can browse, search, and organize their favorite animals. The goal of this project is to streamline shelter operations and improve visibility for animals in need of homes.

---

## Key Features

### For Shelter Staff
- Add, edit, and delete pet profiles
- Update adoption status (Available, Pending, Adopted)

### For Public Users
- View pets in a structured list or gallery
- Filter and sort pets by species, age, size, and temperament
- Search pets by name or breed
- Add pets to a personal favorites list
- Review and optionally share the favorites list

---

## Data Model

Each pet will be represented using the following structure:

```json
{
  "name": "Bella",
  "species": "Dog",
  "breed": "Labrador Retriever",
  "age": 3,
  "size": "Large",
  "temperament": "Friendly",
  "status": "Available",
  "imageURL": "https://example.com/bella.jpg",
  "description": "Loyal and playful, great with children."
}
```

## Timeline

| Week | Milestone                                      | Responsibility     |
|------|-----------------------------------------------|--------------------|
| 1    | Finalize product design, define data model     | All Team Members   |
| 2    | Implement pet list display and filtering       | Development Team   |
| 3    | Develop admin features and favorites view      | All Team Members   |
| 4    | Final integration, testing, and documentation  | All Team Members   |


## Team Roles

| Member Name         | Responsibilities                                     |
|---------------------|------------------------------------------------------|
| [Your Name]         | Pet list display, filtering logic, prototype design  |
| Member 2            | Admin interface and pet profile creation             |
| Member 3            | Favorites feature and user interface styling         |
| Member 4            | Integration, testing, and documentation              |


## Project Timeline

### Week 1
- Finalize the app’s core features and system design using the MVC pattern.
- Design key data model classes: `Pet`, `UserProfile`, `PetType`, `PetDatabase`.
- Draft the MBTI and lifestyle questionnaire structure and flow.
- Set up the GitHub repository and establish the project folder structure.
- Define the rule-based matching strategy and scoring rubric.
- Create a shared task board to organize development responsibilities.

### Week 2
- Implement user input handling and construct the `UserProfile` based on questionnaire responses.
- Populate the `PetDatabase` with diverse sample pet entries.
- Develop the `Matcher` interface and begin implementing the scoring logic in `PetMatcher`.
- Build the initial version of `ConsoleView` to simulate user interaction with placeholder outputs.

### Week 3
- Finalize and refine the matching algorithm and scoring system.
- Integrate all components using the `PetMatchController` to connect input, matching, and output.
- Enhance the console output formatting for clear and user-friendly display of match results.
- Begin writing unit tests for each module (model, matcher, input/output).

### Week 4
- Conduct full application integration testing and fix any bugs.
- Complete all unit tests and verify accuracy of matches.
- Write and finalize documentation including README, JavaDocs, and user instructions.
- Rehearse and deliver the final live demo presentation.
- Submit the complete codebase, documentation, and retrospective.

