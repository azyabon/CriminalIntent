package com.example.criminalintent.db.repository

import androidx.lifecycle.LiveData
import com.example.criminalintent.IntentModel
import com.example.criminalintent.data.Model.Photo

interface IntentRepository {
    val allIntents: LiveData<List<IntentModel>>
    suspend fun insertIntent(intentModel: IntentModel, onSuccess:() -> Unit)
}