package com.hbeonlabs.smartguard.data.local.models

data class Hub
    (
   val hub_name :String,
   val hub_id:String,
   val hub_image:String,
//   val  hub_sensors_list : List<Sensor>,
   val  hub_phone_number : String,
   val hub_siren : Boolean,
   val  hub_arm_state : Boolean,
/*   val  hub_activity_history: List<ActivityHistory>,
   val   hub_secondary_users : List<User>,*/

)
