package com.example.inventory.providers

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.inventory.data.InventoryDatabase


private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
    addURI(InventoryContract.AUTHORITY,
        InventoryContract.ItemsContract.CONTENT_PATH, 101)
    addURI(InventoryContract.AUTHORITY, "${InventoryContract.ItemsContract.CONTENT_PATH}/#", 102)
}

class ItemsProvider : ContentProvider() {
    private lateinit var db:  InventoryDatabase


    override fun onCreate(): Boolean {
        db = InventoryDatabase.getDatabase(context!!)
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String?>?,
        p2: String?,
        p3: Array<out String?>?,
        p4: String?
    ): Cursor? {

        val code = sUriMatcher.match(p0)

        val c = when(code){
            101 -> {
                db.itemDao().getAllItemsCursor()
            }
            102 -> {
                db.itemDao().getAllItemsCursorByID(p0.lastPathSegment!!.toInt())
            }
            else -> null
        }

        return c
    }

    override fun getType(p0: Uri): String? {
        return when(sUriMatcher.match(p0)){
            101 -> InventoryContract.ItemsContract.TYPE_MIME_DIR
            102 -> InventoryContract.ItemsContract.TYPE_MIME_ITEM
            else -> null
        }
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(
        p0: Uri,
        p1: String?,
        p2: Array<out String?>?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        p0: Uri,
        p1: ContentValues?,
        p2: String?,
        p3: Array<out String?>?
    ): Int {
        TODO("Not yet implemented")
    }
}