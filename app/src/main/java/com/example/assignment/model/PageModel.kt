package com.example.assignment.model

import com.google.gson.annotations.SerializedName


data class PageModel (

    @SerializedName("page") var page : Page


)

data class Page (

    @SerializedName("title") var title : String,
    @SerializedName("total-content-items") var total_content_items : String,
    @SerializedName("page-num") var page_num : String,
    @SerializedName("page-size") var page_size : String,
    @SerializedName("content-items") var content_items : Content_items

)

data class Content_ (

    @SerializedName("name") var name : String,
    @SerializedName("poster-image") var poster_image : String

)

data class Content_items (

@SerializedName("content") var content : List<Content_>

)