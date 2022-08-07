package com.task.maintask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.task.maintask.adapter.GuestsMainListAdapter
import com.task.maintask.databinding.ActivityMainBinding
import com.task.maintask.model.Guest
import com.task.maintask.view_model.GuestViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val viewModel: GuestViewModel by viewModels()
    var adapter: GuestsMainListAdapter? = null
    var binding: ActivityMainBinding? = null
    var list: List<Guest>? = arrayListOf()
    var haveReservations: Boolean = false
    var needReservations: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        adapter = GuestsMainListAdapter(itemClick = {
            list = adapter?.lists
            var reservedList = list?.get(0)?.guestsList?.filter {
                it.selected
            }
            var needReservedList = list?.get(1)?.guestsList?.filter {
                it.selected
            }
            needReservations = needReservedList != null && needReservedList.isNotEmpty()
            haveReservations = reservedList != null && reservedList.isNotEmpty()

            setButtonBehavior()

        })
        binding?.recyclerView?.adapter = adapter

        viewModel.getGuestItems(this, "guests.json")
        viewModel.guestItems.observe(this) {
            adapter?.setItemsList(it.guest)
        }

        binding?.actionLayout?.actionBtn?.setOnClickListener {
            if (!haveReservations) {
                CustomAlertDialog.newInstance(
                    getString(R.string.reservation_needed),
                    getString(R.string.reservations)
                ).showDialog(supportFragmentManager)
            } else if (haveReservations && needReservations) {
                startScreen(Constant.conflict)
            } else {
                startScreen(Constant.done)
            }
        }
        binding?.topLayout?.appBar?.setOnClickListener {
            finish()
        }
    }

    private fun startScreen(msg: String) {
        startActivity(Intent(this, ConfirmationScreen::class.java).putExtra("msg", msg))
    }

    private fun setButtonBehavior() {
        if (haveReservations || needReservations) {
            binding?.actionLayout?.actionBtn?.alpha = 1.0f
            binding?.actionLayout?.actionBtn?.isEnabled = true
        } else {
            binding?.actionLayout?.actionBtn?.alpha = 0.5f
            binding?.actionLayout?.actionBtn?.isEnabled = false

        }

    }
}