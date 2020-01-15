package mil.af.kr.pongmatchr

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PlayerControllerTest {
    @InjectMockKs
    private lateinit var subject: PlayerController

    @RelaxedMockK
    private lateinit var playerRepository: PlayerRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `add player saves a player to the database and returns a list of waiting players`() {
        val request = PlayerRequest(name = "bob")
        val player = Player(name = "bob")

        every { playerRepository.save(any<Player>()) } returns player
        every { playerRepository.findAll() } returnsMany listOf(emptyList(), listOf(player))

        val result = subject.add(request)

        verify(exactly = 1) {playerRepository.save(player)}
        assertThat(result, equalTo(listOf(player)))
    }

    @Test
    fun `add player matches with another player if there are players in the table and returns list of all waiting players`() {
        val request = PlayerRequest(name = "bob")
        val player1 = Player(name = "alice")
        val player2 = Player(name = "bob")

        every { playerRepository.findAll() } returnsMany listOf(listOf(player1), emptyList())

        // call to pong-queue app Add player
        val result = subject.add(request)

        verify(exactly = 1) {
            playerRepository.delete(player1)
        }
        verify(exactly = 0) {playerRepository.save(player2)}

        assertThat(result, equalTo(emptyList()))
    }

    @Test
    fun `get endpoint returns all waiting players`() {
        val person1 = Player(name = "Ben")
        every { playerRepository.findAll() } returns listOf(person1)

        val allPlayers = subject.getAll()

        assertThat(allPlayers, equalTo(listOf(person1)))

    }

}