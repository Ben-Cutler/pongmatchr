package mil.af.kr.pongmatchr

import javax.persistence.*

@Entity
data class Match(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_id_seq")
        @SequenceGenerator(name = "player_id_seq", sequenceName = "player_id_seq", allocationSize = 1)
        val id: Int = 0,

        @OneToOne
        val player1: Player,

        @OneToOne
        val player2: Player,

        @ManyToOne
        var winner: Player? = null
)