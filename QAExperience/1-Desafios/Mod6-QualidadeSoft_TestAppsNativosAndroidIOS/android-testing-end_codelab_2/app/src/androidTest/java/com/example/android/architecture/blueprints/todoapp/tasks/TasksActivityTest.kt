package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeAndroidTestRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsNot
import org.junit.After

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TasksActivityTest{
    private lateinit var repository: TasksRepository

    @Before
    fun initRepository(){
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After
    fun cleanRepository() = runBlockingTest{
        repository.clearCompletedTasks()
    }

    @Test
    fun editTask() = runBlockingTest {
        repository.saveTask(Task("TaskTeste", "Uma descrição qualquer"))
        val launch = ActivityScenario.launch(TasksActivity::class.java)

        onView(withText("TaskTeste")).perform(ViewActions.click())
        onView(withId(R.id.task_detail_title_text)).check(matches(withText("TaskTeste")))
        onView(withId(R.id.task_detail_description_text)).check(matches(withText("Uma descrição qualquer")))
        onView(withId(R.id.task_detail_complete_checkbox)).check(matches(IsNot.not(isChecked())))

        Thread.sleep(5000)
        //editar uma task
        onView(withId(R.id.edit_task_fab)).perform(ViewActions.click())
        Thread.sleep(5000)
        onView(withId(R.id.add_task_title_edit_text)).perform(replaceText("Nova Task Teste"))
        onView(withId(R.id.add_task_description_edit_text)).perform(replaceText("Nova Uma descrição qualquer"))
        Thread.sleep(5000)
        onView(withId(R.id.save_task_fab)).perform(ViewActions.click())

        onView(withText("TaskTeste")).check(doesNotExist())
        onView(withText("Nova Task Teste")).check(matches(isDisplayed()))

        launch.close()
    }

}
