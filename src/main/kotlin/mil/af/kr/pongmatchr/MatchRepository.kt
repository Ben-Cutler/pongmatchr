package mil.af.kr.pongmatchr

import org.springframework.data.repository.CrudRepository

interface MatchRepository:CrudRepository<Match, Int> {
}