package com.vibekeyopen.android.vibekeyopenandroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;


public class ChannelSelectionActivity extends ActionBarActivity {

    public static final String KEY_CHANNEL_NAME = "Key Channel Name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_selection);

        final ListView mListView = (ListView) findViewById(R.id.channelList);

        // Temp array. To be replaced by and array of the available channels.
        String[] channels = new String[]{"Rose's Rhythms", "Sadler's Station",
                "Jonathan's Jams", "Trent's Tunes", "Ben's Beats"};

        final ArrayAdapter<String> channelAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, channels);

        mListView.setAdapter(channelAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent channelIntent = new Intent(ChannelSelectionActivity.this, MainActivity.class);
                channelIntent.putExtra(KEY_CHANNEL_NAME, channelAdapter.getItem(position).toString());
                startActivity(channelIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_channel_selection, menu);
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
