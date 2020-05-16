package com.ask.gpylibrary.adapter;

import com.google.firebase.storage.FirebaseStorage;

public class ProfileStore {

    FirebaseStorage storage;

    public ProfileStore() {
        storage = FirebaseStorage.getInstance();
    }
}
