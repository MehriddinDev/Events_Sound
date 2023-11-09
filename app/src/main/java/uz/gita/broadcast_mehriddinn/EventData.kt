package uz.gita.broadcast_mehriddinn

data class EventData(
    val id: Int = 0,
    val name: String = "unkown event",
    val action: String,
    val icon: Int? = null,
    var state: Boolean = false,
):java.io.Serializable