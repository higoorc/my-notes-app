package com.hsilva.mynotes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hsilva.mynotes.R
import java.io.Serializable

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int = R.color.purple,
): Serializable {
    companion object {
        val colors = listOf(
            R.color.orange,
            R.color.yellow,
            R.color.purple,
            R.color.blue,
            R.color.teal
        )
    }
}
