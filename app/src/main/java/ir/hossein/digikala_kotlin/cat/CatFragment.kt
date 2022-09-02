package ir.hossein.digikala_kotlin.cat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.hossein.digikala_kotlin.R
import ir.hossein.digikala_kotlin.databinding.FragmentCatBinding
import ir.hossein.digikala_kotlin.utils.MyFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CatFragment : MyFragment() {
    private lateinit var binding: FragmentCatBinding
    private val allCatListViewModel: AllCatListViewModel by viewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allCatListViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            showProgressBar(it)
        }

        allCatListViewModel.allCatListItemLiveData.observe(viewLifecycleOwner) {
            for (item in it) {
                val recyclerView = RecyclerView(requireContext())
                val txtTitle = TextView(requireContext())
                txtTitle.text = item.cat
                txtTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

                val paddingDp = 8
                val topPadding = 4
                val density = requireContext().resources.displayMetrics.density
                val paddingPixel = (paddingDp * density).toInt()
                val topPaddingPixel = (topPadding * density).toInt()
                txtTitle.setPadding(0, topPaddingPixel, paddingPixel, topPaddingPixel)

                recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
                val allCatListAdapter: AllCatListAdapter by inject { parametersOf(item.catProducts) }
                recyclerView.adapter = allCatListAdapter
                binding.linearCatFragment.addView(txtTitle)
                binding.linearCatFragment.addView(recyclerView)
            }
        }
    }
}