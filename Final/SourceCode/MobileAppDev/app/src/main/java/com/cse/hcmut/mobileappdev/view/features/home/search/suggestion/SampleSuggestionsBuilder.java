package com.cse.hcmut.mobileappdev.view.features.home.search.suggestion;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

import com.cse.hcmut.mobileappdev.utils.ColorFilterGenerator;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;

import org.cryse.widget.persistentsearch.SearchItem;
import org.cryse.widget.persistentsearch.SearchSuggestionsBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dinhn on 3/26/2016.
 */
public class SampleSuggestionsBuilder implements SearchSuggestionsBuilder {

    private Context mContext;

    int mMainColor = 0;

    private List<SearchItem> mHistorySuggestions = new ArrayList<SearchItem>();

    public SampleSuggestionsBuilder(Context context) {
        this.mContext = context;
        mMainColor = ResourceUtils.getDefaultGreenColorTabLayout(context);
        createHistory();
    }

    public SampleSuggestionsBuilder(Context context, int color) {
        this.mContext = context;
        mMainColor = color;
        createHistory();
    }

    private void createHistory() {

        Drawable advanceSearchOptionDrawable = ResourceUtils.getAdvanceSearchIconDrawable(mContext);
        ColorFilter colorFilter = ColorFilterGenerator.from(advanceSearchOptionDrawable).to(mMainColor);
        advanceSearchOptionDrawable.setColorFilter(colorFilter);

        SearchItem item1 = new SearchItem(
                "Tìm kiếm nâng cao",
                "h1hBaLBV0AOItW4sO3vUQXCi8ENERjIizmLYJ6caPGMskqE4xLs8OqEDCvQLCp7",
                SearchItem.TYPE_SEARCH_ITEM_OPTION,
                advanceSearchOptionDrawable,
                mMainColor
        );
        mHistorySuggestions.add(item1);
        SearchItem item2 = new SearchItem(
                "Albert Einstein",
                "Albert Einstein",
                SearchItem.TYPE_SEARCH_ITEM_HISTORY
        );
        mHistorySuggestions.add(item2);
        SearchItem item3 = new SearchItem(
                "John von Neumann",
                "John von Neumann",
                SearchItem.TYPE_SEARCH_ITEM_HISTORY
        );
        mHistorySuggestions.add(item3);
        SearchItem item4 = new SearchItem(
                "Alan Mathison Turing",
                "Alan Mathison Turing",
                SearchItem.TYPE_SEARCH_ITEM_HISTORY
        );
        mHistorySuggestions.add(item4);
    }

    @Override
    public Collection<SearchItem> buildEmptySearchSuggestion(int maxCount) {
        List<SearchItem> items = new ArrayList<SearchItem>();
        items.addAll(mHistorySuggestions);
        return items;
    }

    @Override
    public Collection<SearchItem> buildSearchSuggestion(int maxCount, String query) {
        List<SearchItem> items = new ArrayList<SearchItem>();
        if (query.startsWith("@")) {
            SearchItem peopleSuggestion = new SearchItem(
                    "Search People: " + query.substring(1),
                    query,
                    SearchItem.TYPE_SEARCH_ITEM_SUGGESTION
            );
            items.add(peopleSuggestion);
        } else if (query.startsWith("#")) {
            SearchItem toppicSuggestion = new SearchItem(
                    "Search Topic: " + query.substring(1),
                    query,
                    SearchItem.TYPE_SEARCH_ITEM_SUGGESTION
            );
            items.add(toppicSuggestion);
        } else {
            SearchItem peopleSuggestion = new SearchItem(
                    "Search People: " + query,
                    "@" + query,
                    SearchItem.TYPE_SEARCH_ITEM_SUGGESTION
            );
            items.add(peopleSuggestion);
            SearchItem toppicSuggestion = new SearchItem(
                    "Search Topic: " + query,
                    "#" + query,
                    SearchItem.TYPE_SEARCH_ITEM_SUGGESTION
            );
            items.add(toppicSuggestion);
        }
        for (SearchItem item : mHistorySuggestions) {
            if (item.getValue().startsWith(query)) {
                items.add(item);
            }
        }
        return items;
    }
}
