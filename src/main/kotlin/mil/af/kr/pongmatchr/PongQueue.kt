package mil.af.kr.pongmatchr

data class SimpleQueue(
        val Name: String,

        val TimeStarted: String
)

data class PongQueue(
        val Queue: List<SimpleQueue>,
        val ResourceCount: Int
)

data class PongItem(
        val Name: String
)