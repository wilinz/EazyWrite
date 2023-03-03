package com.eazywrite.app.data.model

data class BillsCropResponseKt(
    val code: Int,
    val cost_time: Int,
    val message: String,
    val result: Result
)

data class Result(
    val object_list: List<Object>
)

data class Object(
    val `class`: String,
    val image_angle: Int,
    val item_list: List<Item>,
    val kind: String,
    val kind_description: String,
    val position: List<Int>,
    val rotated_image_height: Int,
    val rotated_image_width: Int,
    val type: String,
    val type_description: String
)

data class Item(
    val description: String,
    val key: String,
    val position: List<Int>,
    val value: String
)