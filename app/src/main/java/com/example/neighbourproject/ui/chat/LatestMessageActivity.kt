package com.example.neighbourproject.ui.chat

import android.app.SearchableInfo
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.neighbourproject.R
import com.example.neighbourproject.databinding.ActivityEditProfileBinding
import com.example.neighbourproject.databinding.ActivityLatestMessageBinding
import com.example.neighbourproject.databinding.SearchFragmentBinding
import com.example.neighbourproject.ui.edit.EditViewModel
import com.example.neighbourproject.ui.neigbour.NeighbourActivity
import com.example.neighbourproject.ui.search.ClickListener
import com.example.neighbourproject.ui.search.SearchActivity
import com.example.neighbourproject.ui.signup.SignUpActivity
import com.google.firebase.auth.FirebaseAuth

class LatestMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLatestMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLatestMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        verifyUserLoggedIn()

        binding.backToSearchButton.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.newMessageButton.setOnClickListener {
            startActivity(Intent(this, NewMessageActivity::class.java))
        }

    }

    private fun verifyUserLoggedIn(){
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null){
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_new_message -> {
            val intent = Intent(this, NewMessageActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_back_to_search ->{
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}