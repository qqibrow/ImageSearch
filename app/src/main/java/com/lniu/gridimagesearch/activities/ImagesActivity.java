package com.lniu.gridimagesearch.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.lniu.gridimagesearch.R;
import com.lniu.gridimagesearch.adapters.ImageResultsAdapter;
import com.lniu.gridimagesearch.dialogs.SettingsDialog;
import com.lniu.gridimagesearch.listeners.EndlessScrollListener;
import com.lniu.gridimagesearch.models.ImageResult;
import com.lniu.gridimagesearch.models.Settings;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ImagesActivity extends ActionBarActivity {
    private StaggeredGridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImagesAdapter;
    private String query = "";
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        setUpViews();
        imageResults = new ArrayList<>();
        aImagesAdapter = new ImageResultsAdapter(this,imageResults);
        gvResults.setAdapter(aImagesAdapter);
    }

    private void loadMore(int num) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
                + query + "&rsz=8" + "&start=" + String.valueOf(num) + "&" + Settings.Instance().toString();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONObject("responseData")
                            .getJSONArray("results");

                    // Clear the results(in case its a new search)
                    aImagesAdapter.addAll(ImageResult.fromJsonArray(results));

                }catch(JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void setUpViews() {
        gvResults = (StaggeredGridView) findViewById(R.id.gvResults);

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                Toast.makeText(ImagesActivity.this, String.format("onLoadMore called with " +
                        "totalItemsCount %d, call %d", totalItemsCount, (totalItemsCount / 8) * 8)
                        , Toast.LENGTH_SHORT).show();
                if(totalItemsCount > 56) {
                    Toast.makeText(ImagesActivity.this,
                            "Cannot load more with" + String.valueOf(totalItemsCount),
                            Toast.LENGTH_SHORT).show();
                } else  {
                    loadMore((totalItemsCount / 8) * 8);
                }

            }
        });


        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ImagesActivity.this, ImageDisplayActivity.class);

                ImageResult result = imageResults.get(position);
                intent.putExtra("result", result);
                startActivity(intent);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_images, menu);
        //return true;
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_images, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if(!queryTextFocused) {
                    searchItem.collapseActionView();
                    searchView.setQuery("", false);
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                aImagesAdapter.clear();
                ImagesActivity.this.query = query;
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        ImagesActivity.this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

                searchItem.collapseActionView();
                getSupportActionBar().setTitle(String.format("Result for: %s", query));
                searchView.setQuery("", false);
                loadMore(0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showEditDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        SettingsDialog editNameDialog = SettingsDialog.newInstance("Settings");
        editNameDialog.show(fm, "fragment_edit_name");
    }

    /* Replaced by the actionbar searchview.
    public void onImageSearch(View view) {
        Toast.makeText(this, Settings.Instance().toString(), Toast.LENGTH_SHORT).show();
        aImagesAdapter.clear();
        loadMore(0);
    }
    */
}
