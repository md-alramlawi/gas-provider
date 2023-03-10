package com.alramlawi.gasprovider.ui.screen

import androidx.lifecycle.SavedStateHandle

class IdArgs(savedStateHandle: SavedStateHandle) {
    val id: String? = savedStateHandle[ITEM_ID]

    companion object {
        const val ITEM_ID = "item_id"
    }
}