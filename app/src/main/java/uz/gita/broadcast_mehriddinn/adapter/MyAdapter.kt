package uz.gita.broadcast_mehriddinn.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.broadcast_mehriddinn.EventData
import uz.gita.broadcast_mehriddinn.R
import uz.gita.broadcast_mehriddinn.databinding.ItemSwitchBinding

class MyAdapter : ListAdapter<EventData, MyAdapter.MyHolder>(Callback) {
    private lateinit var checkedListener:((Boolean,EventData)->Unit)
    fun setCheckListener(k:(Boolean,EventData)->Unit){
        checkedListener = k
    }
    inner class MyHolder(private val binding: ItemSwitchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                checkedListener.invoke(isChecked,getItem(adapterPosition))
            }
        }

        fun bind() = with(binding) {
            val data = getItem(adapterPosition)
            txtName.text = data.name
            btnSwitch.isChecked = data.state
            val icon = when(data.id){
                1->{
                    R.drawable.plane2}
                2->{R.drawable.ic_screen_off}
                3->{R.drawable.ic_screen_on}
                4->{R.drawable.bt}
                5->{R.drawable.ic_connected}
                6->{R.drawable.ic_disconnected}
                7->{R.drawable.shutdown}
                8->{R.drawable.time}
                else -> {R.drawable.wi_fi_new}
            }
            image.setImageResource(icon)

        }
    }

    object Callback : DiffUtil.ItemCallback<EventData>() {
        override fun areItemsTheSame(oldItem: EventData, newItem: EventData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EventData, newItem: EventData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            ItemSwitchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind()
    }
}