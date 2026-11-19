package com.kharismastudio.campusconnect.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Timestamp
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor() {
    private val db = FirebaseFirestore.getInstance()

    fun observeTasks(): Flow<List<Tugas>> =
        callbackFlow {
            val listener = db.collection("tasks")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val tasks = snapshot
                        ?.documents
                        ?.mapNotNull { document ->
                            val data = document
                                .toObject(TaskDocument::class.java)
                            data?.toDomain()
                        } ?: emptyList()

                    trySend(tasks)
                }
            awaitClose {
                listener.remove()
            }
        }
}