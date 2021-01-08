package com.djira.f1levier.ui.participant;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ParticipantViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ParticipantViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is participant fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}