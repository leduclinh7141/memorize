package cloud.techstar.jisho.words;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.ArrayList;

import cloud.techstar.jisho.R;
import cloud.techstar.jisho.database.Word;

public class WordSuggestionsAdapter extends SuggestionsAdapter<Word, WordSuggestionsAdapter.SuggestionHolder> {

    public WordSuggestionsAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public int getSingleViewHeight() {
        return 80;
    }

    @Override
    public SuggestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.item_suggestion, parent, false);
        return new SuggestionHolder(view);
    }

    @Override
    public void onBindSuggestionHolder(Word suggestion, SuggestionHolder holder, int position) {
        holder.title.setText(suggestion.getCharacter());
        holder.subtitle.setText("орчуулга нь " + suggestion.getMeaning());
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                String term = constraint.toString();
                if(term.isEmpty())
                    suggestions = suggestions_clone;
                else {
                    suggestions = new ArrayList<>();
                    for (Word words: suggestions_clone)
                        if(words.getCharacter().toLowerCase().contains(term.toLowerCase()))
                            suggestions.add(words);
                }
                results.values = suggestions;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                suggestions = (ArrayList<Word>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    static class SuggestionHolder extends RecyclerView.ViewHolder{
        protected TextView title;
        protected TextView subtitle;
        protected ImageView image;

        public SuggestionHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.suggest_char);
            subtitle = (TextView) itemView.findViewById(R.id.suggest_meaning);
        }
    }
}
