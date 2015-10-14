package com.shinobicontrols.grids.sample.styled_editable_grid;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;

import com.shinobicontrols.grids.core.Column;
import com.shinobicontrols.grids.core.ColumnSpec;
import com.shinobicontrols.grids.supplement.HeaderTextViewHolder;
import com.shinobicontrols.grids.supplement.TextColumnStyle;

import java.util.Arrays;

public class EditTextColumnSpec implements ColumnSpec {

    // TextStyle indices
    private static final int NORMAL = 0;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int MONOSPACE = 3;

    // Defaults to use if values cannot be found in the given grid theme (i.e. last resort)
    private static final float DEFAULT_TEXT_SIZE_SP = 18.0f;
    private static final float DEFAULT_HEADER_TEXT_SIZE_SP = 20.0f;
    private static final int INVALID_RESOURCE_ID = 0;
    private final CharSequence columnTitle;
    protected Column.Callback callback;
    private WritablePropertyBinder<CharSequence> propertyBinder;
    private TextColumnStyle defaultStyle;
    private TextColumnStyle alternateStyle;
    private TextColumnStyle headerStyle;
    private TextColumnStyle selectedStyle;

    public EditTextColumnSpec(WritablePropertyBinder<CharSequence> propertyBinder) {
        this(null, propertyBinder);
    }

    public EditTextColumnSpec(CharSequence columnTitle, WritablePropertyBinder<CharSequence> propertyBinder) {

        if (propertyBinder == null) {
            throw new IllegalArgumentException("The propertyBinder parameter cannot be null");
        }

        this.columnTitle = columnTitle;
        this.propertyBinder = propertyBinder;

        defaultStyle = new TextColumnStyle();
        alternateStyle = new TextColumnStyle();
        headerStyle = new TextColumnStyle();
        selectedStyle = new TextColumnStyle();

    }

    @Override
    public void initialize(Column.Callback callback) {
        this.callback = callback;
    }

    @Override
    public int getItemViewType(int rowIndex) {
        return R.id.edit_text_view;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int rowIndex) {
        EditTextViewHolder editTextViewHolder = (EditTextViewHolder) holder;
        CharSequence text = propertyBinder.bind(rowIndex);
        TextColumnStyle textColumnStyle;
        if (rowIndex % 2 == 0) {
            textColumnStyle = defaultStyle;
        } else {
            textColumnStyle = alternateStyle;
        }

        editTextViewHolder.editText.setBackgroundColor(textColumnStyle.getBackgroundColor());
        editTextViewHolder.editText.setGravity(textColumnStyle.getGravity());
        editTextViewHolder.editText.setTextColor(textColumnStyle.getTextColor());
        editTextViewHolder.editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textColumnStyle.getTextSize());
        editTextViewHolder.editText.setTypeface(textColumnStyle.getTypeface());
        editTextViewHolder.editText.setAlpha(textColumnStyle.getAlpha());

        //Prevent old watcher firing upon initial load
        editTextViewHolder.setWatcher(null);
        editTextViewHolder.editText.setText(text);

        //save back edited data
        editTextViewHolder.setWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                propertyBinder.write(s, rowIndex);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getHeaderItemViewType() {
        return com.shinobicontrols.grids.R.id.sg_header_text_view;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderTextViewHolder headerTextViewHolder = (HeaderTextViewHolder) holder;
        headerTextViewHolder.headerTextView.setText(columnTitle);

        headerTextViewHolder.headerTextView.setBackgroundColor(headerStyle.getBackgroundColor());
        headerTextViewHolder.headerTextView.setGravity(headerStyle.getGravity());
        headerTextViewHolder.headerTextView.setTextColor(headerStyle.getTextColor());
        headerTextViewHolder.headerTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, headerStyle.getTextSize());
        headerTextViewHolder.headerTextView.setTypeface(headerStyle.getTypeface());

