// SPDX-FileCopyrightText: 2026 Daniel Levy
// SPDX-License-Identifier: GPL-3.0-or-later

package com.humandalas.humandalasapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class CardRepository {
    public static final List<String> STAGES = Collections.unmodifiableList(Arrays.asList(
            "Connection", "Intention", "Cultivation", "Offering", "Anchor"));

    private static Card c(int id, String stage, String title, String description, String participants) {
        return new Card(id, stage, title, description, participants);
    }

    private static final List<Card> ALL = Collections.unmodifiableList(Arrays.asList(
            c(1, "Connection", "Natural Rhythm", "Feel the earth. Recognize the patterns of nature within you.", "1+"),
            c(2, "Connection", "Galactic Spiral", "Connect the tips of your fingers and curl hands into the center, then bring your thumbs to the outside.", "2–15"),
            c(3, "Connection", "Heaven & Earth", "Connect index fingers and thumbs in the center to create a downward pyramid. Open hands and bring them up and over into an upward pyramid.", "2–6"),
            c(4, "Connection", "Love Beam", "Create a Galactic Spiral, then connect thumbs in the center.", "2–6"),
            c(5, "Connection", "Star Spiral", "Create a Galactic Spiral. Extend index fingers, connecting with the thumb of every other person.", "3–7"),
            c(6, "Connection", "Celtic Spiral", "Create a Galactic Spiral and curl your index fingers into the middle finger of the hand of the person to the left.", "3–4"),
            c(7, "Connection", "Peace Star", "Connect peace signs to create a star. Use both hands to create a Super Star.", "3–20"),
            c(8, "Connection", "Heart", "Create a heart with your hands and pump it on your chest. Open your hands and create a hand heart with your neighbors.", "2+"),
            c(9, "Connection", "Hold Hands", "Hold hands in a circle.", "2+"),

            c(11, "Intention", "Love", "Share what you love. Feel that love.", ""),
            c(12, "Intention", "Gratitude", "Share what you are grateful for. Feel that gratitude.", ""),
            c(13, "Intention", "Freedom", "Share what makes you feel free. Feel that freedom.", ""),
            c(14, "Intention", "Peace", "Share what brings you peace. Feel that peace.", ""),
            c(15, "Intention", "Health", "Share what makes you healthy. Feel healthy.", ""),
            c(16, "Intention", "Bliss", "Share what makes you feel blissful. Feel the bliss.", ""),
            c(17, "Intention", "Balance", "Share what makes you feel in balance. Feel balanced.", ""),
            c(18, "Intention", "Forgiveness", "It is time to let go and forgive. Feel forgiveness.", ""),

            c(20, "Cultivation", "Vortex", "Move hands in a circular motion in rhythm with the wind, tides, heartbeat, and breath.", ""),
            c(21, "Cultivation", "Shake", "Shake and vibrate physically while you visualize light radiating from the movement.", ""),
            c(22, "Cultivation", "Wave Motion", "Move hands in a fluid wave pattern.", ""),
            c(23, "Cultivation", "Twinkle Fingers", "Wiggle your fingers.", ""),
            c(24, "Cultivation", "DNA Arms", "With continuous movement, weave arms loosely around each other.", ""),
            c(25, "Cultivation", "Galactic Dance", "Dance in sync with those around you and nature’s rhythms.", ""),
            c(26, "Cultivation", "Massage", "Share loving touch by massaging each other.", ""),
            c(27, "Cultivation", "Tone", "Sing a beautiful sound in resonance, all together. Use the tone Om, or sing or hum a song.", ""),
            c(28, "Cultivation", "Heart Charger", "Send the energy to each individual. Touch their chest, sing their name, then move to the next person clockwise.", ""),
            c(29, "Cultivation", "Love Blasts", "Inhale and tense your hands, then as you exhale, direct your energy toward each person, sending them a blast of love.", ""),
            c(30, "Cultivation", "Pass the Heart", "Touch the heart of the person to your right, then, with your breath, switch to the person on your left. Repeat.", ""),
            c(31, "Cultivation", "Breathe Together", "All together, inhale through your noses, then exhale from your mouths. Repeat.", ""),
            c(32, "Cultivation", "Notice Beauty", "Notice the beauty of your group and space. See them becoming more and more beautiful.", ""),
            c(33, "Cultivation", "Chakra Spin", "Move like a spiral. Start at the base of your spine and allow the movement to swirl slowly up your body to your head.", ""),

            c(35, "Offering", "All of Creation", "Offer the blessing to All of Creation.", ""),
            c(36, "Offering", "Region", "Offer the blessing to a region, the whole world, or the galaxy.", ""),
            c(37, "Offering", "Project", "Offer the blessing to a project or cause you want to see succeed.", ""),
            c(38, "Offering", "Event", "Offer the blessing to an upcoming event to make it extra special.", ""),
            c(39, "Offering", "People", "Offer the blessing to a person or group of people.", ""),
            c(40, "Offering", "Element", "Offer the blessing to an element, such as water, earth, air, fire, or spirit.", ""),

            c(42, "Anchor", "Your Space", "Anchor the memory into your space by visualizing ripples of light pulsing from it.", ""),
            c(43, "Anchor", "Stone", "Anchor the memory into a stone or crystal and put it in a special place.", ""),
            c(44, "Anchor", "Object", "Anchor the memory into an object you regularly use and cherish.", ""),
            c(45, "Anchor", "Sound", "Anchor the memory into a sound from your environment, a tone, or a new melody.", ""),
            c(46, "Anchor", "Water", "Anchor the memory into drinking water to make it super refreshing, and drink it.", ""),
            c(47, "Anchor", "Food", "Anchor the memory into food, then eat it. Imaginary food works too.", ""),
            c(48, "Anchor", "Art", "Anchor the memory into artwork or create new artwork as a representation.", ""),
            c(50, "Anchor", "Number", "Anchor the memory into a number, time, or date.", ""),
            c(51, "Anchor", "Yourself", "Anchor the memory into yourself. Visualize a cup of light in your hands, then drink and bathe in it.", "")
    ));

    private CardRepository() {}

    public static List<Card> all() {
        return ALL;
    }

    public static List<Card> forStage(String stage) {
        List<Card> cards = new ArrayList<>();
        for (Card card : ALL) {
            if (card.stage.equals(stage)) cards.add(card);
        }
        return cards;
    }

    public static List<Card> guided(Random random) {
        List<Card> result = new ArrayList<>();
        for (String stage : STAGES) {
            List<Card> choices = forStage(stage);
            result.add(choices.get(random.nextInt(choices.size())));
        }
        return result;
    }
}
