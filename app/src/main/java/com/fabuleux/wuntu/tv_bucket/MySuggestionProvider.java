package com.fabuleux.wuntu.tv_bucket;

import android.content.SearchRecentSuggestionsProvider;
import android.widget.Toast;

import static android.content.SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES;

/**
 * Created by Wuntu on 11-09-2017.
 */

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = MySuggestionProvider.class.getName();
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
