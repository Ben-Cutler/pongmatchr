package mil.af.kr.pongmatchr

import org.springframework.data.repository.CrudRepository
import java.util.*

interface PlayerRepository:CrudRepository<Player, Int> {
    fun findByName(playerName:String): Optional<Player>
}