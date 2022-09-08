package com.hbeonlabs.smartguard.data.local.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.models.Sensor

data class HubWithSensors(
    @Embedded val hub:Hub,
    @Relation (
        parentColumn = "hub_serial_number",
        entityColumn = "hub_serial_number"
            )
    val sensor:List<Sensor>
)
