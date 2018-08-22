package com.abhigyan.user.movieviewer1;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.List;
//DESIGNED BY ABHIGYAN RAHA

public class FavouritesActivity extends AppCompatActivity {

    LinearLayout favouritesLinearLayout;
    ImageView sadEmoImageView;
    TextView textView, favoritesText;
    ProgressBar favouritesPB;
    EditText deleteText;
    ImageView deleteImage;
    Button deleteButton;

    ImageView imageView;
    TextView ratingText;
    TextView titleText;
    TextView movieIDText;

    Boolean keyboardIsShowing = false;

    Boolean deleteWindowShowing = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.favorites_auxillary_menu, menu);
        MenuItem deleteItem = menu.findItem(R.id.deleteFav);
        ImageButton delete = (ImageButton) deleteItem.getActionView();
        delete.setPadding(0, 0, 20, 10);
        delete.setImageResource(android.R.drawable.ic_menu_delete);
        delete.setBackground(getResources().getDrawable(android.R.color.transparent));
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(deleteWindowShowing == false) {
                    deleteImage.animate().translationXBy(-2000f).setDuration(700);
                    deleteText.animate().translationXBy(-2000f).setDuration(700);
                    deleteText.setEnabled(true);
                    deleteButton.animate().translationXBy(-2000f).setDuration(700);
                    favouritesLinearLayout.animate().translationXBy(-2000f).setDuration(700);
                    deleteWindowShowing = true;
                }
                else
                {
                    deleteImage.animate().translationXBy(2000f).setDuration(700);
                    deleteText.animate().translationXBy(2000f).setDuration(700);
                    deleteButton.animate().translationXBy(2000f).setDuration(700);
                    favouritesLinearLayout.animate().translationXBy(2000f).setDuration(700);
                    deleteText.setEnabled(false);
                    deleteWindowShowing = false;

                }

            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void deleteFunction(View view)
    {
        if(!deleteText.getText().toString().equals("")) {
            favouritesPB.setVisibility(View.VISIBLE);
            favoritesText.setText("Deleting. Please wait...");
            favoritesText.setVisibility(View.VISIBLE);
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Favourites");
            try {
                query.whereEqualTo("movieID", Integer.parseInt(deleteText.getText().toString()));
            }
            catch(Exception e)
            {
                Toast.makeText(this, "Illegal movieid!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {

                    if (e == null) {
                        try {
                            object.delete();
                            object.saveInBackground();
                            favouritesPB.setVisibility(View.INVISIBLE);
                            favoritesText.setText("Deleted!");
                            favoritesText.setVisibility(View.INVISIBLE);
                            deleteImage.animate().translationXBy(2000f).setDuration(700);
                            deleteText.animate().translationXBy(2000f).setDuration(700);
                            deleteButton.animate().translationXBy(2000f).setDuration(700);
                            favouritesLinearLayout.animate().translationXBy(2000f).setDuration(700);
                            favouritesLinearLayout.removeAllViews();

                            getFavourites();
                            deleteWindowShowing = false;
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        Toast.makeText(FavouritesActivity.this, "Couldn't be deleted!", Toast.LENGTH_SHORT).show();
                        Log.i("abhigyans error********", e.getMessage());
                        favouritesPB.setVisibility(View.INVISIBLE);
                        favoritesText.setVisibility(View.INVISIBLE);
                        deleteImage.animate().translationXBy(2000f).setDuration(700);
                        deleteText.animate().translationXBy(2000f).setDuration(700);
                        deleteButton.animate().translationXBy(2000f).setDuration(700);
                        favouritesLinearLayout.animate().translationXBy(2000f).setDuration(700);
                        favouritesLinearLayout.removeAllViews();
                        getFavourites();
                        deleteWindowShowing = false;
                    }
                }
            });
        }
        else
        {
            Toast.makeText(this, "You cannot leave this field black!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        favouritesLinearLayout = findViewById(R.id.favouritesLinearLayout);
        sadEmoImageView = findViewById(R.id.noFavoritesImg);
        textView = findViewById(R.id.noFavoritesText);
        favouritesPB = findViewById(R.id.favouritesProgress);
        favoritesText = findViewById(R.id.favoritesText);
        deleteButton = findViewById(R.id.deleteButton);
        deleteImage = findViewById(R.id.delteImg);
        deleteText = findViewById(R.id.deleteText);
        deleteImage.animate().translationXBy(2000f).setDuration(10);
        deleteText.animate().translationXBy(2000f).setDuration(10);
        deleteButton.animate().translationXBy(2000f).setDuration(10);
        deleteText.setEnabled(false);

        if (ParseUser.getCurrentUser() != null) {
            sadEmoImageView.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
            getFavourites();
        } else {
            sadEmoImageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            favoritesText.setVisibility(View.INVISIBLE);
            favouritesPB.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }

    public void getFavourites()
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Favourites");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject object : objects)
                        {
                            String username = object.getString("username");
                            if(username.equals(ParseUser.getCurrentUser().getUsername())) {

                                final String titleOfMovie = object.getString("title");
                                final String rating = object.getString("rating");
                                String path = object.getString("posterPath");
                                final int id = object.getInt("movieID");

                                imageView = new ImageView(getApplicationContext());
                                Picasso.get().load("https://image.tmdb.org/t/p/w500" + path).into(imageView);
                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                titleText = new TextView(getApplicationContext());
                                titleText.setText("Title- "+titleOfMovie);
                                titleText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                titleText.setPadding(5,10,5,10);
                                titleText.setTextColor(getResources().getColor(android.R.color.white));
                                titleText.setTextSize(18f);

                                ratingText = new TextView(getApplicationContext());
                                ratingText.setText("Rating- "+rating);
                                ratingText.setTextSize(18f);
                                ratingText.setTextColor(getResources().getColor(android.R.color.white));
                                ratingText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                ratingText.setPadding(5,10,5,40);

                                movieIDText = new TextView(getApplicationContext());
                                movieIDText.setText("Movie Id- "+String.valueOf(id));
                                movieIDText.setTextSize(18f);
                                movieIDText.setTextColor(getResources().getColor(android.R.color.white));
                                movieIDText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                movieIDText.setPadding(5,10,5,40);

                                favouritesLinearLayout.addView(imageView);
                                favouritesLinearLayout.addView(titleText);
                                favouritesLinearLayout.addView(ratingText);
                                favouritesLinearLayout.addView(movieIDText);

                                favoritesText.setVisibility(View.INVISIBLE);
                                favouritesPB.setVisibility(View.INVISIBLE);

                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent intent = new Intent(getApplicationContext(),CompleteInformationActivity.class);
                                        intent.putExtra("movieid", id);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });
    }

    public void showKey(View view)
    {
        if(keyboardIsShowing == false) {
            InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            methodManager.showSoftInput(deleteText, InputMethodManager.SHOW_IMPLICIT);
            keyboardIsShowing = true;
        }
        else
        {
            InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            methodManager.hideSoftInputFromWindow(deleteText.getWindowToken(), InputMethodManager.SHOW_IMPLICIT);
            keyboardIsShowing = false;
        }
    }
}
