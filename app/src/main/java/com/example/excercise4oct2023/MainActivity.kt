package com.example.excercise4oct2023

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.excercise4oct2023.db.DatabaseInit
import com.example.excercise4oct2023.db.entity.BloodGroup
import com.example.excercise4oct2023.ui.theme.Excercise4Oct2023Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Excercise4Oct2023Theme {
                App(applicationContext)
            }
        }
    }
}

@Composable
fun App(context: Context) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val db = DatabaseInit.getDatabase(context)
        val dataModel: DataModel = viewModel(factory = DataModelFactory(db))
        Column {
            Row (Modifier.fillMaxWidth()) {
                Column (
                    Modifier
                        .weight(1f)
                        .padding(12.dp)) {
                    InsertPeople(dataModel)
                }
                Column (
                    Modifier
                        .weight(1f)
                        .padding(12.dp)) {
                    DeletePeople(dataModel)
                }
            }
            Divider(Modifier.padding(0.dp, 5.dp))
            ShowPeople(dataModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    Excercise4Oct2023Theme {
        App(LocalContext.current)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPeople(dataModel: DataModel) {
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        var bloodGroupList = dataModel.bloodGroupList;
        var name by remember { mutableStateOf("") }
        var bloodGroup by remember { mutableStateOf<Int>(0) }
        var expanded by remember { mutableStateOf(false) }
        Text(text = "Insert People Data", Modifier.padding(12.dp))
        TextField(value = name, onValueChange = { name = it },
            Modifier.padding(12.dp),
            label = { Text(text = "Name") })
        ExposedDropdownMenuBox(
            modifier = Modifier.padding(12.dp),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }) {
            TextField(
                value = bloodGroupList.value?.find { it.id == bloodGroup }?.blood_group ?: "",
                label = { Text(text = "Blood Group") },
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                bloodGroupList.value?.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.blood_group) },
                        onClick = {
                            bloodGroup = item.id
                            expanded = false
                        }
                    )
                }
            }
        }
        Button(enabled = (name.trim().length > 0 && bloodGroup != 0),
            onClick = { dataModel.addPeople(name, bloodGroup);
                         name = ""; bloodGroup= 0},
            modifier = Modifier.padding(12.dp)) {
            Text(text = "Add Data")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InsertPeoplePreview() {
    Excercise4Oct2023Theme {
        InsertPeople(viewModel(factory = DataModelFactory(DatabaseInit.getDatabase(LocalContext.current))))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeletePeople(dataModel: DataModel) {
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        var name by remember { mutableStateOf("") }
        Text(text = "Delete People Data", Modifier.padding(12.dp))
        TextField(value = name, onValueChange = { name = it },
            Modifier.padding(12.dp),
            label = { Text(text = "Name") })
        Button(enabled = name.trim().length > 0,
            onClick = { dataModel.deletePeople(name); name = "" },
            modifier = Modifier.padding(12.dp)) {
            Text(text = "Delete Data")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeletePeoplePreview() {
    Excercise4Oct2023Theme {
        DeletePeople(viewModel(factory = DataModelFactory(DatabaseInit.getDatabase(LocalContext.current))))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowPeople(dataModel: DataModel) {
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround){
            var bloodGroupList = dataModel.bloodGroupList;
            var bloodGroup by remember { mutableStateOf<Int>(0) }
            var expanded by remember { mutableStateOf(false) }
            Text(text = "People List", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(2f),
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }) {
                TextField(
                    value = bloodGroupList.value?.find { it.id == bloodGroup }?.blood_group ?: "",
                    label = { Text(text = "Blood Group") },
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    bloodGroupList.value?.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item.blood_group) },
                            onClick = {
                                bloodGroup = item.id
                                expanded = false
                                dataModel.peopleBloodGroupFilter.value = item.id
                                dataModel.populatePeople()
                            }
                        )
                    }
                }
            }
            Button(onClick = { dataModel.peopleBloodGroupFilter.value = null; bloodGroup = 0; dataModel.populatePeople() }, Modifier.weight(1f)) {
                Text("Clear Filter")
            }
        }
        LazyColumn {
            if(dataModel.peopleList.value != null && dataModel.peopleList.value?.size?:0 > 0) {
                items(dataModel.peopleList.value!!) {
                    Text(text = "Id(${it.id}) Name(${it.name}) BloodGroup(${it.blood_group})")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowPeoplePreview() {
    Excercise4Oct2023Theme {
        ShowPeople(viewModel(factory = DataModelFactory(DatabaseInit.getDatabase(LocalContext.current))))
    }
}