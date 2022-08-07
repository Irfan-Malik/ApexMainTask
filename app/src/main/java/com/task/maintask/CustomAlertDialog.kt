package com.task.maintask

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.task.maintask.databinding.NotifyLayoutBinding

fun CustomAlertDialog.showDialog(fragmentManager: FragmentManager,tag:String = "AlertDialog"){
    show(fragmentManager,tag)
}
class CustomAlertDialog : DialogFragment() {

    object CustomAlertDialog {
        const val TITLE_KEY: String = "TITLE_KEY"
        const val SUB_TITLE_KEY: String = "SUB_TITLE_KEY"
    }

    private var _binding: NotifyLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext(), android.R.style.Theme_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.apply {
            setGravity(Gravity.CENTER)
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setGravity(Gravity.BOTTOM)
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NotifyLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = requireArguments().getString(CustomAlertDialog.TITLE_KEY)
        binding.description.text = requireArguments().getString(CustomAlertDialog.SUB_TITLE_KEY)
        binding.closeIcon.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        fun newInstance(
            title: String,
            subtitle: String
        ) = CustomAlertDialog().apply {
            arguments = Bundle().apply {
                putString(CustomAlertDialog.TITLE_KEY, title)
                putString(CustomAlertDialog.SUB_TITLE_KEY, subtitle)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}