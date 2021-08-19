package com.example.data.repositories.remote

import com.example.domain.repositories.remote.IFirebaseRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import com.example.domain.common.Result
import com.example.domain.models.Currency
import com.example.domain.models.FavoriteCurrency
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import java.util.*

class FirebaseRepository: IFirebaseRepository {

    companion object {
        private const val USERS_COLLECTION = "users"
        private const val FAVORITE_IDS = "favorite"
        private const val CREATED_FIELD = "created"
        private const val VALUE_FIELD = "value"
    }

    private val store = Firebase.firestore

    init {
        store.firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
    }

    override suspend fun saveFavorite(currency: Currency): Result<Unit> {
        return try {
            store.collection(USERS_COLLECTION)
                .document(Firebase.auth.currentUser!!.uid)
                .collection(FAVORITE_IDS)
                .document()
                .set(mapOf(
                    CREATED_FIELD to currency.addedToFavorite!!.time,
                    VALUE_FIELD to currency.id
                ))
                .await()

            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun deleteFavorite(id: Int): Result<Unit> {
        return try {
            val data = store.collection(USERS_COLLECTION)
                .document(Firebase.auth.currentUser!!.uid)
                .collection(FAVORITE_IDS)
                .whereEqualTo(VALUE_FIELD, id)
                .get()
                .await()

            data.documents.forEach {
                it.reference.delete().await()
            }

            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getFavorite(): List<FavoriteCurrency> {
        val data = store.collection(USERS_COLLECTION)
            .document(Firebase.auth.currentUser!!.uid)
            .collection(FAVORITE_IDS)
            .orderBy(CREATED_FIELD, Query.Direction.DESCENDING)
            .get()
            .await()

        return data.documents.map { FavoriteCurrency(
            addedToFavorite = Date(it.data?.get(CREATED_FIELD) as Long),
            currencyId = (it.data?.get(VALUE_FIELD) as Long).toInt()
        ) }
    }
}