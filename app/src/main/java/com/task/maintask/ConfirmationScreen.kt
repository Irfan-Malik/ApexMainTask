package com.task.maintask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.task.maintask.databinding.ConfirmationScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmationScreen : AppCompatActivity() {

    var binding: ConfirmationScreenBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ConfirmationScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        intent.apply {
            var action = this.getStringExtra("msg")
            if (action == Constant.conflict) {
                binding?.msg?.text = getString(R.string.conflict_found)
            } else {
                binding?.msg?.text = getString(R.string.confirm)
            }
        }
        binding?.actionLayout?.actionBtn?.text = getString(R.string.done)
        binding?.topLayout?.title?.text = getString(R.string.confirm_guests)
        binding?.actionLayout?.actionBtn?.alpha = 1.0f
        binding?.topLayout?.appBar?.setOnClickListener {
            finish()
        }
        binding?.actionLayout?.actionBtn?.setOnClickListener {
            finish()
        }
    }
}