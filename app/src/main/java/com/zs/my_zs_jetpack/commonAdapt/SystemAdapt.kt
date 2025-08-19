package com.zs.my_zs_jetpack.commonAdapt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.api.SquareBaseBean
import com.zs.my_zs_jetpack.databinding.ItemSystemBinding

class SystemAdapter(val itemList: List<SquareBaseBean>) :
    RecyclerView.Adapter<SystemAdapter.SystemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SystemViewHolder {
        val binding = ItemSystemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SystemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SystemViewHolder,
        position: Int
    ) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    inner class SystemViewHolder(private val binding: ItemSystemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(systemBean: SquareBaseBean) {
            binding.tvTitle.text = systemBean.name
            binding.labelsView.setLabels(systemBean.children)
//            binding.labelsView.setBackgroundResource(R.drawable.ripple_tag_bg)
            binding.labelsView.apply {
                for (child in children){
                    child.setBackgroundResource(R.drawable.ripple_tag_bg)
                }
            }
        }

    }
//    override fun convert(helper: BaseViewHolder, item: SystemBean) {
//        item.let {
//            helper.setText(R.id.tvTitle, item.name)
//            helper.getView<LabelsView>(R.id.labelsView).apply {
//                setLabels(item.children) { _, _, data ->
//                    data.name
//                }
//                //不知为何，在xml中设置主题背景无效
//                for (child in children) {
//                    child.setBackgroundResource(R.drawable.ripple_tag_bg)
//                }
////                //标签的点击监听
////                setOnLabelClickListener { _, _, position ->
////                    tagClick(helper.adapterPosition, position)
////                }
//            }
//        }
//    }

}