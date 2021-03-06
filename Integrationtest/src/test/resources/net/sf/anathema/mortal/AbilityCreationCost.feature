@Integration
Feature: Anathema calculates creation point costs for Abilities

  Scenario: A Mortal pays bonus when she raises an Ability above the threshold
    Given a new Mortal using rules for EverydayHero
    When I set her Archery to 4
    Then she has 3 ability dots spent
    And she has spent 2 bonus points
