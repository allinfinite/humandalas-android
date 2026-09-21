// SPDX-FileCopyrightText: 2026 Daniel Levy
// SPDX-License-Identifier: GPL-3.0-or-later

package com.humandalas.humandalasapp;

import java.util.Objects;

public final class Card {
    public final int id;
    public final String stage;
    public final String title;
    public final String description;
    public final String participants;

    public Card(int id, String stage, String title, String description, String participants) {
        this.id = id;
        this.stage = Objects.requireNonNull(stage);
        this.title = Objects.requireNonNull(title);
        this.description = Objects.requireNonNull(description);
        this.participants = participants == null ? "" : participants;
    }
}
