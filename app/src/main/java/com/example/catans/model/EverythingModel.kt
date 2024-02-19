package com.example.catans.model

import com.example.rooans.util.Utils
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName

data class Everything(
    @SerializedName(Utils.status) val status: String = "",
    @SerializedName(Utils.totalResults) val totalResults: Long = 0,
    @SerializedName(Utils.articles) val articles: List<Article>?,
)

@IgnoreExtraProperties
data class Article(
    @SerializedName(Utils.id) val id: Int,
    @SerializedName(Utils.source) val source: Source?,
    @SerializedName(Utils.author) val author: String?,
    @SerializedName(Utils.title) val title: String?,
    @SerializedName(Utils.description) val description: String?,
    @SerializedName(Utils.url) val url: String?,
    @SerializedName(Utils.urlToImage) val urlToImage: String?,
    @SerializedName(Utils.publishedAt) val publishedAt: String?,
    @SerializedName(Utils.content) val content: String?,
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            Utils.source to source,
            Utils.author to author,
            Utils.title to title,
            Utils.description to description,
            Utils.url to url,
            Utils.urlToImage to urlToImage,
            Utils.publishedAt to publishedAt,
            Utils.content to content,
        )
    }
}

data class Source(
    @SerializedName(Utils.id) val id: String?,
    @SerializedName(Utils.name) val name: String?,
)