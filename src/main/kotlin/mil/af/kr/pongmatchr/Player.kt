package mil.af.kr.pongmatchr

import javax.persistence.*

@Entity
data class Player(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_id_seq")
        @SequenceGenerator(name = "player_id_seq", sequenceName = "player_id_seq", allocationSize = 1)
        val id: Int = 0,

        val name: String
)