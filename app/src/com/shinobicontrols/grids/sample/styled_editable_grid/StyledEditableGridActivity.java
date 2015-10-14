package com.shinobicontrols.grids.sample.styled_editable_grid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.shinobicontrols.grids.core.AdapterSpec;
import com.shinobicontrols.grids.core.Column;
import com.shinobicontrols.grids.core.ShinobiGridView;
import com.shinobicontrols.grids.supplement.PropertyBinder;
import com.shinobicontrols.grids.supplement.TextColumnSpec;

import java.util.List;

public class StyledEditableGridActivity extends Activity {

    private static final int NUMBER_OF_PEOPLE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_styled_editable_grid);

        // TODO: IMPORTANT! You must import the shinobigrids library. See the guide here:
        // (Android Studio) https://www.shinobicontrols.com/docs/ShinobiControls/ShinobiGridsAndroid/1.0.0/Standard/Normal/user-guide/qs-android-studio-import-library.html
        // (Eclipse) https://www.shinobicontrols.com/docs/ShinobiControls/ShinobiGridsAndroid/1.0.0/Standard/Normal/user-guide/qs-eclipse-import-library.html
        ShinobiGridView shinobiGridView = (ShinobiGridView) findViewById(R.id.my_grid);
        // TODO: IMPORTANT! Trial users MUST replace <trial_license_key_here> with your trial license key
        shinobiGridView.setTrialLicenseKey("<trial_license_key_here>");
        shinobiGridView.registerItemViewHolderCreator(new EditTextViewHolder.Creator());

        final List<Person> people = PersonGenerator.generatePeople(NUMBER_OF_PEOPLE);

        int rowHeight = getResources().getDimensionPixelSize(R.dimen.row_height);
        shinobiGridView.setDefaultRowHeight(rowHeight);

        shinobiGridView.setAdapterSpec(new AdapterSpec() {
            @Override
            public int getRowCount() {
                return people.size();
            }
        });

        TextColumnSpec nameColumnSpec = new TextColumnSpec("Name", new PropertyBinder<CharSequence>() {
            @Override
            public CharSequence bind(int rowIndex) {
                Person person = people.get(rowIndex);
                return person.name;
            }
        });

        shinobiGridView.addColumn(Column.create(nameColumnSpec));

        TextColumnSpec ageColumnSpec = new TextColumnSpec("Age", new PropertyBinder<CharSequence>() {
            @Override
            public CharSequence bind(int rowIndex) {
                Person person = people.get(rowIndex);
                return Integer.toString(person.age);
            }
        });

        shinobiGridView.addColumn(Column.create(ageColumnSpec));

        EditTextColumnSpec editTextColumnSpec = new EditTextColumnSpec("Address ", new WritablePropertyBinder<CharSequence>() {
            @Override
            public CharSequence bind(int rowIndex) {
                Person person = people.get(rowIndex);
                return person.address;
            }

            @Override
            public void write(CharSequence data, int rowIndex) {
                Person person = people.get(rowIndex);
                person.address = data.toString();
            }
        });
        shinobiGridView.addColumn(Column.create(editTextColumnSpec));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_styled_editable_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
