package mil.af.kr.pongmatchr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class PongmatchrApplication

fun main(args: Array<String>) {
    runApplication<PongmatchrApplication>(*args)
}
