package mil.af.kr.pongmatchr

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class WaitingRoomEntryController(
        val waitingRoomEntryRepository: WaitingRoomEntryRepository,
        val pongClient: PongQueueClient,
        val playerRepository: PlayerRepository,
        val matchRepository: MatchRepository
) {
    @PostMapping("/api/waiting_room_entries")
    fun add(@RequestBody request: WaitingRoomRequest): List<WaitingRoomEntry> {
        val allWaitingPlayers = waitingRoomEntryRepository.findAll().toList()

        val players = playerRepository.findByName(request.name)

        val player = if (players.isEmpty()) {
            playerRepository.save(Player(name = request.name)) // Case where you add player to Players table
        } else {
            players[0] // Case where you get existing player from Players table
        }

        if (allWaitingPlayers.isEmpty()) {
            waitingRoomEntryRepository.save(WaitingRoomEntry(player = player)) // Save player to waiting list
        } else {
            val waitingRoomEntry = allWaitingPlayers[0]
            waitingRoomEntryRepository.delete(waitingRoomEntry)
            pongClient.addToList(PongItem("${waitingRoomEntry.player.name} + ${request.name}")) // Match!
            matchRepository.save(Match(player1 = waitingRoomEntry.player, player2 = player))
        }
        return waitingRoomEntryRepository.findAll().toList()
    }

    @GetMapping("/api/waiting_room_entries")
    fun getAll(): List<WaitingRoomEntry> {
        return waitingRoomEntryRepository.findAll().toList()
    }
}