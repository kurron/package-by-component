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
                         Armor("Leather Armor", 2, 340, 20),
                         Armor("Light Scale", 3, 610, 49),
                         Armor("Mail", 4, 645, 58),
                         Armor("Steel Laminate", 5, 1_360, 64),
                         Armor("Plate", 6, 4_040, 90),
                         Armor("Flak Jacket", 7, 500, 20),
                         Armor("Ballistic Vest", 8, 400, 2),
                         Armor("Tactical Vest", 12, 900, 9),
        )
        model.addAttribute("ARMOR", data)
    }

    data class Armor(val type: String, val damageResistance: Int, val cost: Int, val weight: Int)

}