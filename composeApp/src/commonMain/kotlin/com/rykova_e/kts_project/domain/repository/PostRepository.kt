package com.rykova_e.kts_project.domain.repository

import com.rykova_e.kts_project.domain.model.PostDto
import kotlin.random.Random

class PostRepository {

    fun getList(): List<PostDto> {
        val titles = listOf(
            "Обсуждение", "Образование", "Спорт", "Музыка", "Кино",
            "Хобби", "Игры", "Отдых"
        )

        val times = listOf(
            "10:00", "11:00", "12:00", "13:00", "14:00",
            "15:00", "16:00", "17:00"
        )

        val destinations = listOf(
            "Давайте обсудим тему",
            "Насколько хорошее образование",
            "Как развивается спорт",
            "Какая музыка успокаивает",
            "Какой фильм сейчас обсуждюат",
            "Что для тебя хобби",
            "Какие игры развивют",
            "Какой отдых лучше выбрать"
        )

        val images = listOf(
            "https://avatars.mds.yandex.net/i?id=3d55202f4ecf326491fadca5f35f7c8d_l-4358366-images-thumbs&n=13",
            "https://avatars.mds.yandex.net/i?id=59b920c5983ff748dda7cfa873ee2511fbee8944-5234092-images-thumbs&n=13",
            "https://img.freepik.com/premium-vector/vector-dumbbell-reverse-lunge-with-bicep-curl-exercise-clipart_1218867-3674.jpg?semt=ais_hybrid&w=740&q=80",
            "https://img.freepik.com/premium-vector/elegant-music-notes-background-with-stylish-musical-theme-composition_1120554-48787.jpg?semt=ais_hybrid&w=740&q=80",
            "https://avatars.mds.yandex.net/i?id=65dac8e9212bc812e8ff5dc8730f6273_l-10869844-images-thumbs&n=13",
            "https://avatars.mds.yandex.net/i?id=4e0c8db939e8887ce89c40660848b9c4f1f79aa6-10877393-images-thumbs&n=13",
            "https://img.freepik.com/premium-vector/two-kids-enjoying-gaming-activities-with-gadgets-game-items-flat-illustration-style_193692-831.jpg?semt=ais_hybrid&w=740&q=80",
            "https://img.freepik.com/premium-vector/young-woman-reading-book-studying-home-book-lovers-hobby_273625-1564.jpg?semt=ais_hybrid&w=740"
        )

        val posts = mutableListOf<PostDto>()
        for (i in 0..7) {
            posts.add(
                PostDto(
                    id = Random.nextLong(),
                    title = titles[i],
                    time = times[i],
                    description = destinations[i],
                    image = images[i]
                )
            )
        }
        return posts
    }
}