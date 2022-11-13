Ability: Create New Campaign

  Gary is a Game Master (GM) who is creating a new campaign for his friends. He is used to following the pen and paper Campaign Planning Form and wants the same level of guidance in the online system.

  @ronbo
  Scenario: Campaign Name
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign a name to the campaign (domain-level work).

    Given Gary has started creating a new campaign
    When Gary provides the campaign name
    Then the campaign is assigned the specified name

  @wip
  Scenario: Starting Year
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign a starting year to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided a starting year
    Then the campaign is assigned the specified starting year

  @wip
  Scenario: Game Time
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the rate game time passes to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the rate game time passes
    Then the campaign is assigned the specified game time rate

  @wip
  Scenario: Genre
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign a genre to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the genre
    Then the campaign is assigned the specified genre

  @wip
  Scenario Outline: Campaign Style
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to specify the campaign style (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the campaign style of "<campaign style>"
    Then the campaign style is "<outcome>"

    Examples: valid and invalid styles
      |campaign style|outcome     |
      |realistic     |accepted    |
      |cinematic     |accepted    |
      |horror        |not accepted|

  @wip
  Scenario Outline: Planes of existence
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to specify the planes of existence (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the planes of existence value of "<planes of existence>"
    Then the planes of existence is "<outcome>"

    Examples: valid and invalid planes of existence
      |planes of existence|outcome     |
      |single             |accepted    |
      |multiple           |accepted    |
      |unknown            |not accepted|

  @wip
  Scenario: Theme
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign a theme to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the theme
    Then the campaign is assigned the specified theme

  @wip
  Scenario Outline: Base location
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to specify the base location (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the base location of "<base location>"
    Then the base location is "<outcome>"

    Examples: valid and invalid base locations
      |base location|outcome     |
      |city         |accepted    |
      |nation       |accepted    |
      |empire       |accepted    |
      |planet       |accepted    |
      |unknown      |not accepted|

  @wip
  Scenario: Society Type
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign a society/government type to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the society type
    Then the campaign is assigned the specified society type

  @wip
  Scenario: Control Rating
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign a control rating to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the control rating
    Then the campaign is assigned the specified control rating

  @wip
  Scenario: Control Rating Exceptions
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign a control rating exceptions to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the control rating exceptions
    Then the campaign is assigned the specified control rating exceptions

  @wip
  Scenario Outline: Tech Level
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to specify the technology level (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the tech level of "<tech level>"
    Then the tech level is "<outcome>"

    Examples: valid and invalid base locations
      |tech level           |outcome     |
      |Stone Age            |accepted    |
      |Bronze Age           |accepted    |
      |Iron Age             |accepted    |
      |Medieval             |accepted    |
      |Age of Sail          |accepted    |
      |Industrial Revolution|accepted    |
      |Mechanized Age       |accepted    |
      |Nuclear Age          |accepted    |
      |Digital Age          |accepted    |
      |Microtech Age        |accepted    |
      |Robotic Age          |accepted    |
      |Age of Exotic Matter |accepted    |
      |unknown              |not accepted|

  @wip
  Scenario: Tech Level Exceptions
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign tech level exceptions to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the tech level exceptions
    Then the campaign is assigned the specified tech level exceptions

  @wip
  Scenario: World Description
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign a brief description of important neighboring powers, political/economic situation, etc to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the world description
    Then the campaign is assigned the specified world description

  @wip
  Scenario: Suggested Reading
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign suggested reading to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided suggested reading
    Then the campaign is assigned the suggested reading

  @wip
  Scenario Outline: Starting Character Points
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to specify the starting character points to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the starting character points of "<starting character category>"
    Then the starting character points is <points>

    Examples: character points category to character points
      |starting character category|points|
      |Feeble                     |25    |
      |Average                    |50    |
      |Competent                  |75    |
      |Exceptional                |100   |
      |Heroic                     |200   |
      |Larger Than-Life           |300   |
      |Legendary                  |500   |
      |unknown                    |0     |

  @wip
  Scenario: Useful Character Types
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign a description of useful character types to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided useful character types
    Then the campaign is assigned the useful character types

  @wip
  Scenario: Useless Character Types
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign a description of useless character types to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided useless character types
    Then the campaign is assigned the useless character types

  @wip
  Scenario: Appropriate Professions
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign a description of appropriate professions to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided appropriate professions
    Then the campaign is assigned the appropriate professions

  @wip
  Scenario: Inappropriate Professions
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign a description of inappropriate professions to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided inappropriate professions
    Then the campaign is assigned the inappropriate professions

  @wip
  Scenario: Allowed Player Character Races
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign a list of allowed player character races to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided allowed player character races
    Then the campaign is assigned the allowed player character races

  @wip
  Scenario: Starting Wealth
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the starting wealth level to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the starting wealth level
    Then the campaign is assigned the starting wealth level

  @wip
  Scenario: Starting Wealth Levels Allowed
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the allowed starting wealth levels to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the starting wealth levels
    Then the campaign is assigned the starting wealth levels

  @wip
  Scenario: Starting Status Levels Allowed
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the allowed starting status levels to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the starting status levels
    Then the campaign is assigned the starting status levels

  @wip
  Scenario: Starting Technology Levels Allowed
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the allowed starting technology levels to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the starting technology levels
    Then the campaign is assigned the starting technology levels

  @wip
  Scenario: Allowed Languages
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the allowed languages to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the allowed languages
    Then the campaign is assigned the allowed languages

  @wip
  Scenario: Allowed Cultural Familiarities
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the allowed cultural familiarities to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the cultural familiarities
    Then the campaign is assigned the cultural familiarities

  @wip
  Scenario: Required Advantages
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the required advantages to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the required advantages
    Then the campaign is assigned the required advantages

  @wip
  Scenario: Required Disadvantages
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the required disadvantages to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the required disadvantages
    Then the campaign is assigned the required disadvantages

  @wip
  Scenario: Required Skills
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the required skills to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the required skill
    Then the campaign is assigned the required skills

  @wip
  Scenario: Appropriate Advantages
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the appropriate advantages to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the appropriate advantages
    Then the campaign is assigned the appropriate advantages

  @wip
  Scenario: Appropriate Disadvantages
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the appropriate disadvantages to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the appropriate disadvantages
    Then the campaign is assigned the appropriate disadvantages

  @wip
  Scenario: Appropriate Skills
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the appropriate skills to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the appropriate skill
    Then the campaign is assigned the appropriate skills

  @wip
  Scenario: Inappropriate Advantages
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the inappropriate advantages to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the inappropriate advantages
    Then the campaign is assigned the inappropriate advantages

  @wip
  Scenario: Inappropriate Disadvantages
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the inappropriate disadvantages to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the inappropriate disadvantages
    Then the campaign is assigned the inappropriate disadvantages

  @wip
  Scenario: Inappropriate Skills
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the inappropriate skills to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the inappropriate skill
    Then the campaign is assigned the inappropriate skills

  @wip
  Scenario: Appropriate Patrons
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the appropriate patrons to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the appropriate patrons
    Then the campaign is assigned the appropriate patrons

  @wip
  Scenario: Appropriate Enemies
  In order to create a valid campaign (valuable outcome), Gary (role in the domain) needs to assign the appropriate enemies to the campaign (domain-level work).

    Given Gary is creating a new campaign
    When Gary provided the appropriate enemies
    Then the campaign is assigned the appropriate enemies