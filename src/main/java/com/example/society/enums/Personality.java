package com.example.society.enums;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * https://www.16personalities.com/personality-types
 */
public enum Personality {
    /**
     * Analyst
     * Imaginative and strategic thinkers, with a plan for everything.
     * rare
     */
    ARCHITECT(Set.of(Trait.INTROVERT, Trait.INTUITIVE, Trait.THINKING, Trait.JUDGING)),
    /**
     * Analyst
     * Innovative inventors with an unquenchable thirst for knowledge.
     * 3%
     */
    LOGICIAN(Set.of(Trait.INTROVERT, Trait.INTUITIVE, Trait.THINKING, Trait.PROSPECTING)),
    /**
     * Analyst
     * Bold, imaginative and strong-willed leaders, always finding a way – or making one.
     * 3%
     */
    COMMANDER(Set.of(Trait.EXTRAVERT, Trait.INTUITIVE, Trait.THINKING, Trait.JUDGING)),
    /**
     * Analyst
     * Smart and curious thinkers who cannot resist an intellectual challenge.
     */
    DEBATER(Set.of(Trait.EXTRAVERT, Trait.INTUITIVE, Trait.THINKING, Trait.PROSPECTING)),

    /**
     * Diplomat
     * Quiet and mystical, yet very inspiring and tireless idealists.
     * rare
     */
    ADVOCATE(Set.of(Trait.INTROVERT, Trait.INTUITIVE, Trait.FEELING, Trait.JUDGING)),

    /**
     * Diplomat
     * Introvert, Intuitive, Feeling, Prospecting
     * Poetic, kind and altruistic people, always eager to help a good cause.
     */
    MEDIATOR(Set.of(Trait.INTROVERT, Trait.INTUITIVE, Trait.FEELING, Trait.JUDGING)),

    /**
     * Diplomat
     * Charismatic and inspiring leaders, able to mesmerize their listeners.
     * Profession: Politicians, coaches, teachers
     * 2%
     */
    PROTAGONIST(Set.of(Trait.EXTRAVERT, Trait.INTUITIVE, Trait.FEELING, Trait.JUDGING)),

    /**
     * Diplomat
     * Extravert, Intuitive, Feeling, Prospecting
     * Enthusiastic, creative and sociable free spirits, who can always find a reason to smile.
     * 7%
     */
    CAMPAIGNER(Set.of(Trait.EXTRAVERT, Trait.INTUITIVE, Trait.FEELING, Trait.PROSPECTING)),

    /**
     * Sentinel
     * Practical and fact-minded individuals, whose reliability cannot be doubted.
     * 13%
     */
    LOGISTICIAN(Set.of(Trait.INTROVERT, Trait.OBSERVANT, Trait.THINKING, Trait.JUDGING)),

    /**
     * Sentinel
     * Very dedicated and warm protectors, always ready to defend their loved ones.
     */
    DEFENDER(Set.of(Trait.INTROVERT, Trait.OBSERVANT, Trait.FEELING, Trait.JUDGING)),

    /**
     * Sentinel
     * Excellent administrators, unsurpassed at managing things – or people.
     * 13%
     */
    EXECUTIVE(Set.of(Trait.EXTRAVERT, Trait.OBSERVANT, Trait.THINKING, Trait.JUDGING)),

    /**
     * Sentinel
     * Extraordinarily caring, social and popular people, always eager to help.
     */
    CONSUL(Set.of(Trait.EXTRAVERT, Trait.OBSERVANT, Trait.FEELING, Trait.JUDGING)),

    /**
     * Explorer
     * Bold and practical experimenters, masters of all kinds of tools.
     */
    VIRTUOSO(Set.of(Trait.INTROVERT, Trait.OBSERVANT, Trait.THINKING, Trait.PROSPECTING)),

    /**
     * Explorer
     * Flexible and charming artists, always ready to explore and experience something new.
     */
    ADVENTURER(Set.of(Trait.INTROVERT, Trait.OBSERVANT, Trait.FEELING, Trait.PROSPECTING)),

    /**
     * Explorer
     * Smart, energetic and very perceptive people, who truly enjoy living on the edge.
     */
    ENTREPRENEUR(Set.of(Trait.EXTRAVERT, Trait.OBSERVANT, Trait.THINKING, Trait.PROSPECTING)),

    /**
     * Explorer
     * Spontaneous, energetic and enthusiastic people – life is never boring around them.
     */
    ENTERTAINER(Set.of(Trait.EXTRAVERT, Trait.OBSERVANT, Trait.FEELING, Trait.PROSPECTING));


    private final Set<Trait> traits;

    Personality(Set<Trait> traits) {
        this.traits = traits;
    }

    public Set<Trait> getTraits() {
        return traits;
    }

    private static final List<Personality> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Personality getAny() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
