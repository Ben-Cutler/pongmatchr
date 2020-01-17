package mil.af.kr.pongmatchr

import org.springframework.web.bind.annotation.*

@RestController
class MatchController(val matchRepository: MatchRepository) {
    @GetMapping("/api/matches")
    fun getAll(): List<Match> {
        return matchRepository.findAll().toList()
    }

    @PatchMapping("/api/matches/{id}")
    fun updateMatch(@PathVariable id: Int, @RequestBody request: MatchRequest): Match {
        val match = matchRepository.findById(id).get()
        match.winner = request.winner
        return matchRepository.save(match)
    }
}