package org.kurron.gurps.asset

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Controller
@RequestMapping("/asset-ui")
class AssetWebPage() {

    // POST /asset - create
    // PUT /asset/{id} - update
    // GET /asset/{id} - read
    // DELETE /asset/{id} - delete
    // bulk upload?
    @GetMapping(path = ["/home"], produces = [MediaType.TEXT_PLAIN_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun handleInitialize(): String {
        val viewName = "asset-home"
        return viewName
    }

    @ModelAttribute
    fun addArmor(model: Model) {
        val data = listOf(Armor("Cloth Armor", 1, 150, 12),
                         Armor("Leather Armor", 2, 340, 20))
        model.addAttribute("ARMOR", data)
/*
        model.addAttribute("Cloth Armor", 1)
        model.addAttribute("Leather Armor", 2)
        model.addAttribute("Light Scale", 3)
        model.addAttribute("Mail", 4)
        model.addAttribute("Steel Laminate", 5)
        model.addAttribute("Plate", 6)
        model.addAttribute("Flak Jacket", 7)
        model.addAttribute("Ballistic Vest", 8)
        model.addAttribute("Tactical Vest", 12)
*/
    }

    data class Armor(val type: String, val damageResistance: Int, val cost: Int, val weight: Int)

}