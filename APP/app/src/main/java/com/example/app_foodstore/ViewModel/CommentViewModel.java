package com.example.app_foodstore.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_foodstore.Model.request.CommentRequestDTO;
import com.example.app_foodstore.Model.response.BaseResponse;
import com.example.app_foodstore.Repo.CommentRepository;

public class CommentViewModel extends ViewModel {

    private final CommentRepository repository;
    private final MutableLiveData<BaseResponse<Void>> commentResponse;

    public CommentViewModel() {
        repository = new CommentRepository();
        commentResponse = new MutableLiveData<>();
    }

    public LiveData<BaseResponse<Void>> getCommentResponse() {
        Log.d("Comment View Model", "getCommentResponse: " + commentResponse);
        return commentResponse;
    }

    public void sendComment(String token, CommentRequestDTO dto) {
        repository.postComment(token, dto, commentResponse);
    }
}