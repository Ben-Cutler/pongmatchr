package mil.af.kr.pongmatchr

import org.springframework.data.repository.CrudRepository

interface WaitingRoomEntryRepository: CrudRepository<WaitingRoomEntry, Int> {
}