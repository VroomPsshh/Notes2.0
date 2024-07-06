package com.vroom_psshh.notes20

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.vroom_psshh.notes20.databinding.ActivityMainBinding
import com.vroom_psshh.notes20.viewmodels.MainViewModel
import com.vroom_psshh.notes20.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var userInputAdapter: UserInputAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //initiating ViewModel
        val repository = (application as NotesApplication).notesRepository
        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(repository)
        )[MainViewModel::class.java]


        mainViewModel.notesLiveData.observe(this) { values ->
            if(values != null){
                userInputAdapter.updateNotes(values)
                val listSize = userInputAdapter.itemCount
                if (listSize != 0){
                    Log.d("App Status", "App running finee!")
                }else{
                    Toast.makeText(this, "Welcome!! You may start creating notes", Toast.LENGTH_SHORT).show()
                }
            }
        }


        //RecyclerView, adapter and layout manager
        userInputAdapter = UserInputAdapter(this, emptyList())
        binding.rcView.adapter = userInputAdapter
        binding.rcView.layoutManager = GridLayoutManager(this, 2)



        //opening a new fragment
        binding.fab.setOnClickListener {
            loadFragment()
        }
    }

    private fun loadFragment() {
        val fm: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = fm.beginTransaction()
        ft.add(R.id.main, NotesAddingFrag())
        ft.addToBackStack("this")
        ft.commit()
    }
}