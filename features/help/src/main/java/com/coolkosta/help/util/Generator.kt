package com.coolkosta.help.util

import com.coolkosta.core.R
import com.coolkosta.help.presentation.model.HelpItem

class Generator {

    fun generateHelpList(): List<HelpItem> {
        return listOf(
            HelpItem(
                imageId = R.drawable.ic_child,
                title = R.string.children
            ),
            HelpItem(
                imageId = R.drawable.ic_adult,
                title = R.string.adult
            ),
            HelpItem(
                imageId = R.drawable.ic_old,
                title = R.string.old
            ),
            HelpItem(
                imageId = R.drawable.ic_animals,
                title = R.string.animals
            ),
            HelpItem(
                imageId = R.drawable.ic_event,
                title = R.string.events
            ),
        )
    }
}
