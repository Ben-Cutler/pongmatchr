package mil.af.kr.pongmatchr

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PlayerController(val playerRepository: PlayerRepository) {
    @PostMapping("/api/players")
    fun add(@RequestBody request: PlayerRequest): Player {
        return playerRepository.save(Player(name = request.name))
    }

    @GetMapping("/api/players")
    fun getAll(): List<Player> {
        return playerRepository.findAll().toList()
    }
}