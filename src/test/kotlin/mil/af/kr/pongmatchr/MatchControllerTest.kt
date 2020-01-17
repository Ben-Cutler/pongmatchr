package mil.af.kr.pongmatchr

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MatchControllerTest {
    @InjectMockKs
    private lateinit var subject: MatchController

    @RelaxedMockK
    private lateinit var matchRepository: MatchRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `getAll returns all existing matches`() {
        val player1 = Player(name = "Yoda")
        val player2 = Player(name = "Jar Jar")
        val expectedMatches = listOf(Match(player1 = player1, player2 = player2))

        every {matchRepository.findAll()} returns expectedMatches

        assertThat(subject.getAll(), equalTo(expectedMatches))
    }

    @Test
    fun `update match updates an existing match`() {
        val player1 = Player(name = "Ben")
        val player2 = Player(name = "Tom")
        val match = Match(player1 = player1, player2 = player2, winner = null, id = 123)

        every {matchRepository.findById(123)} returns Optional.of(match)
        every { matchRepository.save(any<Match>()) } returns match
        subject.updateMatch(123, MatchRequest(player1 = player1, player2 = player2, winner = player1))

        verify(exactly = 1) { matchRepository.save(Match(player1 = player1, player2 = player2, winner = player1, id = 123)) }
    }
}