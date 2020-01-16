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
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class WaitingRoomEntryControllerTest {
    @InjectMockKs
    private lateinit var subject: WaitingRoomEntryController

    @RelaxedMockK
    private lateinit var waitingRoomEntryRepository: WaitingRoomEntryRepository

    @RelaxedMockK
    private lateinit var playerRepository: PlayerRepository

    @RelaxedMockK
    private lateinit var pongQueueClient: PongQueueClient

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `add does not creates a player if it does exist and adds it to the waiting list, and returns a list of waiting players`() {
        val request = WaitingRoomRequest(name = "bob")
        val player = Player(id = 1, name = "bob")
        val waitingRoom = WaitingRoomEntry(id = 0, player = player)

        every { playerRepository.findByName("bob") } returns Optional.of(player)
        every { waitingRoomEntryRepository.save(any<WaitingRoomEntry>()) } returns waitingRoom
        every { waitingRoomEntryRepository.findAll() } returnsMany listOf(emptyList(), listOf(waitingRoom))

        val result = subject.add(request)

        verify(exactly = 1) {waitingRoomEntryRepository.save(waitingRoom)}
        assertThat(result, equalTo(listOf(waitingRoom)))
    }

    @Test
    fun `add does create a player if it does NOT exist and adds it to the waiting list, and returns a list of waiting players`() {
        val request = WaitingRoomRequest(name = "Yoda")
        val player = Player(name = "Yoda")
        val waitingRoom = WaitingRoomEntry( player = player)

        every { playerRepository.save(any<Player>())} returns player
        every { playerRepository.findByName("Yoda")} returns Optional.empty()
        every { waitingRoomEntryRepository.save(any<WaitingRoomEntry>())} returns waitingRoom
        every { waitingRoomEntryRepository.findAll()} returnsMany listOf(emptyList(), listOf(waitingRoom))


        val result = subject.add(request)

        verify(exactly = 1) {playerRepository.save(player)}
        verify(exactly = 1) {waitingRoomEntryRepository.save(waitingRoom)}
        assertThat(result, equalTo(listOf(waitingRoom)))
    }

    @Test
    fun `add player matches with another player if there are players in the table and returns list of all waiting players`() {
        val request = WaitingRoomRequest(name = "bob")
        val player1 = Player(name = "alice")
        val player2 = Player(name = "bob")
        val waitingRoom1 = WaitingRoomEntry(player = player1)
        val waitingRoom2 = WaitingRoomEntry(player = player2)

        every { waitingRoomEntryRepository.findAll() } returnsMany listOf(listOf(waitingRoom1), emptyList())
        every { playerRepository.findByName("alice") } returns Optional.of(player1)
        every { playerRepository.findByName("bob") } returns Optional.of(player2)

        val result = subject.add(request)

        verify(exactly = 1) { waitingRoomEntryRepository.delete(waitingRoom1) }
        verify(exactly = 0) { waitingRoomEntryRepository.save(waitingRoom2) }
        verify(exactly = 1) { pongQueueClient.addToList(PongItem(Name = "alice + bob")) }

        assertThat(result, equalTo(emptyList()))
    }

    @Test
    fun `get endpoint returns all waiting players`() {
        val waitingRoom1 = WaitingRoomEntry(player = Player(name = "Ben"))
        every { waitingRoomEntryRepository.findAll() } returns listOf(waitingRoom1)

        val allPlayers = subject.getAll()

        assertThat(allPlayers, equalTo(listOf(waitingRoom1)))
    }
}