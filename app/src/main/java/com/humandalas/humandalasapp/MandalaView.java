// SPDX-FileCopyrightText: 2026 Daniel Levy
// SPDX-License-Identifier: GPL-3.0-or-later

package com.humandalas.humandalasapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public final class MandalaView extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int accent = Color.rgb(118, 90, 145);

    public MandalaView(Context context) {
        super(context);
        setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);
    }

    public void setAccent(int color) {
        accent = color;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;
        float radius = Math.min(getWidth(), getHeight()) * 0.20f;

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(accent);
        paint.setAlpha(215);
        for (int i = 0; i < 8; i++) {
            double angle = Math.PI * 2 * i / 8d;
            float x = cx + (float) Math.cos(angle) * radius * 1.65f;
            float y = cy + (float) Math.sin(angle) * radius * 1.65f;
            canvas.drawCircle(x, y, radius, paint);
        }
        paint.setColor(Color.rgb(56, 122, 99));
        paint.setAlpha(235);
        canvas.drawCircle(cx, cy, radius * 1.35f, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(radius * 0.18f);
        paint.setColor(Color.WHITE);
        paint.setAlpha(230);
        canvas.drawCircle(cx, cy, radius * 0.72f, paint);
    }
}
