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
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.neighbourproject.R
import com.example.neighbourproject.databinding.NeighbourFragmentBinding
import com.example.neighbourproject.neighbour.data.Area
import com.example.neighbourproject.neighbour.data.FriendStatus

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

            val friends = model.getFriendsStatus()[it.id] ?: FriendStatus.NONE

            binding.neighbourFriendStatus.text = friends.toString()

            when (friends) {
                FriendStatus.NONE -> {
                    setAddIcon()
                }
                FriendStatus.REQUESTED -> {
                    setAddIcon()
                }
                FriendStatus.PENDING -> {
                    setRemoveIcon()
                }
                FriendStatus.FRIENDS -> {
                    setRemoveIcon()
                    binding.neighbourEmail.isVisible = true
                }
            }
            model.loadImage(requireContext(), it.image, binding.neighbourImage)
        }

        binding.friendRequestButton.setOnClickListener {
            Log.d(TAG, "Clicked on add friend")
            model.getNeighbour()?.let {
                when (model.getFriendsStatus()[it.id] ?: FriendStatus.NONE) {
                    FriendStatus.NONE -> {
                        model.addFriend(it.id)
                        binding.neighbourEmail.isVisible = false
                        setRemoveIcon()
                        binding.neighbourFriendStatus.text = FriendStatus.PENDING.toString()
                    }
                    FriendStatus.REQUESTED -> {
                        model.addFriend(it.id)
                        binding.neighbourEmail.isVisible = true
                        setRemoveIcon()
                        binding.neighbourFriendStatus.text = FriendStatus.FRIENDS.toString()
                    }
                    FriendStatus.PENDING -> {
                        model.removeFriend(it.id)
                        binding.neighbourEmail.isVisible = false
                        setAddIcon()
                        binding.neighbourFriendStatus.text = FriendStatus.NONE.toString()
                    }
                    FriendStatus.FRIENDS -> {
                        model.removeFriend(it.id)
                        binding.neighbourEmail.isVisible = false
                        setAddIcon()
                        binding.neighbourFriendStatus.text = FriendStatus.REQUESTED.toString()
                    }
                }
            }
        }

        binding.neighbourEmail.setOnClickListener {
            model.getNeighbour()?.let {
                val email = Intent(Intent.ACTION_SEND)
                email.putExtra(Intent.EXTRA_EMAIL, arrayOf(it.email))
                email.putExtra(
                    Intent.EXTRA_SUBJECT,
                    getString(R.string.neighbour_email_intent_subject)
                )
                email.putExtra(Intent.EXTRA_TEXT, getString(R.string.neighbour_email_intent_text))
                email.type = "message/rfc822"
                startActivity(
                    Intent.createChooser(
                        email,
                        getString(R.string.neighbour_email_intent_message)
                    )
                )
            }
        }
    }

    private fun setRemoveIcon() {
        binding.friendRequestButton.setImageDrawable(
            ResourcesCompat.getDrawable(
                requireActivity().resources,
                R.drawable.ic_baseline_delete_24,
                null
            )
        )
    }

    private fun setAddIcon() {
        binding.friendRequestButton.setImageDrawable(
            ResourcesCompat.getDrawable(
                requireActivity().resources,
                R.drawable.ic_baseline_person_add_24,
                null
            )
        )
    }

    override fun onClickInterest(area: Area) {
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