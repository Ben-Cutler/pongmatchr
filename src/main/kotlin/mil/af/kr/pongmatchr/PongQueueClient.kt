package mil.af.kr.pongmatchr

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "PongQueueClient", url = "\${pong-queue-url}")
interface PongQueueClient {
    @RequestMapping(method = [RequestMethod.GET], value = ["/backend/list"])
    fun getList(): PongQueue

    @RequestMapping(method = [RequestMethod.POST], value = ["/backend/add"])
    fun addToList(item: PongItem)
}