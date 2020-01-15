package mil.af.kr.pongmatchr

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PlayerController(val playerRepository: PlayerRepository) {
    @PostMapping("/api/players")
    fun add(@RequestBody request: PlayerRequest): List<Player> {
        val all_waiting_players = playerRepository.findAll().toList()
        if (all_waiting_players.isEmpty()) {
            playerRepository.save(Player(name = request.name))
        } else {
            // Match happens
            val playerToMatchWith = all_waiting_players[0]
            playerRepository.delete(playerToMatchWith)
            //TODO tell pong server there is a new person waiting
        }
        return playerRepository.findAll().toList()
    }

    @GetMapping("/api/players")
    fun getAll(): List<Player> {
        return playerRepository.findAll().toList()
    }
}