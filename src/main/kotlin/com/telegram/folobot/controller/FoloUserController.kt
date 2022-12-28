package com.telegram.folobot.controller

import com.telegram.folobot.model.ControllerCommandsEnum
import com.telegram.folobot.model.dto.FoloUserDto
import com.telegram.folobot.service.FoloUserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.Objects

// TODO логику из контроллеров вынести в сервисы
@Controller
@RequestMapping("/admin/folouser")
class FoloUserController(private val foloUserService: FoloUserService) {
    @GetMapping
    fun user(model: MutableMap<String, Any>): String {
        model["folousers"] = foloUserService.findAll()
        return "user"
    }

    /**
     * Post-запрос на выполнение команды с основного экрана
     *
     * @param userId ID пользователя
     * @param tag    Переопределеннои имя
     * @param action Команда
     * @param model  Map с переменными
     * @return Имя экрана
     */
    @PostMapping
    fun onAction(
        @RequestParam userId: Long,
        @RequestParam(required = false) mainId: Long,
        @RequestParam(required = false) anchor: Boolean,
        @RequestParam(required = false) tag: String,
        @RequestParam action: String,
        model: MutableMap<String, Any>
    ): String {
        if (!Objects.isNull(userId)) {
            when (ControllerCommandsEnum.valueOf(action.uppercase())) {
                ControllerCommandsEnum.ADD ->
                    if (!foloUserService.existsById(userId)) {
                        foloUserService.save(FoloUserDto(userId, mainId, anchor, tag))
                    }
                ControllerCommandsEnum.UPDATE ->
                    if (foloUserService.existsById(userId)) {
                        foloUserService.save(
                            foloUserService.findById(userId)
                            .setMainId(mainId)
                            .setAnchor(anchor)
                            .setTag(tag)
                        )
                    }
                ControllerCommandsEnum.DELETE -> foloUserService.delete(FoloUserDto(userId))
                else -> {}
            }
        }
        return user(model)
    }
}