package com.example.criminalintent.db.repository

import androidx.lifecycle.LiveData
import com.example.criminalintent.IntentModel

interface IntentRepository {
    val allIntents: LiveData<List<IntentModel>>
    suspend fun insertIntent(intentModel: IntentModel, onSuccess:() -> Unit)
}