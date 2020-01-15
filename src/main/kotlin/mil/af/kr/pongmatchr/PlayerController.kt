package mil.af.kr.pongmatchr

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PlayerController(val playerRepository: PlayerRepository, val pongClient: PongQueueClient) {
    @PostMapping("/api/players")
    fun add(@RequestBody request: PlayerRequest): List<Player> {
        val allWaitingPlayers = playerRepository.findAll().toList()
        if (allWaitingPlayers.isEmpty()) {
            playerRepository.save(Player(name = request.name))
        } else {
            val playerToMatchWith = allWaitingPlayers[0]
            playerRepository.delete(playerToMatchWith)
            pongClient.addToList(PongItem("${playerToMatchWith.name} + ${request.name}"))
        }
        return playerRepository.findAll().toList()
    }

    @GetMapping("/api/players")
    fun getAll(): List<Player> {
        return playerRepository.findAll().toList()
    }
}