package mil.af.kr.pongmatchr

data class MatchRequest (
    val player1: Player,
    val player2: Player,
    val winner: Player
)