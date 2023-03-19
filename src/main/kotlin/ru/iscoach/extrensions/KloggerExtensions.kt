package ru.iscoach.extrensions

import mu.KLogger
import ru.iscoach.service.model.Actions

fun KLogger.addActionReceived(action: Actions) = this.info { "Received request with action $action" }