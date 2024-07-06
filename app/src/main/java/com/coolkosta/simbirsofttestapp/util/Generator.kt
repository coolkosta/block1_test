package com.coolkosta.simbirsofttestapp.util

import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.presentation.model.HelpItem

class Generator {

    fun generateHelpList(): List<HelpItem> {
        return listOf(
            HelpItem(imageId = R.drawable.ic_child, title = R.string.children),
            HelpItem(imageId = R.drawable.ic_adult, title = R.string.adult),
            HelpItem(imageId = R.drawable.ic_old, title = R.string.old),
            HelpItem(imageId = R.drawable.ic_animals, title = R.string.animals),
            HelpItem(imageId = R.drawable.ic_event, title = R.string.events),
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
