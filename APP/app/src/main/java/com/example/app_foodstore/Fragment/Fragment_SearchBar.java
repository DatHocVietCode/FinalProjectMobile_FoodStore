package com.example.app_foodstore.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.app_foodstore.Activity.SearchActivity;
import com.example.app_foodstore.Activity.SearchResultActivity;
import com.example.app_foodstore.R;

public class Fragment_SearchBar extends Fragment {
    private EditText searchEditText;
    private ImageView searchIcon;
    private LinearLayout searchBar;

    public LinearLayout getSearchBar() {
        return searchBar;
    }

    public void setSearchBar(LinearLayout searchBar) {
        this.searchBar = searchBar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        searchEditText = rootView.findViewById(R.id.et_search);
        searchIcon = rootView.findViewById(R.id.i_search);
        searchBar = rootView.findViewById(R.id.searchbar);

        // Thiết lập sự kiện click cho LinearLayout
        searchBar.setOnClickListener(v -> {
                if (!(getActivity() instanceof SearchActivity)) {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    startActivity(intent);
                }
        });
        searchEditText.setOnClickListener(v-> {
            if (!(getActivity() instanceof SearchActivity)) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        searchIcon.setOnClickListener(v -> {
            if (!(getActivity() instanceof SearchActivity)) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                            && event.getAction() == KeyEvent.ACTION_DOWN)) {

                String text = searchEditText.getText().toString();
                Toast.makeText(getContext(), "Bạn nhập: " + text, Toast.LENGTH_SHORT).show();
                if (!text.isEmpty())
                {
                    Toast.makeText(getContext(), "Start Search result activity: " + text, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                    intent.putExtra("keyword", text);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getContext(), "Vui lòng nhập từ khóa", Toast.LENGTH_SHORT).show();
                }
                return true; // đã xử lý sự kiện
            }
            return false;
        });
        return rootView;
    }
    public EditText getSearchEditText() {
        return searchEditText;
    }

    public void setSearchEditText(EditText searchEditText) {
        this.searchEditText = searchEditText;
    }

    public ImageView getSearchIcon() {
        return searchIcon;
    }

    public void setSearchIcon(ImageView searchIcon) {
        this.searchIcon = searchIcon;
    }
}
