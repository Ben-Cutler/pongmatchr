package mil.af.kr.pongmatchr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PongmatchrApplication

fun main(args: Array<String>) {
    runApplication<PongmatchrApplication>(*args)
}
