package com.blessingdropshop.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.blessingdropshop.app.databinding.FragmentUpdateItemsDialogBinding
import com.blessingdropshop.app.viewmodels.ItemsViewModel
import com.blessingdropshop.app.models.AdminItemModel


class UpdateItemsDialogFragment(private val item: AdminItemModel) : DialogFragment() {

    private var _binding: FragmentUpdateItemsDialogBinding? =null
    private val binding get() =_binding!!
    private lateinit var viewModel: ItemsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateItemsDialogBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[ItemsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.confirmBtnClaim.setOnClickListener{
//            val item = AdminItemModel()
//            item.status = "claimed"
//            viewModel.updateItem(item)
//            dismiss()
//            Toast.makeText(context, "Item has been claimed", Toast.LENGTH_SHORT).show()
//        }
//        binding.cancelBtnClaim.setOnClickListener{
//            dismiss()
//        }
    }

}