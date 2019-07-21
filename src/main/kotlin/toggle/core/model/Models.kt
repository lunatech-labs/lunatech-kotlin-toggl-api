package toggle.core.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime
import org.joda.time.Duration
import utils.TimeUtil
import java.lang.reflect.Type
import java.math.BigDecimal

object Projects {
    const val VacationProjectId = 1900577L
    const val SicknessProjectId = 1900578L
}

data class DetailsReport(
    @SerializedName("total_billable") @JsonAdapter(DurationDeserializer::class) val totalBillable: Duration,
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("data") val entries: List<TimeEntry>
) {

    fun entriesByDay() = entries.groupBy { it.start.dayOfMonth }

    fun users() = entriesByUser.keys

    private val entriesByUser = entries.groupBy { User(it.userId, it.userName) }

//    fun clients = entriesByClient().keys
//
//    fun entriesByClient: ListMap[Client, List[TimeEntry]] = Listmap {entries.filter {_.client.isDefined).groupBy(_.client.get).toSeq.sortBy(_._1.name): _*)

//    fun entriesWithoutProjects() = entries.flatMap {
//        it.projectId?.Some(it) ?: None
//    }

    fun projectsByClient() = projects.filter { it.clientName != null }.groupBy { it.clientName }

    private val projects = entriesByProject().keys

    private fun entriesByProject() = entries.filter { it.projectId != null && it.projectName != null }
        .groupBy { Project(it.projectId!!.toLong(), it.projectName!!.intern(), it.clientName!!.toString()) }

    fun vacationHours() =
        entries.filter { it.projectId?.compareTo(Projects.VacationProjectId) == 0 }.map { it.hours() }.sum()

    fun sicknessHours() =
        entries.filter { it.projectId?.compareTo(Projects.SicknessProjectId) == 0 }.map { it.hours() }.sum()

    val totalNonBillableHours
        get() = totalHours.invoke() - totalBillableHours.invoke()

    private val totalBillableHours =
        entries.filter { it.billable }.map { TimeUtil.toHours(it.duration.standardSeconds) }::sum

    private val totalHours = entries.map { TimeUtil.toHours(it.duration.standardSeconds) }::sum

}

data class TimeEntry(
    @SerializedName("uid") val userId: Long,
    @SerializedName("user") val userName: String,
    @SerializedName("description") val _description: String,
    @JsonAdapter(DateTimeDeserializer::class) var start: DateTime,
    @JsonAdapter(DateTimeDeserializer::class) val end: DateTime?,
    @SerializedName("dur") @JsonAdapter(DurationDeserializer::class) val duration: Duration,
    @JsonAdapter(DateTimeDeserializer::class) val updated: DateTime,
    @SerializedName("is_billable") val billable: Boolean,
    @SerializedName("billable") val amount: BigDecimal?,
    val tags: Set<String>,
    @SerializedName("pid") val projectId: Long?,
    @SerializedName("project") val projectName: String?,
    @SerializedName("client") val clientName: String?
) {
    companion object

    fun hours() = TimeUtil.toHours(duration.standardSeconds).toDouble()

    fun description() = if (this._description.isEmpty()) "No description" else this._description
}

object DateTimeDeserializer : JsonDeserializer<DateTime> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): DateTime {
        return DateTime.parse(json?.asString)
    }
}

object DurationDeserializer : JsonDeserializer<Duration> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Duration {
        return Duration.millis(json?.asLong!!)
    }
}

data class WeeklyReport(
    @SerializedName("total_billable") @JsonAdapter(DurationDeserializer::class) val totalBillable: Duration,
    @SerializedName("total_count") val totalCount: Int
)

data class Title(
    val client: String, val project: String, val color: String,
    @SerializedName("hex_color") val hexColor: String
)

data class Totals(

)

data class Project(val id: Long, val name: String, val clientName: String?)

data class ProjectUser(val userId: Long, val manager: Boolean, val hourlyRate: BigDecimal?)

data class User(val id: Long, val name: String)

data class UserReport(val uid: Long, val name: String?, val email: String?, val at: DateTime)

data class Client(val id: Long, val name: String, val hourlyRate: BigDecimal?)


enum class Billable(val value: String) {
    Yes("yes"),
    No("no"),
    Both("both")
}
