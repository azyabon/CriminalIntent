package com.example.criminalintent.db.repository

import androidx.lifecycle.LiveData
import com.example.criminalintent.IntentModel
import com.example.criminalintent.data.Model.Photo
import com.example.criminalintent.db.dao.IntentDao

class IntentRealization(private val intentDao: IntentDao): IntentRepository {


    override val allIntents: LiveData<List<IntentModel>>
        get() = intentDao.getAllIntents()

    override suspend fun insertIntent(intentModel: IntentModel, onSuccess: () -> Unit) {
        intentDao.insert(intentModel)
        onSuccess()
    }

}