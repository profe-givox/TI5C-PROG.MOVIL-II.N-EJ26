package com.example.inventory.providers

import android.net.Uri


object InventoryContract {
    val AUTHORITY = "com.example.inventory.provider"

    object ItemsContract {
        val CONTENT_PATH = "items"
        val CONTENT_URI = Uri.parse("content://$AUTHORITY/$CONTENT_PATH")

        val TYPE_MIME_ITEM = "vnd.android.cursor.item/vnd.com.example.inventory.provider.items"
        val TYPE_MIME_DIR = "vnd.android.cursor.dir/vnd.com.example.inventory.provider.items"

        object Columns {
            val ID = "id"
            val NAME = "name"
            val PRICE = "price"
            val QUANTITY = "quantity"

        }
    }

}