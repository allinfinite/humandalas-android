// SPDX-FileCopyrightText: 2026 Daniel Levy
// SPDX-License-Identifier: GPL-3.0-or-later

package com.humandalas.humandalasapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.Random;
import org.junit.Test;

public class CardRepositoryTest {
    @Test
    public void recoveredDeckContainsFivePopulatedStages() {
        assertEquals(46, CardRepository.all().size());
        for (String stage : CardRepository.STAGES) {
            assertFalse(stage, CardRepository.forStage(stage).isEmpty());
        }
    }

    @Test
    public void guidedFlowReturnsOneCardPerStageInOrder() {
        List<Card> flow = CardRepository.guided(new Random(42));
        assertEquals(5, flow.size());
        for (int i = 0; i < flow.size(); i++) {
            assertEquals(CardRepository.STAGES.get(i), flow.get(i).stage);
        }
    }
}
