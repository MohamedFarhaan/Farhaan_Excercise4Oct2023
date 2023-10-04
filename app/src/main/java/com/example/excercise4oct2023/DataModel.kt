package com.example.excercise4oct2023

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.excercise4oct2023.db.DatabaseInit
import com.example.excercise4oct2023.db.entity.BloodGroup
import com.example.excercise4oct2023.db.entity.People
import kotlinx.coroutines.launch

class DataModel(private val db: DatabaseInit): ViewModel() {
    var peopleBloodGroupFilter = mutableStateOf<Int?>(null)
    var bloodGroupList = mutableStateOf<List<BloodGroup>?>(null)
    var peopleList = mutableStateOf<List<People>?>(null)
    init {
        viewModelScope.launch {
            bloodGroupList.value = db.bloodGroupDao().getAllBloodGroupData()
            peopleList.value =
                if(peopleBloodGroupFilter.value == null)
                    db.peopleDao().getAllPeopleData()
                else
                    db.peopleDao().getAllPeopleWithBloodGroup(peopleBloodGroupFilter.value!!)
        }
    }

    fun addPeople(name: String, bloodGroupId: Int) {
        val people = People(name = name, blood_group_id = bloodGroupId, blood_group = "")
        viewModelScope.launch {
            db.peopleDao().insertPeople(people);
            peopleList.value =
                if(peopleBloodGroupFilter.value == null)
                    db.peopleDao().getAllPeopleData()
                else
                    db.peopleDao().getAllPeopleWithBloodGroup(peopleBloodGroupFilter.value!!)
        }
    }

    fun deletePeople(name: String) {
        viewModelScope.launch {
            db.peopleDao().deletePeople(name);
            peopleList.value =
                if(peopleBloodGroupFilter.value == null)
                    db.peopleDao().getAllPeopleData()
                else
                    db.peopleDao().getAllPeopleWithBloodGroup(peopleBloodGroupFilter.value!!)
        }
    }

    fun populatePeople() {
        viewModelScope.launch {
            peopleList.value =
                if(peopleBloodGroupFilter.value == null)
                    db.peopleDao().getAllPeopleData()
                else
                    db.peopleDao().getAllPeopleWithBloodGroup(peopleBloodGroupFilter.value!!)
        }
    }

}

class DataModelFactory(private val db: DatabaseInit) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataModel::class.java)) {
            return DataModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}