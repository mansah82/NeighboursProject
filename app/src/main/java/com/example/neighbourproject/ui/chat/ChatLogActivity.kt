package com.example.neighbourproject.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.neighbourproject.databinding.ActivityChatLogBinding

class ChatLogActivity : AppCompatActivity() {
    private val model: ChatViewModel by viewModels()

    private lateinit var binding: ActivityChatLogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewChatLog.adapter = ChatLogAdapter(model, this)

        binding.sendButtonChatLog.setOnClickListener {
            if(binding.enterMessageChatLog.text.toString().isNotEmpty()) {
                model.writeMessage(binding.enterMessageChatLog.text.toString())
                binding.enterMessageChatLog.setText("")
                scrollLast()
            }
        }
    }

    private fun scrollLast(){
        model.getLiveMessages().value?.let {
            if (it.isNotEmpty()) {
                binding.recyclerViewChatLog.scrollToPosition(it.size - 1)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        model.startChat()
        scrollLast()
    }

    override fun onPause() {
        super.onPause()
        model.stopChat()
    }
}