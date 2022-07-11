package com.mateuszcholyn.wallet.ui.wellness

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


interface WellnessRepository {
    fun getWellnessTasks(): List<WellnessTask>
}

class InMemoryWellnessRepository : WellnessRepository {
    override fun getWellnessTasks(): List<WellnessTask> =
        List(30) { i -> WellnessTask(i, "Task # $i") }
}


@HiltViewModel
class WellnessViewModel @Inject constructor(
    private val clicker: Clicker,
    private val wellnessRepository: WellnessRepository,
) : ViewModel() {
    private val _tasks = wellnessRepository.getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun remove(item: WellnessTask) {
        _tasks.remove(item)
        clicker.show()
    }

    fun changeTaskChecked(item: WellnessTask, checked: Boolean) {
        tasks
            .find { it.id == item.id }
            ?.let { task ->
                task.checked = checked
            }
    }


}

