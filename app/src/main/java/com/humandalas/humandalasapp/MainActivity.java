// SPDX-FileCopyrightText: 2026 Daniel Levy
// SPDX-License-Identifier: GPL-3.0-or-later

package com.humandalas.humandalasapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public final class MainActivity extends Activity {
    private static final int INK = Color.rgb(39, 35, 58);
    private static final int CREAM = Color.rgb(255, 249, 240);
    private static final int SAGE = Color.rgb(56, 122, 99);
    private static final int LAVENDER = Color.rgb(118, 90, 145);
    private static final int CORAL = Color.rgb(200, 90, 84);
    private static final int GOLD = Color.rgb(190, 139, 48);
    private static final int BLUE = Color.rgb(57, 112, 145);
    private static final int[] STAGE_COLORS = {CORAL, GOLD, SAGE, BLUE, LAVENDER};

    private FrameLayout host;
    private final ArrayList<Runnable> backStack = new ArrayList<>();
    private List<Card> flow;
    private int flowIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(CREAM);
        window.setNavigationBarColor(CREAM);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
            window.setDecorFitsSystemWindows(false);
        }

        host = new FrameLayout(this);
        host.setBackgroundColor(CREAM);
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            host.setOnApplyWindowInsetsListener((view, insets) -> {
                android.graphics.Insets bars = insets.getInsets(WindowInsets.Type.systemBars());
                view.setPadding(bars.left, bars.top, bars.right, bars.bottom);
                return insets;
            });
        }
        setContentView(host);
        showHome(false);
    }

    @Override
    public void onBackPressed() {
        if (backStack.isEmpty()) {
            super.onBackPressed();
        } else {
            backStack.remove(backStack.size() - 1).run();
        }
    }

    private void replace(View view) {
        host.removeAllViews();
        host.addView(view, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void showHome(boolean fromBack) {
        if (fromBack) backStack.clear();
        ScrollView scroll = new ScrollView(this);
        LinearLayout page = column(24);
        page.setPadding(dp(24), dp(22), dp(24), dp(32));
        scroll.addView(page);

        MandalaView mark = new MandalaView(this);
        mark.setContentDescription("Humandalas symbol");
        page.addView(mark, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(210)));

        TextView title = text("HUMANDALAS", 34, Typeface.BOLD, INK);
        title.setGravity(Gravity.CENTER);
        page.addView(title, matchWrap());
        TextView lead = text("Gather. Connect. Create a shared intention.", 18, Typeface.NORMAL, INK);
        lead.setGravity(Gravity.CENTER);
        lead.setPadding(0, dp(8), 0, dp(28));
        page.addView(lead, matchWrap());

        Button begin = button("Begin a guided Humandala", SAGE);
        begin.setOnClickListener(v -> startFlow());
        page.addView(begin, matchHeight(56));

        Button browse = outlineButton("Browse all 46 cards");
        LinearLayout.LayoutParams browseParams = matchHeight(56);
        browseParams.topMargin = dp(12);
        page.addView(browse, browseParams);
        browse.setOnClickListener(v -> showBrowser("Connection"));

        TextView how = text("HOW IT FLOWS", 14, Typeface.BOLD, LAVENDER);
        how.setPadding(0, dp(34), 0, dp(10));
        page.addView(how, matchWrap());
        String[] notes = {
                "1  CONNECTION  •  Come together",
                "2  INTENTION  •  Choose what you will nurture",
                "3  CULTIVATION  •  Move, breathe, sound, and feel",
                "4  OFFERING  •  Share the blessing",
                "5  ANCHOR  •  Carry the moment with you"
        };
        for (int i = 0; i < notes.length; i++) {
            TextView note = text(notes[i], 15, Typeface.NORMAL, INK);
            note.setPadding(dp(14), dp(13), dp(14), dp(13));
            note.setBackground(roundRect(lighten(STAGE_COLORS[i]), 14, 0, 0));
            LinearLayout.LayoutParams noteParams = matchWrap();
            noteParams.bottomMargin = dp(8);
            page.addView(note, noteParams);
        }
        replace(scroll);
    }

    private void startFlow() {
        flow = CardRepository.guided(new Random());
        flowIndex = 0;
        backStack.add(() -> showHome(true));
        showFlowCard();
    }

    private void showFlowCard() {
        Card card = flow.get(flowIndex);
        ScrollView scroll = new ScrollView(this);
        LinearLayout page = column(18);
        page.setPadding(dp(22), dp(20), dp(22), dp(28));
        scroll.addView(page);
        page.addView(headerButton("‹  End flow", v -> showHome(true)), matchHeight(48));

        TextView progress = text("STEP " + (flowIndex + 1) + " OF 5  •  " + card.stage.toUpperCase(Locale.ROOT),
                14, Typeface.BOLD, stageColor(card.stage));
        progress.setGravity(Gravity.CENTER);
        progress.setPadding(0, dp(18), 0, dp(6));
        page.addView(progress, matchWrap());

        MandalaView art = new MandalaView(this);
        art.setAccent(stageColor(card.stage));
        page.addView(art, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(230)));

        TextView title = text(card.title, 30, Typeface.BOLD, INK);
        title.setGravity(Gravity.CENTER);
        page.addView(title, matchWrap());
        if (!card.participants.isEmpty()) {
            TextView people = text(card.participants + " people", 14, Typeface.BOLD, stageColor(card.stage));
            people.setGravity(Gravity.CENTER);
            people.setPadding(0, dp(5), 0, 0);
            page.addView(people, matchWrap());
        }
        TextView description = text(card.description, 20, Typeface.NORMAL, INK);
        description.setGravity(Gravity.CENTER);
        description.setLineSpacing(0, 1.18f);
        description.setPadding(dp(8), dp(18), dp(8), dp(28));
        page.addView(description, matchWrap());

        LinearLayout controls = new LinearLayout(this);
        controls.setOrientation(LinearLayout.HORIZONTAL);
        controls.setGravity(Gravity.CENTER);
        if (flowIndex > 0) {
            Button previous = outlineButton("Previous");
            previous.setOnClickListener(v -> { flowIndex--; showFlowCard(); });
            controls.addView(previous, weightedHeight(1, 54));
        }
        Button next = button(flowIndex == 4 ? "Complete" : "Continue", stageColor(card.stage));
        LinearLayout.LayoutParams nextParams = weightedHeight(1, 54);
        if (flowIndex > 0) nextParams.leftMargin = dp(10);
        controls.addView(next, nextParams);
        next.setOnClickListener(v -> {
            if (flowIndex == 4) showComplete();
            else { flowIndex++; showFlowCard(); }
        });
        page.addView(controls, matchWrap());

        Button redraw = headerButton("Choose another " + card.stage.toLowerCase(Locale.ROOT) + " card", v -> {
            List<Card> choices = CardRepository.forStage(card.stage);
            Card current = flow.get(flowIndex);
            int nextIndex = (choices.indexOf(current) + 1) % choices.size();
            flow.set(flowIndex, choices.get(nextIndex));
            showFlowCard();
        });
        LinearLayout.LayoutParams redrawParams = matchHeight(48);
        redrawParams.topMargin = dp(10);
        page.addView(redraw, redrawParams);
        replace(scroll);
    }

    private void showComplete() {
        ScrollView scroll = new ScrollView(this);
        LinearLayout page = column(20);
        page.setGravity(Gravity.CENTER_HORIZONTAL);
        page.setPadding(dp(26), dp(42), dp(26), dp(32));
        scroll.addView(page);
        MandalaView mark = new MandalaView(this);
        page.addView(mark, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(260)));
        TextView title = text("Humandala complete", 30, Typeface.BOLD, INK);
        title.setGravity(Gravity.CENTER);
        page.addView(title, matchWrap());
        TextView copy = text("Take one quiet breath together. Notice what changed, and carry the shared intention into what comes next.", 19, Typeface.NORMAL, INK);
        copy.setGravity(Gravity.CENTER);
        copy.setLineSpacing(0, 1.2f);
        copy.setPadding(0, dp(12), 0, dp(28));
        page.addView(copy, matchWrap());
        Button again = button("Create another Humandala", SAGE);
        again.setOnClickListener(v -> startFlow());
        page.addView(again, matchHeight(56));
        Button home = outlineButton("Return home");
        home.setOnClickListener(v -> showHome(true));
        LinearLayout.LayoutParams homeParams = matchHeight(56);
        homeParams.topMargin = dp(12);
        page.addView(home, homeParams);
        replace(scroll);
    }

    private void showBrowser(String selectedStage) {
        backStack.clear();
        backStack.add(() -> showHome(true));
        LinearLayout page = column(0);
        page.setPadding(dp(18), dp(14), dp(18), dp(18));
        page.addView(headerButton("‹  Home", v -> showHome(true)), matchHeight(48));
        TextView title = text("Card library", 30, Typeface.BOLD, INK);
        title.setPadding(dp(6), dp(12), 0, dp(8));
        page.addView(title, matchWrap());

        Spinner spinner = new Spinner(this);
        ArrayAdapter<String> stages = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, CardRepository.STAGES);
        spinner.setAdapter(stages);
        spinner.setSelection(CardRepository.STAGES.indexOf(selectedStage));
        page.addView(spinner, matchHeight(52));

        ListView list = new ListView(this);
        list.setDividerHeight(dp(8));
        list.setDivider(null);
        list.setPadding(0, dp(10), 0, 0);
        page.addView(list, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));

        Runnable update = () -> {
            String stage = String.valueOf(spinner.getSelectedItem());
            List<Card> cards = CardRepository.forStage(stage);
            list.setAdapter(new CardAdapter(cards));
            list.setOnItemClickListener((parent, view, position, id) -> showDetail(cards.get(position), stage));
        };
        spinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) { update.run(); }
            @Override public void onNothingSelected(android.widget.AdapterView<?> parent) { }
        });
        replace(page);
    }

    private void showDetail(Card card, String returnStage) {
        backStack.add(() -> showBrowser(returnStage));
        ScrollView scroll = new ScrollView(this);
        LinearLayout page = column(18);
        page.setPadding(dp(22), dp(18), dp(22), dp(28));
        scroll.addView(page);
        page.addView(headerButton("‹  Card library", v -> showBrowser(returnStage)), matchHeight(48));
        TextView stage = text(card.stage.toUpperCase(Locale.ROOT) + "  •  CARD " + card.id,
                14, Typeface.BOLD, stageColor(card.stage));
        stage.setGravity(Gravity.CENTER);
        stage.setPadding(0, dp(16), 0, 0);
        page.addView(stage, matchWrap());
        MandalaView art = new MandalaView(this);
        art.setAccent(stageColor(card.stage));
        page.addView(art, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(250)));
        TextView title = text(card.title, 31, Typeface.BOLD, INK);
        title.setGravity(Gravity.CENTER);
        page.addView(title, matchWrap());
        TextView description = text(card.description, 20, Typeface.NORMAL, INK);
        description.setGravity(Gravity.CENTER);
        description.setLineSpacing(0, 1.2f);
        description.setPadding(dp(6), dp(16), dp(6), dp(20));
        page.addView(description, matchWrap());
        Button share = outlineButton("Share this card");
        share.setOnClickListener(v -> share(card));
        page.addView(share, matchHeight(54));
        replace(scroll);
    }

    private void share(Card card) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Humandalas: " + card.title);
        intent.putExtra(Intent.EXTRA_TEXT, card.stage + " — " + card.title + "\n\n" + card.description);
        startActivity(Intent.createChooser(intent, "Share Humandalas card"));
    }

    private int stageColor(String stage) {
        int index = CardRepository.STAGES.indexOf(stage);
        return index < 0 ? LAVENDER : STAGE_COLORS[index];
    }

    private final class CardAdapter extends android.widget.BaseAdapter {
        private final List<Card> cards;
        CardAdapter(List<Card> cards) { this.cards = cards; }
        @Override public int getCount() { return cards.size(); }
        @Override public Card getItem(int position) { return cards.get(position); }
        @Override public long getItemId(int position) { return cards.get(position).id; }
        @Override public View getView(int position, View oldView, ViewGroup parent) {
            Card card = getItem(position);
            LinearLayout item = column(3);
            item.setPadding(dp(18), dp(15), dp(18), dp(15));
            item.setBackground(roundRect(Color.WHITE, 16, lighten(stageColor(card.stage)), 1));
            TextView title = text(card.id + "  " + card.title, 18, Typeface.BOLD, INK);
            item.addView(title, matchWrap());
            TextView body = text(card.description, 14, Typeface.NORMAL, Color.rgb(75, 69, 88));
            body.setMaxLines(2);
            body.setPadding(0, dp(5), 0, 0);
            item.addView(body, matchWrap());
            item.setContentDescription(card.title + ". " + card.description);
            return item;
        }
    }

    private LinearLayout column(int spacingIgnored) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        return layout;
    }

    private TextView text(String value, int sizeSp, int style, int color) {
        TextView text = new TextView(this);
        text.setText(value);
        text.setTextSize(sizeSp);
        text.setTextColor(color);
        text.setTypeface(Typeface.create("sans", style));
        return text;
    }

    private Button button(String label, int color) {
        Button button = new Button(this);
        button.setText(label);
        button.setTextSize(16);
        button.setTextColor(Color.WHITE);
        button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        button.setAllCaps(false);
        button.setBackground(roundRect(color, 18, 0, 0));
        return button;
    }

    private Button outlineButton(String label) {
        Button button = button(label, Color.TRANSPARENT);
        button.setTextColor(INK);
        button.setBackground(roundRect(Color.TRANSPARENT, 18, LAVENDER, 2));
        return button;
    }

    private Button headerButton(String label, View.OnClickListener listener) {
        Button button = button(label, Color.TRANSPARENT);
        button.setTextColor(LAVENDER);
        button.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        button.setPadding(dp(8), 0, dp(8), 0);
        button.setOnClickListener(listener);
        return button;
    }

    private GradientDrawable roundRect(int fill, int radiusDp, int stroke, int strokeDp) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(fill);
        drawable.setCornerRadius(dp(radiusDp));
        if (strokeDp > 0) drawable.setStroke(dp(strokeDp), stroke);
        return drawable;
    }

    private int lighten(int color) {
        int r = (Color.red(color) + 255 * 4) / 5;
        int g = (Color.green(color) + 255 * 4) / 5;
        int b = (Color.blue(color) + 255 * 4) / 5;
        return Color.rgb(r, g, b);
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }

    private LinearLayout.LayoutParams matchWrap() {
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private LinearLayout.LayoutParams matchHeight(int dp) {
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(dp));
    }

    private LinearLayout.LayoutParams weightedHeight(float weight, int dp) {
        return new LinearLayout.LayoutParams(0, dp(dp), weight);
    }
}
