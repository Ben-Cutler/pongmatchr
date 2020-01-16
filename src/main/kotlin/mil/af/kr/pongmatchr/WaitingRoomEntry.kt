package mil.af.kr.pongmatchr

import javax.persistence.*

@Entity
data class WaitingRoomEntry(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "waiting_room_id_seq")
        @SequenceGenerator(name = "waiting_room_id_seq", sequenceName = "waiting_room_id_seq", allocationSize = 1)
        val id: Int = 0 ,

        @OneToOne
        val player: Player
)
