package com.cse.hcmut.mobileappdev.view.features.home.search.suggestion;

import android.content.Context;

import com.cse.hcmut.mobileappdev.utils.ResourceUtils;

import org.cryse.widget.persistentsearch.SearchItem;
import org.cryse.widget.persistentsearch.SearchSuggestionsBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dinhn on 4/26/2016.
 */
public class MapSuggestionsBuilder implements SearchSuggestionsBuilder {

    public enum IconType {

        FOOD("Food"),
        CLOTH("Cloth"),
        COSMETIC("Cosmetic"),
        ELECTRONIC("Electronic"),
        STATIONERY("Stationery");

        private String mLabel;

        IconType(String label) {
            mLabel = label;
        }

        public String getTitle() {
            return mLabel;
        }
    }

    private Context mContext;

    private List<SearchItem> mHistorySuggestions = new ArrayList<SearchItem>();

    public MapSuggestionsBuilder(Context context) {
        this.mContext = context;
        createHistory();
    }

    private void createHistory() {

        SearchItem item1 = new SearchItem(
                "Thức ăn",
                "3os6yCkzTtKu6ABEZd5Dz5OOTZygkucRLMtqS0yEt7SFVt3p458O58T1QruZOHq",
                SearchItem.TYPE_SEARCH_ITEM_OPTION,
                ResourceUtils.getMapSuggestionIconDrawable(mContext, IconType.FOOD)
        );
        mHistorySuggestions.add(item1);

        SearchItem item2 = new SearchItem(
                "Quần áo",
                "FaHLABkYtU4RAq7dOa0okh2bExQv7lbtDTGFblbvpHhwMvLuJErrn4Pt2iL9sAy",
                SearchItem.TYPE_SEARCH_ITEM_OPTION,
                ResourceUtils.getMapSuggestionIconDrawable(mContext, IconType.CLOTH)
        );
        mHistorySuggestions.add(item2);

        SearchItem item3 = new SearchItem(
                "Mỹ phẩm",
                "FuaLc9xdIMlQ181B8MH1vGimxNf8dkGQcL5ExOssZcVCOVNr4Fi794QSfpfQGzI",
                SearchItem.TYPE_SEARCH_ITEM_OPTION,
                ResourceUtils.getMapSuggestionIconDrawable(mContext, IconType.COSMETIC)
        );
        mHistorySuggestions.add(item3);

        SearchItem item4 = new SearchItem(
                "Đồ điện tử",
                "8NyRftlGByEw7V1eMXMpOlhn9j7gu3hvvMcsIaq4DeocvcqWb4xkCGdze6kNr6B",
                SearchItem.TYPE_SEARCH_ITEM_OPTION,
                ResourceUtils.getMapSuggestionIconDrawable(mContext, IconType.ELECTRONIC)
        );
        mHistorySuggestions.add(item4);

        SearchItem item5 = new SearchItem(
                "Văn phòng phẩm",
                "odUFXiFGMN0G7bIh1NnmMcbFuVKzTPbIJ9zZ82VQahodPBBm5cIwW2B8pCN1yfL",
                SearchItem.TYPE_SEARCH_ITEM_OPTION,
                ResourceUtils.getMapSuggestionIconDrawable(mContext, IconType.STATIONERY)
        );
        mHistorySuggestions.add(item5);
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
