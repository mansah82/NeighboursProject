package com.example.neighbourproject.ui.neigbour

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.neighbourproject.databinding.NeighbourFragmentBinding
import com.example.neighbourproject.neighbour.data.Area
import com.example.neighbourproject.neighbour.data.FriendStatus
import com.example.neighbourproject.ui.search.ClickListener
import com.example.neighbourproject.ui.search.SearchRecyclerAdapter

class NeighbourFragment : Fragment(), InterestClickListener {
    companion object {
        private const val TAG = "NeighbourFragment"
    }

    private lateinit var binding: NeighbourFragmentBinding

    private val model: NeighbourViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NeighbourFragmentBinding.inflate(inflater)

        model.getNeighbour()?.let {

            val interestAdapter = InterestRecyclerAdapter(
                it.interests,
                this as InterestClickListener,
                model
            )

            binding.interestList.adapter = interestAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.neighbourEmail.isVisible = false
        
        model.getNeighbour()?.let {
            binding.neighbourName.text = it.firstName.plus(" ").plus(it.lastName)
            binding.neighbourAge.text = it.age.toString()
            binding.neighbourGender.text = it.gender.text
            binding.neighbourStatus.text = it.relationshipStatus.text
            binding.neighbourEmail.text = it.email
            if(model.getFriendsStatus().containsKey(it.id)){
                binding.neighbourFriendStatus.text = model.getFriendsStatus()[it.id].toString()
                if(model.getFriendsStatus()[it.id] == FriendStatus.FRIENDS)

                    binding.neighbourEmail.isVisible = true
            }else{
                binding.neighbourFriendStatus.text = FriendStatus.NONE.toString()
            }
        }

        binding.friendRequestButton.setOnClickListener {
            Log.d(TAG, "Clicked on add friend")
            model.getNeighbour()?.let {
                model.setFriend(it.id)
            }
        }
    }

    override fun onClick(area: Area) {
        val position = area.position

        val gmmIntentUri = if (position != null) {
            Uri.parse("geo:0,0?q=${position.latitude},${position.longitude}(${area.area})")
        } else {
            Uri.parse("geo:0,0?q=${area.area}")
        }

        gmmIntentUri?.let {
            Log.d(TAG, "Starting google map with: $it")
            val mapIntent = Intent(Intent.ACTION_VIEW, it)
            mapIntent.setPackage("com.google.android.apps.maps")
            try {
                startActivity(mapIntent)
            } catch (e: ActivityNotFoundException) {
                Log.d(TAG, "Could not start google map")
            }
        }
    }
}