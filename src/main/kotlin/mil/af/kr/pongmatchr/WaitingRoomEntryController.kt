package mil.af.kr.pongmatchr

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class WaitingRoomEntryController(val waitingRoomEntryRepository: WaitingRoomEntryRepository, val pongClient: PongQueueClient, val playerRepository: PlayerRepository) {
    @PostMapping("/api/waiting_room_entries")
    fun add(@RequestBody request: WaitingRoomRequest): List<WaitingRoomEntry> {
        val allWaitingPlayers = waitingRoomEntryRepository.findAll().toList()

        val optionalPlayer = playerRepository.findByName(request.name)

        val player = if (optionalPlayer.isEmpty()) {
            playerRepository.save(Player(name = request.name))
        } else {
            optionalPlayer.get()
        }

        if (allWaitingPlayers.isEmpty()) {
            waitingRoomEntryRepository.save(WaitingRoomEntry(player = player))
        } else {
            val waitingRoomPlayer = allWaitingPlayers[0]
            waitingRoomEntryRepository.delete(waitingRoomPlayer)
            pongClient.addToList(PongItem("${waitingRoomPlayer.player.name} + ${request.name}"))
        }
        return waitingRoomEntryRepository.findAll().toList()
    }

    @GetMapping("/api/waiting_room_entries")
    fun getAll(): List<WaitingRoomEntry> {
        return waitingRoomEntryRepository.findAll().toList()
    }
}