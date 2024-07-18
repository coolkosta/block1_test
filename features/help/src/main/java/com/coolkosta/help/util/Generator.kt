package com.coolkosta.help.util

import com.coolkosta.core.R

class Generator {

    fun generateHelpList(): List<com.coolkosta.help.presentation.model.HelpItem> {
        return listOf(
            com.coolkosta.help.presentation.model.HelpItem(
                imageId = R.drawable.ic_child,
                title = R.string.children
            ),
            com.coolkosta.help.presentation.model.HelpItem(
                imageId = R.drawable.ic_adult,
                title = R.string.adult
            ),
            com.coolkosta.help.presentation.model.HelpItem(
                imageId = R.drawable.ic_old,
                title = R.string.old
            ),
            com.coolkosta.help.presentation.model.HelpItem(
                imageId = R.drawable.ic_animals,
                title = R.string.animals
            ),
            com.coolkosta.help.presentation.model.HelpItem(
                imageId = R.drawable.ic_event,
                title = R.string.events
            ),
        )
    }

    fun generateEventList(): List<String> {
        val init = listOf(
            "Благотворительный забег 'Беги за мечтой'",
            "Сбор средств для детского дома 'Солнечный'",
            "Эко-акция 'Сделаем мир чище'",
            "Марафон 'Помощь бездомным животным'",
            "Концерт в поддержку фонда 'Живи полной жизнью'",
            "Ярмарка 'Рука помощи' - сбор средств для больницы",
            "Спортивный фестиваль 'Здоровье нации'",
            "Аукцион картин 'Искусство, которое помогает'",
            "Флешмоб 'Танцуй, чтобы помочь'",
            "Квест-игра 'Путешествие добрых сердец'"
        )
        val result = init.shuffled()
        return result
    }

    fun generateNkoList(): List<String> {
        val init = listOf(
            "Фонд 'Подари жизнь' - благотворительный концерт",
            "Фонд 'Нужна помощь' - сбор средств для лечения детей",
            "Фонд 'Старость в радость' - акция 'Помощь пожилым'",
            "Фонд 'Друзья' - марафон 'Вместе против рака'",
            "Фонд 'Спасибо' - акция 'Помощь ветеранам'",
            "Фонд 'Эко-защита' - эко-акция 'Сохраним природу вместе'",
            "Фонд 'Котики против' - сбор средств на помощь бездомным животным",
            "Фонд 'Открытые сердца' - благотворительный забег",
            "Фонд 'Русфонд' - акция 'Помощь детям с редкими заболеваниями'",
            "Фонд 'Вера' - акция 'Поддержка хосписов'"
        )
        val result = init.shuffled()
        return result
    }
}
