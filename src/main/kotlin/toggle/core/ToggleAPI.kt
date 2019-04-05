package toggle.core

import toggle.core.model.fetchDetailsReport


object ToggleAPI {

    @JvmStatic
    fun main(args: Array<String>) {
        fetchDetailsReport()
    }

}

//private def getClients()
///** Returns a report for all entries **/
//def fullReport(interval: Interval, billable: Billable = Both) = fetchDetailsReport(interval, billable = billable)
//
///** Returns a report for the 'sickness' project **/
//def sicknessReport(interval: Interval) = projectReport(interval, Projects.SicknessProjectId)
//
///** Returns a report for a single Project **/
//def projectReport(interval: Interval, projectId: Long) = fetchDetailsReport(interval, projectIds = projectId :: Nil)
//
///** Returns a report for the 'vacation' project **/
//def vacationReport(interval: Interval) = projectReport(interval, Projects.VacationProjectId)


