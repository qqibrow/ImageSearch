package com.lniu.gridimagesearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.lniu.gridimagesearch.R;
import com.lniu.gridimagesearch.adapters.ImageResultsAdapter;
import com.lniu.gridimagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ImagesActivity extends ActionBarActivity {
    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        setUpViews();
        imageResults = new ArrayList<>();
        aImagesAdapter = new ImageResultsAdapter(this,imageResults);
        gvResults.setAdapter(aImagesAdapter);
    }

    private void setUpViews() {
        etQuery = (EditText) findViewById(R.id.etInput);
        gvResults = (GridView) findViewById(R.id.gvResults);
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
        getMenuInflater().inflate(R.menu.menu_images, menu);
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

    public void onImageSearch(View view) {
        String query = etQuery.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
                + query + "&rsz=8";
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONObject("responseData")
                            .getJSONArray("results");

                    // Clear the results(in case its a new search)
                    imageResults.clear();
                    aImagesAdapter.addAll(ImageResult.fromJsonArray(results));

                }catch(JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}