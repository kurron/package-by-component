package org.kurron.gurps.campaign

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Controller
@RequestMapping("/campaign-ui")
class CampaignWebPage() {

    // POST /asset - create
    // PUT /asset/{id} - update
    // GET /asset/{id} - read
    // DELETE /asset/{id} - delete
    // bulk upload?
    @GetMapping(path = ["/home"], produces = [MediaType.TEXT_PLAIN_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun handleInitialize(): String {
        val viewName = "campaign-home"
        return viewName
    }
}