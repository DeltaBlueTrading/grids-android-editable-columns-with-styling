package com.shinobicontrols.grids.sample.styled_editable_grid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;

import com.shinobicontrols.grids.core.ItemViewHolderCreator;

public class EditTextViewHolder extends RecyclerView.ViewHolder {
    public final EditText editText;
    private TextWatcher watcher;
    public EditTextViewHolder(EditText editText) {
        super(editText);
        this.editText = editText;
    }

    public void setWatcher(TextWatcher watcher) {
        if (this.watcher != null) {
            editText.removeTextChangedListener(this.watcher);
        }
        if (watcher != null) {
            editText.addTextChangedListener(watcher);
        }
        this.watcher = watcher;
    }

    public static class Creator implements ItemViewHolderCreator {

        @Override
        public int getItemViewType() {
            return R.id.edit_text_view;
        }

        @Override
        public RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent) {
            Context context = parent.getContext();
            EditText editText = new EditText(context);
            editText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            return new EditTextViewHolder(editText);
        }
    }
}