        headerTextViewHolder.headerTextView.setAlpha(headerStyle.getAlpha());
    }

    @Override
    public boolean hasHeader() {
        return columnTitle != null;
    }

    @Override
    public void onColumnAdded(Context context) {
        TypedArray styledAttributes = context.obtainStyledAttributes(com.shinobicontrols.grids.R.styleable.ShinobiGridTheme);

        defaultStyle.setBackgroundColor(
                styledAttributes.getColor(com.shinobicontrols.grids.R.styleable.ShinobiGridTheme_sg_itemBackgroundColor, Color.TRANSPARENT));
        alternateStyle.setBackgroundColor(
                styledAttributes.getColor(com.shinobicontrols.grids.R.styleable.ShinobiGridTheme_sg_itemAlternateBackgroundColor, Color.TRANSPARENT));
        headerStyle.setBackgroundColor(
                styledAttributes.getColor(com.shinobicontrols.grids.R.styleable.ShinobiGridTheme_sg_headerItemBackgroundColor, Color.TRANSPARENT));
        selectedStyle.setBackgroundColor(
                styledAttributes.getColor(com.shinobicontrols.grids.R.styleable.ShinobiGridTheme_sg_selectedItemBackgroundColor, Color.TRANSPARENT));

        defaultStyle.setGravity(retrieveGravity(context, styledAttributes, com.shinobicontrols.grids.R.styleable.ShinobiGridTheme_sg_itemGravityStyle));
        alternateStyle.setGravity(retrieveGravity(context, styledAttributes, com.shinobicontrols.grids.R.styleable.ShinobiGridTheme_sg_itemAlternateGravityStyle));
        headerStyle.setGravity(retrieveGravity(context, styledAttributes, com.shinobicontrols.grids.R.styleable.ShinobiGridTheme_sg_headerItemGravityStyle));
        selectedStyle.setGravity(retrieveGravity(context, styledAttributes, com.shinobicontrols.grids.R.styleable.ShinobiGridTheme_sg_selectedItemGravityStyle));

        // Now we have the context we can set a meaningful default
        defaultStyle.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE_SP, context.getResources().getDisplayMetrics()));
        alternateStyle.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE_SP, context.getResources().getDisplayMetrics()));
        headerStyle.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_HEADER_TEXT_SIZE_SP, context.getResources().getDisplayMetrics()));
        selectedStyle.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE_SP, context.getResources().getDisplayMetrics()));

        int itemTextAppearanceResId = styledAttributes.getResourceId(
                com.shinobicontrols.grids.R.styleable.ShinobiGridTheme_sg_itemTextAppearance, com.shinobicontrols.grids.R.style.ShinobiGridTheme_Light_ItemTextAppearance);
        setTextAppearanceProperties(context, itemTextAppearanceResId, defaultStyle);
        int itemAlternateTextAppearanceResId = styledAttributes.getResourceId(
                com.shinobicontrols.grids.R.styleable.ShinobiGridTheme_sg_itemAlternateTextAppearance, com.shinobicontrols.grids.R.style.ShinobiGridTheme_Light_ItemAlternateTextAppearance);
        setTextAppearanceProperties(context, itemAlternateTextAppearanceResId, alternateStyle);
        int headerItemTextAppearanceResId = styledAttributes.getResourceId(
                com.shinobicontrols.grids.R.styleable.ShinobiGridTheme_sg_headerItemTextAppearance, com.shinobicontrols.grids.R.style.ShinobiGridTheme_Light_HeaderItemTextAppearance);
        setTextAppearanceProperties(context, headerItemTextAppearanceResId, headerStyle);
        int selectedItemTextAppearanceResId = styledAttributes.getResourceId(
                com.shinobicontrols.grids.R.styleable.ShinobiGridTheme_sg_selectedItemTextAppearance, com.shinobicontrols.grids.R.style.ShinobiGridTheme_Light_SelectedItemTextAppearance);
        setTextAppearanceProperties(context, selectedItemTextAppearanceResId, selectedStyle);

        styledAttributes.recycle();
    }

    private int retrieveGravity(Context context, TypedArray styledAttributes, int resourceId) {
        int gravity = Gravity.NO_GRAVITY;

        int gravityStyleResourceId = styledAttributes.getResourceId(resourceId, INVALID_RESOURCE_ID);
        if (resourceId != INVALID_RESOURCE_ID) {
            int[] attrs = {android.R.attr.gravity};
            TypedArray gravityStyleAttributes = context.obtainStyledAttributes(gravityStyleResourceId, attrs);
            gravity = gravityStyleAttributes.getInt(0, gravity);
            gravityStyleAttributes.recycle();
        }

        return gravity;
    }

    private void setTextAppearanceProperties(Context context, int textAppearanceResId, TextColumnStyle textColumnStyle) {
        // Attrs have to be sorted - http://stackoverflow.com/questions/19034597/get-multiple-style-attributes-with-obtainstyledattributes
        // but we need to know the index of each so we'll have to get each attribute in a for loop
        int[] attrs = {android.R.attr.textSize, android.R.attr.textColor, android.R.attr.textStyle, android.R.attr.typeface};
        Arrays.sort(attrs);

        TypedArray textAppearanceAttributes = context.obtainStyledAttributes(textAppearanceResId, attrs);

        String familyName = null;
        int styleIndex = NORMAL;
        int typefaceIndex = Typeface.NORMAL;

        int attrCount = textAppearanceAttributes.getIndexCount();
        for (int i = 0; i < attrCount; i++) {
            int indexIntoAttrs = textAppearanceAttributes.getIndex(i);

            switch (attrs[indexIntoAttrs]) {
                case android.R.attr.textSize: {
                    textColumnStyle.setTextSize(textAppearanceAttributes.getDimension(indexIntoAttrs, textColumnStyle.getTextSize()));
                    break;
                }
                case android.R.attr.textColor: {
                    textColumnStyle.setTextColor(textAppearanceAttributes.getColor(indexIntoAttrs, textColumnStyle.getTextColor()));
                    break;
                }
                case android.R.attr.textStyle: {
                    styleIndex = textAppearanceAttributes.getInt(indexIntoAttrs, styleIndex);
                    break;
                }
                case android.R.attr.typeface: {
                    typefaceIndex = textAppearanceAttributes.getInt(indexIntoAttrs, typefaceIndex);
                    break;
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            familyName = getFamilyNameForJellyBean(context, textAppearanceResId);
        }

        textColumnStyle.setTypeface(getTypefaceFromAttrs(familyName, styleIndex, typefaceIndex));

        textAppearanceAttributes.recycle();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private String getFamilyNameForJellyBean(Context context, int textAppearanceResId) {
        int[] attrs = new int[]{android.R.attr.fontFamily};
        TypedArray fontFamilyAttribute = context.obtainStyledAttributes(textAppearanceResId, attrs);
        String familyName = fontFamilyAttribute.getString(0);
        fontFamilyAttribute.recycle();
        return familyName;
    }

    private Typeface getTypefaceFromAttrs(String familyName, int styleIndex, int typefaceIndex) {
        Typeface tf = null;
        if (familyName != null) {
            tf = Typeface.create(familyName, styleIndex);
            if (tf != null) {
                return tf;
            }
        }

        // NORMAL is covered by tf == null
        switch (typefaceIndex) {
            case SANS:
                tf = Typeface.SANS_SERIF;
                break;
            case SERIF:
                tf = Typeface.SERIF;
                break;
            case MONOSPACE:
                tf = Typeface.MONOSPACE;
                break;
        }
        return Typeface.create(tf, styleIndex);
    }
}
