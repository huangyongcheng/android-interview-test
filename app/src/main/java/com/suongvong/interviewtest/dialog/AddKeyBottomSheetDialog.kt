package com.suongvong.interviewtest.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.suongvong.interviewtest.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddKeyBottomSheetDialog(private var listener: AddKeyBottomSheetDialogListener) : BottomSheetDialogFragment(), View.OnClickListener {

    private var ivClose: ImageView? = null
    private var tvScanQR: TextView? = null
    private var tvEnterSetupKey: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bottom_sheet_add_key, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivClose = view.findViewById(R.id.ivClose)
        tvScanQR = view.findViewById(R.id.tvScanQR)
        tvEnterSetupKey = view.findViewById(R.id.tvEnterSetupKey)

        ivClose?.setOnClickListener(this)
        tvScanQR?.setOnClickListener(this)
        tvEnterSetupKey?.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivClose -> {
                dismiss()
            }

            R.id.tvScanQR -> {
                listener.onScanQR()
                dismiss()
            }

            R.id.tvEnterSetupKey -> {
                listener.onEnterSetupKey()
                dismiss()
            }
        }
    }

    interface AddKeyBottomSheetDialogListener {
        fun onScanQR()
        fun onEnterSetupKey()
    }
}