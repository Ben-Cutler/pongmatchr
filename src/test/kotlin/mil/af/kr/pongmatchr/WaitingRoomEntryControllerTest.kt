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
    private lateinit var matchRepository: MatchRepository

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

        every { playerRepository.findByName("bob") } returns listOf(player)
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
        every { playerRepository.findByName("Yoda")} returns emptyList()
        every { waitingRoomEntryRepository.save(any<WaitingRoomEntry>())} returns waitingRoom
        every { waitingRoomEntryRepository.findAll()} returnsMany listOf(emptyList(), listOf(waitingRoom))


        val result = subject.add(request)

        verify(exactly = 1) {playerRepository.save(player)}
        verify(exactly = 1) {waitingRoomEntryRepository.save(waitingRoom)}
        assertThat(result, equalTo(listOf(waitingRoom)))
    }

    @Test
    fun `add player matches with another player if there are players in the table, returns a list of all waiting players and creates a match`() {
        val request = WaitingRoomRequest(name = "bob")
        val player1 = Player(name = "alice")
        val player2 = Player(name = "bob")
        val waitingRoomEntry1 = WaitingRoomEntry(player = player1)
        val waitingRoomEntry2 = WaitingRoomEntry(player = player2)
        val match = Match(player1 = player1, player2 = player2)

        every { waitingRoomEntryRepository.findAll() } returnsMany listOf(listOf(waitingRoomEntry1), emptyList())
        every { playerRepository.findByName("alice") } returns listOf(player1)
        every { playerRepository.findByName("bob") } returns listOf(player2)
        every { matchRepository.save(any<Match>())} returns match

        val result = subject.add(request)

        verify(exactly = 1) { waitingRoomEntryRepository.delete(waitingRoomEntry1) }
        verify(exactly = 0) { waitingRoomEntryRepository.save(waitingRoomEntry2) }
        verify(exactly = 1) { pongQueueClient.addToList(PongItem(Name = "alice + bob")) }
        verify(exactly = 1) {matchRepository.save(match)}

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