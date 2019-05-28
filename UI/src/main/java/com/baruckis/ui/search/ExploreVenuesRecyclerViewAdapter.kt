/*
 * Copyright 2019 Andrius Baruckis www.baruckis.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.baruckis.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.baruckis.ui.databinding.ListItemVenueBinding
import com.baruckis.ui.model.VenueUi
import com.baruckis.ui.utils.logConsoleVerbose
import javax.inject.Inject

class ExploreVenuesRecyclerViewAdapter @Inject constructor() :
        RecyclerView.Adapter<ExploreVenuesRecyclerViewAdapter.BindingViewHolder>() {

    private var dataList: List<VenueUi> = ArrayList()

    private var itemClickListener: VenueClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemVenueBinding.inflate(inflater, parent, false)

        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.count()

    fun setData(newDataList: List<VenueUi>) {
        logConsoleVerbose("setData: $newDataList")
        dataList = newDataList
        notifyDataSetChanged()
    }

    fun setItemClickListener(newListener: VenueClickListener) {
        itemClickListener = newListener
    }


    inner class BindingViewHolder(private var binding: ListItemVenueBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(venueUi: VenueUi) {

            binding.root.setOnClickListener {
                itemClickListener?.onVenueClicked(venueUi)
            }

            binding.venueUi = venueUi

            binding.executePendingBindings()
        }

    }

}