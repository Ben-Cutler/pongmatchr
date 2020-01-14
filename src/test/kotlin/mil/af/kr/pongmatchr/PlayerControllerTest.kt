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
    fun `add player saves a player to the database`() {
        val request = PlayerRequest(name = "bob")
        val player = Player(name = "bob")

        every { playerRepository.save(any<Player>()) } returns player

        val result = subject.add(request)

        verify(exactly = 1) {playerRepository.save(player)}
        assertThat(result, equalTo(player))
    }

    @Test
    fun `get endpoint returns all waiting players`() {
        val person1 = Player(name = "Ben")
        every { playerRepository.findAll() } returns listOf(person1)

        val allPlayers = subject.getAll()

        assertThat(allPlayers, equalTo(listOf(person1)))

    }

}