package com.djira.f1levier.ui.equipe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EquipeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EquipeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is equipe fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}