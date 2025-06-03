package com.suongvong.interviewtest.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.extentions.formatTOTPCode
import com.suongvong.interviewtest.model.QRData
import com.suongvong.interviewtest.utils.totp.OTPUtils
import com.suongvong.interviewtest.utils.totp.Totp.Companion.INVALID_TOTP_CODE

class TokenAdapter(var qrDatas: List<QRData>, private var itemListener: ItemListener) : RecyclerView.Adapter<TokenAdapter.ItemViewHolder>() {

    private var timeRemain: Int = 0
    private val handler = Handler(Looper.getMainLooper())
    private val runnableMap = mutableMapOf<ItemViewHolder, Runnable>()

    fun setTimeRemain(time: Int) {
        timeRemain = time;
    }

    interface ItemListener {
        fun onDelete(qrData: QRData)
        fun onItemClick(code: String)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvInfo: TextView = itemView.findViewById(R.id.tvInfo)
        val tvCode: TextView = itemView.findViewById(R.id.tvCode)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        var lnDelete: LinearLayout = itemView.findViewById(R.id.lnDelete)
        var ctlTokenItem:LinearLayout = itemView.findViewById(R.id.ctlTokenItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_token, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = qrDatas[position]
        holder.tvInfo.text = "${item.appName}: ${item.userName}"

        holder.lnDelete.setOnClickListener{
            itemListener.onDelete(item)
        }
        holder.ctlTokenItem.setOnClickListener {
            itemListener.onItemClick(holder.tvCode.text.toString().replace(" ",""))
        }
        startTotpUpdates(holder, item)

    }

    private fun startTotpUpdates(holder: ItemViewHolder, item: QRData) {
        stopTotpUpdates(holder) // Stop previous updates if any

        val runnable = object : Runnable {
            override fun run() {
                val remainingTime = getRemainingTime()
                holder.tvTime.text = remainingTime.toString()

                val totpCode = OTPUtils.generateOTP(item.secretKey)
                holder.tvCode.text = if (totpCode == INVALID_TOTP_CODE.toString()) "___ ___" else totpCode.formatTOTPCode()

                handler.postDelayed(this, 1000) // Repeat every second
            }
        }

        runnableMap[holder] = runnable
        handler.post(runnable)
    }

    fun getRemainingTime(): Int {
        val currentTimeSeconds = ((System.currentTimeMillis()) / 1000)
        val time = currentTimeSeconds % 30
        return (30 - time).toInt()

    }


    override fun onViewRecycled(holder: ItemViewHolder) {
        super.onViewRecycled(holder)
        stopTotpUpdates(holder) // Stop updates when item is recycled
    }

    private fun stopTotpUpdates(holder: ItemViewHolder) {
        runnableMap[holder]?.let {
            handler.removeCallbacks(it)
            runnableMap.remove(holder)
        }
    }

    private fun stopAllOtpUpdates() {
        handler.removeCallbacksAndMessages(null)
        runnableMap.clear()
    }


    fun updateItems(qrDatas: List<QRData>) {
        this.qrDatas = qrDatas
        stopAllOtpUpdates()
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return qrDatas.size
    }


}