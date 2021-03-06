package mil.af.kr.pongmatchr

import org.springframework.data.repository.CrudRepository

interface PlayerRepository:CrudRepository<Player, Int> {
    fun findByName(playerName:String): List<Player>
}