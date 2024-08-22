package com.vroom_psshh.notes20

import android.content.Intent
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
import com.vroom_psshh.notes20.roomDB.UserInput
import com.vroom_psshh.notes20.viewmodels.DialogActionListener
import com.vroom_psshh.notes20.viewmodels.MainViewModel
import com.vroom_psshh.notes20.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity(), DialogActionListener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var userInputAdapter: UserInputAdapter
    private var userID: Int = 0
    private lateinit var title: String
    private lateinit var userInput: String

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

        liveDataObserver()

        //RecyclerView, adapter and layout manager
        userInputAdapter = UserInputAdapter(this, emptyList(), this)
        binding.rcView.adapter = userInputAdapter
        binding.rcView.layoutManager = GridLayoutManager(this, 2)


        //opening a new fragment
        binding.fab.setOnClickListener {
            loadFragment(0)
        }
    }

    private fun liveDataObserver() {
        mainViewModel.notesLiveData.observe(this) { notes ->
            notes?.let {
                userInputAdapter.syncNotes(it)
                val listSize = userInputAdapter.itemCount
                if (listSize != 0) {
                    //Toast.makeText(this, "Welcome Back!, size is ${it.size}", Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(this, "Welcome, you may start creating notes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadFragment(checkPoint: Int) {
        val fm: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = fm.beginTransaction()
        if (checkPoint == 0) {
            ft.add(R.id.main, NotesAddingFrag())
            ft.addToBackStack("this")
            ft.commit()
        } else {
            val frag = NotesAddingFrag()
            val bundle = Bundle()
            bundle.putInt("notesId", userID)
            bundle.putString("title", title)
            bundle.putString("userInput", userInput)
            frag.arguments = bundle
            ft.add(R.id.main, frag)
            ft.addToBackStack("this")
            ft.commit()
        }
    }

    override fun onEdit(position: Int) {
        mainViewModel.notesLiveData.observe(this) { values ->
            values?.let {
                val dataAtThisPosition = it[position]
                userID = dataAtThisPosition.notesId
                title = dataAtThisPosition.title
                userInput = dataAtThisPosition.userInput
                loadFragment(1)
            }
        }
    }

    override fun onDelete(userInput: UserInput){
        Log.d("Deleting note:", userInput.toString())
        Log.d("Adapter size before delete:", userInputAdapter.entryList.size.toString())
        mainViewModel.deleteNotes(userInput)
        mainViewModel.notesLiveData.observe(this) { notes ->
            userInputAdapter.syncNotes(notes)
            userInputAdapter.syncNotes(notes)
            Log.d("Notes after delete:", notes.toString())
            Log.d("Adapter size after delete:", userInputAdapter.entryList.size.toString()) // Log after deleting
        }
    }

    override fun onShare(position: Int) {
        mainViewModel.notesLiveData.observe(this) { values ->
            values?.let {
                val dataAtThisPosition = it[position]
                userID = dataAtThisPosition.notesId
                title = dataAtThisPosition.title
                userInput = dataAtThisPosition.userInput
            }
        }
        val shareData = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, "Title = $title, Note = $userInput")
            this.type = "text/plain"
        }
        startActivity(shareData)
    }
}
