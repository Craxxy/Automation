Feature: Registration
#This feature file describes the scenari outline with the following differences of examples.
#Each example is the description of the given scenario and its outcome from the example table.
  Scenario Outline: Registration - <DescriptionOfScenario>
    Given I choose "<Browser>"
    Given I enter my date of birth "<DayOfBirth>"
    And I enter firstname "<FirstName>"
    And I enter lastname "<Surname>"
    And I enter email & confirm email "<GeneratedEmail>"
    And I enter password & confirm password "<Password>"
    And I have terms & conditions "<Box>"
    When I am Over 18 & code of conduct checked
    When I press join button
    Then I am registered

    Examples:
      | DescriptionOfScenario              | Browser | DayOfBirth | FirstName | Surname | GeneratedEmail | Password      |  | Box       |
      | 1.Successful Registration          | Edge    | 01/01/1995 | Elias     | Hansson |                | Password12345 |  | checked   |
      | 2.Surname Missing                  | Chrome  | 01/02/1996 | Elias     |         |                | Password12345 |  | checked   |
      | 3.Password Missmatching            | Edge    | 01/03/1997 | Elias     | Husayn  |                | Password1234  |  | checked   |
      | 4.Terms and conditions not checked | Chrome  | 01/04/1998 | Elias     | Hans    |                | Password12345 |  | unchecked |



