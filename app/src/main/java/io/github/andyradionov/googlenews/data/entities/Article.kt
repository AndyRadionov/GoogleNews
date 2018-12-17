package io.github.andyradionov.googlenews.data.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import java.util.*

@Entity(tableName = "articles")
data class Article(@PrimaryKey(autoGenerate = true)
                   var articleId: Int,
                   val publishedAt: Date,
                   val author: String,
                   val urlToImage: String,
                   val description: String,
                   val title: String,
                   val url: String,
                   var isFavourite: Boolean = false):
        Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            Date(parcel.readLong()),
            parcel.readString() as String,
            parcel.readString() as String,
            parcel.readString() as String,
            parcel.readString() as String,
            parcel.readString() as String,
            parcel.readByte() != 0.toByte())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(articleId)
        parcel.writeLong(publishedAt.time)
        parcel.writeString(author)
        parcel.writeString(urlToImage)
        parcel.writeString(description)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeByte(if (isFavourite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}
